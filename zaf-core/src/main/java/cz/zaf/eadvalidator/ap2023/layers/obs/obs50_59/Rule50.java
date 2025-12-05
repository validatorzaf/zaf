package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unitid;
import java.util.List;

public class Rule50 extends EadRule {

    static final public String CODE = "obs50";
    static final public String RULE_TEXT = "Element <unitid> obsažený v elementu <ead:did> má atributy \"localtype\" a \"label\" o povolených hodnotách z tabulky (viz karta Typy označení), přičemž hodnoty obou atributů si odpovídají. Pokud má atribut \"localtype\" hodnotu \"JINE\", hodnota atributu \"label\" není prázdná.";
    static final public String RULE_ERROR = "Některý element <ead:unitid> nemá \"localtype\" a/nebo \"label\" nebo tyto atributy neobsahují povolenou hodnotu, případně si hodnoty neodpovídají.";
    static final public String RULE_SOURCE = "Část 5.4 a 5.5 profilu EAD3 MV ČR";

    static private final Map<String, String> allowedlevelTypes = new HashMap<>();

    {
        allowedlevelTypes.put("REFERENCNI_OZNACENI", "referenční označení");
        allowedlevelTypes.put("PORADOVE_CISLO", "pořadové číslo");
        allowedlevelTypes.put("INV_CISLO", "inventární číslo");
        allowedlevelTypes.put("SIGNATURA_PUVODNI", "signatura přidělená původcem");
        allowedlevelTypes.put("SIGNATURA_ZPRACOVANI", "signatura přidělená při zpracování archiválie");
        allowedlevelTypes.put("UKLADACI_ZNAK", "ukládací znak / spisový znak");
        allowedlevelTypes.put("CISLO_JEDNACI", "číslo jednací");
        allowedlevelTypes.put("SPISOVA_ZNACKA", "spisová značka");
        allowedlevelTypes.put("CISLO_VLOZKY", "číslo vložky úřední knihy");
        allowedlevelTypes.put("CISLO_PRIRUSTKOVE", "přírůstkové číslo");
        allowedlevelTypes.put("NEPL_PORADOVE_CISLO", "neplatné pořadové číslo manipulačního seznamu");
        allowedlevelTypes.put("NEPL_INV_CISLO", "neplatné inventární číslo");
        allowedlevelTypes.put("NEPL_SIGNATURA_ZPRACOVANI", "signatura přidělená při předchozím zpracování");
        allowedlevelTypes.put("NEPL_REFERENCNI_OZNACENI", "neplatné referenční označení");
        allowedlevelTypes.put("NAKL_CISLO", "číslo pohlednice nakladatelství Orbis");
        allowedlevelTypes.put("CISLO_NEGATIVU", "číslo negativu");
        allowedlevelTypes.put("CISLO_PRODUKCE", "číslo produkce CD");
        allowedlevelTypes.put("KOD_ISBN", "kód ISBN");
        allowedlevelTypes.put("KOD_ISSN", "kód ISSN");
        allowedlevelTypes.put("KOD_ISMN", "kód ISMN");
        allowedlevelTypes.put("MATRICNI_CISLO", "matriční číslo (propůjčeného vyznamenání)");
//        allowedlevelTypes.put("JINE", "neprázdná hodnota");
    }

    public Rule50() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        List<Object> mDidDidA = didA.getMDid();
        validate(mDidDidA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            List<Object> mDidDidC = didC.getMDid();
            validate(mDidDidC);
        });
    }

    private void validate(List<Object> mDidDid) {
        for (Object obj : mDidDid) {
            if (obj instanceof Unitid unitid) {
                String localtype = unitid.getLocaltype();
                if (StringUtils.isEmpty(localtype)) {
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut localtype.", ctx.formatEadPosition(unitid));
                }
                String label = unitid.getLabel();
                if (StringUtils.isEmpty(label)) {
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut label.", ctx.formatEadPosition(unitid));
                }
                if (allowedlevelTypes.containsKey(localtype)) {
                    String value = allowedlevelTypes.get(localtype);
                    if (!label.equals(value)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut label neobsahuje očekávanou hodnotu: " + allowedlevelTypes.get(label) + ", ale hodnotu: " + label + ".", ctx.formatEadPosition(unitid));
                    }
                } else {
                    if (!localtype.equals("JINE")) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype obsahuje nepovolenou hodnotu: " + localtype + ".", ctx.formatEadPosition(unitid));
                    }
                }
            }
        }
    }

}
