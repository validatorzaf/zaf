package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

import java.util.Arrays;

// OBSAHOVÁ č.98 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů 
// <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, 
// jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> 
// jakékoli dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>).
public class Pravidlo98 extends K06PravidloBase {
    
    public static final String OBS98 = "obs98";
    
    public Pravidlo98() {
        super(OBS98,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>).",
                "Chybně jsou uvedeny spisové znaky.",
                "§ 12 odst. 3 a 4 vyhlášky č. 259/2012 Sb.");
    }
    
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals(NsesssV4.SPIS)) {
                checkDokumenty(zakladnientita);
            }
            
            if (zakladnientita.getNodeName().equals(NsesssV4.DIL)) {
                checkDokumenty(zakladnientita);
                
                Element elSpisy = ValuesGetter.getXChild(zakladnientita, NsesssV4.SPISY);
                if (elSpisy != null) {
                    List<Element> listSpisy = ValuesGetter.getChildNodes(elSpisy, NsesssV4.SPIS);
                    kontrolaEntity(zakladnientita, listSpisy);
                    for (Element elSpis : listSpisy) {
                        checkDokumenty(elSpis);
                    }
                }
            }
            
        }
    }
    
    private void checkDokumenty(Element elParent) {
        Element elDokumenty = ValuesGetter.getXChild(elParent, NsesssV4.DOKUMENTY);
        if (elDokumenty != null) {
            List<Element> listDokumenty = ValuesGetter.getChildNodes(elDokumenty, NsesssV4.DOKUMENT);
            kontrolaEntity(elParent, listDokumenty);
        }
    }
    
    private void kontrolaEntity(Element zakladniEntita, List<Element> dokumenty) {
        Element n_zakl_jsz = ValuesGetter.getXChild(zakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                NsesssV4.JEDNODUCHY_SPISOVY_ZNAK);
        if (n_zakl_jsz == null) {
            String detailChyby = "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladniEntita);
            nastavChybu(BaseCode.CHYBI_ELEMENT, detailChyby, getMistoChyby(zakladniEntita),
                    kontrola.getEntityId(zakladniEntita));
        }
        Element n_zakl_pusz = ValuesGetter.getXChild(zakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                NsesssV4.PLNE_URCENY_SPISOVY_ZNAK);
        if (n_zakl_pusz == null) {
            String detailChyby = "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladniEntita);
            nastavChybu(BaseCode.CHYBI_ELEMENT, detailChyby, getMistoChyby(zakladniEntita),
                    kontrola.getEntityId(zakladniEntita));
        }
        String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
        String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();
        
        String jednoduchy, plneUrceny;
        
        for (int j = 0; j < dokumenty.size(); j++) {
            Element dokument = dokumenty.get(j);
            Element n_j = ValuesGetter.getXChild(dokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                    NsesssV4.JEDNODUCHY_SPISOVY_ZNAK);
            if (n_j == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument),
                        getMistoChyby(dokument), kontrola.getEntityId(dokument));
            }
            jednoduchy = n_j.getTextContent();
            Element n_p = ValuesGetter.getXChild(dokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                    NsesssV4.PLNE_URCENY_SPISOVY_ZNAK);
            if (n_p == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument), getMistoChyby(dokument),
                        kontrola.getEntityId(dokument));
            }
            
            plneUrceny = n_p.getTextContent();
            
            boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
            if (!b) {
                List<Element> zakladniEntitaPlusDokument = Arrays.asList(zakladniEntita, dokument);
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + kontrola.getJmenoIdentifikator(zakladniEntita)
                        + " " + kontrola.getJmenoIdentifikator(dokument),
                        getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz)
                        + " " + getMistoChyby(n_j) + " " + getMistoChyby(n_p),
                        kontrola.getEntityId(zakladniEntitaPlusDokument));
            }
        }
    }
    
}
