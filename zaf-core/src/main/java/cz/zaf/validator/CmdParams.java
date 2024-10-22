package cz.zaf.validator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.validator.profiles.ValidationProfile;

/**
 * Třída pro načtení vstupních parametrů
 * 
 *
 */
public class CmdParams {

    protected static void printUsage() {
        PrintStream output = System.out;
        output.println("CmdValidator [přepínače] [<path>]");
        output.println("");
        output.println("path - cesta k SIPu/AIPu/XML, v případě dávkového režimu ke složce daty");
        output.println("");
        output.println("Přepínače:");
        output.println(" -b|--batch Dávkový režim, cesta je adresář obsahující SIPy");
        output.println(" -w|--workdir= Umístění pracovního adresáře, zde budou SIPy rozbaleny");
        output.println(" -k|--keep Zachování rozbalených souborů na disku");
        output.println(" -d|--druh= Druh kontroly pro danou validaci (AUTO - výchozí), stačí uvést druh:");
        output.println("        AUTO = výběr základní kontroly pro daný typ kontroly");
        output.println("        1 = pro provedení skartačního řízení (jen metadata bez přiložených komponent)");
        output.println("        2 = pro provedení skartačního řízení (s přiloženými komponentami)");
        output.println("        3 = pro předávání dokumentů a jejich metadat do archivu");
        output.println("        AD = archivní popis (pro AP2023)");
        output.println("        FA = archivní pomůcka (pro AP2023)");
        output.println("        AIP = výměnný AIP (pro DAAIP2024)");
        output.println("        DIP_METADATA = metadatový DIP (pro DAAIP2024)");
        output.println("        DIP_CONTENT = úplný DIP (pro DAAIP2024)");
        output.println(" -e|--exclude= Seznam kontrol oddělených čárkou, které se nemají provádět");
        output.println(" -i|--id= Identifikátor prováděné kontroly");
        output.println(" -z|--hrozba= Podrobnosti v případě nalezení hrozby (pro předání z antivirového programu)");
        output.println(" -o|--output= Jméno souboru nebo adresáře pro uložení výsledků");
        output.println(" -p|--ports= Rozsah portů pro vnitřní procesy (standardně 10000-32000)");
        output.println(" -t|--type= Typ validace (AUTO - výchozí)");
        output.println(" 		AUTO - automatická detekce formátu vstupu");
        output.println(" 		NSESSS2017");
        output.println("        NSESSS2023");
        output.println(" 		AP2023");
        output.println(" 		DAAIP2024");
        output.println(" -f|--format= Výstupní formát (1 - výchozí)");
        output.println(" 		1 = obecné schéma (validace_v1.xsd)");
        output.println(" 		2 = schéma pouze pro kontrolu NSESSS");
    }

    String inputPath = System.getProperty("user.dir");

    String workDir = null;

    /**
     * Priznak davkoveho rezimu
     * 
     * V davkovem rezimu se kontroluje sada SIPu
     */
    boolean davkovyRezim = false;

    /**
     * Priznak testu pameti
     * 
     * V pripade testu pameti dojde k opakovani testu
     */
    boolean memTest = false;

    /**
     * Aktivni profil validace
     */
    ProfilValidace nsesssProfile = ZakladniProfilValidace.SKARTACE_METADATA;

    /**
     * Profile pro AP2023
     */
    AP2023Profile ap2023Profile = AP2023Profile.ARCH_DESC;
    
    /**
     * Profile pro DAAIP2024
     */
    DAAIP2024Profile da2024Profile = DAAIP2024Profile.AIP;

    public AP2023Profile getAp2023Profile() {
        return ap2023Profile;
    }

    /**
     * Výstupní formát
     */
    VystupniFormat vystupniFormat = VystupniFormat.VALIDACE_V1;
    
    /**
     * Typ balíčku
     */
    ValidationProfile typBalicku = null;
    
    /**
     * Popis hrozby
     */
    private String hrozba;

    /**
     * Cesta k vystupu
     */
    private String output;

    /**
     * Pozice pri cteni vstupnich parametru
     */
    int pos = 0;

    /**
     * Identifikátor prováděné kontroly
     */
    private String idKontroly;

    /**
     * Příznak pro zachování rozbalených SIPů
     */
    boolean keepFiles = false;

    /**
     * Seznam kontrol, ktere se nemaji provadet
     */
    final private List<String> excludeChecks = new ArrayList<>();

    public String getIdKontroly() {
        return idKontroly;
    }

    String args[];

    public String getInputPath() {
        return inputPath;
    }

    public String getWorkDir() {
        return workDir;
    }

    public boolean isDavkovyRezim() {
        return davkovyRezim;
    }

    public boolean isMemTest() {
        return memTest;
    }

    public void setMemTest(boolean memTest) {
        this.memTest = memTest;
    }

    public boolean isKeepFiles() {
        return keepFiles;
    }

    public void setKeepFiles(boolean keepFiles) {
        this.keepFiles = keepFiles;
    }

    public ProfilValidace getProfilValidace() {
        return nsesssProfile;
    }

    public VystupniFormat getVystupniFormat() {
		return vystupniFormat;
	}

	public List<String> getExcludeChecks() {
        return excludeChecks;
    }

    public String getHrozba() {
        return hrozba;
    }

    public String getOutput() {
        return output;
    }

    /**
     * Nacte vstupni parametry
     * 
     * @param args
     *            pole argumentů
     * @return true při úspěšném načtení, false při chybě
     */
    public boolean read(String args[]) {
        this.pos = 0;
        this.args = args;

        while (pos < args.length) {
            String arg = args[pos];
            if (arg.equals("-b") || arg.equals("--batch")) {
                davkovyRezim = true;
            } else if (arg.equals("-k") || arg.equals("--keep")) {
                keepFiles = true;
            } else if (arg.equals("-w")) {
                if (!readW()) {
                    return false;
                }
            } else if (arg.startsWith("--workdir=")) {
                if (!readWorkDir(arg.substring(10))) {
                    return false;
                }
            } else if (arg.equals("-d")) {
                if (!readD()) {
                    return false;
                }
            } else if (arg.startsWith("--druh=")) {
                if (!readDruh(arg.substring("--druh=".length()))) {
                    return false;
                }
            } else if (arg.equals("-i")) {
                if (!readIdKontroly()) {
                    return false;
                }
            } else if (arg.startsWith("--id=")) {
                if (!readIdKontroly(arg.substring(5))) {
                    return false;
                }
            } else if (arg.equals("-z")) {
                if (!readZ()) {
                    return false;
                }
            } else if (arg.startsWith("--hrozba=")) {
                if (!readHrozba(arg.substring(9))) {
                    return false;
                }
            } else if (arg.equals("-o")) {
                if (!readO()) {
                    return false;
                }
            } else if (arg.startsWith("--output=")) {
                if (!readOutput(arg.substring(9))) {
                    return false;
                }
            } else if (arg.equals("-e")) {
                if (!readE()) {
                    return false;
                }
            } else if (arg.startsWith("--exclude=")) {
                if (!readExclude(arg.substring(10))) {
                    return false;
                }
            } else if (arg.equals("-t")) {
                if (!readT()) {
                    return false;
                }
            } else if (arg.startsWith("--type=")) {
                if (!readTyp(arg.substring("--type=".length()))) {
                    return false;
                }
            } else if (arg.equals("-f")) {
                if (!readF()) {
                    return false;
                }
            } else if (arg.startsWith("--format=")) {
                if (!readOutputFormat(arg.substring("--format=".length()))) {
                    return false;
                }
            } else if (arg.equals("--memTest")) {
                this.memTest = true;
            } else {
                inputPath = arg;
            }
            pos++;
        }
        return true;
    }

    private boolean readIdKontroly(String arg) {
        if (arg.length() == 0) {
            System.out.println("Missing id detail");
            return false;
        }
        this.idKontroly = arg;
        return true;
    }

    private boolean readIdKontroly() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing id");
            return false;
        }
        String arg = args[pos];
        return readIdKontroly(arg);
    }

    private boolean readOutput(String arg) {
        if (arg.length() == 0) {
            System.out.println("Missing output detail");
            return false;
        }
        this.output = arg;
        return true;
    }

    private boolean readO() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing output");
            return false;
        }
        String arg = args[pos];
        return readOutput(arg);
    }

    private boolean readExclude(String arg) {
        if (arg.length() == 0) {
            System.out.println("Missing excluded rules");
            return false;
        }
        String[] excluded = arg.split(",");
        for (String e : excluded) {
            e = e.trim();
            if (e.length() > 0) {
                excludeChecks.add(e);
            }
        }
        return true;
    }

    private boolean readE() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing excluded rules");
            return false;
        }
        String arg = args[pos];
        return readExclude(arg);
    }

    private boolean readZ() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing hrozba");
            return false;
        }
        String arg = args[pos];
        return readHrozba(arg);
    }

    private boolean readHrozba(String arg) {
        if (arg.length() == 0) {
            System.out.println("Missing hrozba detail");
            return false;
        }
        this.hrozba = arg;
        return true;
    }

    private boolean readD() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing druh validace");
            return false;
        }
        String arg = args[pos];
        return readDruh(arg);
    }

    private boolean readDruh(String arg) {
        try {
            switch (arg) {
            case "AUTO":
                return true;
            case "FA":
                ap2023Profile = AP2023Profile.FINDING_AID;
                return true;
            case "AD":
                ap2023Profile = AP2023Profile.ARCH_DESC;
                return true;
            case "AIP":
            	da2024Profile = DAAIP2024Profile.AIP;
            	return true;
            case "DIP_METADATA":
            	da2024Profile = DAAIP2024Profile.DIP_METADATA;
            	return true;
            case "DIP_CONTENT":
            	da2024Profile = DAAIP2024Profile.DIP_CONTENT;
            	return true;
            }

            int druh = Integer.parseInt(arg);
            switch (druh) {
            case 0:
                nsesssProfile = ZakladniProfilValidace.DEVEL;
                break;                
            case 1:
                nsesssProfile = ZakladniProfilValidace.SKARTACE_METADATA;
                break;
            case 2:
                nsesssProfile = ZakladniProfilValidace.SKARTACE_UPLNY;
                break;
            case 3:
                nsesssProfile = ZakladniProfilValidace.PREJIMKA;
                break;
            default:
                System.out.println("Chybný druh validace: " + arg);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Není číslo: " + arg);
            return false;
        }
        return true;
    }

    private boolean readWorkDir(String substring) {
        if (substring.length() == 0) {
            System.out.println("Missing work directory");
            return false;
        }
        workDir = substring;
        return true;
    }

    private boolean readW() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing work directory");
            return false;
        }
        workDir = args[pos];
        return true;
    }
    
    private boolean readT() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing typ balíčku");
            return false;
        }
        String arg = args[pos];
        return readTyp(arg);
    }

    private boolean readTyp(String arg) {
        try {
            switch (arg) {
            case "AUTO":
                typBalicku = null;
                break;                
            case "NSESSS2017":
            	typBalicku = ValidationProfile.NSESSS2017;
                break;
            case "NSESSS2023":
            	typBalicku = ValidationProfile.NSESSS2023;
                break;                
            case "AP2023":
            	typBalicku = ValidationProfile.AP2023;
                break;                
            case "DAAIP2024":
            	typBalicku = ValidationProfile.DAAIP2024;
                break;                
            default:
                System.out.println("Chybný typ balíčku: " + arg);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Není číslo: " + arg);
            return false;
        }
        return true;
    }
    
    private boolean readF() {
        pos++;
        if (pos == args.length) {
            System.out.println("Missing typ vystupniho formatu");
            return false;
        }
        String arg = args[pos];
        return readOutputFormat(arg);
    }

    private boolean readOutputFormat(String arg) {
        try {
            int outputFormat = Integer.parseInt(arg);
            switch (outputFormat) {
            case 1:
                vystupniFormat = VystupniFormat.VALIDACE_V1;
                break;                
            case 2:
            	vystupniFormat = VystupniFormat.VALIDACE_SIP;
                break;
            default:
                System.out.println("Chybný typ výstupního formátu: " + arg);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Není číslo: " + arg);
            return false;
        }
        return true;
    }

	public DAAIP2024Profile getDa2024Profile() {
		return da2024Profile;
	}
}
