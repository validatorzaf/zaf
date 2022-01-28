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

    enum DruhEntity {
        SPISOVY_PLAN,
        DIL,
        SPIS,
        DOKUMENT
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

}
