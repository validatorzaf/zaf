package cz.zaf.common.result;

/**
 * Identifikátor entity pro předání jako chyba.
 * 
 * @see IndetifierWithSource
 * 
 *      Pro identifikaci entity se uvádí:
 *      - typ entity
 *      - zdroj identifikátoru
 *      - hodnota identifikátoru
 */
public class EntityId {

    private final EntityType entityType;

    private final IndetifierWithSource id;

    public EntityId(final EntityType druhEntity,
                    final IndetifierWithSource id) {
        this.entityType = druhEntity;
        this.id = id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public IndetifierWithSource getId() {
        return id;
    }

}
