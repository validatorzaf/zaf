package cz.zaf.sipvalidator.nsesss2017.pravidla07;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.ZafXmlPositionException;
import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.xml.PositionalXMLReader;

/**
 * Vychozi trida pro implementaci pravidel kontrol komponent
 */
abstract public class K07PravidloBase extends BaseRule<K07KontrolaContext> {

    protected K07KontrolaContext ctx;

    public K07PravidloBase(final String kodPravidla,
                           final String textPravidla,
                           final String obecnyPopisChyby,
                           final String zdrojChyby) {
        super(kodPravidla, textPravidla, obecnyPopisChyby, zdrojChyby);
    }

    @Override
    final public void eval(K07KontrolaContext ctx) {
        this.ctx = ctx;
        kontrola();
        this.ctx = null;
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby, Element node) {
        throw new ZafXmlPositionException(errorCode, detailChyby, node);

    }

    protected void nastavChybuSouboru(ErrorCode errorCode, String detailChyby, Element node,
                                    String cestaKSouboru) {
        throw new ZafException(errorCode, detailChyby,
                PositionalXMLReader.formatPosition(node)
                        + " Soubor: " + cestaKSouboru + ".");
    }

    protected void nastavChybuSouboru(ErrorCode errorCode, String detailChyby, Element node,
                                    String cestaKSouboru, Throwable cause) {
        throw new ZafException(errorCode, detailChyby,
                PositionalXMLReader.formatPosition(node) + " Soubor: " + cestaKSouboru + ".",
                cause);
    }

    protected abstract void kontrola();
}
