package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.sipvalidator.nsesss2017.EntityId;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.MetsParser;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * Zakladni implementace spustitelneho pravidla
 *
 * Kazde takove pravidlo vytvari prave jeden zaznam ve vystupu
 *
 */
public abstract class K06PravidloBase implements ObsahovePravidlo {

    /**
     * Výstupní datový formát: Neprovádění kontroly, pokud byla základní entita
     * vyřízena/uzavřena do 31. 7. 2012 včetně.
     */
    final public static LocalDate ROZHODNE_DATUM_VYSTUPNI_FORMAT = LocalDate.parse("2012-07-31");

    protected K06KontrolaContext kontrola;

    final protected String kodPravidla;
    final protected String textPravidla;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    /**
     * Volitelný seznam entit, kde byla identifikována chyba
     */
    protected List<EntityId> chybneEntity;

    protected MetsParser metsParser;

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
    public void eval(final K06KontrolaContext kontrolaCtx) {
        this.kontrola = kontrolaCtx;
        this.metsParser = kontrola.getMetsParser();
        kontrola();
        this.metsParser = null;
        this.kontrola = null;
    }

    public static String getMistoChyby(Node node) {
        return PositionalXMLReader.formatPosition(node);
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
     * @param detailChyby detailní popis chyby
     * @param mistoChyby určení místa chyby
     * @return Vraci vzdy false, lze vyuzit pro jednoradkovy zapis
     */
    protected boolean nastavChybu(final String detailChyby, final String mistoChyby) {
        nastavChybu(BaseCode.CHYBA, detailChyby, mistoChyby);
        return false;
    }

    static protected ZafException pripravChybu(ErrorCode errorCode, String detailChyby) {
        return new ZafException(errorCode, detailChyby);
    }

    static protected void nastavChybu(ErrorCode errorCode, String detailChyby) {
        throw pripravChybu(errorCode, detailChyby);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby,
            List<? extends Node> errorList) {
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n)).collect(Collectors.joining(" "));
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoCh);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby,
            List<? extends Node> errorList, final EntityId entityId) {
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n)).collect(Collectors.joining(" "));
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoCh).addEntity(entityId);
    }

    protected void nastavChybu(ErrorCode errorCode, String detailChyby,
            List<? extends Node> errorList, final List<EntityId> listEntityId) {
        String mistoCh = errorList.stream().map(n -> getMistoChyby(n)).collect(Collectors.joining(" "));
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoCh).addEntity(listEntityId);
    }

    static protected void nastavChybu(ErrorCode errorCode, final String detailChyby, final Node mistoChyby) {
        nastavChybu(errorCode, detailChyby, PositionalXMLReader.formatPosition(mistoChyby));
    }

    static public void nastavChybu(ErrorCode errorCode, final String detailChyby, final String mistoChyby) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoChyby);
    }

    static public void nastavChybu(ErrorCode errorCode, String detailChyby, final Node mistoChyby,
            final EntityId entityId) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(getMistoChyby(mistoChyby)).addEntity(entityId);
    }

    static public void nastavChybu(ErrorCode errorCode, String detailChyby, final String mistoChyby,
            final EntityId entityId) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoChyby).addEntity(entityId);
    }

    static public void nastavChybu(ErrorCode errorCode, String detailChyby, final Node mistoChyby,
            final List<EntityId> entityIds) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(getMistoChyby(mistoChyby)).addEntity(entityIds);
    }

    static public void nastavChybu(ErrorCode errorCode, String detailChyby, final String mistoChyby,
            final List<EntityId> entityIds) {
        throw pripravChybu(errorCode, detailChyby).setMistoChyby(mistoChyby).addEntity(entityIds);
    }

    @Override
    public String getCode() {
        return kodPravidla;
    }

    @Override
    public String getDescription() {
        return textPravidla;
    }

    @Override
    public String getGenericError() {
        return obecnyPopisChyby;
    }

    @Override
    public String getRuleSource() {
        return zdrojChyby;
    }

    protected String getJmenoIdentifikator(Element node) {
        return K06_Obsahova.getJmenoIdentifikator(node);
    }

    /**
     * Nacteni roku z obsahu elementu
     *
     * @param node Element pro nacteni roku
     * @return Vraci rok z retezce
     */
    protected Integer vratRok(Element node) {
        String content = node.getTextContent();
        String strYear = content.substring(0, 4);
        try {
            return Integer.parseInt(strYear);
        } catch (NumberFormatException nfe) {
            Element entita = K06_Obsahova.getEntity(node);
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Hodnota roku v elementu <" + node.getNodeName() + "> uvedena ve špatném formátu. Hodnota: "
                                + content,
                        node, K06_Obsahova.getEntityId(entita));
            return null;
        }
    }

    static protected boolean jePozdeji(Element node, LocalDate rozhodneDatum) {
        String content = node.getTextContent();
        try {
            LocalDate date = LocalDate.parse(content);
            return date.isAfter(rozhodneDatum);
        } catch (DateTimeParseException dtpe) {
            Element entita = K06_Obsahova.getEntity(node);
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Hodnota v elementu <" + node.getNodeName() + "> uvedena ve špatném formátu. Hodnota: "
                    + content,
                        node, K06_Obsahova.getEntityId(entita));
            return false;
        }
    }

    /**
     * Test, zda se na zakladni entitu uplatni pozadavek na vystupni format
     * 
     * @param zaklEntita
     * @return
     */
    static public boolean vratKontrolaVystupniFormat(Element zaklEntita) {
        Element datumVyrizeni;
        switch (zaklEntita.getNodeName()) {
        case NsesssV3.DIL:
            datumVyrizeni = ValuesGetter.getXChild(zaklEntita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.UZAVRENI,
                                                   NsesssV3.DATUM);
            break;
        case NsesssV3.SPIS:
            datumVyrizeni = ValuesGetter.getXChild(zaklEntita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.VYRIZENI_UZAVRENI,
                                                   NsesssV3.DATUM);
            break;
        case NsesssV3.DOKUMENT:
            datumVyrizeni = ValuesGetter.getXChild(zaklEntita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.VYRIZENI,
                                                   NsesssV3.DATUM);
            break;
        default:
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT, "Neni zakladni entita", zaklEntita);
            datumVyrizeni = null;
        }
        if (datumVyrizeni != null) {
            return jePozdeji(datumVyrizeni, ROZHODNE_DATUM_VYSTUPNI_FORMAT);
        }

        return false;
    }

}
