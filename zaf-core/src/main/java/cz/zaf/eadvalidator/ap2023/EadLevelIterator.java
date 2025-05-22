package cz.zaf.eadvalidator.ap2023;

import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Dsc;
import cz.zaf.schema.ead3.Ead;

/**
 * Class to iterate EAD levels
 */
public class EadLevelIterator {
	
	@FunctionalInterface
	public interface LevelProcessor {
		void process(C level, C parentLevel);
	}
	
	private final Ead ead;
	
	public EadLevelIterator(Ead ead) {
		this.ead = ead;
	}
	
	public void iterate(LevelProcessor processor) {
		for(Object obj: ead.getArchdesc().getAccessrestrictOrAccrualsOrAcqinfo()) {
			if(obj instanceof Dsc) {
				Dsc dsc = (Dsc)obj;
				for(C c: dsc.getC()) {
					processor.process(c, null);
					// iterate children
					iterate(c, processor);
				}
			}			
		}
	}

	private void iterate(C cParent, LevelProcessor processor) {
		cParent.getTheadAndC().forEach(obj -> {
			if(obj instanceof C) {
				C c = (C)obj;
				processor.process(c, cParent);
				// iterate children
				iterate(c, processor);
			}
		});		
	}
}
