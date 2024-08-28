package cz.zaf.eadvalidator.ap2023.profile;

public enum AP2023Profile implements EadValidationProfile {
    FINDING_AID {

        @Override
        public String getName() {
            return "archivní pomůcka";
        }

    },

    ARCH_DESC {

        @Override
        public String getName() {
            return "archivní popis";
        }

    },
    
    
}
