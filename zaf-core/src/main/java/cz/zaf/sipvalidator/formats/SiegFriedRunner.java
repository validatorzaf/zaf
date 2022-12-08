package cz.zaf.sipvalidator.formats;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;

/**
 * Class to run detection in SiegFried
 * 
 *
 */
public class SiegFriedRunner implements Closeable {

    public static final String SIEGFRIED_PATH = "zaf.siegfried.path";
    public static final String SIEGFRIED_PORT = "zaf.siegfried.port";

    final static Logger log = LoggerFactory.getLogger(SiegFriedRunner.class);
    private Path siegFriedPath;
    // Default port is 20000
    private int siegFriedPort = 20000;
    private Process activeProcess;

    static private int parallelRunCounter = 0;

    public SiegFriedRunner() {
    }

    static boolean isConfigured() {
        try (SiegFriedRunner sfr = new SiegFriedRunner()) {
            sfr.loadConfiguration();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void loadConfiguration() {

        String siegFriedPathProp = System.getProperty(SIEGFRIED_PATH);
        if(StringUtils.isEmpty(siegFriedPathProp)) {
            throw new ZafException(BaseCode.CHYBA, "Není nastavena proměnná: " + SIEGFRIED_PATH);
        }
        siegFriedPath = Paths.get(siegFriedPathProp);
        String siegFriedPortProp = System.getProperty(SIEGFRIED_PORT);
        if (!StringUtils.isEmpty(siegFriedPortProp)) {
            try {
                siegFriedPort = Integer.parseInt(siegFriedPortProp);
            } catch (NumberFormatException nfe) {
                throw new ZafException(BaseCode.CHYBA, "Chybně nastavena proměnná: " + SIEGFRIED_PORT + ", hodnota: "
                        + siegFriedPortProp);
            }
        }
    }

    public static SiegFriedRunner create() {
        SiegFriedRunner sfr = new SiegFriedRunner();
        sfr.init();
        return sfr;
    }

    private void init() {
        loadConfiguration();

        if (!Files.isExecutable(siegFriedPath)) {
            throw new ZafException(BaseCode.CHYBA, "Nenalezen soubor: " + SIEGFRIED_PATH);
        }
        try {
            getProcess();
        } catch (IOException e) {
            throw new ZafException(BaseCode.CHYBA, "Nelze spustit SiegFried", null, e);
        }
    }

    private Process getProcess() throws IOException {
        if (activeProcess == null || !activeProcess.isAlive()) {
            List<String> params = new ArrayList<>(10);
            params.addAll(Arrays.asList(siegFriedPath.toString(),
                                        "-log",
                                        "error,warn,unknown,stdout",
                                        "--serve",
                                        "127.0.0.1:" + siegFriedPort,
                                        "-json"));
            /*if (serverMode) {
                params.add("--servermode");
            }*/

            // Run vera
            ProcessBuilder pb = new ProcessBuilder(params);
            pb.redirectErrorStream(true);
            activeProcess = pb.start();
        }
        return activeProcess;
    }

    synchronized public void destroyActiveProcess() {
        if (activeProcess != null) {
            activeProcess.destroy();
            activeProcess = null;
        }
        parallelRunCounter = 0;
    }

    @Override
    public synchronized void close() {
        destroyActiveProcess();
    }

    public String detect(Path file) throws IOException, URISyntaxException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI("http", null, "127.0.0.1", this.siegFriedPort,
                    "/identify/" + file, null, null);
            HttpGet httpGet = new HttpGet(uri);
            
            HttpClientResponseHandler<Object> respHandler = (response) -> {
                if(response.getCode()!=200) {
                    return null;
                }
                HttpEntity ent = response.getEntity();
                if (ent == null) {
                    return null;
                }
                try (InputStream inputStream = ent.getContent()) {
                    Yaml yaml = new Yaml();
                    Object result = yaml.load(inputStream);
                    if (result == null) {
                        return null;
                    }
                    if (result instanceof Map) {
                        Map<String, Object> map = (Map) result;
                        Object files = map.get("files");
                        return files;
                    }
                    return null;
                }
            };
            Object response = httpclient.execute(httpGet, respHandler);
            if (response == null) {
                return null;
            }
            if (response instanceof List) {
                List<Object> list = (List) response;
                if (list.size() != 1) {
                    return null;
                }
                Object fileData = list.get(0);
                if (fileData == null) {
                    return null;
                }
                if (fileData instanceof Map) {
                    Map<String, Object> fileDataMap = (Map) fileData;
                    if (fileDataMap != null) {
                        Object detectedObj = fileDataMap.get("matches");
                        if (detectedObj == null) {
                            return null;
                        }
                        if (detectedObj instanceof List) {
                            List<Object> detected = (List) detectedObj;
                            // List of detected objects
                            if (detected.size() != 1) {
                                return null;
                            }
                            Object detectionResultObj = detected.get(0);
                            if (detectionResultObj instanceof Map) {
                                Map<String, String> detectionResult = (Map) detectionResultObj;
                                return detectionResult.get("mime");
                            }
                        }
                    }
                }
            }
            return null;
        }

    }

}
