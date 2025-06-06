package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule07;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule08;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule09;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule10;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule11;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule12;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule13;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule14;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule15;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule16;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule17;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule18;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule19;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule20;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule21;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule22;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule23;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule24;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule25;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule26;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule27;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule28;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule29;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule30;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule31;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule32;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule33;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule34;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule35;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule36;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule37;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule38;
import cz.zaf.earkvalidator.layers.obs.obs30_39.Rule39;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class ObsTest extends AipValidatorTestBase {

	static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testObs_01_OK01() {
    	testObs("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
						Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
						Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE, Rule38.CODE, Rule39.CODE },
                new String[] {});
    }	
	
    @Test
    void testObs_01_OK02() {
    	testObs("OK2/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
						Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
						Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE, Rule38.CODE, Rule39.CODE },
                new String[] {});
    }	

    @Test
    void testObs_01_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule01.CODE });
    }	

    @Test
    void testObs_01_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule01.CODE });
    }	

	
    @Test
    void testObs_02_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/02-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule02.CODE });
    }	

    @Test
    void testObs_03_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_03_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_04_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule04.CODE });
    }	

    @Test
    void testObs_04_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule04.CODE });
    }


    @Test
    void testObs_05_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/05-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule05.CODE });
    }	

    @Test
    void testObs_05_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/05-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule05.CODE });
    }

    @Test
    void testObs_06_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/06-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE },
                new String[] { Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE });
    }	

    @Test
    void testObs_07_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/07-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testObs_08_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/08-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule09.CODE },
                new String[] { Rule08.CODE });
    }

    @Test
    void testObs_09_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/09-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testObs_09_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/09-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testObs_10_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testObs_10_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }


    @Test
    void testObs_10_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testObs_10_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testObs_10_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testObs_10_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/10-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testObs_11_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/11-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE },
                new String[] { Rule11.CODE });
    }

    @Test
    void testObs_12_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/12-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, Rule11.CODE },
                new String[] { Rule12.CODE });
    }

    @Test
    void testObs_13_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/13-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE  },
                new String[] { Rule13.CODE });
    }

    @Test
    void testObs_13_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/13-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE },
                new String[] { Rule13.CODE });
    }


    @Test
    void testObs_14_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/14-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE },
                new String[] { Rule13.CODE, Rule14.CODE });
    }

    @Test
    void testObs_14_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/14-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE },
                new String[] { Rule14.CODE });
    }

    @Test
    void testObs_15_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/15-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] { Rule15.CODE });
    }

    @Test
    void testObs_15_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/15-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] { Rule15.CODE });
    }

    // Test should fail but passes
    // Problem is with xlink:type="simple" which is autogenerated by JAXB
    /*
    @Test
    void testObs_15_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/15-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] { Rule15.CODE });
    }*/

    @Test
    void testObs_15_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/15-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] { Rule15.CODE, Rule17.CODE });
    }
    
    // CZDAX-PMT0310
    @Test
    void testObs_16_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }
    
    // CZDAX-PMT0311
    @Test
    void testObs_16_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }
    
    // CZDAX-PMT0312
    @Test
    void testObs_16_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }

    // CZDAX-PMT0314
    @Test
    void testObs_16_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }

    // CZDAX-PMT0315
    @Test
    void testObs_16_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }

    // CZDAX-PMT0313
    @Test
    void testObs_16_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/16-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE },
                new String[] { Rule16.CODE });
    }

    @Test
    void testObs_17_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/17-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE },
                new String[] { Rule17.CODE });
    }

    @Test
    void testObs_18_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/18-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE },
                new String[] { Rule18.CODE });
    }

    // pokryva take pravidlo 25
    @Test
    void testObs_18_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/18-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE },
                new String[] { Rule18.CODE, Rule25.CODE });
    }


    @Test
    void testObs_19_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/19-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE },
                new String[] { Rule19.CODE });
    }

    @Test
    void testObs_20_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/20-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE },
                new String[] { Rule20.CODE });
    }

    @Test
    void testObs_21_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/21-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE },
                new String[] { Rule21.CODE });
    }

    @Test
    void testObs_21_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/21-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE },
                new String[] { Rule21.CODE });
    }

    @Test
    void testObs_22_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/22-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE },
                new String[] { Rule22.CODE });
    }

    @Test
    void testObs_22_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/22-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE },
                new String[] { Rule22.CODE });
    }


    @Test
    void testObs_22_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/22-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE },
                new String[] { Rule22.CODE });
    }


    @Test
    void testObs_23_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/23-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE },
                new String[] { Rule23.CODE });
    }

    @Test
    void testObs_24_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule18.CODE, Rule24.CODE });
    }

    @Test
    void testObs_24_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule24.CODE });
    }

    @Test
    void testObs_24_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule24.CODE });
    }

    @Test
    void testObs_24_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule24.CODE });
    }

    @Test
    void testObs_24_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule24.CODE });
    }

    @Test
    void testObs_24_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/24-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE },
                new String[] { Rule24.CODE });
    }

    @Test
    void testObs_27_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/27-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE },
                new String[] { Rule27.CODE });
    }

    @Test
    void testObs_28_OK01() {
    	testObs("06-KONTROLA OBSAHU/28-OK01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE },
                new String[] { });
    }

    @Test
    void testObs_28_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/28-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE },
                new String[] { Rule28.CODE });
    }

    @Test
    void testObs_28_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/28-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE },
                new String[] { Rule28.CODE });
    }

    @Test
    void testObs_29_OK01() {
    	testObs("06-KONTROLA OBSAHU/29-OK01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE },
                new String[] { });
    }

    @Test
    void testObs_29_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/29-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE },
                new String[] { Rule29.CODE });
    }

    @Test
    void testObs_29_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/29-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE },
                new String[] { Rule29.CODE });
    }


    @Test
    void testObs_30_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/30-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE },
                new String[] { Rule30.CODE });
    }

    @Test
    void testObs_30_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/30-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE },
                new String[] { Rule30.CODE });
    }

    @Test
    void testObs_31_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/31-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE },
                new String[] { Rule31.CODE });
    }

    @Test
    void testObs_31_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/31-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE },
                new String[] { Rule31.CODE });
    }

    @Test
    void testObs_32_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/32-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE },
                new String[] { Rule32.CODE });
    }

    @Test
    void testObs_32_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/32-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE },
                new String[] { Rule32.CODE });
    }

    @Test
    void testObs_33_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA07() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA07/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA08() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA08/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }

    @Test
    void testObs_33_CHYBA09() {
    	testObs("06-KONTROLA OBSAHU/33-CHYBA09/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE },
                new String[] { Rule33.CODE });
    }
    
    @Test
    void testObs_34_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/34-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule31.CODE, Rule32.CODE, Rule33.CODE },
                new String[] { Rule30.CODE, Rule34.CODE });
    }

    @Test
    void testObs_34_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/34-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule31.CODE, Rule32.CODE, Rule33.CODE },
                new String[] { Rule30.CODE, Rule34.CODE });
    }

    @Test
    void testObs_34_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/34-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule31.CODE, Rule32.CODE, Rule33.CODE },
                new String[] { Rule30.CODE, Rule34.CODE });
    }

    @Test
    void testObs_34_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/34-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE },
                new String[] { Rule34.CODE });
    }


    @Test
    void testObs_34_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/34-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule31.CODE, Rule32.CODE, Rule33.CODE },
                new String[] { Rule30.CODE, Rule34.CODE });
    }

    @Test
    void testObs_35_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/35-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE,  },
                new String[] { Rule35.CODE });
    }


    @Test
    void testObs_35_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/35-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE,  },
                new String[] { Rule35.CODE });
    }


    @Test
    void testObs_35_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/35-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE,  },
                new String[] { Rule35.CODE });
    }


    @Test
    void testObs_36_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE },
                new String[] { Rule35.CODE, Rule36.CODE });
    }


    @Test
    void testObs_36_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE,  },
                new String[] { Rule35.CODE, Rule36.CODE });
    }


    @Test
    void testObs_36_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE },
                new String[] { Rule36.CODE });
    }


    @Test
    void testObs_36_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_36_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_36_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/36-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_37_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA06() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA06/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA07() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA07/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_37_CHYBA08() {
    	testObs("06-KONTROLA OBSAHU/37-CHYBA08/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE },
                new String[] { Rule37.CODE });
    }

    @Test
    void testObs_38_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/38-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule38.CODE });
    }

    @Test
    void testObs_38_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/38-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule38.CODE });
    }

    @Test
    void testObs_38_CHYBA03() {
    	testObs("06-KONTROLA OBSAHU/38-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule38.CODE });
    }

    @Test
    void testObs_38_CHYBA04() {
    	testObs("06-KONTROLA OBSAHU/38-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule38.CODE });
    }

    @Test
    void testObs_38_CHYBA05() {
    	testObs("06-KONTROLA OBSAHU/38-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule38.CODE });
    }
    
    @Test
    void testObs_39_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/39-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule10.CODE, 
                		Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule19.CODE,
                		Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule30.CODE, Rule31.CODE, Rule32.CODE, Rule33.CODE, Rule34.CODE, Rule35.CODE, Rule36.CODE, Rule37.CODE },
                new String[] { Rule39.CODE });
    }

    private void testObs(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.OBSAH,
				status, oks, fails);
	}
}
