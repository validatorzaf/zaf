package cz.zaf.sipvalidator.formats;

import java.nio.file.Path;

public class SiegFriedDetector implements MimetypeDetector {

    private SiegFriedRunner siegFriedRunner;

    public SiegFriedDetector() {
        this.siegFriedRunner = SiegFriedRunner.create();
    }

    @Override
    public MimeTypeResult getMimeType(Path file) {
        try {
            String mimetype = siegFriedRunner.detect(file);
            return new MimeTypeResult(mimetype);
        } catch (Exception e) {
            return new MimeTypeResult(e);
        }
    }

    @Override
    public void cleanUp() {
        if (siegFriedRunner != null) {
            siegFriedRunner.close();
            siegFriedRunner = null;
        }

    }

}
