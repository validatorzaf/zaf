package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Node;

/**
 * Zakladni implementace spustitelneho pravidla
 * 
 * Kazde takove pravidlo vytvari prave jeden
 * zaznam ve vystupu
 *
 */
public abstract class K06PravidloBase implements ObsahovePravidlo {

    protected K06_Obsahova kontrola;

    final protected String kodPravidla;
    final protected String textPravidla;
    protected String mistoChyby;
    protected String detailChyby;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    protected MetsParser metsParser;

    protected KontrolaNsess2017Context context;

    public K06PravidloBase(final String kodPravidla,
                           final String textPravidla,
                           final String obecnyPopisChyby,
                           final String zdrojChyby) {        
        Validate.notNull(kodPravidla);
        Validate.notNull(textPravidla);
        
        this.kodPravidla = kodPravidla;
        this.textPravidla = textPravidla;
        this.obecnyPopisChyby = obecnyPopisChyby;
        this.zdrojChyby = zdrojChyby;
    }

    protected abstract boolean kontrolaPravidla();

    @Override
    public void kontrolaPravidla(final K06_Obsahova kontrola) {
    	this.kontrola = kontrola;
        // reset promennych pred spustenim
        boolean stav = false;
        mistoChyby = null;
        detailChyby = null;

        this.metsParser = kontrola.getMetsParser();
        this.context = this.kontrola.getContext();

        try {
        	stav = kontrolaPravidla();
        } catch(Exception e) {
        	detailChyby = e.getLocalizedMessage();
        }

        kontrola.pridejPravidlo(kodPravidla,
                                stav,
                                textPravidla,
                                detailChyby,
                                stav ? obecnyPopisChyby : null,
                                mistoChyby,
                                zdrojChyby);

        this.context = null;
        this.kontrola = null;
    }

    protected String getMistoChyby(Node node) {
        return kontrola.getMistoChyby(node);
    }

    /**
     * Pravidlo predpoklada existenci zakladnich entit
     * 
     * Dojde k selhani pokud neexistuji.
     * 
     * @return
     */
    protected List<Node> predpokladZakladniEntity() {
        List<Node> zaklEntity = kontrola.getZakladniEnity();
        if (zaklEntity == null || zaklEntity.size() == 0) {
            nastavChybu("Chybí základní entita/entity. Předpokladem kontroly je existence alespoň jedné základní entity.");
            return null;
        }
        return zaklEntity;
    }

    protected List<Node> predpokladDokumenty() {
        List<Node> dokumenty = metsParser.getDokumenty();
        if (CollectionUtils.isEmpty(dokumenty)) {
            nastavChybu("Chybí dokumenty. Předpokladem kontroly je existence alespoň jednoho dokumentu.");
            return null;
        }
        return dokumenty;
    }
    
    protected boolean nastavChybu(String detailChyby, List<Node> errorList) {
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n) ).collect(Collectors.joining(" "));
        return nastavChybu(detailChyby, mistoCh);
    }    

    protected boolean nastavChybu(final String detailChyby, final Node mistoChyby) {
        return nastavChybu(detailChyby, getMistoChyby(mistoChyby));
    }

    protected boolean nastavChybu(final String detailChyby) {
        return nastavChybu(detailChyby, (String) null);
    }

    /**
     * Nastaveni chyby a mista jejiho vyskytu
     * 
     * @param detailChyby
     * @param mistoChyby
     * @return Vraci vzdy false, lze vyuzit pro jednoradkovy zapis
     */
    protected boolean nastavChybu(final String detailChyby, final String mistoChyby) {
        this.detailChyby = detailChyby;
        this.mistoChyby = mistoChyby;
        return false;
    }

    @Override
    public String getKodPravidla() {
        return kodPravidla;
    }

    protected String getJmenoIdentifikator(Node node) {
        return kontrola.getJmenoIdentifikator(node);
    }

    /**
     * Nacteni roku z obsahu elementu
     * 
     * @param node
     *            Element pro nacteni roku
     * @return Vraci rok z retezce
     */
    protected Integer vratRok(Node node) {
        String content = node.getTextContent();
        String strYear = content.substring(0, 4);
        try {
            return Integer.parseInt(strYear);
        } catch (NumberFormatException nfe) {
            nastavChybu("Hodnota roku v elementu <" + node.getNodeName() + "> uvedena ve špatném formátu. Hodnota: "
                    + content,
                        node);
            return null;
        }
    }
}
