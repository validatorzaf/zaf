package cz.zaf.earkvalidator.daaip2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule01;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule02;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.validator.TestHelper;

public class AipValidatorDatTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testDAT_01_OK01() {
    	testDat("OK1",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE },
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

    @Test
    void testDAT_02_OK01() {
    	testDat("01-KONTROLA DATA/02-OK1/8b58672e-7893-45c3-ab37-2b133389329d.zip",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] {});
    }
    

    @Test
    void testDAT_02_CHYBA01() {
    	testDat("01-KONTROLA DATA/02-CHYBA01.txt",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule02.CODE });
    }

    @Test
    void testDAT_02_CHYBA02() {
    	testDat("01-KONTROLA DATA/02-CHYBA02.zip",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule02.CODE });
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
