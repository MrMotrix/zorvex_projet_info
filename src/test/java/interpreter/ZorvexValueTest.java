package interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ZorvexValueTest {

    @Test
    void testIntegerValue() {
        ZorvexValue intValue = new ZorvexValue(269);

        assertTrue(intValue.isInteger());
        assertFalse(intValue.isString());
        assertEquals(269, intValue.asInteger());
        assertEquals("269", intValue.asString());
    }

    @Test
    void testNumericStringValue() {
        ZorvexValue stringValue = new ZorvexValue("269");

        assertTrue(stringValue.isString());
        assertFalse(stringValue.isInteger());
        assertEquals(269, stringValue.asInteger());
        assertEquals("269", stringValue.asString());
    }

    @Test
    void testNonNumericStringValue() {
        ZorvexValue stringValue = new ZorvexValue("hello");

        assertTrue(stringValue.isString());
        assertFalse(stringValue.isInteger());
        assertEquals("hello", stringValue.asString());

        assertThrows(NumberFormatException.class, stringValue::asInteger);
    }

    @Test
    void testToStringFormat() {
        ZorvexValue intValue = new ZorvexValue(10);
        ZorvexValue stringValue = new ZorvexValue("test");

        assertEquals("INTEGER 10", intValue.toString());
        assertEquals("STRING test", stringValue.toString());
    }
}
