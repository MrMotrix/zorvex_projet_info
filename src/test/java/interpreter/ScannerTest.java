package interpreter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.SyntaxErrorException;

import java.util.List;

public class ScannerTest {

    @Test
    public void testTokenizeAffichage() {
        String code = "afficher 5";
        try {
            List<Token> tokens = Scanner.tokenize(code);
            assertEquals(2, tokens.size(), "Le nombre de tokens devrait être 2");
            assertToken(tokens.get(0), TokenType.AFFICHER, "afficher");
            assertToken(tokens.get(1), TokenType.NOMBRE, "5");
        } catch (SyntaxErrorException e) {
            fail("Erreur de syntaxe : " + e.getMessage());
        }
    }

    @Test
    public void testTokenizeAssignation() {
        String code = "n <- 10";
        try {
            List<Token> tokens = Scanner.tokenize(code);
            assertEquals(3, tokens.size(), "Le nombre de tokens devrait être 3");
            assertToken(tokens.get(0), TokenType.IDENTIFIANT, "n");
            assertToken(tokens.get(1), TokenType.ASSIGNER, "<-");
            assertToken(tokens.get(2), TokenType.NOMBRE, "10");
        } catch (SyntaxErrorException e) {
            fail("Erreur de syntaxe : " + e.getMessage());
        }
    }

    @Test
    public void testTokenizeConditionIf() {
        String code = "si n > 5 { afficher \"n est plus grand que 5\" }";
        try {
            List<Token> tokens = Scanner.tokenize(code);
            assertEquals(8, tokens.size(), "Le nombre de tokens devrait être 8");
            assertToken(tokens.get(0), TokenType.SI, "si");
            assertToken(tokens.get(1), TokenType.IDENTIFIANT, "n");
            assertToken(tokens.get(2), TokenType.PLUS_GRAND, ">");
            assertToken(tokens.get(3), TokenType.NOMBRE, "5");
            assertToken(tokens.get(4), TokenType.BRACKET_OUVRANT, "{");
            assertToken(tokens.get(5), TokenType.AFFICHER, "afficher");
            assertToken(tokens.get(6), TokenType.CHAINE, "n est plus grand que 5");
            assertToken(tokens.get(7), TokenType.BRACKET_FERMANT, "}");
        } catch (SyntaxErrorException e) {
            fail("Erreur de syntaxe : " + e.getMessage());
        }
    }

    @Test
    public void testTokenizeWhileLoop() {
        String code = "tant que n < 20 { n <- n + 1 }";
        try {
            List<Token> tokens = Scanner.tokenize(code);
            assertEquals(11, tokens.size(), "Le nombre de tokens devrait être 11");
            assertToken(tokens.get(0), TokenType.TANT_QUE, "tant que");
            assertToken(tokens.get(1), TokenType.IDENTIFIANT, "n");
            assertToken(tokens.get(2), TokenType.PLUS_PETIT, "<");
            assertToken(tokens.get(3), TokenType.NOMBRE, "20");
            assertToken(tokens.get(4), TokenType.BRACKET_OUVRANT, "{");
            assertToken(tokens.get(5), TokenType.IDENTIFIANT, "n");
            assertToken(tokens.get(6), TokenType.ASSIGNER, "<-");
            assertToken(tokens.get(7), TokenType.IDENTIFIANT, "n");
            assertToken(tokens.get(8), TokenType.PLUS, "+");
        } catch (SyntaxErrorException e) {
            fail("Erreur de syntaxe : " + e.getMessage());
        }
    }

    @Test
    public void testTokenizeExpression() {
        String code = "a <- (b + 2) * (c - 3)";
        try {
            List<Token> tokens = Scanner.tokenize(code);
            assertEquals(13, tokens.size(), "Le nombre de tokens devrait être 13");

            assertToken(tokens.get(0), TokenType.IDENTIFIANT, "a");
            assertToken(tokens.get(1), TokenType.ASSIGNER, "<-");
            assertToken(tokens.get(2), TokenType.PARENTHESE_GAUCHE, "(");
            assertToken(tokens.get(3), TokenType.IDENTIFIANT, "b");
            assertToken(tokens.get(4), TokenType.PLUS, "+");
            assertToken(tokens.get(5), TokenType.NOMBRE, "2");
            assertToken(tokens.get(6), TokenType.PARENTHESE_DROIT, ")");
            assertToken(tokens.get(7), TokenType.FOIS, "*");
            assertToken(tokens.get(8), TokenType.PARENTHESE_GAUCHE, "(");
            assertToken(tokens.get(9), TokenType.IDENTIFIANT, "c");
            assertToken(tokens.get(10), TokenType.MOINS, "-");
            assertToken(tokens.get(11), TokenType.NOMBRE, "3");
            assertToken(tokens.get(12), TokenType.PARENTHESE_DROIT, ")");
        } catch (SyntaxErrorException e) {
            fail("Erreur de syntaxe : " + e.getMessage());
        }
    }

    // Méthode pour vérifier que le token correspond au type attendu
    private void assertToken(Token token, TokenType expectedType, String expectedLexeme) {
        assertEquals(expectedType, token.type(), "Type de token incorrect");
        assertEquals(expectedLexeme, token.lexeme(), "Lexème de token incorrect");
    }
}

