package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import java.util.Arrays;

public class Pravidlo76 extends K06PravidloBase {

    static final public String OBS76 = "obs76";

    public Pravidlo76() {
        super(OBS76,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom rodičovské entity obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> se stejnou hodnotou.",
                "Uvedeno je chybně u věcné skupiny, typového spisu, součásti, dílu nebo spisu, že neobsahují dokumenty v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 616.");
    }

    //OBSAHOVÁ č.76 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, 
    // potom rodičovské entity obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
    // <nsesss:Manipulace> element <nsesss:AnalogovyDokument> se stejnou hodnotou.
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals(NsessV3.DIL) || zakladnientita.getNodeName().equals(NsessV3.SPIS)) {
                Element an_ze = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                        NsessV3.ANALOGOVY_DOKUMENT);
                if (an_ze == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <" + zakladnientita.getNodeName() + "> "
                            + "neobsahuje dětský element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(zakladnientita),
                            getMistoChyby(zakladnientita), kontrola.getEntityId(zakladnientita));
                }
                String analogovyzakladni = an_ze.getTextContent();

                List<Element> dokumenty = metsParser.getDokumenty();
                if (CollectionUtils.isEmpty(dokumenty)) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladnientita),
                            zakladnientita, kontrola.getEntityId(zakladnientita));
                }
                for (int j = 0; j < dokumenty.size(); j++) {
                    Element dokument = dokumenty.get(j);
                    Element node = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                            NsessV3.ANALOGOVY_DOKUMENT);
                    if (node == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. "
                                + getJmenoIdentifikator(dokument),
                                dokument, kontrola.getEntityId(dokument));
                    }
                    String hodnota = node.getTextContent();
                    if ("ano".equals(hodnota) && "ne".equals(analogovyzakladni)) {
                        List<Element> zakladniEntitaPlusDokument = Arrays.asList(zakladnientita, dokument);
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho základní entita. "
                                + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(dokument),
                                getMistoChyby(zakladnientita) + " " + getMistoChyby(dokument),
                                kontrola.getEntityId(zakladniEntitaPlusDokument));
                    }
                }
            }
            if (zakladnientita.getNodeName().equals(NsessV3.DOKUMENT)) {
                Element and = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                        NsessV3.ANALOGOVY_DOKUMENT);
                if (and == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. "
                            + getJmenoIdentifikator(zakladnientita),
                            getMistoChyby(zakladnientita), kontrola.getEntityId(zakladnientita));
                }
                String analogovyzakladni = and.getTextContent();
                if ("ano".equals(analogovyzakladni)) {
                    // nalezeni alespon jedne rodicovske vecne skupiny
                    List<Element> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
                    List<Element> vecneSkupiny = ValuesGetter.getAllChildNodes(zakladnientita, vsechnyVecneSkupiny);
                    if (CollectionUtils.isEmpty(vecneSkupiny)) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:VecnaSkupina>. "
                                + getJmenoIdentifikator(zakladnientita),
                                zakladnientita, kontrola.getEntityId(zakladnientita));
                    }
                    for (Element vs : vecneSkupiny) {
                        Element elAnalogovyDokumentVecSkupiny = ValuesGetter.getXChild(vs, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                                NsessV3.ANALOGOVY_DOKUMENT);
                        if (elAnalogovyDokumentVecSkupiny == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>. "
                                    + getJmenoIdentifikator(vs),
                                    vs, kontrola.getEntityId(vs));
                        }
                        String ad = elAnalogovyDokumentVecSkupiny.getTextContent();
                        if ("ne".equals(ad)) {
                            List<Element> dokumentPlusVecnaSkupina = Arrays.asList(zakladnientita, vs);
                            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho věcná skupina. "
                                    + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(vs),
                                    getMistoChyby(zakladnientita) + " " + getMistoChyby(elAnalogovyDokumentVecSkupiny),
                                    kontrola.getEntityId(dokumentPlusVecnaSkupina));
                        }
                    }
                }
            }
        }
    }

}
