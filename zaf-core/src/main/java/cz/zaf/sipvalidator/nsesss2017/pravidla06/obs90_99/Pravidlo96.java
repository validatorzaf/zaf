package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.96 Každá základní entita a každá entita typový spis
// (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů
// <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými
// hodnotami, jaké obsahují v hierarchii dětských elementů
// <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské
// entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást
// (<nsesss:Soucast>).",
public class Pravidlo96 extends K06PravidloBaseOld {

    static final public String OBS96 = "obs96";

    public Pravidlo96() {
        super(OBS96,
                "Každá základní entita a každá entita typový spis (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).",
                "Chybně jsou uvedeny spisové znaky.",
                "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (Node zakladnientita: zakladniEntity) {
            Node n_zakl_jsz = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                     "nsesss:JednoduchySpisovyZnak");
            Node n_zakl_pusz = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                      "nsesss:PlneUrcenySpisovyZnak");
            if (n_zakl_jsz == null)
                return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladnientita),
                        zakladnientita);
            if (n_zakl_pusz == null)
                return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladnientita),
                        zakladnientita);

            if (zakladnientita.getNodeName().equals("nsesss:Dil")) {
                if (!kontrolaDilu(zakladnientita, n_zakl_jsz, n_zakl_pusz)) {
                    return false;
                }
            } else {
                String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
                String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

                String jednoduchy, plneUrceny;
                List<Node> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
                List<Node> vecneSkupiny = ValuesGetter.getAllChildNodes(zakladnientita, vsechnyVecneSkupiny);
                if (CollectionUtils.isEmpty(vecneSkupiny)) {
                    return nastavChybu("Nenalezen element <nsesss:VecnaSkupina> základní entity.",
                                       zakladnientita);
                }
                Node prvniVecnaSkupina = vecneSkupiny.get(0);
                Node n_j = ValuesGetter.getXChild(prvniVecnaSkupina, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:JednoduchySpisovyZnak");
                Node n_p = ValuesGetter.getXChild(prvniVecnaSkupina, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:PlneUrcenySpisovyZnak");
                if (n_j == null)
                    return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(
                                                                                                                   zakladnientita),
                                       prvniVecnaSkupina);
                if (n_p == null)
                    return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(
                                                                                                                   zakladnientita),
                                       prvniVecnaSkupina);
                jednoduchy = n_j.getTextContent();
                plneUrceny = n_p.getTextContent();
                boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                if (!b) {
                    return nastavChybu("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita),
                                      getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz) + " "
                                              + getMistoChyby(n_j) + " " + getMistoChyby(n_p));
                }
            }
        }
        return true;
    }

    private boolean kontrolaDilu(Node dil,
                                 Node n_zakl_jsz, Node n_zakl_pusz) {
        String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
        String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

        Node n_soucast =  ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni", 
                                                 "nsesss:MaterskaEntita", NsessV3.SOUCAST);
        if (n_soucast == null) {
            return nastavChybu("Nenalezen element <nsesss:Soucast>. " + getJmenoIdentifikator(dil), dil);
        }
        Node trideniSoucasti = ValuesGetter.getXChild(n_soucast, NsessV3.EVIDENCNI_UDAJE, 
                                                      "nsesss:Trideni");
        if(trideniSoucasti==null) {
            return nastavChybu("Nenalezen element <nsesss:Trideni>. " + getJmenoIdentifikator(n_soucast),
                               n_soucast);
        }
        Node n_soucast_jsz = ValuesGetter.getXChild(trideniSoucasti, "nsesss:JednoduchySpisovyZnak");
        if (n_soucast_jsz == null)
            return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(dil),
                               n_soucast);
        Node n_soucast_pusz = ValuesGetter.getXChild(trideniSoucasti, "nsesss:PlneUrcenySpisovyZnak");
        if (n_soucast_pusz == null)
            return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(dil),
                               n_soucast);

        Node n_typ = ValuesGetter.getXChild(trideniSoucasti, "nsesss:MaterskaEntita", "nsesss:TypovySpis"); 
        if (n_typ == null) {
            return nastavChybu("Nenalezen element <nsesss:TypovySpis>. " + getJmenoIdentifikator(dil),
                               dil);
        }
        Node n_typ_jsz = ValuesGetter.getXChild(n_typ, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                "nsesss:JednoduchySpisovyZnak");
        if (n_typ_jsz == null)
            return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(dil),
                               n_typ);
        Node n_typ_pusz = ValuesGetter.getXChild(n_typ, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                 "nsesss:PlneUrcenySpisovyZnak");
        if (n_typ_pusz == null)
            return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(dil),
                               n_typ);

        String jednoduchy, plneUrceny;
        jednoduchy = n_soucast_jsz.getTextContent();
        plneUrceny = n_soucast_pusz.getTextContent();

        String tyt_p = n_typ_pusz.getTextContent();
        String typ_j = n_typ_jsz.getTextContent();

        boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
        if (!b)
            return nastavChybu("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(dil) + " "
                    + getJmenoIdentifikator(n_soucast_jsz),
                               getMistoChyby(n_zakl_jsz) + " "
                                       + getMistoChyby(n_zakl_pusz) + " " + getMistoChyby(n_soucast_jsz) + " "
                                       + getMistoChyby(n_soucast_pusz));

        List<Node> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
        List<Node> vecneSkupiny = ValuesGetter.getAllChildNodes(dil, vsechnyVecneSkupiny);
        if (vecneSkupiny == null || vecneSkupiny.isEmpty())
            return nastavChybu("Nenalezen element <nsesss:VecnaSkupina> základní entity. "
                    + getJmenoIdentifikator(dil), getMistoChyby(dil));
        //ss
        Node n_j = ValuesGetter.getXChild(vecneSkupiny.get(vecneSkupiny.size() - 1), NsessV3.EVIDENCNI_UDAJE,
                                          "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
        Node n_p = ValuesGetter.getXChild(vecneSkupiny.get(vecneSkupiny.size() - 1), NsessV3.EVIDENCNI_UDAJE,
                                          "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
        if (n_j == null)
            return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(
                                                                                                           dil),
                               getMistoChyby(vecneSkupiny.get(vecneSkupiny.size() - 1)));
        if (n_p == null)
            return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(
                                                                                                           dil),
                               getMistoChyby(vecneSkupiny.get(vecneSkupiny.size() - 1)));
        String jednoduchy_vs = n_j.getTextContent();
        String plneUrceny_vs = n_p.getTextContent();
        boolean b1 = jednoduchy_vs.equals(typ_j) && plneUrceny_vs.equals(tyt_p);
        if (!b1)
            return nastavChybu("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(dil),
                              getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz) + " "
                                      + getMistoChyby(n_typ_jsz) + " " + getMistoChyby(n_typ_pusz));
        return true;
    }

}