package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule103 extends EadRule {

    static final public String CODE = "obs103";
    static final public String RULE_TEXT = "Atributy \"linkrole\" a \"linktitle\" elementu <ead:relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", obsahují povolené hodnoty (viz karta Tabulka rolí), které si vzájemně odpovídají. Atribut \"linktitle\" může obsahovat jinou hodnotu než povoluje Tabulka rolí.";
    static final public String RULE_ERROR = "Atributy \"linkrole\" a \"linktitle\" elementu <ead:relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", obsahují nepovolené hodnoty. Případně si jejich hodnoty vzájemně neodpovídají.";
    static final public String RULE_SOURCE = "Část 8.2 a 8.2.3 profilu EAD3 MV ČR";

    static private final Map<String, String> allowed = new HashMap<>();

    {
        allowed.put("AUTHOR", " autor");
        allowed.put("AUTHOR_DIALOGS", "	autor dialogu");
        allowed.put("AUTHOR_ACCOMP_TEXT", "autor doprovodného textu");
        allowed.put("COMPOSER", "autor hudby/skladatel");
        allowed.put("CHOREOGRAPHER", "autor choreografie/choreograf");
        allowed.put("AUTHOR_COMMENT", "autor komentáře");
        allowed.put("AUTHOR_TOPIC", "autor námětu");
        allowed.put("LYRICIST", "autor textové složky/textař");
        allowed.put("AUTHOR_TEXT", "autor textu");
        allowed.put("TRICKS_EFFECTS", "autor triků a speciálních efektů");
        allowed.put("ARTWORK", "autorské dílo");
        allowed.put("PUBLISHER_OWNER", "vydavatel");
        allowed.put("PUBLISHER", "vydavatel/nakladatel");
        allowed.put("SEALER", "pečetitel");
        allowed.put("PRODUCER", "produkční společnost/producent");
        allowed.put("CLIENT", "objednavatel/příjemce");
        allowed.put("DISTRIBUTOR", "distributor");
        allowed.put("RECIPIENT", "příjemce");
        allowed.put("APPLICANT", "žadatel");
        allowed.put("HOLDER_SECURITY", "držitel cenného papíru");
        allowed.put("SENDER", "odesílatel");
        allowed.put("APPROVER", "schvalovatel technického výkresu");
        allowed.put("BUILDER", "stavitel");
        allowed.put("DIRECTOR", "režisér");
        allowed.put("SCRIPTWRITER", "scénárista");
        allowed.put("CAMERAMAN", "kameraman");
        allowed.put("MUSIC_INTERPRETER", "interpret hudby");
        allowed.put("PHOTOGRAPHER", "fotograf");
        allowed.put("REDACTOR", "redaktor");
        allowed.put("CARTOGRAPHER", "kartograf");
        allowed.put("EDITOR", "editor");
        allowed.put("DRAFTSMAN", "kreslič");
        allowed.put("OWNER_AUTHORIZED", "majitel typáře");
        allowed.put("CREATOR_TECHNICAL", "tvůrce technického zpracování");
        allowed.put("CREATOR_ARTWORK", "tvůrce výtvarné stránky");
        allowed.put("DRAMATURG", "dramaturg");
        allowed.put("CUTTER", "střih/střihač");
        allowed.put("SOUND", "zvuk/zvukař");
        allowed.put("PERFORMER", "účinkující");
        allowed.put("TRANSLATOR", "překladatel");
        allowed.put("LECTOR", "lektor");
        allowed.put("WITNESS", "svědek");
        allowed.put("GUARANTOR", "ručitel (rukojmě)");
        allowed.put("SCRIBE", "písař");
        allowed.put("PROCESSOR_CARRIER", "zpracovatel nosiče záznamu");
        allowed.put("MANUFACTURER_CARRIER", "výrobce nosiče záznamu");
        allowed.put("PRINTER", "tiskárna/tiskař");
        allowed.put("MANUFACTURER", "výrobce");
        allowed.put("LOCATION_SHOOTING", "místo natáčení");
        allowed.put("LOCATION_PUBLISHER", "místo vydavatele");
        allowed.put("LOCATION_PUBLISHING", "místo vydání");
        allowed.put("PLACE_MANUFACTURE", "místo výroby jednotky popisu");
        allowed.put("PLACE_ORIGIN", "místo vzniku jednotky popisu");
        allowed.put("PLACE_COPY_CREATION", "místo vzniku předlohy popisované kopie");
        allowed.put("TYPE", "typové označení a název výrobku a typové stavby");
        allowed.put("ENTITY", "související entita");
        allowed.put("AWARD", "vyznamenání/cena");
        allowed.put("PERSON_AWARDED", "nositel vyznamenání/ceny");
        allowed.put("PROPONENT", "navrhovatel");
        allowed.put("PERSON_HANDING", "předávající");
        allowed.put("PERSON_APPOINTED", "osoba jmenovaná / ustanovená do funkce");
        allowed.put("POSITION", "funkce");
        allowed.put("CORPORATION_ASSIGNED", "korporace výkonu funkce");
        allowed.put("LOCATION_ASSIGNED", "místo výkonu funkce");
        allowed.put("PLACE_REGISTER", "matriční místo");
        allowed.put("CLASSIFICATION", "sekundární klasifikace");
        allowed.put("COPYIST", "opisovač");
        allowed.put("OWNER", "vlastník");
        allowed.put("LOCATION_PHOTOGRAPHING", "místo fotografování");
        allowed.put("COOPERATION", "odborná spolupráce");
        allowed.put("PLACE_HANDING", "místo předání");
        allowed.put("CAPTURED_ENTITY", "obrazově a/nebo zvukově zachycená entita");
    }

    public Rule103() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> accessrestrictOrAccrualsOrAcqinfo = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archObject : accessrestrictOrAccrualsOrAcqinfo) {
            if (archObject instanceof Relations relations) {
                validate(relations);
            }
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> mDescBase = c.getMDescBase();
            for (Object mDescBaseObject : mDescBase) {
                if (mDescBaseObject instanceof Relations relations) {
                    validate(relations);
                }
            }
        });
    }

    private void validate(Relations relations) {
        List<Relation> listRelation = relations.getRelation();
        for (Relation relation : listRelation) {
            String relationtype = relation.getRelationtype();
            if (StringUtils.equals("cpfrelation", relationtype) || StringUtils.equals("resourcerelation", relationtype)) {
                String linkrole = relation.getLinkrole();
                String linktitle = relation.getLinktitle();
                if (StringUtils.isEmpty(linkrole) || StringUtils.isEmpty(linktitle)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nenalezena požadovaná hodnota atributu.", ctx.formatEadPosition(relation));
                }
                boolean containsRole = allowed.containsKey(linkrole);
                if (!containsRole) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezena požadovaná hodnota atributu linkrole.", ctx.formatEadPosition(relation));
                }
                String title = allowed.get(linkrole);
                if (!StringUtils.equals(title, linktitle)) {
                    //může mít cokoli nezávisle na tabulce
                }
            }
        }
    }

}
