package cz.zaf.common.cz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UnitdateFormatTypeTest {

	@Test
	public void testUnitdate() {
		LocalDateTime ldt230228dt =  LocalDateTime.parse("2023-02-28T23:59:58");
		
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt230228dt) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt230228dt) );
		assertFalse( UnitdateFormatType.D.validateFrom(ldt230228dt) );
		assertFalse( UnitdateFormatType.D.validateTo(ldt230228dt) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt230228dt) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt230228dt) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt230228dt) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt230228dt) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt230228dt) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt230228dt) );
		
		// from D
		LocalDateTime ldt18210210f =  LocalDateTime.parse("1821-02-10T00:00:00");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt18210210f) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt18210210f) );
		assertTrue( UnitdateFormatType.D.validateFrom(ldt18210210f) );
		assertFalse( UnitdateFormatType.D.validateTo(ldt18210210f) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt18210210f) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt18210210f) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt18210210f) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt18210210f) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt18210210f) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt18210210f) );

		// to D
		LocalDateTime ldt240228t =  LocalDateTime.parse("2024-02-28T23:59:59");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt240228t) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt240228t) );
		assertFalse( UnitdateFormatType.D.validateFrom(ldt240228t) );
		assertTrue( UnitdateFormatType.D.validateTo(ldt240228t) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt240228t) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt240228t) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt240228t) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt240228t) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt240228t) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt240228t) );

		// from YM
		LocalDateTime ldt18210201f =  LocalDateTime.parse("1821-02-01T00:00:00");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt18210201f) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt18210201f) );
		assertTrue( UnitdateFormatType.D.validateFrom(ldt18210201f) );
		assertFalse( UnitdateFormatType.D.validateTo(ldt18210201f) );
		assertTrue( UnitdateFormatType.YM.validateFrom(ldt18210201f) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt18210201f) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt18210201f) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt18210201f) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt18210201f) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt18210201f) );

		// to YM
		LocalDateTime ldt240229t =  LocalDateTime.parse("2024-02-29T23:59:59");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt240229t) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt240229t) );
		assertFalse( UnitdateFormatType.D.validateFrom(ldt240229t) );
		assertTrue( UnitdateFormatType.D.validateTo(ldt240229t) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt240229t) );
		assertTrue( UnitdateFormatType.YM.validateTo(ldt240229t) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt240229t) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt240229t) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt240229t) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt240229t) );

		// from Y
		LocalDateTime ldt18210101f =  LocalDateTime.parse("1821-01-01T00:00:00");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt18210101f) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt18210101f) );
		assertTrue( UnitdateFormatType.D.validateFrom(ldt18210101f) );
		assertFalse( UnitdateFormatType.D.validateTo(ldt18210101f) );
		assertTrue( UnitdateFormatType.YM.validateFrom(ldt18210101f) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt18210101f) );
		assertTrue( UnitdateFormatType.Y.validateFrom(ldt18210101f) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt18210101f) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt18210101f) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt18210101f) );

		// to Y
		LocalDateTime ldt241231t =  LocalDateTime.parse("2024-12-31T23:59:59");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt241231t) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt241231t) );
		assertFalse( UnitdateFormatType.D.validateFrom(ldt241231t) );
		assertTrue( UnitdateFormatType.D.validateTo(ldt241231t) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt241231t) );
		assertTrue( UnitdateFormatType.YM.validateTo(ldt241231t) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt241231t) );
		assertTrue( UnitdateFormatType.Y.validateTo(ldt241231t) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt241231t) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt241231t) );

		// from C
		LocalDateTime ldt18010101f =  LocalDateTime.parse("1801-01-01T00:00:00");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt18010101f) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt18010101f) );
		assertTrue( UnitdateFormatType.D.validateFrom(ldt18010101f) );
		assertFalse( UnitdateFormatType.D.validateTo(ldt18010101f) );
		assertTrue( UnitdateFormatType.YM.validateFrom(ldt18010101f) );
		assertFalse( UnitdateFormatType.YM.validateTo(ldt18010101f) );
		assertTrue( UnitdateFormatType.Y.validateFrom(ldt18010101f) );
		assertFalse( UnitdateFormatType.Y.validateTo(ldt18010101f) );
		assertTrue( UnitdateFormatType.C.validateFrom(ldt18010101f) );
		assertFalse( UnitdateFormatType.C.validateTo(ldt18010101f) );

		// to C
		LocalDateTime ldt19001231t =  LocalDateTime.parse("1900-12-31T23:59:59");
		assertTrue( UnitdateFormatType.DT.validateFrom(ldt19001231t) );
		assertTrue( UnitdateFormatType.DT.validateTo(ldt19001231t) );
		assertFalse( UnitdateFormatType.D.validateFrom(ldt19001231t) );
		assertTrue( UnitdateFormatType.D.validateTo(ldt19001231t) );
		assertFalse( UnitdateFormatType.YM.validateFrom(ldt19001231t) );
		assertTrue( UnitdateFormatType.YM.validateTo(ldt19001231t) );
		assertFalse( UnitdateFormatType.Y.validateFrom(ldt19001231t) );
		assertTrue( UnitdateFormatType.Y.validateTo(ldt19001231t) );
		assertFalse( UnitdateFormatType.C.validateFrom(ldt19001231t) );
		assertTrue( UnitdateFormatType.C.validateTo(ldt19001231t) );

	}
}
