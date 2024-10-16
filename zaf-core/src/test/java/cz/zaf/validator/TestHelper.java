package cz.zaf.validator;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationStatus;

public abstract class TestHelper {

    private static final Logger log = LoggerFactory.getLogger(TestHelper.class);

    static {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
        //root.setLevel(Level.ALL); - set for debug
        root.setLevel(Level.INFO);
        for (Handler handler : root.getHandlers()) {
            // handler.setLevel(Level.ALL); - set for debug
        	handler.setLevel(Level.INFO);
        }
    }

    public static Path getPath(String relativePath) throws UnsupportedEncodingException {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        URL url = classLoader.getResource(relativePath);
        if (url == null) {
            fail("Failed to locate resource, path: " + relativePath);
        }

        String filePath = java.net.URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
        return new File(filePath).toPath();
    }

    static public void checkTestResult(String path,
            ValidationStatus stavKontroly,
            ValidationLayerResult result,
            String[] pravidlaOk, String[] pravidlaChybna) {
    	checkTestResult(path, stavKontroly, result, pravidlaOk, pravidlaChybna, null);
    }

    static public void checkTestResult(String path,
                                       ValidationStatus stavKontroly,
                                       ValidationLayerResult result,
                                       String[] pravidlaOk, String[] pravidlaChybna,
                                       String innerFileName) {
        if (result == null) {
            fail("Result is null, path: " + path);
        }
        
        // check if relevant result accoring innerFileName
        if(innerFileName != null) {
	        String resultInnerFileName = result.getInnerFileName();
	        if(!innerFileName.equals(resultInnerFileName)) {
		        return;
	        }
        }

        if (stavKontroly != null) {
            if (!result.getValidationStatus().equals(stavKontroly)) {
                // detail selhanych kontrol
                if (result.getValidationStatus() == ValidationStatus.ERROR) {
                    // doslo k neocekavanemu selhani -> vypis selhanych
                    boolean errorLogged = false;
                    for (String pravidloOk : pravidlaOk) {
                        RuleValidationError prav = result.getPravidlo(pravidloOk);
                        if (prav != null) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}", pravidloOk,
                                      prav.getVypisChyby(), prav.getMistoChyby(), prav.getMistoChyby());
                            errorLogged = true;
                        }
                    }
                    if(!errorLogged) {
                        // vypis vsech chybujicich kontrol
                        for (RuleValidationError prav : result.getPravidla()) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}",
                                      prav.getId(),
                                      prav.getVypisChyby(), prav.getMistoChyby(), prav.getMistoChyby());
                            
                        }
                    }
                }
    
                fail(() -> {
                	StringBuilder sb = new StringBuilder();
                	sb.append("Vstup: ").append(path).append(", Očekávaný stav: ").append(stavKontroly)
                	.append(", výsledný stav: ").append(result.getValidationStatus());
                	if(stavKontroly==ValidationStatus.OK) {
                		// was expected OK, have to write failed states
                        for (String pravidloOk : pravidlaOk) {
                            RuleValidationError prav = result.getPravidlo(pravidloOk);
							if (prav != null) {
								sb.append("\nChybné pravidlo: ").append(prav.getId());
								if(prav.getVypisChyby()!=null) {
									sb.append(", vypisChyby: ").append(prav.getVypisChyby());
								}
								if(prav.getMistoChyby()!=null) {
									sb.append(", mistoChyby: ").append(prav.getMistoChyby());
								}							
							}
                        }
                	}
                    return sb.toString();
                    });
            }
        }
    
        // overeni pravidel OK
        if (pravidlaOk != null) {
            for (int i = 0; i < pravidlaOk.length; i++) {
                String kodPravidla = pravidlaOk[i];
                RuleValidationError pravidlo = result.getPravidlo(kodPravidla);
                if (pravidlo != null) {
                    fail(() -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                        + ", ocekavano OK, ale selhalo, misto chyby: " + pravidlo.getMistoChyby()
                        + ", popis chyby: " + pravidlo.getVypisChyby());
                }
            }
        }
    
        // overeni pravidel s chybou
        if (pravidlaChybna != null) {
            for (int i = 0; i < pravidlaChybna.length; i++) {
                String kodPravidla = pravidlaChybna[i];
                RuleValidationError pravidlo = result.getPravidlo(kodPravidla);
                if (pravidlo == null) {
                    fail(() -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                            + ", ocekavana Chyba, ale bylo OK");
                }
            }
        }
    }

}
