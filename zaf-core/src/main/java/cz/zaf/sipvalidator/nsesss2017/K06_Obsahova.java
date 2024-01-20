/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;
import cz.zaf.sipvalidator.nsesss2017.EntityId.DruhEntity;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.ObsahovePravidlo;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Obsahova kontrola
 *
 */
public class K06_Obsahova
        extends KontrolaBase {

    static final public String NAME = "kontrola obsahu";

    SipInfo sipSoubor;
    private ObsahovePravidlo[] seznamPravidel;

    MetsParser metsParser;

    private List<Element> zakladniEntity;

    /**
     * Mapa kontrol
     */
    Map<String, ObsahovePravidlo> kontroly = new HashMap<>();

    public K06_Obsahova(ObsahovePravidlo[] obsahovaPravidla) {
        this.seznamPravidel = obsahovaPravidla;
    }

    public String getJmenoIdentifikator(Element node) {
        if (node != null) {
            Element entity = getEntity(node);
            return getJmeno(entity) + " " + getIdentifikatory(entity) + ".";
        }
        return "";
    }

    /**
     * Vyhledani entity na zaklade elementu
     * 
     * @param node
     * @return
     */
    static public Element getEntity(Element node) {
        String nodeName = node.getNodeName();
        if (!isMainEnetity(nodeName)) {
            return getEntity((Element) node.getParentNode());
        } else {
            return node;
        }
    }

    static private boolean isMainEnetity(String nodeName) {
        boolean bol = (nodeName.equals("nsesss:SpisovyPlan") || nodeName.equals("nsesss:VecnaSkupina") || nodeName.equals("TypovySpis") || nodeName.equals("nsesss:Soucast") || nodeName.equals("nsesss:Dil") || nodeName.equals("nsesss:Spis") || nodeName.equals("nsesss:Dokument"));
        return bol;
    }

    static public EntityId getEntityId(Element node) {
        String nodename = node.getNodeName();
        Node identNode;
        DruhEntity druhEntity;
        switch (nodename) {
            case NsessV3.VECNA_SKUPINA:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.VECNA_SKUPINA;
                break;
            case NsessV3.SOUCAST:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SOUCAST;
                break;
            case NsessV3.TYPOVY_SPIS:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.TYPOVY_SPIS;
                break;
            case NsessV3.SPISOVY_PLAN:
                identNode = ValuesGetter.getXChild(node, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SPISOVY_PLAN;
                break;
            case NsessV3.DOKUMENT:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.DOKUMENT;
                break;
            case NsessV3.SPIS:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SPIS;
                break;
            case NsessV3.DIL:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.DIL;
                break;
            case NsessV3.KOMPONENTA:
                identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                        NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.KOMPONENTA;
                break;
            case NsessV3.SKARTACNI_RIZENI:
                identNode = ValuesGetter.getXChild(node, NsessV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SKARTACNI_RIZENI;
                break;
            default:
                return null;
        }
        String hodnota, zdroj;
        if (identNode == null) {
            hodnota = "nenalezeno";
            zdroj = "nenalezeno";
        } else {
            hodnota = identNode.getTextContent();
            zdroj = ValuesGetter.getValueOfAttribut(identNode, "zdroj");
        }
        PairZdrojIdent zdrojIdent = new PairZdrojIdent(zdroj, hodnota);

        return new EntityId(druhEntity, zdrojIdent);

    }

    static public List<EntityId> getEntityId(List<Element> listEl) {
        List<EntityId> listEntityIds = new ArrayList<>();
        listEl.stream().map((el) -> getEntityId(el)).forEachOrdered((entId) -> {
            listEntityIds.add(entId);
        });
        return listEntityIds;
    }

    /**
     * Vrati odkaz na element tIdentifikator
     * 
     * @param element
     * @return
     */
    public Element getIdentifikator(Element element) {
        String elementName = element.getNodeName();
        Element identifikator;

        if (elementName.equals(NsessV3.SPISOVY_PLAN)) {
            identifikator = ValuesGetter.getXChild(element, NsessV3.IDENTIFIKATOR);
            return identifikator;
        } else {
            identifikator = ValuesGetter.getXChild(element, NsessV3.EVIDENCNI_UDAJE,
                    NsessV3.IDENTIFIKACE, NsessV3.IDENTIFIKATOR);
            return identifikator;
        }
    }

    public String getIdentifikatory(Element node) {
        String nodename = node.getNodeName();
        String hodnota = "nenalezeno", zdroj = "nenalezeno";
        Node identifikator;
        if (nodename.equals(NsessV3.SPISOVY_PLAN)) {
            identifikator = ValuesGetter.getXChild(node, NsessV3.IDENTIFIKATOR);
            if (identifikator != null) {
                hodnota = identifikator.getTextContent();
                boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
                if (ma) {
                    zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
                }
                return "(Ident. hodnota: " + hodnota + ", zdroj: " + zdroj + ")";
            }
        }
        identifikator = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                "nsesss:Identifikace", NsessV3.IDENTIFIKATOR);
        if (identifikator != null) {
            hodnota = identifikator.getTextContent();
            boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
            if (ma) {
                zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            }
            return "(Ident. hodnota: " + hodnota + ", zdroj: " + zdroj + ")";
        }

        return "(Ident. hodnota: " + hodnota + ", zdroj: " + zdroj + ")";
    }

    private String getJmeno(Node node) {
        String jmeno = node.getNodeName();
        switch (jmeno) {
            case "nsesss:SpisovyPlan":
                return "Spisový plán";
            case "nsesss:VecnaSkupina":
                return "Věcná skupina";
            case "nsesss:TypovySpis":
                return "Typový spis";
            case "nsesss:Soucast":
                return "Součást";
            case "nsesss:Dil":
                return "Díl";
            case "nsesss:Spis":
                return "Spis";
            case "nsesss:Dokument":
                return "Dokument";
        }
        return "";
    }

    /**
     *
     * @param idPravidla
     * @param errorCode kod chyby
     * @param textPravidla
     * @param detailChyby
     * @param obecnyPopisChyby
     * @param mistoChyby
     * @param zdroj
     */
    void pridejChybu(String kodPravidla,
            ErrorCode errorCode,
            String textPravidla,
            String detailChyby,
            String obecnyPopisChyby,
            String mistoChyby,
            String zdroj) {
        pridejChybu(kodPravidla, errorCode, textPravidla, detailChyby, obecnyPopisChyby, mistoChyby, zdroj,
                null);
    }

    public void pridejChybu(String kodPravidla, ErrorCode errorCode, String textPravidla, String detailChyby,
            String obecnyPopisChyby, String mistoChyby, String zdrojChyby,
            List<EntityId> entityIds) {
        ChybaPravidla p = new ChybaPravidla(kodPravidla,
                textPravidla,
                detailChyby,
                obecnyPopisChyby,
                mistoChyby,
                zdrojChyby,
                errorCode,
                entityIds);
        vysledekKontroly.add(p);

    }

    // na konci oddělovač nehlídá
    static public boolean spisZnakObsahujeOddelovac(String spisovy_znak) {
        for (int i = 0; i < spisovy_znak.length() - 1; i++) {
            char c = spisovy_znak.charAt(i);
            if (c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.') {
                return true;
            }

        }

        return false;
    }

    @Override
    public void provedKontrolu() {
        // Nastaveni promenych pro kontroly
        // bude nutne casem prepracovat
        this.metsParser = ctx.getMetsParser();
        this.zakladniEntity = metsParser.getZakladniEntity();

        this.sipSoubor = ctx.getSip();

        for (int i = 0; i < seznamPravidel.length; i++) {
            ObsahovePravidlo pravidlo = seznamPravidel[i];

            String kodPravidla = pravidlo.getCode();
            // skip excluded checks
            if (ctx.isExcluded(kodPravidla)) {
                continue;
            }

            pravidlo.kontrolaPravidla(this);
        }

    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.OBSAHOVA;
    }

    public List<Element> getZakladniEnity() {
        return zakladniEntity;
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }

}
