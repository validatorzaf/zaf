package cz.zaf.sipvalidator.nsesss2017;

import java.util.Objects;

public class PairZdrojIdent {

    final private String zdroj;
    final private String identifikator;

    public PairZdrojIdent(final String zdroj, final String identifikator) {
        this.zdroj = zdroj;
        this.identifikator = identifikator;
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof PairZdrojIdent)) {
            return false;        
        }

        PairZdrojIdent op = (PairZdrojIdent)o;        
        if(!Objects.equals(zdroj, op.zdroj)) {
            return false;
        }
        if (!Objects.equals(identifikator, op.identifikator)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(zdroj, identifikator);
    }

    public String getZdroj() {
        return zdroj;
    }

    public String getIdentifikator() {
        return identifikator;
    }
}
