package cz.zaf.sipvalidator.formats;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MimetypeDetectorFactory {

    private static Logger log = LoggerFactory.getLogger(MimetypeDetectorFactory.class);

    static MimetypeDetector mimetypeDetector;

    static MimetypeDetector getDetector() {
        if (mimetypeDetector == null) {
            initDetector();
        }
        return mimetypeDetector;
    }

    private static void initDetector() {
        if (SiegFriedRunner.isConfigured()) {
            SiegFriedDetector siegFriedDetector = new SiegFriedDetector();
            mimetypeDetector = siegFriedDetector;
            return;
        }
        // Fallback to default TikaDetector
        if (mimetypeDetector == null) {
            mimetypeDetector = new TikaDetector();
        }

    }

    public static MimeTypeResult getMimeType(Path filePath) {
        MimetypeDetector mimetypeDetector = getDetector();
        return mimetypeDetector.getMimeType(filePath);
    }

    public static void destroy() {
        if (mimetypeDetector != null) {
            try {
                mimetypeDetector.cleanUp();
            } catch (Exception e) {
                log.error("Filed to clean up MimetypeDetector", e);
            }
            mimetypeDetector = null;
        }

    }

}
