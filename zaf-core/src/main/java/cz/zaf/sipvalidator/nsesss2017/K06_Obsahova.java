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

import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.result.EntityId;
import cz.zaf.common.result.IndetifierWithSource;
import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;

/**
 * Obsahova kontrola
 *
 */
public class K06_Obsahova
        extends KontrolaBase<K06KontrolaContext> {

    static final public String NAME = "kontrola obsahu";

    SipInfo sipSoubor;
    private Rule<K06KontrolaContext>[] seznamPravidel;

    MetsParser metsParser;

    private List<Element> zakladniEntity;

    /**
     * Mapa kontrol
     */
    Map<String, Rule<K06KontrolaContext>> kontroly = new HashMap<>();

    public K06_Obsahova(Rule<K06KontrolaContext>[] obsahovaPravidla) {
        super(TypUrovenKontroly.OBSAHOVA);
        this.seznamPravidel = obsahovaPravidla;
    }

    static public String getJmenoIdentifikator(Element node) {
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
            case NsesssV3.VECNA_SKUPINA:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.VECNA_SKUPINA;
                break;
            case NsesssV3.SOUCAST:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SOUCAST;
                break;
            case NsesssV3.TYPOVY_SPIS:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.TYPOVY_SPIS;
                break;
            case NsesssV3.SPISOVY_PLAN:
                identNode = ValuesGetter.getXChild(node, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SPISOVY_PLAN;
                break;
            case NsesssV3.DOKUMENT:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.DOKUMENT;
                break;
            case NsesssV3.SPIS:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.SPIS;
                break;
            case NsesssV3.DIL:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.DIL;
                break;
            case NsesssV3.KOMPONENTA:
                identNode = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
                druhEntity = DruhEntity.KOMPONENTA;
                break;
            case NsesssV3.SKARTACNI_RIZENI:
                identNode = ValuesGetter.getXChild(node, NsesssV3.IDENTIFIKATOR);
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
        IndetifierWithSource zdrojIdent = new IndetifierWithSource(zdroj, hodnota);

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
    static public Element getIdentifikator(Element element) {
        String elementName = element.getNodeName();
        Element identifikator;

        if (elementName.equals(NsesssV3.SPISOVY_PLAN)) {
            identifikator = ValuesGetter.getXChild(element, NsesssV3.IDENTIFIKATOR);
            return identifikator;
        } else {
            identifikator = ValuesGetter.getXChild(element, NsesssV3.EVIDENCNI_UDAJE,
                    NsesssV3.IDENTIFIKACE, NsesssV3.IDENTIFIKATOR);
            return identifikator;
        }
    }

    static public String getIdentifikatory(Element node) {
        String nodename = node.getNodeName();
        String hodnota = "nenalezeno", zdroj = "nenalezeno";
        Node identifikator;
        if (nodename.equals(NsesssV3.SPISOVY_PLAN)) {
            identifikator = ValuesGetter.getXChild(node, NsesssV3.IDENTIFIKATOR);
            if (identifikator != null) {
                hodnota = identifikator.getTextContent();
                boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
                if (ma) {
                    zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
                }
                return "(Ident. hodnota: " + hodnota + ", zdroj: " + zdroj + ")";
            }
        }
        identifikator = ValuesGetter.getXChild(node, NsesssV3.EVIDENCNI_UDAJE,
                "nsesss:Identifikace", NsesssV3.IDENTIFIKATOR);
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

    static private String getJmeno(Node node) {
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
     * @param detailChyby
     * @param mistoChyby
     */
    void pridejChybu(final Rule<K06KontrolaContext> rule,
                     ErrorCode errorCode,
                     String detailChyby,
                     String mistoChyby) {
        pridejChybu(rule, errorCode, detailChyby, mistoChyby, null);
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
    public void validateImpl() {

        K06KontrolaContext k06KontrolaContext = new K06KontrolaContext(ctx.getMetsParser(), ctx);
        provedKontrolu(k06KontrolaContext, seznamPravidel);

    }

    public List<Element> getZakladniEnity() {
        return zakladniEntity;
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }

}
