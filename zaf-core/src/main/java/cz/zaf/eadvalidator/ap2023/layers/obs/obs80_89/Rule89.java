package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Geogname;
import cz.zaf.schema.ead3.Geographiccoordinates;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

public class Rule89 extends EadRule {

    static final public String CODE = "obs89";
    static final public String RULE_TEXT = "Každý element <relation> s atributem \"relationtype\" o hodnotě \"otherrelationtype\" a zároveň s atributem \"otherrelationtype\" o hodnotě \"COORDINATES\" obsahuje právě jeden element <geogname>. Element <geogname> obsahuje právě jeden element <part> a právě jeden element <geographiccoordinates>. Element <part>, který je obsažen v elementu <geogname>, obsahuje hodnotu \"5.2.6 Souřadnice\". Element <geographiccoordinates> má atribut \"coordinatesystem\" o hodnotě \"WGS84\" a obsahuje hodnotu ve formátu WKB (ISO/IEC 13249-3:2016) převedeném do BASE64, varianta little-endian.";
    static final public String RULE_ERROR = "Element <relation> s atributem \"relationtype\" o hodnotě \"otherrelationtype\" a zároveň s atributem \"otherrelationtype\" o hodnotě \"COORDINATES\" neobsahuje právě jeden element <geogname>. Nebo element <geogname> neobsahuje právě jeden element <part> a/nebo právě jeden element <geographiccoordinates>. Nebo element <part> neobsahuje hodnotu \"5.2.6 Souřadnice\". Nebo element <geographiccoordinates> nemá atribut \"coordinatesystem\" nebo tento atribut neobsahuje hodnotu \"WGS84\". Případně některý element <geographiccoordinates> neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 6.10 a 6.10.1 profilu EAD3 MV ČR";

    public Rule89() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> childrenListA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validateRelation(childrenListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> childrenListC = c.getMDescBase();
            validateRelation(childrenListC);
        });
    }

    private void validateRelation(List<Object> childrenList) {
        for (Object child : childrenList) {
            if (child instanceof Relations relations) {
                List<Relation> relationList = relations.getRelation();
                for (Relation relation : relationList) {
                    String relationtype = relation.getRelationtype();
                    String otherrelationtype = relation.getOtherrelationtype();
                    if (StringUtils.equals("otherrelationtype", relationtype) && StringUtils.equals("COORDINATES", otherrelationtype)) {
                        Geogname geogname = relation.getGeogname();
                        if (geogname == null) {
                            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element geogname.", ctx.formatEadPosition(relation));
                        }
                        validateGeogname(geogname);
                    }
                }
            }
        }
    }

    private void validateGeogname(Geogname geogname) {
        List<Part> part = geogname.getPart();
        List<Geographiccoordinates> geographiccoordinates = geogname.getGeographiccoordinates();
        if (part.isEmpty() || geographiccoordinates.isEmpty()) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element neobsahuje požadované elementy part a geographiccoordinates.", ctx.formatEadPosition(geogname));
        }
        if (part.size() != 1) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element part.", ctx.formatEadPosition(geogname));
        }
        if (geographiccoordinates.size() != 1) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element geographiccoordinates.", ctx.formatEadPosition(geogname));
        }

        validatePartContent(part.get(0));

        validateGeographiccoordinatesContent(geographiccoordinates.get(0));

    }

    private void validatePartContent(Part part) {
        List<Serializable> content = part.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(part));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(part));
            }
            if (!StringUtils.equals("5.2.6 Souřadnice", str)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu part.", ctx.formatEadPosition(part));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(part));
        }
    }

    private void validateGeographiccoordinatesContent(Geographiccoordinates geographiccoordinates) {
        String coordinatesystem = geographiccoordinates.getCoordinatesystem();
        if (!StringUtils.equals("WGS84", coordinatesystem)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota elementu geographiccoordinates.", ctx.formatEadPosition(geographiccoordinates));
        }
        String content = geographiccoordinates.getContent();
        boolean validWkbBase64LittleEndian = isValidWkbBase64LittleEndian(content);
        if (!validWkbBase64LittleEndian) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(geographiccoordinates));
        }
    }

    private boolean isValidWkbBase64LittleEndian(String base64Wkb) {
        if (base64Wkb == null || base64Wkb.isBlank()) {
            return false;
        }

        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(base64Wkb);
        } catch (IllegalArgumentException e) {
            // není validní Base64
            return false;
        }

        // WKB musí mít aspoň 5 bytů: 1 byte byte-order + 4 byty geometry type (uint32)
        if (bytes.length < 5) {
            return false;
        }

        // byte-order: 0 = big-endian, 1 = little-endian
        byte byteOrder = bytes[0];
        if (byteOrder != 1) { // požadujeme little-endian
            return false;
        }

        // WKBReader bere byte[] a respektuje endianity uložené v datech.
        WKBReader reader = new WKBReader();
        try {
            Geometry geom = reader.read(bytes);
            // (volitelně) může ověřit typ geometrie, nenulovost, počet souřadnic apod.
            return geom != null && !geom.isEmpty();
        } catch (ParseException | IllegalArgumentException ex) {
            // neplatný WKB
            return false;
        }
    }

}
