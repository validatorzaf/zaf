package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Node;

/**
 * Zakladni implementace spustitelneho pravidla
 * 
 * Kazde takove pravidlo vytvari prave jeden
 * zaznam ve vystypu
 *
 */
public abstract class K06PravidloBase implements Runnable {

    protected K06_Obsahova kontrola;

    final protected String kodPravidla;
    final protected String textPravidla;
    protected String mistoChyby;
    protected String detailChyby;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    protected MetsParser metsParser;

    public K06PravidloBase(final K06_Obsahova kontrola,
                           final String kodPravidla,
                           final String textPravidla,
                           final String obecnyPopisChyby,
                           final String zdrojChyby) {
        Validate.notNull(kontrola);
        Validate.notNull(kodPravidla);
        Validate.notNull(textPravidla);

        this.kontrola = kontrola;
        this.kodPravidla = kodPravidla;
        this.textPravidla = textPravidla;
        this.obecnyPopisChyby = obecnyPopisChyby;
        this.zdrojChyby = zdrojChyby;
    }

    protected abstract boolean kontrolaPravidla();

    @Override
    public void run() {
        // reset promennych pred spustenim
        boolean stav = false;
        mistoChyby = null;
        detailChyby = null;

        this.metsParser = kontrola.getMetsParser();

        stav = kontrolaPravidla();

        kontrola.pridejPravidlo(kodPravidla,
                                stav,
                                textPravidla,
                                detailChyby,
                                stav ? obecnyPopisChyby : null,
                                mistoChyby,
                                zdrojChyby);
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
            detailChyby = "Chybí základní entita/entity. Předpokladem kontroly je existence alespoň jedné základní entity.";
            return null;
        }
        return zaklEntity;
    }

    protected List<Node> predpokladDokumenty() {
        List<Node> dokumenty = kontrola.getDokumenty();
        if (dokumenty == null || dokumenty.size() == 0) {
            detailChyby = "Chybí dokumenty. Předpokladem kontroly je existence alespoň jednoho dokumentu.";
            return null;
        }
        return dokumenty;
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

    public String getKodPravidla() {
        return kodPravidla;
    }

    protected String getJmenoIdentifikator(Node node) {
        return kontrola.getJmenoIdentifikator(node);
    }
}