package cz.zaf.common.result;

import java.util.Objects;

public class IndetifierWithSource {

    final private String source;
    final private String identifier;

    public IndetifierWithSource(final String zdroj, final String identifikator) {
        this.source = zdroj;
        this.identifier = identifikator;
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof IndetifierWithSource)) {
            return false;        
        }

        IndetifierWithSource op = (IndetifierWithSource)o;        
        if(!Objects.equals(source, op.source)) {
            return false;
        }
        if (!Objects.equals(identifier, op.identifier)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(source, identifier);
    }

    public String getSource() {
        return source;
    }

    public String getIdentifier() {
        return identifier;
    }
}
