package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Element;
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
        String mistoChyby = null;
        String detailChyby = null;

        this.metsParser = kontrola.getMetsParser();
        this.context = this.kontrola.getContext();

        ErrorCode errorCode = null;

        try {
            kontrola();
            // vse ok
            return;
        } catch (ZafException e) {
            errorCode = e.getErrorCode();
            detailChyby = e.getMessage();
            mistoChyby = e.getMistoChyby();

            // TODO: ID chybnych entit lze pridat do vystupu 
            // List<EntityId> entityIds = e.getEntityIds();
        } catch (Exception e) {
            errorCode = BaseCode.NEZNAMA_CHYBA;
            detailChyby = e.getLocalizedMessage();
        }

        kontrola.pridejChybu(kodPravidla,
                             errorCode,
                             textPravidla,
                             detailChyby,
                             obecnyPopisChyby,
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
    protected List<Element> predpokladZakladniEntity() {
        List<Element> zaklEntity = kontrola.getZakladniEnity();
        if (CollectionUtils.isEmpty(zaklEntity)) {
            nastavChybu("Chybí základní entita/entity. Předpokladem kontroly je existence alespoň jedné základní entity.");
            return null;
        }
        return zaklEntity;
    }

    protected List<Element> predpokladDokumenty() {
        List<Element> dokumenty = metsParser.getDokumenty();
        if (CollectionUtils.isEmpty(dokumenty)) {
            nastavChybu("Chybí dokumenty. Předpokladem kontroly je existence alespoň jednoho dokumentu.");
            return null;
        }
        return dokumenty;
    }

    protected boolean nastavChybu(String detailChyby, List<? extends Node> errorList) {
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
        nastavChybu(BaseCode.CHYBA, detailChyby, mistoChyby);
        return false;
    }

    protected ZafException pripravChybu(ErrorCode errorCode, String detailChyby) {
        return new ZafException(errorCode, detailChyby);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby,
                               List<? extends Node> errorList,
                               final EntityId entityId) {
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n)).collect(Collectors.joining(" "));
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoCh).addEntity(entityId);
    }

    protected void nastavChybu(ErrorCode errorCode, final String detailChyby, final Node mistoChyby) {
        nastavChybu(errorCode, detailChyby, getMistoChyby(mistoChyby));
    }

    protected void nastavChybu(ErrorCode errorCode, final String detailChyby, final String mistoChyby) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoChyby);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby, final Node mistoChyby,
                               final EntityId entityId) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(getMistoChyby(mistoChyby));
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

    protected String getJmenoIdentifikator(Element node) {
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
