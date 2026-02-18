package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Container;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Quantity;
import cz.zaf.schema.ead3.Unittype;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;

public class Rule80 extends EadRule {

    static final public String CODE = "obs80";
    static final public String RULE_TEXT = "Pokud se na kořeni archivního popisu nenachází element <physdescstructured> má atribut \"physdescstructuredtype\" o hodnotě \"spaceoccupied\", má atribut \"coverage\" o hodnotě \"whole\". Podřízený element <unittype> obsahuje hodnotu \"bm\" nebo \"byte\". Pokud element <unittype> obsahuje hodnotu \"bm\", obsahuje podřízený element <quantity> kladné číslo zarovnané na dvě desetinná místa. Pokud element <unittype> obsahuje hodnotu \"byte\", obsahuje podřízený element <quantity> kladné celé číslo.\n"
            + " Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"spaceoccupied\" není opakovatelný, vyjma možnosti současného uvedení hodnot pro \"byte\" a \"bm\", kdy bude uveden dvakrát.\n"
            + " Množství \"bm\" je možné uvést jen pokud existuje alespoň jedna analogová archiválie, tj. existuje alespoň jedna ukládací jednotka zapsaná pomocí elementu <container>. Množství \"byte\" je možné uvést jen pokud existuje alespoň jedna digitální archiválie, tj. existuje alespoň jeden element <dao> s atributem \"daotype\" o hodnotě \"borndigital\".";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"spaceoccupied\" má špatně nastavený atribut \"coverage\" a/nebo v něm obsažené elementy <unittype> a/nebo <quantity> obsahují nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 4.3 profilu EAD3 MV ČR";
    
    boolean isBM, isByte;
    boolean isAnalog, isDigital;
    Container analogContainer = null;
    Dao digitalDao = null;

    public Rule80() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        
        // check if has spaceoccupied
        readSpaceoccupied(didA);
        if(!isBM && !isByte) {
        	return;
        }
        
        findAnalogAndDigital(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            findAnalogAndDigital(didC);
        });

        if (isBM && !isAnalog) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Uvedeno množství BM, ale nebyla zjištěna analogová archiválie.", ctx.formatEadPosition(analogContainer));
        }
        if (isByte && !isDigital) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Uvedeno množství byte, ale nebyla zjištěna digitální archiválie.", ctx.formatEadPosition(digitalDao));
        }
    }

    private void readSpaceoccupied(Did did) {
        Unittype foundBM = null, foundByte = null;
        for (Object child : did.getMDid()) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String coverage = physdescstructured.getCoverage();
                if (StringUtils.equals("spaceoccupied", physdescstructuredtype) && StringUtils.equals("whole", coverage)) {
                    Unittype unittype = physdescstructured.getUnittype();
                    Quantity quantity = physdescstructured.getQuantity();
                    if (unittype == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element unittype.", ctx.formatEadPosition(physdescstructured));
                    }
                    if (quantity == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element quantity.", ctx.formatEadPosition(physdescstructured));
                    }
                    String contentUnitType = unittype.getContent();
                    String contentQuantity = quantity.getContent();
                    if (StringUtils.isEmpty(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element unittype neobsahuje žádnou hodnotu.", ctx.formatEadPosition(unittype));
                    }
                    if (StringUtils.isEmpty(contentQuantity)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element quantity neobsahuje žádnou hodnotu.", ctx.formatEadPosition(quantity));
                    }
                    if (StringUtils.equals("bm", contentUnitType)) {
                        if (foundBM != null) {
                            List<Unittype> list = List.of(foundBM, unittype);
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element unittype.", ctx.formatEadPosition(list));
                        }
                        foundBM = unittype;
                        if (!isPositiveWithTwoDecimals(contentQuantity)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsahuje kladné celé číslo zarovnané na dvě desetiná místa.", ctx.formatEadPosition(quantity));
                        }
                        isBM = true;

                    } else if (StringUtils.equals("byte", contentUnitType)) {
                        if (foundByte != null) {
                            List<Unittype> list = List.of(foundByte, unittype);
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element unittype.", ctx.formatEadPosition(list));
                        }
                        foundByte = unittype;
                        if (!NumberUtils.isCreatable(contentQuantity)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(quantity));
                        } else {
                            if (Integer.parseInt(contentQuantity) < 0) {
                                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje kladné celé číslo.", ctx.formatEadPosition(quantity));
                            }
                        }
                        isByte = true;
                    } else {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsahuje nepovolenou hodnotu:" + contentUnitType + ".", ctx.formatEadPosition(unittype));
                    }
                }
            }
        }
	}

    // find analog and digital
	private void findAnalogAndDigital(Did did) {
        List<Object> childList = did.getMDid();        
        for (Object child : childList) {
            if (child instanceof Container container) {
                analogContainer = container;
                isAnalog = true;
            }
            if (child instanceof Dao dao) {
                String daotype = dao.getDaotype();
                if (StringUtils.equals("borndigital", daotype)) {
                    digitalDao = dao;
                    isDigital = true;
                }
            }
        }
    }

    private boolean isPositiveWithTwoDecimals(String s) {
        return s != null && s.matches("\\d+(\\.\\d{1,2})?");
    }

}
