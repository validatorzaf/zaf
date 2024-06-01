package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.result.EntityType;
import cz.zaf.schema.validace_v1.TTypEntity;

public enum DruhEntity implements EntityType {
    SPISOVY_PLAN(TTypEntity.SPISOVÝ_PLÁN),
    DIL(TTypEntity.DÍL),
    SPIS(TTypEntity.SPIS),
    DOKUMENT(TTypEntity.DOKUMENT),
    KOMPONENTA(null),
    SKARTACNI_RIZENI(null),
    VECNA_SKUPINA(TTypEntity.VĚCNÁ_SKUPINA),
    SOUCAST(TTypEntity.SOUČÁST),
    TYPOVY_SPIS(null);

    private final TTypEntity typEntity;

    DruhEntity(final TTypEntity typEntity) {
        this.typEntity = typEntity;
    }

    @Override
    public TTypEntity getTypEntity() {
        return typEntity;
    }
}