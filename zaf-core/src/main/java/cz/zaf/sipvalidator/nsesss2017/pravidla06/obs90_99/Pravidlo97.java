package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.97
// Pokud existuje více než jedna základní entita, všechny obsahují
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni>
// elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak>
// se stejnými hodnotami.
public class Pravidlo97 extends K06PravidloBase {
    
    public static final String OBS97 = "obs97";
    
    public Pravidlo97() {
        super(OBS97,
                "Pokud existuje více než jedna základní entita, všechny obsahují v hierarchii dětských elementů "
                + "<nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> "
                + "se stejnými hodnotami.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.34 NSESSS.");
    }
    
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        
        Element zakladniEntitaPrvni = zakladniEntity.get(0);
        Element n0_j = ValuesGetter.getXChild(zakladniEntitaPrvni, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
        Element n0_p = ValuesGetter.getXChild(zakladniEntitaPrvni, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (n0_j == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                    + getJmenoIdentifikator(zakladniEntitaPrvni), getMistoChyby(zakladniEntitaPrvni),
                    kontrola.getEntityId(zakladniEntitaPrvni));
        }
        if (n0_p == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                    + getJmenoIdentifikator(zakladniEntitaPrvni), getMistoChyby(zakladniEntitaPrvni),
                    kontrola.getEntityId(zakladniEntitaPrvni));
        }
        
        String jednoduchy = n0_j.getTextContent();
        String plneUrceny = n0_p.getTextContent();
        for (int i = 1; i < zakladniEntity.size(); i++) {
            Element zakladniEntita = zakladniEntity.get(i);
            Element n_j = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                    NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
            Element n_p = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                    NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
            if (n_j == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladniEntita), getMistoChyby(zakladniEntita),
                        kontrola.getEntityId(zakladniEntita));
            }
            
            if (n_p == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladniEntita), getMistoChyby(zakladniEntita),
                        kontrola.getEntityId(zakladniEntita));
            }
            
            String jednoduchySpZnZaklEnt = n_j.getTextContent();
            String plneUrcenySpZnZaklEnt = n_p.getTextContent();
            boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
            if (!b) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladniEntita),
                        getMistoChyby(n0_j) + " " + getMistoChyby(n0_p) + " " + getMistoChyby(n_j) + " " + getMistoChyby(n_p),
                        kontrola.getEntityId(zakladniEntita));
            }
        }
    }
    
}
