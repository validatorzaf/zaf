package cz.zaf.sipvalidator.nsesss2017.profily;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06KontrolaContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo4;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo9;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo100;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo101;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo102;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo103;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo104;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo105;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo106;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo107;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo108;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo10;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo11;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo12;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo13;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo14;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo15;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo16;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo17;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo18;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo19;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo20;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo22;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo23;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo24;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo25;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo26;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo27;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo28;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo29;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo30;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo31;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo33;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo34;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo35;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo36;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo37;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo38;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo39;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo40;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo41;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo43a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo44;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo46;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo49;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo50;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo51;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo52;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo53;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo55;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo56;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo57;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo58;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo59;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo60;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo62;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo63;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo64;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo65;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo67;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo68;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo69;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo70;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo71;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo72;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo73;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo74;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo75;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo76;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo77;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo78;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo79;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo80;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo81;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo82;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo83;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo84;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo85;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo86;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo87;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo88;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo92;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo94;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo96;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo97;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo98;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_01;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_02;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_03;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_04;

public enum ZakladniProfilValidace 
	implements ProfilValidace {
	
	// Specialni seznam pro vyvoj a overovani implementace
	DEVEL("vývojářská validace") {
		@Override
        public Rule<K06KontrolaContext>[] createObsahovaPravidla() {
            Rule prav[] = {
					new Pravidlo1(),
					new Pravidlo2(), 
					new Pravidlo3(),
					new Pravidlo4(),
					new Pravidlo9(),
					new Pravidlo10(),
					new Pravidlo11(),
					new Pravidlo12(),
					new Pravidlo13(),
					new Pravidlo14(),
					new Pravidlo15(), 
					new Pravidlo16(),
					new Pravidlo17(), 
					new Pravidlo18(),
					new Pravidlo19(),
					new Pravidlo20(),
					new Pravidlo22(),
					new Pravidlo23(),
					new Pravidlo24(),
					new Pravidlo25(),
					new Pravidlo26(),
					new Pravidlo27(),
					new Pravidlo28(),
					new Pravidlo29(),
					new Pravidlo30(), 
					new Pravidlo31(),
					new Pravidlo33(),
					new Pravidlo34(), 
					new Pravidlo35(), 
					new Pravidlo36(),
					new Pravidlo37(),
					new Pravidlo38(),
					new Pravidlo39(), 
                    new Pravidlo40(),
                    new Pravidlo43a(),
                    new Pravidlo44(),
                    new Pravidlo46(),
					new Pravidlo49(), 
					new Pravidlo50(), 
					new Pravidlo51(), 
					new Pravidlo52(), 
					new Pravidlo53(), 
                    new Pravidlo54(),
					new Pravidlo55(),
					new Pravidlo56(), 
					new Pravidlo57(), 
					new Pravidlo58(), 
					new Pravidlo59(), 
					new Pravidlo60(), 
					new Pravidlo61(), 
					new Pravidlo61a(), 
					new Pravidlo62(), 
					new Pravidlo63(), 
					new Pravidlo64(), 
                    new Pravidlo65(),
					new Pravidlo67(), 
					new Pravidlo68(), 
					new Pravidlo69(), 
					new Pravidlo70(), 
					new Pravidlo71(), 
					new Pravidlo72(), 
					new Pravidlo73(), 
					new Pravidlo74(), 
					new Pravidlo75(), 
					new Pravidlo76(), 
					new Pravidlo77(), 
					new Pravidlo78(), 
					new Pravidlo79(), 
					new Pravidlo80(), 
					new Pravidlo81(),
					new Pravidlo82(), 
					new Pravidlo83(), 
					new Pravidlo84(), 
					new Pravidlo85(), 
					new Pravidlo86(), 
					new Pravidlo87(), 
					new Pravidlo88(), 
					new Pravidlo92(), 
					new Pravidlo93(), 
					new Pravidlo93a(), 
					new Pravidlo94(), 
					new Pravidlo95(), 
					new Pravidlo96(), 
					new Pravidlo97(), 
                    new Pravidlo98(),
                    new Pravidlo100(),
                    new Pravidlo101(),
                    new Pravidlo102(),
                    new Pravidlo103(),
                    new Pravidlo104(),
                    new Pravidlo105(),
                    new Pravidlo106(),
                    new Pravidlo107(),
                    new Pravidlo108()
			};
			return prav;
        }

        @Override
        public K07PravidloBase[] createFormatovaPravidla() {
            K07PravidloBase[] prav = {
                    new Pravidlo7_01(),
                    new Pravidlo7_02(),
                    new Pravidlo7_03(),
                    new Pravidlo7_04(),
            };
            return prav;
        }
	},
	SKARTACE_METADATA("skartační řízení (jen metadata)") {
		@Override
        public Rule<K06KontrolaContext>[] createObsahovaPravidla() {
            Rule[] prav = {
					new Pravidlo1(),
					new Pravidlo2(), 
					//new Pravidlo3(),
					new Pravidlo4(),
					new Pravidlo9(),					
					new Pravidlo10(),
					new Pravidlo11(),
					new Pravidlo12(),
					new Pravidlo13(),
					new Pravidlo14(),
					new Pravidlo15(), 
					new Pravidlo16(),
					new Pravidlo17(), 
					new Pravidlo18(),
					new Pravidlo19(),					
					new Pravidlo20(),
					new Pravidlo22(),
					new Pravidlo23(),
					new Pravidlo24(),
					new Pravidlo25(),
					new Pravidlo26(),
					new Pravidlo27(),
					new Pravidlo28(),
					new Pravidlo29(),					
					new Pravidlo30(), 
					new Pravidlo31(),
					new Pravidlo33(),
					new Pravidlo34(), 
					new Pravidlo35(), 
					new Pravidlo36(),
					new Pravidlo37(),
					new Pravidlo38(),
					new Pravidlo39(),
                    new Pravidlo41(),
                    new Pravidlo43a(),
                    new Pravidlo44(),
					// new Pravidlo45(), 
                    new Pravidlo46(),
					// new Pravidlo47(), 
					// new Pravidlo48(), 
                    new Pravidlo49(),
                    new Pravidlo50(),
                    new Pravidlo51(),
					// new Pravidlo52(),
                    new Pravidlo53(),
					new Pravidlo54(), 
					// new Pravidlo55(),
                    new Pravidlo56(),
					new Pravidlo57(), 
					new Pravidlo58(), 
					new Pravidlo59(),
                    new Pravidlo60(),
					new Pravidlo61(), 
					new Pravidlo61a(), 
					new Pravidlo62(), 
					new Pravidlo63(), 
					new Pravidlo64(), 
                    new Pravidlo65(),
					new Pravidlo67(), 
					new Pravidlo68(), 
					new Pravidlo69(), 
					new Pravidlo70(), 
					new Pravidlo71(), 
					new Pravidlo72(), 
					new Pravidlo73(), 
					new Pravidlo74(), 
					new Pravidlo75(), 
					new Pravidlo76(), 
					// new Pravidlo77(), 
					// new Pravidlo78(), 
					// new Pravidlo79(), 
					// new Pravidlo80(), 
					new Pravidlo81(),
					new Pravidlo82(), 
					new Pravidlo83(), 
					new Pravidlo84(), 
					new Pravidlo85(), 
					new Pravidlo86(), 
					new Pravidlo87(), 
					new Pravidlo88(), 
					new Pravidlo92(), 
					new Pravidlo93(), 
					new Pravidlo93a(), 
					new Pravidlo94(), 
					new Pravidlo95(), 
					new Pravidlo96(), 
					new Pravidlo97(), 
					new Pravidlo98(),
					// new Pravidlo99()
                    new Pravidlo100(),
                    new Pravidlo101(),
                    new Pravidlo102(),
                    new Pravidlo103(),
                    new Pravidlo104(),
                    new Pravidlo105(),
                    new Pravidlo106(),
                    new Pravidlo107(),
                    new Pravidlo108()
			};
			return prav;
		}		

        @Override
        public K07PravidloBase[] createFormatovaPravidla() {
            K07PravidloBase[] prav = {};
            return prav;
        }
	},
	SKARTACE_UPLNY("skartační řízení (s komponentami)") {
		@Override
        public Rule<K06KontrolaContext>[] createObsahovaPravidla() {
            Rule[] prav = {
					new Pravidlo1(),
					new Pravidlo2(), 
					// new Pravidlo3(),
					new Pravidlo4(),
					new Pravidlo9(),
					new Pravidlo10(),
					new Pravidlo11(),
					new Pravidlo12(),
					new Pravidlo13(),
					new Pravidlo14(),
					new Pravidlo15(), 
					new Pravidlo16(),
					new Pravidlo17(), 
					new Pravidlo18(),
					new Pravidlo19(),
					new Pravidlo20(),
					new Pravidlo22(),
					new Pravidlo23(),
					new Pravidlo24(),
					new Pravidlo25(),
					new Pravidlo26(),
					new Pravidlo27(),
					new Pravidlo28(),
					new Pravidlo29(),
					new Pravidlo30(), 
					new Pravidlo31(),
					new Pravidlo33(),
					new Pravidlo34(), 
					new Pravidlo35(), 
					new Pravidlo36(),
					new Pravidlo37(),
					new Pravidlo38(),
					new Pravidlo39(), 
                    new Pravidlo40(),
                    new Pravidlo41(),
                    new Pravidlo43a(),
					new Pravidlo44(), 
                    new Pravidlo46(),
					new Pravidlo49(), 
					new Pravidlo50(), 
					new Pravidlo51(), 
					new Pravidlo52(), 
					new Pravidlo53(), 
                    new Pravidlo54(),
					new Pravidlo55(),
					new Pravidlo56(), 
					new Pravidlo57(), 
					new Pravidlo58(), 
					new Pravidlo59(), 
					new Pravidlo60(), 
					new Pravidlo61(), 
					new Pravidlo61a(), 
					new Pravidlo62(), 
					new Pravidlo63(), 
					new Pravidlo64(), 
                    new Pravidlo65(),
					new Pravidlo67(), 
					new Pravidlo68(), 
					new Pravidlo69(), 
					new Pravidlo70(), 
					new Pravidlo71(), 
					new Pravidlo72(), 
					new Pravidlo73(), 
					new Pravidlo74(), 
					new Pravidlo75(), 
					new Pravidlo76(), 
					// new Pravidlo77(), 
					// new Pravidlo78(), 
					// new Pravidlo79(), 
					// new Pravidlo80(), 
					new Pravidlo81(),
					new Pravidlo82(), 
					new Pravidlo83(), 
					new Pravidlo84(), 
					new Pravidlo85(), 
					new Pravidlo86(), 
					new Pravidlo87(), 
					new Pravidlo88(), 
					new Pravidlo92(), 
					new Pravidlo93(), 
					new Pravidlo93a(), 
					new Pravidlo94(), 
					new Pravidlo95(), 
					new Pravidlo96(), 
					new Pravidlo97(), 
                    new Pravidlo98(),
                    new Pravidlo100(),
                    new Pravidlo101(),
                    new Pravidlo102(),
                    new Pravidlo103(),
                    new Pravidlo104(),
                    new Pravidlo105(),
                    new Pravidlo106(),
                    new Pravidlo107(),
                    new Pravidlo108()
			};
			return prav;
		}		

        @Override
        public K07PravidloBase[] createFormatovaPravidla() {
            K07PravidloBase[] prav = {
                    new Pravidlo7_01(),
                    new Pravidlo7_02(),
                    new Pravidlo7_03(),
                    new Pravidlo7_04(),
            };
            return prav;
        }
	},
	PREJIMKA("přejímka") {
		@Override
        public Rule<K06KontrolaContext>[] createObsahovaPravidla() {
            Rule[] prav = {
					new Pravidlo1(),
					// new Pravidlo2(), 
					new Pravidlo3(),
					new Pravidlo4(),
					new Pravidlo9(),
					new Pravidlo10(),
					new Pravidlo11(),
					new Pravidlo12(),
					new Pravidlo13(),
					new Pravidlo14(),
					new Pravidlo15(), 
					new Pravidlo16(),
					new Pravidlo17(), 
					new Pravidlo18(),
					new Pravidlo19(),
					new Pravidlo20(),
					new Pravidlo22(),
					new Pravidlo23(),
					new Pravidlo24(),
					new Pravidlo25(),
					new Pravidlo26(),
					new Pravidlo27(),
					new Pravidlo28(),
					new Pravidlo29(),
					new Pravidlo30(), 
					new Pravidlo31(),
					new Pravidlo33(),
					new Pravidlo34(), 
					new Pravidlo35(), 
					new Pravidlo36(),
					new Pravidlo37(),
					new Pravidlo38(),
					new Pravidlo39(), 
                    new Pravidlo40(),
                    new Pravidlo41(),
                    new Pravidlo43a(),
					new Pravidlo44(), 
                    new Pravidlo46(),
					new Pravidlo49(), 
					new Pravidlo50(), 
					new Pravidlo51(), 
					new Pravidlo52(), 
					new Pravidlo53(), 
                    new Pravidlo54(),
					new Pravidlo55(),
					new Pravidlo56(), 
					new Pravidlo57(), 
					new Pravidlo58(), 
					new Pravidlo59(), 
					new Pravidlo60(), 
					new Pravidlo61(), 
					new Pravidlo61a(), 
					new Pravidlo62(), 
					new Pravidlo63(), 
					new Pravidlo64(), 
                    new Pravidlo65(),
					new Pravidlo67(), 
					new Pravidlo68(),
					new Pravidlo69(), 
					new Pravidlo70(), 
					new Pravidlo71(), 
					new Pravidlo72(), 
					new Pravidlo73(), 
					new Pravidlo74(), 
					new Pravidlo75(), 
					new Pravidlo76(), 
					new Pravidlo77(), 
					new Pravidlo78(), 
					new Pravidlo79(), 
					new Pravidlo80(), 
					new Pravidlo81(),
					new Pravidlo82(), 
					new Pravidlo83(), 
					new Pravidlo84(), 
					new Pravidlo85(), 
					new Pravidlo86(), 
					new Pravidlo87(), 
					new Pravidlo88(), 
					new Pravidlo92(), 
					new Pravidlo93(), 
					new Pravidlo93a(), 
					new Pravidlo94(), 
					new Pravidlo95(), 
					new Pravidlo96(), 
					new Pravidlo97(), 
                    new Pravidlo98(),
                    new Pravidlo100(),
                    new Pravidlo101(),
                    new Pravidlo102(),
                    new Pravidlo103(),
                    new Pravidlo104(),
                    new Pravidlo105(),
                    new Pravidlo106(),
                    new Pravidlo107(),
                    new Pravidlo108()
			};
			return prav;
		}		

        @Override
        public K07PravidloBase[] createFormatovaPravidla() {
            K07PravidloBase[] prav = {
                    new Pravidlo7_01(),
                    new Pravidlo7_02(),
                    new Pravidlo7_03(),
                    new Pravidlo7_04(),
            };
            return prav;
        }
	};
	
    final String nazev;

    ZakladniProfilValidace(final String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String getName() {
        return nazev;
    }
}
