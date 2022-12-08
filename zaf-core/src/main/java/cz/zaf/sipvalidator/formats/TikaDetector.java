package cz.zaf.sipvalidator.formats;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.tika.Tika;

public class TikaDetector implements MimetypeDetector {

    static private Tika tikaInstance = new Tika();

    public TikaDetector() {
    }

    @Override
    public MimeTypeResult getMimeType(Path file) {
        try {
            String tika = tikaInstance.detect(file);
            return new MimeTypeResult(tika);
        } catch (IOException ex) {
            return new MimeTypeResult(ex);
        }
    }

    @Override
    public void cleanUp() {
        // nop
    }

}
