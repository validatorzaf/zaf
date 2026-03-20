package cz.zaf.common.cz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidateICTest {
	@Test
	public void testValidator() {
		assertTrue( ValidateIC.validate("25038249") );
		assertFalse( ValidateIC.validate("25038248") );
		assertFalse( ValidateIC.validate("5038249") );
		assertTrue( ValidateIC.validate("00064581") );
		assertTrue( ValidateIC.validate("64581") );
		
		assertTrue( ValidateIC.validate("00216208") );
		assertTrue( ValidateIC.validate("00005886") );
	}
}
