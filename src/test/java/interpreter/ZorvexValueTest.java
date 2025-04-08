package interpreter;
import org.junit.jupiter.api.Test;

import interpreter.exceptions.RuntimeError;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ZorvexValueTest {

    @Test
    void testIntegerValue() throws RuntimeError {
        ZorvexValue intValue = new ZorvexValue(269);

        assertTrue(intValue.isInteger());
        assertFalse(intValue.isString());
        assertEquals(269, intValue.asInteger());
        assertEquals("269", intValue.asString());
    }

    @Test
    void testNumericStringValue() throws RuntimeError {
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

    @Test
    void testEqualitySameValues() {
        ZorvexValue a = new ZorvexValue(42);
        ZorvexValue b = new ZorvexValue(42);
        ZorvexValue c = new ZorvexValue("hello");
        ZorvexValue d = new ZorvexValue("hello");

        assertEquals(a, b);
        assertEquals(c, d);
    }

    @Test
    void testEqualityDifferentTypesOrValues() {
        ZorvexValue intVal = new ZorvexValue(42);
        ZorvexValue stringVal = new ZorvexValue("42");
        ZorvexValue anotherInt = new ZorvexValue(99);

        assertNotEquals(intVal, stringVal);
        assertNotEquals(intVal, anotherInt);
    }

    @Test
    void testListOperations() throws RuntimeError {
        ZorvexValue list = new ZorvexValue(List.of(new ZorvexValue(1), new ZorvexValue(2)));

        assertEquals(2, list.size());
        assertEquals(new ZorvexValue(1), list.get(0));
        list.set(1, new ZorvexValue(99));
        assertEquals(new ZorvexValue(99), list.get(1));
        list.add(new ZorvexValue(123));
        assertEquals(3, list.size());
        assertEquals("[1,99,123]", list.asString());
    }

    @Test
    void testListOutOfBounds() {
        ZorvexValue list = new ZorvexValue(List.of(new ZorvexValue(1)));

        assertThrows(RuntimeError.class, () -> list.get(2));
        assertThrows(RuntimeError.class, () -> list.set(5, new ZorvexValue(1)));
    }

    @Test
    void testStackOperations() throws RuntimeError {
        ZorvexValue stack = ZorvexValue.emptyStack();

        assertTrue(stack.isEmpty());
        stack.empiler(new ZorvexValue(10));
        assertFalse(stack.isEmpty());
        assertEquals(new ZorvexValue(10), stack.depiler());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testDepilerEmptyStack() {
        ZorvexValue stack = ZorvexValue.emptyStack();

        assertThrows(RuntimeError.class, stack::depiler);
    }
}
