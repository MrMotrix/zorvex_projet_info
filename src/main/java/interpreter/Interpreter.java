package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interpreter.expression.BinaryOperation;
import interpreter.expression.BinaryOperator;
import interpreter.expression.Literal;
import interpreter.expression.Variable;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.Instruction;
import interpreter.instruction.TantQue;
import interpreter.instruction.InstructionInfo;

public class Interpreter {
    private ArrayDeque<Context> stack = new ArrayDeque<>();
    private ArrayDeque<Integer> returnLine = new ArrayDeque<>();

    private int currentInstruction = 0;
    private List<InstructionInfo> instructions = new ArrayList<>();

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        List<Token> tokens = Scanner.tokenize(
            """
            n <- 13
            p <- 2
            compose <- 0
            n <- n+p
            """);

        interpreter.instructions = Parser.parse(tokens);

        while (interpreter.currentInstruction < interpreter.instructions.size()) {
            System.out.println(interpreter.step());
        }
    }
    public Interpreter() {
        stack.add(new Context());
    }

    public Interpreter(String code) {
        this();
        this.instructions = Parser.parse(Scanner.tokenize(code));
    }

    public int getLineCount() {
        return instructions.getLast().line();
    }

    public int getCurrentLine() {
        return instructions.get(currentInstruction).line();
    }

    public ZorvexValue getVariable(String name) {
        return stack.getLast().getVariable(name);
    }

    public String step() {
        return interpret();
    }

    private String interpret() {
        currentInstruction += 1;
        return interpret(instructions.get(currentInstruction-1).instruction());
    }

    public String interpret(Instruction instruction) {
        if (instruction instanceof Assigner assign) {
            assign.interpret(stack.getLast());
            return assign.variableName();
        }
        instruction.interpret(stack.getLast());
        return "";
    }
}
