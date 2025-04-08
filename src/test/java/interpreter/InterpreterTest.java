package interpreter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import interpreter.Interpreter;
import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.SyntaxErrorException;
import interpreter.ZorvexValue;

public class InterpreterTest {

    private ZorvexValue runAndGetVariable(String code, String varName) throws SyntaxErrorException, RuntimeError {
        Interpreter interpreter = new Interpreter(code+"\n");
        while (!interpreter.isDone())
            interpreter.step();
        return interpreter.getVariable(varName);
    }

    @Test
    public void testSimpleAssignment() throws Exception {
        String code = "x <- 42";
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(42, x.asInteger());
    }

    @Test
    public void testConditionTrueBranch() throws Exception {
        String code = String.join("\n",
            "x <- 10",
            "si x = 10 {",
                "y <- 1",
            "} sinon {",
                "y <- 2",
            "}"
        );
        ZorvexValue y = runAndGetVariable(code, "y");
        assertEquals(1, y.asInteger());
    }

    @Test
    public void testWhileLoop() throws Exception {
        String code = String.join("\n",
            "i <- 0",
            "tant que i < 5 {",
                "i <- i + 1",
            "}"
        );
        ZorvexValue i = runAndGetVariable(code, "i");
        assertEquals(5, i.asInteger());
    }

    @Test
    public void testFunctionCall() throws Exception {
        String code = String.join("\n",
            "fonction carre(n) {",
                "retourner n * n",
            "}",
            "x <- carre(6)"
        );
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(36, x.asInteger());
    }

    @Test
    public void testListManipulation() throws Exception {
        String code = String.join("\n",
            "l <- [1,2,3]",
            "ajouter_liste(l, 4)",
            "x <- taille_liste(l)"
        );
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(4, x.asInteger());
    }

    @Test
    public void testRecupererListe() throws Exception {
        String code = String.join("\n",
            "l <- [7,8,9]",
            "x <- recuperer_liste(l, 1)"
        );
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(8, x.asInteger());
    }

    @Test
    public void testPileSimple() throws Exception {
        String code = String.join("\n",
            "p <- pile_vide()",
            "empiler(p, 5)",
            "empiler(p, 10)",
            "x <- depiler(p)",
            "y <- depiler(p)",
            "z <- est_pile_vide(p)"
        );
        Interpreter interpreter = new Interpreter(code+"\n");
        while (!interpreter.isDone()) interpreter.step();

        assertEquals(10, interpreter.getVariable("x").asInteger());
        assertEquals(5, interpreter.getVariable("y").asInteger());
        assertEquals(1, interpreter.getVariable("z").asInteger()); // 1 = true
    }

    @Test
    public void testRecursionFactorielle() throws Exception {
        String code = String.join("\n",
            "fonction fact(n) {",
                "si n = 0 {",
                    "retourner 1",
                "}",
                "retourner n * fact(n-1)",
            "}",
            "x <- fact(5)"
        );
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(120, x.asInteger());
    }

    @Test
    public void testRecursionFibonacci() throws Exception {
        String code = String.join("\n",
            "fonction fibo(n) {",
                "si n = 0 {",
                    "retourner 0",
                "}",
                "si n = 1 {",
                    "retourner 1",
                "}",
                "retourner fibo(n-1) + fibo(n-2)",
            "}",
            "x <- fibo(6)"
        );
        ZorvexValue x = runAndGetVariable(code, "x");
        assertEquals(8, x.asInteger()); // fib(6) = 8
    }

}
