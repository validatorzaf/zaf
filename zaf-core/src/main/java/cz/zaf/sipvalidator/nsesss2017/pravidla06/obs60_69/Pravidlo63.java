package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import org.apache.commons.lang.StringUtils;

public class Pravidlo63 extends K06PravidloBase {

    static final public String OBS63 = "obs63";

    public Pravidlo63() {
        super(OBS63,
                "Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
                "Chybí odůvodnění vyřízení jiným způsobem.",
                "Příloha č. 2 NSESSS, ř. 1270.");
    }

    //OBSAHOVÁ č.63 Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, 
    // potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    @Override
    protected void kontrola() {
        List<Element> vyrizenis = metsParser.getNodes(NsessV3.VYRIZENI);
        for (Element elVyrizeni : vyrizenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(elVyrizeni, NsessV3.ZPUSOB, "jiný způsob");
            if (maZpusobSHodnotou) {
                Element elOduvodneni = ValuesGetter.getXChild(elVyrizeni, NsessV3.ODUVODNENI);
                Element elDokument = kontrola.getEntity(elVyrizeni);
                if (elOduvodneni == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(elVyrizeni),
                            elVyrizeni, kontrola.getEntityId(elDokument));
                }
                String hodnotaElOduvodneni = elOduvodneni.getTextContent();
                if (StringUtils.isBlank(hodnotaElOduvodneni)) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(elVyrizeni),
                            elOduvodneni, kontrola.getEntityId(elDokument));
                }
            }
        }
        List<Element> vyrizeniUzavrenis = metsParser.getNodes(NsessV3.VYRIZENI_UZAVRENI);
        for (Element elVyrizeniUzavreni : vyrizeniUzavrenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(elVyrizeniUzavreni, NsessV3.ZPUSOB, "jiný způsob");
            if (maZpusobSHodnotou) {
                Element elOduvodneni = ValuesGetter.getXChild(elVyrizeniUzavreni, NsessV3.ODUVODNENI);
                Element elDokument = kontrola.getEntity(elVyrizeniUzavreni);
                if (elOduvodneni == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(elVyrizeniUzavreni),
                            elVyrizeniUzavreni, kontrola.getEntityId(elDokument));
                }
                String hodnotaElOduvodneni = elOduvodneni.getTextContent();
                if (StringUtils.isBlank(hodnotaElOduvodneni)) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(elVyrizeniUzavreni),
                            elOduvodneni, kontrola.getEntityId(elDokument));
                }
            }
        }
    }

}
