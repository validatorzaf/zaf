package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;

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
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    /**
     * Volitelný seznam entit, kde byla identifikována chyba
     */
    protected List<EntityId> chybneEntity;

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

    /**
     * Hlavni metoda pro spusteni kontroly pravidla
     */
    protected abstract void kontrola();

    @Override
    public void kontrolaPravidla(final K06_Obsahova kontrola) {
        this.kontrola = kontrola;
        // reset promennych pred spustenim
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;

        this.metsParser = kontrola.getMetsParser();
        this.context = this.kontrola.getContext();

        ErrorCode errorCode = null;

        try {
            kontrola();
        } catch (ZafException e) {
            errorCode = e.getErrorCode();
            detailChyby = e.getMessage();
            mistoChyby = e.getMistoChyby();

            // TODO: ID chybnych entit lze pridat do vystupu 
            // List<EntityId> entityIds = e.getEntityIds();
        } catch (Exception e) {
            errorCode = BaseCode.UNKNOWN_ERROR;
            detailChyby = e.getLocalizedMessage();
        }

        kontrola.pridejPravidlo(kodPravidla,
                                errorCode,
                                textPravidla,
                                detailChyby,
                                !stav ? obecnyPopisChyby : null,
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
     * @return Seznam základních entit, null při neexistenci
     */
    protected List<Node> predpokladZakladniEntity() {
        List<Node> zaklEntity = kontrola.getZakladniEnity();
        if (CollectionUtils.isEmpty(zaklEntity)) {
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
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n)).collect(Collectors.joining(" "));
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
     *            detailní popis chyby
     * @param mistoChyby
     *            určení místa chyby
     * @return Vraci vzdy false, lze vyuzit pro jednoradkovy zapis
     */
    protected boolean nastavChybu(final String detailChyby, final String mistoChyby) {
        nastavChybu(BaseCode.ERROR, detailChyby, mistoChyby);
        return false;
    }

    protected void nastavChybu(ErrorCode errorCode, final String detailChyby, final Node mistoChyby) {
        nastavChybu(errorCode, detailChyby, getMistoChyby(mistoChyby));
    }

    protected void nastavChybu(ErrorCode errorCode, final String detailChyby, final String mistoChyby) {
        throw new ZafException(errorCode, detailChyby, mistoChyby);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby, final Node mistoChyby,
                               final EntityId entityId) {
        throw new ZafException(errorCode, detailChyby, getMistoChyby(mistoChyby))
                .addEntity(entityId);

    }

    @Override
    public String getKodPravidla() {
        return kodPravidla;
    }

    @Override
    public String getTtextPravidla() {
        return textPravidla;
    }

    @Override
    public String getObecnyPopisChyby() {
        return obecnyPopisChyby;
    }

    @Override
    public String getZdroj() {
        return zdrojChyby;
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
