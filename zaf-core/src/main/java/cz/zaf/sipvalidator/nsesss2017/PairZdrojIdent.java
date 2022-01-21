package cz.zaf.sipvalidator.nsesss2017;

import java.util.Objects;

public class PairZdrojIdent {

    private String zdroj;
    private String ident;

    public PairZdrojIdent(final String zdroj, final String ident) {
        this.zdroj = zdroj;
        this.ident = ident;
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;        
        }

        PairZdrojIdent op = (PairZdrojIdent)o;        
        if(!Objects.equals(zdroj, op.zdroj)) {
            return false;
        }
        if(!Objects.equals(ident, op.ident)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(zdroj, ident);
    }

    public String getZdroj() {
        return zdroj;
    }

    public String getIdent() {
        return ident;
    }
}
