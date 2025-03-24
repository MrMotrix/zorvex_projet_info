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

public class Interpreter {
    private ArrayDeque<Context> stack = new ArrayDeque<>();
    private ArrayDeque<Integer> returnLine = new ArrayDeque<>();

    private int currentInstruction = 0;
    private List<Instruction> instructions = new ArrayList<Instruction>();

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        List<Token> tokens = Scanner.tokenize(
            """
            n <- 13
            p <- 2
            compose <- 0
            tant que p < n {
                si n/p*p=n {
                    compose <- 1
                }
                p <- p + 1
            }
            si compose=1 {
                afficher "non premier"
            }
            si compose=0 {
                afficher "premier"
            }
            """);

        interpreter.instructions = Parser.parse(tokens);

        while (interpreter.currentInstruction < interpreter.instructions.size())
            interpreter.interpret();
    }
    public Interpreter() {
        stack.add(new Context());
    }

    public int getLineCount() {
        return -1;
    }

    public int getCurrentLine() {
        return -1;
    }  

    public ZorvexValue getVariable(String name) {
        return stack.getLast().getVariable(name);
    }

    public String interpret() {
        currentInstruction += 1;
        return interpret(instructions.get(currentInstruction-1));
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
