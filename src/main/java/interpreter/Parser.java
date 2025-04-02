package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import interpreter.exceptions.ExpectedCharacterNotFound;
import interpreter.exceptions.SyntaxErrorException;
import interpreter.exceptions.UnexpectedTokenException;
import interpreter.expression.*;
import interpreter.instruction.*;

// program          → instruction* EOF;
// instruction      → declfonction | afficher | assigner | condition;
// declfonction     → "fonction " IDENTIFIER "(" ((IDENTIFIER',')* IDENTIFIER) | () ")" block;
// afficher         → "afficher " expression ENDL;
// assignation      → IDENTIFIER "<-" expression ENDL;
// condition        → "si " expression block ;
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

    public static Expression parseExpression(List<Token> tokens) throws SyntaxErrorException {
        Parser parser = new Parser(tokens);
        return parser.expression();
    }

    public static List<InstructionInfo> parse(List<Token> tokens) throws SyntaxErrorException {
        Parser parser = new Parser(tokens);
        return parser.program();
    }

    public List<InstructionInfo> program() throws SyntaxErrorException {
        List<InstructionInfo> instructions = new ArrayList<>();

        while (i < tokens.size()) {
            instructions.add(instruction());
        }
        
        return instructions;
    }

    public InstructionInfo instruction() throws SyntaxErrorException {
        InstructionInfo result = parse();
        if (current().type() != TokenType.ENDL) {
            throw new UnexpectedTokenException(current(), TokenType.ENDL);
        }
        advance();
        return result;
    }

    public InstructionInfo parse() throws SyntaxErrorException {
        int line = current().line();
        if (current().type() == TokenType.AFFICHER) {
            advance();
            return new InstructionInfo(new Afficher(expression()), line);
        }

        if (current().type() == TokenType.IDENTIFIANT) {
            String name = current().lexeme();
            advance();
            if (current().type() != TokenType.ASSIGNER) {
                throw new UnexpectedTokenException(current(), TokenType.ASSIGNER);
            }
            advance();
            return new InstructionInfo(new Assigner(name, expression()), line);
        }

        if (current().type() == TokenType.SI) {
            advance();
            Expression condition = expression();
            Block block = block();
            return new InstructionInfo(new Si(condition, block), line);
        }

        if (current().type() == TokenType.TANT_QUE) {
            advance();
            Expression condition = expression();
            Block block = block();
            return new InstructionInfo(new TantQue(condition, block), line);
        }

        if (current().type() == TokenType.FONCTION) {
            advance();
            if (current().type() != TokenType.IDENTIFIANT) 
                throw new UnexpectedTokenException(current(), TokenType.IDENTIFIANT);
            Token name = current();
            advance();

            if (current().type() != TokenType.PARENTHESE_GAUCHE) 
                throw new UnexpectedTokenException(current(), TokenType.PARENTHESE_GAUCHE);
            advance();
            
            List<String> arguments = new ArrayList<>();

            while (i < tokens.size()-2 && current().type() != TokenType.PARENTHESE_DROIT) {
                if (current().type() !=  TokenType.IDENTIFIANT)
                    throw new UnexpectedTokenException(current(), TokenType.IDENTIFIANT);
                arguments.add(current().lexeme());
                advance();
                
                if (current().type() == TokenType.PARENTHESE_DROIT)
                    break;
                if (current().type() != TokenType.VIRGULE)
                    throw new UnexpectedTokenException(current(), TokenType.VIRGULE);
                advance();
            }
            if (i >= tokens.size()-2) 
                throw new UnexpectedTokenException(null, TokenType.PARENTHESE_DROIT);
            
            advance();

            Block block = block();
            return new InstructionInfo(new FunctionDeclaration(name.lexeme(), block, arguments), name.line());
        }


        throw new UnexpectedTokenException(current());
    }

    private Block block() throws SyntaxErrorException {
        if (current().type() != TokenType.BRACKET_OUVRANT) {
            throw new UnexpectedTokenException(current(), TokenType.BRACKET_OUVRANT);
        }
        advance();

        if (current().type() != TokenType.ENDL) {
            throw new UnexpectedTokenException(current(), TokenType.ENDL);
        }
        advance();

        List<InstructionInfo> instructions = new ArrayList<>();
        while (i < tokens.size() && current().type() != TokenType.BRACKET_FERMANT)  {
            InstructionInfo inst = instruction();
            if (inst != null)
                instructions.add(inst);
        }
        
        advance();
        return new Block(instructions);
    }

    private Expression expression() throws SyntaxErrorException {
        return equality();
    }

    private Expression equality() throws SyntaxErrorException {
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

    private Expression comparaison() throws SyntaxErrorException {
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

    private Expression term() throws SyntaxErrorException {
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

    private Expression factor() throws SyntaxErrorException {
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

    private Expression unary() throws SyntaxErrorException {
        if (Set.of(TokenType.NON, TokenType.MOINS).contains(current().type())) {
            Token operator = current();
            advance();
            return new UnaryOperation(unary(), new UUnaryOperator(operator));
        }

        return primary();
    }

    private Expression primary() throws SyntaxErrorException {
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
            throw new ExpectedCharacterNotFound(current().line(), ')'); 
        }
        throw new UnexpectedTokenException(token);
    }


    private Token current() {
        return tokens.get(i);
    }

    private void advance() {
        i += 1;
    }
}
