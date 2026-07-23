package cz.zaf.sipvalidator.pdfa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class VeraValidatorProxyTest {

    @Test
    void testParseCliParamsBlank() {
        assertTrue(VeraValidatorProxy.parseCliParams(null).isEmpty());
        assertTrue(VeraValidatorProxy.parseCliParams("").isEmpty());
        assertTrue(VeraValidatorProxy.parseCliParams("   ").isEmpty());
    }

    @Test
    void testParseCliParamsSimple() {
        List<String> params = VeraValidatorProxy.parseCliParams("--profile /profily/custom.xml");
        assertEquals(List.of("--profile", "/profily/custom.xml"), params);
    }

    @Test
    void testParseCliParamsExtraWhitespace() {
        List<String> params = VeraValidatorProxy.parseCliParams("  -Xmx1g   -Xms256m ");
        assertEquals(List.of("-Xmx1g", "-Xms256m"), params);
    }

    @Test
    void testParseCliParamsQuotedValue() {
        List<String> params = VeraValidatorProxy
                .parseCliParams("--profile \"C:\\Program Files\\profily\\custom profile.xml\"");
        assertEquals(List.of("--profile", "C:\\Program Files\\profily\\custom profile.xml"), params);
    }

    @Test
    void testParseCliParamsQuotesInsideToken() {
        List<String> params = VeraValidatorProxy.parseCliParams("-Dname=\"value with spaces\"");
        assertEquals(List.of("-Dname=value with spaces"), params);
    }

}
