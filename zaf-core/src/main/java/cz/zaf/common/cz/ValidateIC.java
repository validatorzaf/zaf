package cz.zaf.common.cz;

/**
 * Czech company IC validation
 */
public class ValidateIC {

	// číslo o osmi číslicích, jejichž vážený součet je dělitelný jedenácti beze zbytku
	public static boolean validate(String ic) {
		if(ic.length()>8) {
			return false;
		}
		// first prepend 0
	    while(ic.length() < 8) {
	    	ic = '0'+ic;
	    }	    

	    // second convert to ints
	    int icNumbers[] = new int[8];
		for(int i=0; i<8; i++) {
			int cislice = Character.getNumericValue(ic.charAt(i));
			if(cislice<0) {
				return false;
			}
			if(cislice>9) {
				return false;
			}
			icNumbers[i] = cislice;
		}
		
		// count weighted sum
	    // https://phpfashion.com/jak-overit-platne-ic-a-rodne-cislo
		int vazenySoucetIco = 0;
	    for (int i = 0; i < 7; i++) {
	        vazenySoucetIco += (8-i) * icNumbers[i];
	    }
	    int zbytek = vazenySoucetIco % 11;
	    int ocekavanaHodnota = 11 - zbytek;
	    if(ocekavanaHodnota==10) {
	    	ocekavanaHodnota = 0;
	    } else 
	    if(ocekavanaHodnota==11) {
	    	ocekavanaHodnota = 1;
	    }
	    return ocekavanaHodnota==icNumbers[7];
	}
	
}
