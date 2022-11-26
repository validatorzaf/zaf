package cz.zaf.sipvalidator.nsesss2017;

/**
 * Identifikátor entity pro předání jako chyba.
 * 
 * @see PairZdrojIdent
 * 
 *      Pro identifikaci entity se uvádí:
 *      - typ entity
 *      - zdroj identifikátoru
 *      - hodnota identifikátoru
 */
public class EntityId {

    public enum DruhEntity {
        SPISOVY_PLAN,
        DIL,
        SPIS,
        DOKUMENT,
        KOMPONENTA,
        SKARTACNI_RIZENI,
        VECNA_SKUPINA,
        SOUCAST,
        TYPOVY_SPIS
    }

    private final DruhEntity druhEntity;

    private final PairZdrojIdent zdrojIdent;

    public EntityId(final DruhEntity druhEntity,
                    final PairZdrojIdent zdrojIdent) {
        this.druhEntity = druhEntity;
        this.zdrojIdent = zdrojIdent;
    }

    public DruhEntity getDruhEntity() {
        return druhEntity;
    }

    public PairZdrojIdent getZdrojIdent() {
        return zdrojIdent;
    }

}
