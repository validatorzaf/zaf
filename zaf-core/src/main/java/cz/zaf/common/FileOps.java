package cz.zaf.common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class FileOps {
	
	static private final Logger log = LoggerFactory.getLogger(FileOps.class);
    
    static public void recursiveDelete(Path pathForDelete) throws IOException {
        if (Files.isRegularFile(pathForDelete)) {
            Files.delete(pathForDelete);
        } else {
            if (Files.isDirectory(pathForDelete)) {
                // recursive dir delete
                Files.walkFileTree(pathForDelete, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(
                      Path dir, IOException exc) throws IOException {
                        delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult visitFile(
                      Path file, BasicFileAttributes attrs) 
                      throws IOException {
                        delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }        
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("win"));
    }

    /**
     * Delete file or folder
     * 
     * Method has special check to delete ReadOnly files on Windows
     * 
     * @param path
     * @throws IOException
     */
    static public void delete(Path path) throws IOException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            boolean retry = false;
            if (isWindows()) {
                // try to reset readolny attribute
                Object val = Files.getAttribute(path, "dos:readonly");
                if(val!=null && val instanceof Boolean) {
                    Boolean readOnly = (Boolean)val;
                    if (readOnly) {
                        Files.setAttribute(path, "dos:readonly", false);
                        retry = true;
                    }
                    
                }
            }
            if (retry) {
                Files.delete(path);
                return;
            }
            // rethrow orig exception
            throw e;
        }

    }
    
    static public boolean unzip(final Path zipPath, 
    		final Path pathForUnzip) {
    	return unzip(zipPath, pathForUnzip, null);
    }

    /**
     * Check if ZIP file is valid and extract it
     * @param zipPath
     * @param pathForUnzip
     * @param zipChecker
     * @return
     */
    static public boolean unzip(final Path zipPath, 
    		final Path pathForUnzip, 
    		final Function<ZipFile, Boolean> zipChecker) {
		Validate.notNull(zipPath, "zipPath cannot be null");
		Validate.notNull(pathForUnzip, "pathForUnzip cannot be null");
		
        String zipEncoding = System.getProperty(Properties.ZAF_ZIP_ENCODING);
        if (zipEncoding == null) {
            // Dříve bylo výchozí kódování
            // pro ČR: Charset.forName("IBM852"));
            zipEncoding = "UTF8";
        }
        
        try(ZipFile zipFile = new ZipFile(zipPath.toFile())) {
            zipFile.setCharset(Charset.forName(zipEncoding)); // extrakce českých znaků
            boolean isvalidZipFile = zipFile.isValidZipFile();
            if (!isvalidZipFile) {
             	return false;
            }
        	
            if(zipChecker!=null) {
            	Boolean checkResult = zipChecker.apply(zipFile);
            	if(checkResult==null || !checkResult) {
    				return false;
    			}
            }
            // unzip file
        	zipFile.extractAll(pathForUnzip.toString());
        } catch (ZipException e) {
        	log.error("Failed to extract zip file", e);
			return false;
		} catch (IOException e) {
        	log.error("Failed to extract zip file", e);
			return false;
		}
		
		return true;
    }
    
    /**
     * Check whether ZIP file contains only one directory with given name
     * 
     * Directory can contain other directories or files. 
     * @param zipFile
     * @param expectedFileName
     * @return
     */
    public static boolean containsSingleDirectory(ZipFile zipFile, String expectedDirectoryName) {
        log.debug("Checking zip content, expected main directory: {}", expectedDirectoryName);
        try {
            List<FileHeader> list = zipFile.getFileHeaders();
            if (CollectionUtils.isEmpty(list)) {
                log.info("Empty ZIP file");
                return false;
            }
            for (FileHeader fh: list) {
                String s = fh.getFileName();
                if (!(s.startsWith(expectedDirectoryName + "\\") || 
                		s.startsWith(expectedDirectoryName + "/"))) {
                    log.debug("Found unexpected content in zip: {}", s);
                    return false;
                }
            }
        } catch (ZipException ex) {
            log.error("Exception in ZIP library", ex);
            return false;
        }

        return true;
    }

    /** 
     * Format file size to user friendly format (KB, MB, GB, TB)
     * @param size
     * @return
     */
	public static String formatSize(long size) {
		if (size < 1024) {
			return size + " B";
		}
		else if (size < 1024 * 1024) {
			return (size / 1024) + " KB";
		}
		else if (size < 1024 * 1024 * 1024) {
			return (size / 1024 / 1024) + " MB";
		}
		else if (size < 1024 * 1024 * 1024 * 1024) {
			return (size / 1024 / 1024 / 1024) + " GB";
		}
		return Long.toString(size / 1024 / 1024 / 1024 / 1024) + " TB";
	}

	/**
	 * Parse size from string format (B KB, MB, GB, TB)
	 * 
	 * If size is not parsable, returns 0
	 *
	 * @param maxSizeStr
	 * @return
	 */
	public static long parseSize(String maxSizeStr) {
		if (StringUtils.isEmpty(maxSizeStr)) {
			return 0;
		}
		try {
			long value = Long.parseLong(maxSizeStr.replaceAll("[^0-9]", ""));
			
			// parse units in suffix
			String units = maxSizeStr.replaceAll("[0-9]", "");
			if ("B".equals(units)||StringUtils.isBlank(units)) {
				return value;
			}
			if ("KB".equals(units)) {
				return value * 1024;
			}
			if ("MB".equals(units)) {
				return value * 1024 * 1024;
			}
			if ("GB".equals(units)) {
				return value * 1024 * 1024 * 1024;
			}
			if ("TB".equals(units)) {
				return value * 1024 * 1024 * 1024 * 1024;
			}
		} catch (NumberFormatException e) {
			log.error("Failed to parse size: {}", maxSizeStr, e);
		}
		
		return 0;
	}
}
