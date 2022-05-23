package cz.zaf.sipvalidator.nsesss2017;

/**
 * Identifikátor entity
 * 
 * Pro identifikaci entity se uvádí:
 * - typ entity
 * - zdroj identifikátoru
 * - hodnota identifikátoru
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

    private final String identifikator;
    
    private final String zdroj;

    private final DruhEntity druhEntity;

    public EntityId(final DruhEntity druhEntity,
                    final String identifikator,
                    final String zdroj) {
        this.identifikator = identifikator;
        this.zdroj = zdroj;
        this.druhEntity = druhEntity;
    }

    public String getIdentifikator() {
        return identifikator;
    }

    public String getZdroj() {
        return zdroj;
    }

    public DruhEntity getDruhEntity() {
        return druhEntity;
    }

}
