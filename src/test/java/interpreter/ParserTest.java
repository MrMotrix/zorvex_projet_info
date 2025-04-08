package interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import interpreter.exceptions.SyntaxErrorException;
import interpreter.expression.*;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.FunctionDeclaration;
import interpreter.instruction.InstructionInfo;
import interpreter.instruction.Retourner;
import interpreter.instruction.Si;
import interpreter.instruction.TantQue;

public class ParserTest {

    private Expression parseExpression(String input) {
        List<Token> tokens = List.of();
        try {
            tokens = Scanner.tokenize(input);
        }
        catch (Exception e) { }
        try {
            return Parser.parseExpression(tokens);
        } catch(Exception e) { return null; }
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
        Expression expr = parseExpression("vrai = faux");
        assertNotNull(expr);
        assertEquals("(1 = 0)", expr.printValue());
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
    
    private InstructionInfo parseInstruction(String src) throws SyntaxErrorException {
        List<Token> tokens = Scanner.tokenize(src + "\n");
        Parser parser = new Parser(tokens);
        return parser.instruction();
    }

    @Test
    void testAfficherInstruction() throws SyntaxErrorException {
        InstructionInfo info = parseInstruction("afficher 42");
        assertInstanceOf(Afficher.class, info.instruction());
    }

    @Test
    void testVariableAssignment() throws SyntaxErrorException {
        InstructionInfo info = parseInstruction("x <- 5");
        Assigner assigner = (Assigner) info.instruction();
        assertEquals("x", assigner.variableName());
    }

    @Test
    void testFunctionCallAsInstruction() throws SyntaxErrorException {
        InstructionInfo info = parseInstruction("f()");
        Assigner assigner = (Assigner) info.instruction();
        assertEquals("", assigner.variableName());  // void call assigner
    }

    @Test
    void testIfWithoutElse() throws SyntaxErrorException {
        String src = "si vrai {\n afficher 1\n}\n";
        InstructionInfo info = parseInstruction(src);
        assertInstanceOf(Si.class, info.instruction());
        Si si = (Si) info.instruction();
        assertNotNull(si.expression());
        assertNull(si.elseBlock());
    }

    @Test
    void testIfWithElse() throws SyntaxErrorException {
        String src = "si vrai {\n afficher 1\n}\n sinon {\n afficher 0\n}";
        InstructionInfo info = parseInstruction(src);
        Si si = (Si) info.instruction();
        assertNotNull(si.elseBlock());
    }

    @Test
    void testWhileLoop() throws SyntaxErrorException {
        String src = "tant que faux {\n afficher 0\n}";
        InstructionInfo info = parseInstruction(src);
        assertInstanceOf(TantQue.class, info.instruction());
    }

    @Test
    void testFunctionDeclaration() throws SyntaxErrorException {
        String src = "fonction salut(x, y) {\n afficher x\n}";
        InstructionInfo info = parseInstruction(src);
        FunctionDeclaration decl = (FunctionDeclaration) info.instruction();
        assertEquals("salut", decl.name());
        assertEquals(List.of("x", "y"), decl.parameters());
    }

    @Test
    void testReturnStatement() throws SyntaxErrorException {
        InstructionInfo info = parseInstruction("retourner 10");
        assertInstanceOf(Retourner.class, info.instruction());
    }

    @Test
    void testInvalidAssignmentMissingEquals() {
        String src = "x 5";
        assertThrows(SyntaxErrorException.class, () -> parseInstruction(src));
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
