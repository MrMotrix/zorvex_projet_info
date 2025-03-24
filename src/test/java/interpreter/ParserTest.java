package interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import interpreter.expression.*;

public class ParserTest {

    private Expression parseExpression(String input) {
        List<Token> tokens = Scanner.tokenize(input);
        return Parser.parseExpression(tokens);
    }

    @Test
    void testSingleBinaryOperation() {
        Expression expr = parseExpression("3 + 4");
        assertNotNull(expr);
        assertEquals("(3 + 4)", expr.printValue());
    }

    @Test
    void testUnaryOperation() {
        Expression expr = parseExpression("-5");
        assertNotNull(expr);
        assertEquals("(-5)", expr.printValue());
    }

    @Test
    void testMultipleBinaryOperationsWithoutPrecedenceIssues() {
        Expression expr = parseExpression("1 + 2 + 3");
        assertNotNull(expr);
        assertEquals("((1 + 2) + 3)", expr.printValue());
    }

    @Test
    void testBinaryOperationsWithPrecedence() {
        Expression expr = parseExpression("2 + 3 * 4");
        assertNotNull(expr);
        assertEquals("(2 + (3 * 4))", expr.printValue());
    }

    @Test
    void testParenthesesAffectingPrecedence() {
        Expression expr = parseExpression("(2 + 3) * 4");
        assertNotNull(expr);
        assertEquals("((2 + 3) * 4)", expr.printValue());
    }

    @Test
    void testComplexExpression() {
        Expression expr = parseExpression("(1 + 2) * (3 - 4) / 5");
        assertNotNull(expr);
        assertEquals("(((1 + 2) * (3 - 4)) / 5)", expr.printValue());
        expr = parseExpression("8*20/5+2*3");
        assertNotNull(expr);
        assertEquals("(((8 * 20) / 5) + (2 * 3))", expr.printValue());
    }
    
    @Test
    void testBooleanExpressions() {
        Expression expr = parseExpression("vrai == faux");
        assertNotNull(expr);
        assertEquals("(1 == 0)", expr.printValue());
    }

    @Test
    void testComparisonOperators() {
        Expression expr = parseExpression("2 > 1");
        assertNotNull(expr);
        assertEquals("(2 > 1)", expr.printValue());
    }

    @Test
    void testLogicalNegation() {
        Expression expr = parseExpression("!vrai");
        assertNotNull(expr);
        assertEquals("(!1)", expr.printValue());
    }

    @Test
    void testVariableUsage() {
        Expression expr = parseExpression("x + 3");
        assertNotNull(expr);
        assertEquals("(x + 3)", expr.printValue());
    }
    
    // @Test
    // void testInvalidExpressionMissingOperand() {
    //     assertThrows(RuntimeException.class, () -> parseExpression("3 +"));
    // }

    // @Test
    // void testInvalidExpressionUnmatchedParentheses() {
    //     assertThrows(RuntimeException.class, () -> parseExpression("(3 + 4"));
    // }
}
