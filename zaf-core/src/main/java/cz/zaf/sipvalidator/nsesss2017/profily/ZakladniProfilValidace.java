package cz.zaf.sipvalidator.nsesss2017.profily;

import java.util.Collections;
import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.sipvalidator.nsesss2017.KontrolaNsessContext;
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
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_01;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_02;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_03;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_04;

public enum ZakladniProfilValidace
	implements ProfilValidace {

	SKARTACE_METADATA("skartační řízení (jen metadata)") {
		@Override
		public List<Class<? extends BaseRule<KontrolaNsessContext>>> getContentRuleClasses() {
			return List.of(
					Pravidlo1.class,
					Pravidlo2.class,
					//new Pravidlo3(),
					Pravidlo4.class,
					Pravidlo9.class,
					Pravidlo10.class,
					Pravidlo11.class,
					Pravidlo12.class,
					Pravidlo13.class,
					Pravidlo14.class,
					Pravidlo15.class,
					Pravidlo16.class,
					Pravidlo17.class,
					Pravidlo18.class,
					Pravidlo19.class,
					Pravidlo20.class,
					Pravidlo22.class,
					Pravidlo23.class,
					Pravidlo24.class,
					Pravidlo25.class,
					Pravidlo26.class,
					Pravidlo27.class,
					Pravidlo28.class,
					Pravidlo29.class,
					Pravidlo30.class,
					Pravidlo31.class,
					Pravidlo33.class,
					Pravidlo34.class,
					Pravidlo35.class,
					Pravidlo36.class,
					Pravidlo37.class,
					Pravidlo38.class,
					Pravidlo39.class,
                    Pravidlo41.class,
                    Pravidlo43a.class,
                    Pravidlo44.class,
					// new Pravidlo45(),
                    Pravidlo46.class,
					// new Pravidlo47(),
					// new Pravidlo48(),
                    Pravidlo49.class,
                    Pravidlo50.class,
                    Pravidlo51.class,
					// new Pravidlo52(),
                    Pravidlo53.class,
					Pravidlo54.class,
					// new Pravidlo55(),
                    Pravidlo56.class,
					Pravidlo57.class,
					Pravidlo58.class,
					Pravidlo59.class,
                    Pravidlo60.class,
					Pravidlo61.class,
					Pravidlo61a.class,
					Pravidlo62.class,
					Pravidlo63.class,
					Pravidlo64.class,
                    Pravidlo65.class,
					Pravidlo67.class,
					Pravidlo68.class,
					Pravidlo69.class,
					Pravidlo70.class,
					Pravidlo71.class,
					Pravidlo72.class,
					Pravidlo73.class,
					Pravidlo74.class,
					Pravidlo75.class,
					Pravidlo76.class,
					// new Pravidlo77(),
					// new Pravidlo78(),
					// new Pravidlo79(),
					// new Pravidlo80(),
					Pravidlo81.class,
					Pravidlo82.class,
					Pravidlo83.class,
					Pravidlo84.class,
					Pravidlo85.class,
					Pravidlo86.class,
					Pravidlo87.class,
					Pravidlo88.class,
					Pravidlo92.class,
					Pravidlo93.class,
					Pravidlo93a.class,
					Pravidlo94.class,
					Pravidlo95.class,
					Pravidlo96.class,
					Pravidlo97.class,
					Pravidlo98.class,
					// new Pravidlo99()
                    Pravidlo100.class,
                    Pravidlo101.class,
                    Pravidlo102.class,
                    Pravidlo103.class,
                    Pravidlo104.class,
                    Pravidlo105.class,
                    Pravidlo106.class,
                    Pravidlo107.class,
                    Pravidlo108.class
			);
		}

        @Override
        public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses() {
			return Collections.emptyList();
        }
	},
	SKARTACE_UPLNY("skartační řízení (s komponentami)") {
		@Override
		public List<Class<? extends BaseRule<KontrolaNsessContext>>> getContentRuleClasses() {
			return List.of(
					Pravidlo1.class,
					Pravidlo2.class,
					// new Pravidlo3(),
					Pravidlo4.class,
					Pravidlo9.class,
					Pravidlo10.class,
					Pravidlo11.class,
					Pravidlo12.class,
					Pravidlo13.class,
					Pravidlo14.class,
					Pravidlo15.class,
					Pravidlo16.class,
					Pravidlo17.class,
					Pravidlo18.class,
					Pravidlo19.class,
					Pravidlo20.class,
					Pravidlo22.class,
					Pravidlo23.class,
					Pravidlo24.class,
					Pravidlo25.class,
					Pravidlo26.class,
					Pravidlo27.class,
					Pravidlo28.class,
					Pravidlo29.class,
					Pravidlo30.class,
					Pravidlo31.class,
					Pravidlo33.class,
					Pravidlo34.class,
					Pravidlo35.class,
					Pravidlo36.class,
					Pravidlo37.class,
					Pravidlo38.class,
					Pravidlo39.class,
                    Pravidlo40.class,
                    Pravidlo41.class,
                    Pravidlo43a.class,
					Pravidlo44.class,
                    Pravidlo46.class,
					Pravidlo49.class,
					Pravidlo50.class,
					Pravidlo51.class,
					Pravidlo52.class,
					Pravidlo53.class,
                    Pravidlo54.class,
					Pravidlo55.class,
					Pravidlo56.class,
					Pravidlo57.class,
					Pravidlo58.class,
					Pravidlo59.class,
					Pravidlo60.class,
					Pravidlo61.class,
					Pravidlo61a.class,
					Pravidlo62.class,
					Pravidlo63.class,
					Pravidlo64.class,
                    Pravidlo65.class,
					Pravidlo67.class,
					Pravidlo68.class,
					Pravidlo69.class,
					Pravidlo70.class,
					Pravidlo71.class,
					Pravidlo72.class,
					Pravidlo73.class,
					Pravidlo74.class,
					Pravidlo75.class,
					Pravidlo76.class,
					// new Pravidlo77(),
					// new Pravidlo78(),
					// new Pravidlo79(),
					// new Pravidlo80(),
					Pravidlo81.class,
					Pravidlo82.class,
					Pravidlo83.class,
					Pravidlo84.class,
					Pravidlo85.class,
					Pravidlo86.class,
					Pravidlo87.class,
					Pravidlo88.class,
					Pravidlo92.class,
					Pravidlo93.class,
					Pravidlo93a.class,
					Pravidlo94.class,
					Pravidlo95.class,
					Pravidlo96.class,
					Pravidlo97.class,
                    Pravidlo98.class,
                    Pravidlo100.class,
                    Pravidlo101.class,
                    Pravidlo102.class,
                    Pravidlo103.class,
                    Pravidlo104.class,
                    Pravidlo105.class,
                    Pravidlo106.class,
                    Pravidlo107.class,
                    Pravidlo108.class
			);
		}

        @Override
        public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses() {
			return List.of(
                    Pravidlo7_01.class,
                    Pravidlo7_02.class,
                    Pravidlo7_03.class,
                    Pravidlo7_04.class
            );
        }
	},
	PREJIMKA("přejímka") {
		@Override
		public List<Class<? extends BaseRule<KontrolaNsessContext>>> getContentRuleClasses() {
			return List.of(
					Pravidlo1.class,
					// new Pravidlo2(),
					Pravidlo3.class,
					Pravidlo4.class,
					Pravidlo9.class,
					Pravidlo10.class,
					Pravidlo11.class,
					Pravidlo12.class,
					Pravidlo13.class,
					Pravidlo14.class,
					Pravidlo15.class,
					Pravidlo16.class,
					Pravidlo17.class,
					Pravidlo18.class,
					Pravidlo19.class,
					Pravidlo20.class,
					Pravidlo22.class,
					Pravidlo23.class,
					Pravidlo24.class,
					Pravidlo25.class,
					Pravidlo26.class,
					Pravidlo27.class,
					Pravidlo28.class,
					Pravidlo29.class,
					Pravidlo30.class,
					Pravidlo31.class,
					Pravidlo33.class,
					Pravidlo34.class,
					Pravidlo35.class,
					Pravidlo36.class,
					Pravidlo37.class,
					Pravidlo38.class,
					Pravidlo39.class,
                    Pravidlo40.class,
                    Pravidlo41.class,
                    Pravidlo43a.class,
					Pravidlo44.class,
                    Pravidlo46.class,
					Pravidlo49.class,
					Pravidlo50.class,
					Pravidlo51.class,
					Pravidlo52.class,
					Pravidlo53.class,
                    Pravidlo54.class,
					Pravidlo55.class,
					Pravidlo56.class,
					Pravidlo57.class,
					Pravidlo58.class,
					Pravidlo59.class,
					Pravidlo60.class,
					Pravidlo61.class,
					Pravidlo61a.class,
					Pravidlo62.class,
					Pravidlo63.class,
					Pravidlo64.class,
                    Pravidlo65.class,
					Pravidlo67.class,
					Pravidlo68.class,
					Pravidlo69.class,
					Pravidlo70.class,
					Pravidlo71.class,
					Pravidlo72.class,
					Pravidlo73.class,
					Pravidlo74.class,
					Pravidlo75.class,
					Pravidlo76.class,
					Pravidlo77.class,
					Pravidlo78.class,
					Pravidlo79.class,
					Pravidlo80.class,
					Pravidlo81.class,
					Pravidlo82.class,
					Pravidlo83.class,
					Pravidlo84.class,
					Pravidlo85.class,
					Pravidlo86.class,
					Pravidlo87.class,
					Pravidlo88.class,
					Pravidlo92.class,
					Pravidlo93.class,
					Pravidlo93a.class,
					Pravidlo94.class,
					Pravidlo95.class,
					Pravidlo96.class,
					Pravidlo97.class,
                    Pravidlo98.class,
                    Pravidlo100.class,
                    Pravidlo101.class,
                    Pravidlo102.class,
                    Pravidlo103.class,
                    Pravidlo104.class,
                    Pravidlo105.class,
                    Pravidlo106.class,
                    Pravidlo107.class,
                    Pravidlo108.class
			);
		}

        @Override
        public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses() {
			return List.of(
                    Pravidlo7_01.class,
                    Pravidlo7_02.class,
                    Pravidlo7_03.class,
                    Pravidlo7_04.class
            );
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
