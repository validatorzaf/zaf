package cz.zaf.earkvalidator.daaip2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule01;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.validator.TestHelper;

public class AipValidatorDatTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testDAT_01_OK01() {
    	testDat("OK1",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    @Test
    void testDAT_01_CHYBA01() throws IOException {
    	// create empty test directory
    	Path testDirPath = TestHelper.getPath(PATH_TESTDATA).resolve("01-KONTROLA DATA/CHYBA01");
    	if(!Files.isDirectory(testDirPath)) {
    		Files.createDirectories(testDirPath);
    	}
    	testDat("01-KONTROLA DATA/CHYBA01",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    private void testDat(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.DATA,
				status, oks, fails);
	}
}
