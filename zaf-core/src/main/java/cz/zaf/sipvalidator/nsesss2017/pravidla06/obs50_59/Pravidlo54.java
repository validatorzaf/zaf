package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.google.common.base.Objects;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.EntityId;
import cz.zaf.sipvalidator.nsesss2017.EntityId.DruhEntity;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.PairZdrojIdent;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// OBSAHOVÁ č.54: Každý element mets:div obsahuje dětský element podle struktury
// entit/objektů (od spisového plánu po komponentu)
// v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu 
// v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu
// ID příslušné entity/objektu v sekci amdSec
// (entita/objekt v hierarchii dětských elementů mets:digiprovMD, mets:mdWrap,
// mets:xmlData, tp:TransakcniLogObjektu, tp:TransLogInfo,
// tp:Objekt, tp:Identifikator, tns:HodnotaID a tns:ZdrojID odpovídá v hodnotách
// hodnotám elementu nsesss:Identifikator a
// jeho atributu zdroj příslušné entity/objektu v sekci dmdSec). Uvedená
// entita/objekt je v sekci dmdSec, amdSec a structMap
// uvedena právě jednou. Pokud existuje jakýkoli element nsesss:KrizovyOdkaz a
// obsahuje atribut pevny s hodnotou ano, potom
// je entita/objekt typu součást, typový spis, věcná skupina nebo spisový plán v
// sekci dmdSec uvedená multiplicitně. V takovém případě je stejná entita/objekt
// uvedena v sekci structMap právě jednou
// (atribut DMDID obsahuje ID libovolného výskytu příslušné
// entity/objektu).
//
public class Pravidlo54 extends K06PravidloBase {

    static final public String OBS54 = "obs54";

    /**
     * List entit z DMDSec
     */
    List<Entita> listEntit = new ArrayList<>();

    private Map<PairZdrojIdent, Entita> entitaMap = new HashMap<>();

    /**
     * DMD ID to entity
     */
    private Map<String, Entita> dmdIdEntityMap = new HashMap<>();

    /**
     * ADM ID to entity
     */
    private Map<String, Entita> admIdEntityMap = new HashMap<>();

    public Pravidlo54() {
        super(OBS54,
                "Každý element mets:div obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů mets:digiprovMD, mets:mdWrap, mets:xmlData, tp:TransakcniLogObjektu, tp:TransLogInfo, tp:Objekt, tp:Identifikator, tns:HodnotaID a tns:ZdrojID odpovídá v hodnotách hodnotám elementu nsesss:Identifikator a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec). Uvedená entita/objekt je v sekci dmdSec, amdSec a structMap uvedena právě jednou. Pokud existuje jakýkoli element nsesss:KrizovyOdkaz a obsahuje atribut pevny s hodnotou ano, potom je entita/objekt typu součást, typový spis, věcná skupina nebo spisový plán v sekci dmdSec uvedená multiplicitně. V takovém případě je stejná entita/objekt uvedena v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).",
                "Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol.",
                "Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3).");

    }

    @Override
    protected void kontrola() {
        //získány všechny entity ve tvaru (Xkrát OdkazMetsDiv - Xx OdkatAmdSec - Xx OdkazDmdSec)
        //správně by to mělo být takto (1x OdkazMetsDiv - 1x OdkatAmdSec - 1x OdkazDmdSec)
        //když bude křížový odkaz nastanou výjimky
        //KO by měl vypadat takto  (1x OdkazMetsDiv - 1x OdkatAmdSec - Xx OdkazDmdSec kolik je odkazů) přičemž nesedí id u metsDiv
        // zbytek (0x || >1x OdkazMetsDiv - 0x || >1x OdkatAmdSec - 0x || >1x OdkazDmdSec) by melo být špatně
        //najdi všechny entity v dmd Sec a podle hodnot identifikátoru je rozřaď 

        // do seznamu objektů Entit (se stejným identifikátorem do jednoho objektu Entity)
        zpracujElementyDmdSec();

        // najdi všechny odkazy v amdSec a přiřad je k již nalezeným Entitám z dmdSec
        zpracujElementyAmdSec();

        // najdi všechny odkazy metsDiv  a přiřad je k již nalezeným Entitám
        zpracujElementyMetsDiv();

        kontrolaEntit();

    }

    private void kontrolaEntit() {
        for (Entita kontrolovanaEntita : listEntit) {
            kontrolovanaEntita.kontrola();
        }
    }


    private List<Element> getChybneElementyZOdkazuDmdSec(List<OdkazDmdSec> odkazyDmdSec) {
        List<Element> listElementu = new ArrayList<>();
        for (OdkazDmdSec odkazDmdSec : odkazyDmdSec) {
            Element element = odkazDmdSec.getElement();
            listElementu.add(element);
        }
        return listElementu;
    }

    private void zpracujElementyDmdSec() {
        String[] listNsesssEntit = {NsessV3.SPISOVY_PLAN, NsessV3.VECNA_SKUPINA, NsessV3.SOUCAST, NsessV3.TYPOVY_SPIS,
            NsessV3.DIL, NsessV3.SPIS, NsessV3.DOKUMENT, NsessV3.KOMPONENTA};
        for (String jmenoNsesssEntity : listNsesssEntit) {
            List<Element> listHledanychEntit = metsParser.getNodes(jmenoNsesssEntity);
            // 
            for (Element entElem : listHledanychEntit) {
                EntityId entitaId = K06_Obsahova.getEntityId(entElem);
                PairZdrojIdent zdrojIdent = entitaId.getZdrojIdent();
                Entita entita = getOrCreateEntity(zdrojIdent);

                String id = hodnotaAtributu(entElem, "ID");
                OdkazDmdSec odkazDmdSec = new OdkazDmdSec(entitaId, id, entElem);
                dmdIdEntityMap.put(id, entita);
                entita.addOdkazDmdSec(odkazDmdSec);
            }
        }
        if (CollectionUtils.isEmpty(listEntit)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná entita v sekci dmdSec.");
        }
    }


    private void zpracujElementyAmdSec() {
        List<Element> listAmdSec = metsParser.getNodes(MetsElements.AMD_SEC);
        if (CollectionUtils.isEmpty(listAmdSec)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <mets:amdSec>.");
        }
        for (Element elAmdSec : listAmdSec) {
            OdkazAmdSec novyOdkazAmdSec = createOdkazAmdSec(elAmdSec);
            Entita e = getOrCreateEntity(novyOdkazAmdSec.getZdrojIdent());
            admIdEntityMap.put(novyOdkazAmdSec.getID(), e);
            e.addOdkazAmdSec(novyOdkazAmdSec);
        }
    }

    /**
     * Vrati stavajici entitu nebo vytvori novou
     * 
     * @param zdrojIdent
     * @return
     */
    private Entita getOrCreateEntity(PairZdrojIdent zdrojIdent) {
        Entita entita = entitaMap.get(zdrojIdent);
        if (entita == null) {
            entita = createEntity();
            entitaMap.put(zdrojIdent, entita);
        }
        return entita;
    }

    private Entita createEntity() {
        Entita entita = new Entita();
        listEntit.add(entita);
        return entita;
    }

    private void zpracujElementyMetsDiv() {
        List<Element> listMetsDiv = metsParser.getNodes(MetsElements.DIV);
        if (CollectionUtils.isEmpty(listMetsDiv)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <mets:div>.");
        }
        for (Element elMetsDiv : listMetsDiv) {
            OdkazMetsDiv novyOdkazMetsDiv = new OdkazMetsDiv(elMetsDiv);
            Entita entita = dmdIdEntityMap.get(novyOdkazMetsDiv.getDMDID());
            if (entita == null) {
                // entita nenalezena
                entita = createEntity();
            } else {
                // overeni, zda ADMID vede na shodnou entitu
                Entita entitaAdm = admIdEntityMap.get(novyOdkazMetsDiv.getADMID());
                if (entita != entitaAdm) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut ADMID vede na jinou entitu než-li DMDID",
                                elMetsDiv);
                }
            }
            entita.addOdkazMetsDiv(novyOdkazMetsDiv);
        }
    }

    private class OdkazDmdSec {
        private final String id;
        private final Element element;
        private final EntityId entityId;

        public OdkazDmdSec(final EntityId entityId,
                           final String id,
                           final Element element) {
            this.id = id;
            this.element = element;
            this.entityId = entityId;

        }

        public String getId() {
            return id;
        }

        public Element getElement() {
            return element;
        }

        public EntityId getEntityId() {
            return entityId;
        }
    }

    private class Entita {
        List<OdkazDmdSec> odkazyDmdSec = new ArrayList<>();
        List<OdkazAmdSec> odkazyAmdSec = new ArrayList<>();
        List<OdkazMetsDiv> odkazyMetsDiv = new ArrayList<>();

        public Entita() {
        }

        public void addOdkazDmdSec(final OdkazDmdSec odkazDmdSec) {
            odkazyDmdSec.add(odkazDmdSec);
        }

        public void addOdkazMetsDiv(OdkazMetsDiv odkazMetsDiv) {
            odkazyMetsDiv.add(odkazMetsDiv);
        }

        public void addOdkazAmdSec(OdkazAmdSec odkazAmdSec) {
            odkazyAmdSec.add(odkazAmdSec);
        }

        /**
         * Vrati seznam vsech dotcenych elementu.
         * 
         * Pouziva se pro vypis mista chyby
         * 
         * @return
         */
        private List<Element> getAllElements() {
            List<Element> result = new ArrayList<>(odkazyDmdSec.size() + odkazyAmdSec.size() + odkazyMetsDiv.size());
            for (OdkazDmdSec odkaz : odkazyDmdSec) {
                result.add(odkaz.getElement());
            }
            for (OdkazAmdSec odkaz : odkazyAmdSec) {
                result.add(odkaz.getElement());
            }
            for (OdkazMetsDiv odkaz : odkazyMetsDiv) {
                result.add(odkaz.getElMetsDiv());
            }
            return result;
        }

        private List<Element> getDmdElements() {
            return odkazyDmdSec.stream().map(OdkazDmdSec::getElement).collect(Collectors.toList());

        }

        private List<Element> getAmdElements() {
            return odkazyAmdSec.stream().map(OdkazAmdSec::getElement).collect(Collectors.toList());

        }

        private void chybaBezDmdSec() {
            // Neexistuje zaznam ani ve strukt. mapě
            String popisChyby;
            if (odkazyMetsDiv.size() == 0) {
                popisChyby = "K entitě v amdSec chybí entita ve structMap i v dmdSec.";
            } else {
                // Neexistuje záznam v DMDSec
                popisChyby = "Entitě chybí odkaz v sekci dmdSec a zároveň existují odkazy v sekci metsDiv a amdSec.";
            }
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        popisChyby,
                        getAllElements());
        }

        private void chybaBezAmdSec() {
            String popisChyby;
            if (odkazyMetsDiv.size() == 0) {
                popisChyby = "K entitě v dmdSec chybí entita ve structMap i v amdSec.";
            } else {
                // Neexistuje záznam v AmdSec
                popisChyby = "Entitě chybí odkaz v sekci amdSec a zároveň existují odkazy v sekci metsDiv a dmdSec.";
            }

            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        popisChyby,
                        getAllElements());
        }

        private void chybaBezMetsDiv() {
            // volano jako posledni, tj. dmdSec i amdSec jsou neprazdne
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Entitě chybí odkaz v sekci mets:div a zároveň existují odkazy v sekci amdSec a dmdSec.",
                        getAllElements());
        }

        // volano po kontrole na prazdne seznamu, tj. obsahuje od kazde polozky alespon jednu
        private void chybaOpakovaniMetsDiv() {
            List<Element> listElMetsDiv = odkazyMetsDiv.stream().map(OdkazMetsDiv::getElMetsDiv)
                    .collect(Collectors.toList());

            OdkazDmdSec dmdSec = odkazyDmdSec.get(0);
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT, "Entita ve structMap je uvedena vícekrát.", listElMetsDiv,
                        dmdSec.getEntityId());
        }

        // overeni, ze identifikatory maji stejny typ entity
        // hodnoty musi byt shodne pokud jsou v jednom Elementu
        private void kontrolaShodnychDmd() {
            Iterator<OdkazDmdSec> it = this.odkazyDmdSec.iterator();
            OdkazDmdSec prvniDmd = it.next();
            while (it.hasNext()) {
                OdkazDmdSec dmd = it.next();
                // porovnani hodnot
                // staci porovnat typ
                if (!prvniDmd.getEntityId().getDruhEntity().equals(dmd.getEntityId().getDruhEntity())) {
                    nastavChybu(BaseCode.CHYBNY_ELEMENT,
                                "Entity nsesss se shodnými identifikátory nemají stejný typ.",
                                getDmdElements(), prvniDmd.getEntityId());
                }
            }
        }

        public void kontrola() {
            if (odkazyDmdSec.size() == 0) {
                chybaBezDmdSec();
            }
            if (odkazyAmdSec.size() == 0) {
                chybaBezAmdSec();
            }
            if (odkazyMetsDiv.size() == 0) {
                chybaBezMetsDiv();
            }
            if (odkazyMetsDiv.size() > 1) {
                chybaOpakovaniMetsDiv();
            }
            // zde je jiz metsDiv.size()==1
            OdkazMetsDiv odkazMetsDiv = odkazyMetsDiv.get(0);

            //  vice odkazu v amdSec
            if (odkazyDmdSec.size() == 1 && odkazyAmdSec.size() > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT, "Nalezeno vice odkazů v sekci AmdSec.",
                            getAmdElements(),
                            odkazyDmdSec.get(0).getEntityId());
            }

            kontrolaShodnychDmd();

            if (odkazyDmdSec.size() > 1) {
                // Vic odkazu je moznych jen v pripade krizovych odkazu?
                // je to tedy mozne jen nad urovni dokumentu vyse
                // konkretne: spisovy plan, věcné skupiny, spisu, součásti, dílu
                OdkazDmdSec odkazDmdSec = odkazyDmdSec.get(0);
                DruhEntity druhEntity = odkazDmdSec.getEntityId().getDruhEntity();
                if (druhEntity == DruhEntity.KOMPONENTA || druhEntity == DruhEntity.DOKUMENT) {
                    List<Element> listkomponent = getDmdElements();
                    nastavChybu(BaseCode.CHYBNY_ELEMENT, "Entita v dmdSec je uvedena vícekrát.",
                                listkomponent, odkazDmdSec.getEntityId());
                }
            }

            // tyhle by měly být v pořádku
            // amd by měl být 1, ale prý to NA neřeší, tak ten případ nejspíše může nastat                            
            for (OdkazDmdSec odkazDmdSec : odkazyDmdSec) {
                //kontrola rodice na základě identifikátoru - mělo by to pokrýt i KO
                zkontrolujRodice(odkazDmdSec, odkazMetsDiv);
            }

        }

        private void zkontrolujRodice(OdkazDmdSec odkazDmdSec, OdkazMetsDiv odkazMetsDiv) {
            Element elMetsDivParent = (Element) odkazMetsDiv.getElMetsDiv().getParentNode();
            EntityId entityId = odkazDmdSec.getEntityId();
            // kontrola, zda je entita korenova (spisovy plan)
            if (entityId.getDruhEntity().equals(DruhEntity.SPISOVY_PLAN)) {
                String elMetsDivParentName = elMetsDivParent.getNodeName();
                // musi byt primo pod strukturalni mapou, jinak chyba
                if (!MetsElements.STRUCTMAP.equals(elMetsDivParentName)) {
                    nastavChybu(BaseCode.CHYBNY_ELEMENT, "Element <nsesss:SpisovyPlan> je špatně zatříděn.",
                                getAllElements(), entityId);
                }
            } else {
                String dmdid = hodnotaAtributu(elMetsDivParent, "DMDID");
                // vrati odkazovanou entitu
                // ta musi existovat, jinak by nedoslo k zahajeni kontroly
                Entita entita = dmdIdEntityMap.get(dmdid);
                OdkazDmdSec odkazDmdSecParent = entita.getOdkazDmdSecById(dmdid);
                if (odkazDmdSecParent == null) {
                    nastavChybu(BaseCode.CHYBNY_ELEMENT, "Nebyl nalezen platný rodič v dmdSec.",
                                Arrays.asList(odkazMetsDiv.getElMetsDiv(), elMetsDivParent),
                                entityId);
                }

                overRodice(odkazDmdSec, odkazDmdSecParent, odkazMetsDiv);
            }
        }

        private void overRodice(OdkazDmdSec odkazDmdSec,
                                OdkazDmdSec odkazDmdSecParent,
                                OdkazMetsDiv odkazMetsDiv) {
            Element elOdkazu = odkazDmdSec.getElement();
            DruhEntity druhEntity = odkazDmdSec.getEntityId().getDruhEntity();

            DruhEntity parentDruhEntity = odkazDmdSecParent.getEntityId().getDruhEntity();
            Element parentElement = null;
            switch (druhEntity) {
            case VECNA_SKUPINA:
                if (DruhEntity.SPISOVY_PLAN.equals(parentDruhEntity)) {
                    parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                           NsessV3.SPISOVY_PLAN);
                } else
                if (DruhEntity.VECNA_SKUPINA.equals(parentDruhEntity)) {
                    parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                           NsessV3.MATERSKA_ENTITA, NsessV3.VECNA_SKUPINA);
                }
                break;
            case TYPOVY_SPIS:
                parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                       NsessV3.MATERSKA_ENTITA, NsessV3.VECNA_SKUPINA);
                break;
            case SOUCAST:
                parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                       NsessV3.MATERSKA_ENTITA, NsessV3.TYPOVY_SPIS);
                break;
            case SPIS:
                parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                       NsessV3.MATERSKA_ENTITA, NsessV3.VECNA_SKUPINA);
                break;
            case DIL:
                parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                       NsessV3.MATERSKA_ENTITA, NsessV3.SOUCAST);
                break;
            case DOKUMENT:
                if (DruhEntity.VECNA_SKUPINA.equals(parentDruhEntity)) {
                    parentElement = ValuesGetter.getXChild(elOdkazu, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                                                           NsessV3.MATERSKE_ENTITY, NsessV3.VECNA_SKUPINA);
                } else if (DruhEntity.SPIS.equals(parentDruhEntity)) {
                    parentElement = ValuesGetter.getXParent(elOdkazu, NsessV3.DOKUMENTY, NsessV3.SPIS);
                } else if (DruhEntity.DIL.equals(parentDruhEntity)) {
                    parentElement = ValuesGetter.getXParent(elOdkazu, NsessV3.DOKUMENTY, NsessV3.DIL);
                }
                break;
            case KOMPONENTA:
                parentElement = ValuesGetter.getXParent(elOdkazu, NsessV3.KOMPONENTY, NsessV3.DOKUMENT);
                break;
            default:
                // nop
                break;
            }

            if (parentElement == null) {
                nastavChybu(BaseCode.CHYBNY_ELEMENT, "Nenalezena očekávaná rodičovská entita ve structMap.",
                            Arrays.asList(odkazDmdSec.getElement(), odkazDmdSecParent.getElement(),
                                          odkazMetsDiv.getElMetsDiv()),
                            Arrays.asList(odkazDmdSec.getEntityId(), odkazDmdSecParent.getEntityId()));
            } else {
                EntityId nalezenyRodicEntityId = K06_Obsahova.getEntityId(parentElement);

                PairZdrojIdent parentIdentZdroj = odkazDmdSecParent.getEntityId().getZdrojIdent();

                if (!parentIdentZdroj.equals(nalezenyRodicEntityId.getZdrojIdent())) {
                    nastavChybu(BaseCode.CHYBNY_ELEMENT,
                                "Nenalezen očekávaný rodičovský element. Rodič nemá očekávané hodnoty v identifikátoru",
                                Arrays.asList(odkazDmdSec.getElement(), odkazDmdSecParent.getElement(),
                                              odkazMetsDiv.getElMetsDiv(), parentElement),
                                Arrays.asList(odkazDmdSec.getEntityId(), odkazDmdSecParent.getEntityId(),
                                              nalezenyRodicEntityId));
                }

            }

        }

        public OdkazDmdSec getOdkazDmdSecById(String dmdid) {
            for (OdkazDmdSec odkaz : odkazyDmdSec) {
                if (Objects.equal(dmdid, odkaz.getId())) {
                    return odkaz;
                }
            }
            return null;
        }
    }

    private class OdkazAmdSec {

        final Element element;
        final String id;
        final PairZdrojIdent zdrojIdent;

        public OdkazAmdSec(final Element elAmdSec, final String id,
                           final PairZdrojIdent zdrojIdent) {
            this.element = elAmdSec;
            this.id = id;
            this.zdrojIdent = zdrojIdent;
        }

        public Element getElement() {
            return element;
        }

        public String getID() {
            return id;
        }

        public PairZdrojIdent getZdrojIdent() {
            return zdrojIdent;
        }

    }

    private class OdkazMetsDiv {

        final private Element elMetsDiv;
        final private String type;
        final private String admId;
        final private String dmdId;

        public OdkazMetsDiv(Element elMetsDiv) {
            this.elMetsDiv = elMetsDiv;
            this.type = hodnotaAtributu(elMetsDiv, "TYPE");
            this.admId = hodnotaAtributu(elMetsDiv, "ADMID");
            this.dmdId = hodnotaAtributu(elMetsDiv, "DMDID");
        }

        public Element getElMetsDiv() {
            return elMetsDiv;
        }

        public String getTYPE() {
            return type;
        }

        public String getDMDID() {
            return dmdId;
        }

        public String getADMID() {
            return admId;
        }
    }

    OdkazAmdSec createOdkazAmdSec(Element elAmdSec) {
        Element elIdentifikator = ValuesGetter.getXChild(elAmdSec, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData",
                                                         "tp:TransakcniLogObjektu", "tp:TransLogInfo",
                                                         "tp:Objekt", "tp:Identifikator");
        if (elIdentifikator == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <tp:Identifikator>.", elAmdSec);
        }

        Element elHodnotaID = ValuesGetter.getXChild(elIdentifikator, "tns:HodnotaID");
        if (elHodnotaID == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <tns:HodnotaID>.", elIdentifikator);
        }
        Element elZdroj = ValuesGetter.getXChild(elIdentifikator, "tns:ZdrojID");
        if (elZdroj == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <tns:ZdrojID>.", elIdentifikator);
        }

        String id = hodnotaAtributu(elAmdSec, "ID");
        String identifikatorHodnota = elHodnotaID.getTextContent();
        String identifikatorZdroj = elZdroj.getTextContent();
        PairZdrojIdent zdrojIdent = new PairZdrojIdent(identifikatorZdroj, identifikatorHodnota);

        return new OdkazAmdSec(elIdentifikator, id, zdrojIdent);
    }

    public String hodnotaAtributu(Element element, String atrName) {
        String atrValue = element.getAttribute(atrName);
        if (StringUtils.isBlank(atrValue)) {
            String elementName = element.getNodeName();
            if (elementName.startsWith("nsesss")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <" + elementName + "> neobsahuje atribut " + atrName,
                            element, K06_Obsahova.getEntityId(element));
            } else {
                if (elementName.equals(MetsElements.DIV)) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Entita ve structMap neobsahuje atribut " + atrName, element);
                } else {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <" + elementName + "> neobsahuje atribut " + atrName, element);
                }

            }
        }
        return atrValue;
    }

}
