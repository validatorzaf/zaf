package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.schema.validace_v1.TEntity;
import cz.zaf.schema.validace_v1.TIdentifikator;
import cz.zaf.schema.validace_v1.TPravidlo;
import cz.zaf.schema.validace_v1.TTypEntity;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.XmlProtokolWriter;

public class ChybaPravidlaNsesss2017 extends ChybaPravidla {
	

	public ChybaPravidlaNsesss2017(Rule<? extends RuleEvaluationContext> rule, String vypisChyby, String mistoChyby,
			ErrorCode errorCode, List<EntityId> entityIds) {
		super(rule, vypisChyby, mistoChyby, errorCode, entityIds);
		// TODO Auto-generated constructor stub
	}
	
	public ChybaPravidlaNsesss2017(String id, String textPravidla, String vypisChyby, String popisChybyObecny, String mistoChyby,
			String zdroj, ErrorCode errorCode, List<EntityId> entityIds) {
		super(id, textPravidla, vypisChyby, popisChybyObecny, mistoChyby, zdroj, errorCode, entityIds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void zapisDetail(TPravidlo pravNode) {
    	List<EntityId> entityIds = this.getEntityIds();
        if (CollectionUtils.isNotEmpty(entityIds)) {
            TEntity entityNode = XmlProtokolWriter.objectFactory.createTEntity();
            List<TIdentifikator> idents = entityNode.getIdentifikator();
            for (EntityId entityId : entityIds) {
                TIdentifikator ident = XmlProtokolWriter.objectFactory.createTIdentifikator();
                ident.setZdroj(entityId.getZdrojIdent().getZdroj());
                ident.setValue(entityId.getZdrojIdent().getIdentifikator());
                TTypEntity typEntity = null;
                switch (entityId.getDruhEntity()) {
                case SPISOVY_PLAN:
                    typEntity = TTypEntity.SPISOVÝ_PLÁN;
                    break;
                case VECNA_SKUPINA:
                    typEntity = TTypEntity.VĚCNÁ_SKUPINA;
                    break;
                case DIL:
                    typEntity = TTypEntity.DÍL;
                    break;
                case SPIS:
                    typEntity = TTypEntity.SPIS;
                    break;
                case DOKUMENT:
                    typEntity = TTypEntity.DOKUMENT;
                    break;
                case SOUCAST:
                    typEntity = TTypEntity.SOUČÁST;
                    break;
                case TYPOVY_SPIS:
                case KOMPONENTA:
                case SKARTACNI_RIZENI:
                default:
                    continue;
                }
                if (typEntity == null) {
                    continue;
                }
                ident.setTyp(typEntity);

                idents.add(ident);
            }
            if (idents.size() > 0) {
                pravNode.setEntity(entityNode);
            }
        }
    }

}
