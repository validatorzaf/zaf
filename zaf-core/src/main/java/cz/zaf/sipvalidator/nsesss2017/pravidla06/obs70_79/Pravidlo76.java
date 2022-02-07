package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo76 extends K06PravidloBaseOld {

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
    protected boolean kontrolaPravidla() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals("nsesss:Dil") || zakladnientita.getNodeName().equals(
                                                                                                         "nsesss:Spis")) {
                Node an_ze = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                                    "nsesss:AnalogovyDokument");
                if (an_ze == null) {
                    return nastavChybu("Element <" + zakladnientita.getNodeName()
                            + "> neobsahuje dětský element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(
                                                                                                                 zakladnientita),
                                       getMistoChyby(zakladnientita));
                }
                String analogovyzakladni = an_ze.getTextContent();

                List<Element> dokumenty = metsParser.getDokumenty();
                if (CollectionUtils.isEmpty(dokumenty)) {
                    return nastavChybu("Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(
                                                                                                            zakladnientita),
                                       zakladnientita);
                }
                for (int j = 0; j < dokumenty.size(); j++) {
                    Element dokument = dokumenty.get(j);
                    Node node = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                                       "nsesss:AnalogovyDokument");
                    if (node == null) {
                        return nastavChybu("Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. "
                                + getJmenoIdentifikator(dokument), getMistoChyby(dokument));
                    }
                    String hodnota = node.getTextContent();
                    if (hodnota.equals("ano") && analogovyzakladni.equals("ne")) {
                        return nastavChybu("Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho základní entita. "
                                + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(dokument),
                                          getMistoChyby(zakladnientita) + " " + getMistoChyby(dokument));
                    }
                }
            }
            if (zakladnientita.getNodeName().equals("nsesss:Dokument")) {
                Node and = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                                  "nsesss:AnalogovyDokument");
                if (and == null) {
                    return nastavChybu("Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. "
                            + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
                }
                String analogovyzakladni = and.getTextContent();
                if (analogovyzakladni.equals("ano")) {
                    // nalezeni alespon jedne rodicovske vecne skupiny
                    List<Element> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
                    List<Element> vecneSkupiny = ValuesGetter.getAllChildNodes(zakladnientita, vsechnyVecneSkupiny);
                    if (CollectionUtils.isEmpty(vecneSkupiny)) {
                        return nastavChybu("Nenalezen element <nsesss:VecnaSkupina>. "
                                           + getJmenoIdentifikator(zakladnientita),
                                           zakladnientita);
                    }
                    for (Element vs : vecneSkupiny) {
                        Element n = ValuesGetter.getXChild(vs, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                                        "nsesss:AnalogovyDokument");
                        if (n == null) {
                            return nastavChybu("Nenalezen element <nsesss:AnalogovyDokument>. " 
                                                + getJmenoIdentifikator(vs),
                                               vs);
                        }
                        String ad = n.getTextContent();
                        if (ad.equals("ne")) {
                            return nastavChybu("Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho věcná skupina. "
                                    + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(vs),
                                              getMistoChyby(zakladnientita) + " " + getMistoChyby(n));
                        }
                    }
                }
            }
        }
        return true;
    }

}
