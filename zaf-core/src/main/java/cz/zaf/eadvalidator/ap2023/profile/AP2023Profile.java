package cz.zaf.eadvalidator.ap2023.profile;

import cz.zaf.common.validation.ValidationSubprofile;

public enum AP2023Profile implements ValidationSubprofile {
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
