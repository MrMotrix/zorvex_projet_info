package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import interpreter.expression.*;
import interpreter.instruction.*;

// program          → instruction* EOF;
// instruction      → afficher | assigner ;
// afficher         → "afficher " expression ENDL;
// assignation      → IDENTIFIER "<-" expression ENDL;
// condition        → "si " expression "faire " block ;
// block            → "{" instruction* "}" ENDL ;
// expression       → equality ;
// equality         → comparison ( ( "!=" | "==" ) comparison )* ;
// comparison       → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
// term             → factor ( ( "-" | "+" ) factor )* ;
// factor           → unary ( ( "/" | "*" ) unary )* ;
// unary            → ( "!" | "-" ) unary | primary ;
// primary          → NUMBER | STRING | IDENTIFIER | "true" | "false" | "nil" | "(" expression ")" ;

public class Parser {
    private final List<Token> tokens;
    private int i = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public static Expression parseExpression(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        return parser.expression();
    }

    public static List<Instruction> parse(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        return parser.program();
    }

    public List<Instruction> program() {
        List<Instruction> instructions = new ArrayList<>();

        while (i < tokens.size()) 
            instructions.add(instruction());
        
        return instructions;
    }

    public Instruction instruction() {
        Instruction result = parse();
        if (current().type() != TokenType.ENDL) {
            // erreur
        }
        advance();
        return result;
    }

    public Instruction parse() {
        if (current().type() == TokenType.AFFICHER) {
            advance();
            return new Afficher(expression());
        }

        if (current().type() == TokenType.IDENTIFIANT) {
            String name = current().lexeme();
            advance();
            if (current().type() != TokenType.ASSIGNER) {
                // erreur ici
            }
            advance();
            return new Assigner(name, expression());
        }

        if (current().type() == TokenType.SI) {
            advance();
            Expression condition = expression();
            Block block = block();
            return new Si(condition, block);
        }

        if (current().type() == TokenType.TANT_QUE) {
            advance();
            Expression condition = expression();
            Block block = block();
            return new TantQue(condition, block);
        }

        return null;
    }

    private Block block() {
        if (current().type() != TokenType.BRACKET_OUVRANT) {
            // erreur
        }
        advance();
        List<Instruction> instructions = new ArrayList<>();
        while (i < tokens.size() && current().type() != TokenType.BRACKET_FERMANT)  {
            Instruction inst = instruction();
            if (inst != null)
                instructions.add(inst);
        }
        
        advance();
        return new Block(instructions);
    }

    private Expression expression() {
        return equality();
    }

    private Expression equality() {
        Expression expr = comparaison();
        Set<TokenType> operators = Set.of(TokenType.EGAL, TokenType.DIFFERENT);

        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = comparaison();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression comparaison() {
        Expression expr = term();
        Set<TokenType> operators = Set.of(TokenType.GRAND_EGAL, TokenType.PLUS_GRAND, TokenType.PETIT_EGAL, TokenType.PLUS_PETIT);
        
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = term();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression term() {
        Expression expr = factor();
        Set<TokenType> operators = Set.of(TokenType.MOINS, TokenType.PLUS);
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = factor();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression factor() {
        Expression expr = unary();
        Set<TokenType> operators = Set.of(TokenType.FOIS, TokenType.DIVISE);
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = unary();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression unary() {
        if (Set.of(TokenType.NON, TokenType.MOINS).contains(current().type())) {
            Token operator = current();
            advance();
            return new UnaryOperation(unary(), new UUnaryOperator(operator));
        }

        return primary();
    }

    private Expression primary() {
        Token token = current();
        TokenType type = token.type();
        advance();

        switch (type) {
            case TokenType.NOMBRE:
                return new Literal(new ZorvexValue((int)token.data()));
            case TokenType.CHAINE:
                return new Literal(new ZorvexValue((String)token.data()));
            case TokenType.IDENTIFIANT:
                return new Variable((String)token.lexeme());
            case TokenType.VRAI:
                return new Literal(new ZorvexValue(1));
            case TokenType.FAUX:
                return new Literal(new ZorvexValue(0));
        }

        if (type == TokenType.PARENTHESE_GAUCHE) {
            Expression expr = expression();
            
            if (current().type() == TokenType.PARENTHESE_DROIT) {
                advance();
                return new Grouping(expr);
            }
            // erreur, parenthèse non fermée   
        }
        // erreur, unexpected token
        return null;   
    }


    private Token current() {
        return tokens.get(i);
    }

    private void advance() {
        i += 1;
    }
}
