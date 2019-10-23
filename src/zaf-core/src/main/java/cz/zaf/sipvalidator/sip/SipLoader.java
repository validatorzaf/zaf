package cz.zaf.sipvalidator.sip;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

import cz.zaf.sipvalidator.helper.HelperTime;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class SipLoader
	implements AutoCloseable
{
	LoadType loadType = LoadType.LT_UNKNOWN;
	SipInfo sip;
	Path sipPath;
	
	/**
	 * Short name of SIP
	 */
	String sipName;
	
	/**
	 * Jméno ZIP souboru se SIPem
	 */
    String sipZipFileName;

    /**
     * Cesta ke zdrojovemu ZIPu
     */
    private Path sipZipPath;

    /**
     * Pracovni adresar pro rozbaleni
     */
    String workDir;

    /**
     * Adresar, kde je rozbalen ZIP
     * 
     * SIPLoader by mel po sobe adresar vymazat
     */
    private Path pathForUnzip;

    public SipLoader(final String inputPath, final String workDir) {
        this.workDir = workDir;
		loadSip(inputPath);
	}

    // TODO: Nutno prepracovat
    public static long getSipLenght(Path sipPath) {
        long g = 0;
        try {
            if (Files.exists(sipPath)) {
                if (Files.isDirectory(sipPath)) {
                    g = FileUtils.sizeOfDirectory(sipPath.toFile());
                } else {
                    g = Files.size(sipPath);
                }
            } else {
                g = -20;
            }
            return g;
        } catch (IllegalArgumentException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
    }

	private void loadSip(String inputPath) {		
		detectLoadType(inputPath);
        // osetreni unzip
        if (loadType == LoadType.LT_ZIP) {
            unzipSip();
        }
        long l = getSipLenght(sipPath);
        sip = new SipInfo(sipName, sipZipFileName, loadType, l, sipPath);
		//sipName, sipZilFileName, loadType, lenght, cesta)
	}

    private void unzipSip() {
        Path workdirPath;
        if (workDir == null) {
            // throw new RuntimeException("Need to unzip file but workdir was not specified");
            workdirPath = Paths.get("rozbaleno");
        } else {
            workdirPath = Paths.get(workDir);
        }
        // get actual time and create temp directory
        String ads = HelperTime.getActualDateString();
        Path pathForUnzip = workdirPath.resolve(ads);
        int counter=0;
        for(counter=0; counter<1000; counter++ ) {
            if(!Files.exists(pathForUnzip)) {
                break;
            }
            pathForUnzip = workdirPath.resolve(ads+counter);
        }        
        if(counter==1000) {
            throw new RuntimeException("Failed to prepare working dir: "+workdirPath.normalize().toString());
        }
        
        try {
            Files.createDirectories(pathForUnzip);
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare working dir: " + pathForUnzip.normalize().toString());
        }
        this.pathForUnzip = pathForUnzip;

        Path newSipPath = unzipSip(sipZipPath, pathForUnzip);
        if (newSipPath != null) {
            // SIP byl rozbalen
            sipPath = newSipPath;
            // aktualizace jmena (bez .zip)
            sipName = sipPath.getName(sipPath.getNameCount() - 1).toString();
        }
        
    }

    /**
     * Rozbali SIP a vraci cestu do adresare s rozbalenym
     * 
     * @param zip
     * @param path_for_unzip
     * @return
     */
    private static Path unzipSip(Path zipPath, Path pathForUnzip) {
        ZipFile zipFile = new ZipFile(zipPath.toFile());
        zipFile.setCharset(Charset.forName("IBM852")); // extrakce českých znaků
        boolean isvalidZipFile = zipFile.isValidZipFile();
        if (!isvalidZipFile) {
            // invalid ZIP file
            return null;
        }
        // ocekavane jmeno je bez koncovky .ZIP
        String ocekavanejmeno = zipFile.getFile().getName();
        ocekavanejmeno = ocekavanejmeno.substring(0, ocekavanejmeno.length() - 4);

        boolean spravnaStrukturaZipu = obsahujePraveAdresar(zipFile, ocekavanejmeno);
        if (!spravnaStrukturaZipu) {
            return null;
        }
        try {
            zipFile.setRunInThread(false);
            zipFile.extractAll(pathForUnzip.toString());
            Path rozbaleny = pathForUnzip.resolve(ocekavanejmeno);
            //?? smysl
            /*while(zipFile.getProgressMonitor().getPercentDone() == 100){
                return rozbaleny;
            }*/
            return rozbaleny;
        } catch (ZipException ex) {
            return null;
        }
    }

    /**
     * Kontrola zda obsahuje jen jeden adresar
     * 
     * @param zipFile
     * @return
     */
    private static boolean obsahujePraveAdresar(ZipFile zipFile, String ocekavanejmeno) {
        try {
            ArrayList<FileHeader> list = (ArrayList<FileHeader>) zipFile.getFileHeaders();
            for (int i = 0; i < list.size(); i++) {
                FileHeader fh = list.get(i);
                String s = fh.getFileName();
                if (!(s.contains(ocekavanejmeno + File.separator) || s.contains(ocekavanejmeno + "/"))) {
                    return false;
                }
            }
        } catch (ZipException ex) {
            return false;
        }

        return true;
    }

    private void detectLoadType(String inputPath) {
		sipPath = Paths.get(inputPath);
        sipName = sipPath.getName(sipPath.getNameCount() - 1).toString();
        if (Files.isDirectory(sipPath)) {
			loadType = LoadType.LT_DIR;
		} else {
			String lowerCasePath = inputPath.toLowerCase();
			if(lowerCasePath.endsWith(".zip")) {
				loadType = LoadType.LT_ZIP;
                sipZipFileName = sipPath.getName(sipPath.getNameCount() - 1).toString();
                // zip musi byt nejprve odzipovan
                sipZipPath = sipPath;
                sipPath = null;
			} else
			if(lowerCasePath.endsWith(".xml")) {
				loadType = LoadType.LT_XML;
			}
		}
	}

	@Override
	public void close() throws Exception {
        if (pathForUnzip != null) {
            Files.walk(pathForUnzip)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    //.peek(System.out::println)
                    .forEach(File::delete);
        }
        pathForUnzip = null;
	}

	public SipInfo getSip() {
		// can be called only on loaded state
		Validate.notNull(sip);
		return sip;
	}

}
