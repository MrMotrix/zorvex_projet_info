package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Interpreter {
    private ArrayDeque<Context> stack = new ArrayDeque<>();
    private ArrayDeque<Integer> returnLine = new ArrayDeque<>();

    private int currentInstruction = 0;
    private List<Instruction> instructions = new ArrayList<Instruction>();
    
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();

        BinaryOperator addition = (x, y) -> new ZorvexValue(x.asInteger() + y.asInteger());     // j'ai la flemme de créer les fichiers pour ces choses
        BinaryOperator lessThan = (x, y) -> new ZorvexValue(x.asInteger() < y.asInteger() ? 1 : 0);  // là pour l'instant

        List<Instruction> instructions = List.of(
            new Afficher(new Literal(new ZorvexValue("Liste des entiers pairs inférieurs ou égaux à 14"))),
            new Assigner("test", new Literal(new ZorvexValue(0))),
            new TantQue(new BinaryOperation(new Variable("test"), new Literal(new ZorvexValue(14)), lessThan), 
                List.of(
                    new Afficher(new Variable("test")),
                    new Assigner("test", new BinaryOperation(new Variable("test"), new Literal(new ZorvexValue(2)), addition))
                )
            ),
            new Afficher(new Literal(new ZorvexValue("voila")))
        );
        interpreter.instructions = instructions;
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
