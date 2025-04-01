package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interpreter.exceptions.SyntaxErrorException;
import interpreter.expression.BinaryOperation;
import interpreter.expression.BinaryOperator;
import interpreter.expression.Literal;
import interpreter.expression.Variable;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.Block;
import interpreter.instruction.Instruction;
import interpreter.instruction.TantQue;
import interpreter.instruction.InstructionInfo;
import interpreter.instruction.Si;

public class Interpreter {
    private ArrayDeque<Context> stack = new ArrayDeque<>();
    private ExecutionState state = new ExecutionState();

    private int currentInstruction = 0;

    public static void main(String[] args) throws SyntaxErrorException {
        List<String> program = List.of("n <- 13", "p <- 5", "compose <- 0", "i <- 1"
        , "tant que i < n {", "i <- i+2", "p <- p+1", "si i < p {", "i <- i", "x <- i+1", "}", "x <- i+2", "}", "n <- n+p");
        System.out.println();
        Interpreter interpreter = new Interpreter(String.join("\n", program) + "\n");
        
        while (interpreter.state.inBlock()) {
            int currentLine = interpreter.getCurrentLine();

            for (int i = 1; i <= program.size(); i++) {
                if (i == currentLine)
                    System.out.println(program.get(i-1) + " | " + interpreter.getVariable("p") + " " + interpreter.getVariable("i"));
                else
                    System.out.println(program.get(i-1));
            }
            try {
                Thread.sleep(0);
            }
            catch (Exception e) {
                
            }
            interpreter.step();

        }
    }
    public Interpreter() {
        stack.add(new Context());
    }

    public Interpreter(String code) throws SyntaxErrorException {
        this();
        this.state.enterBlock(new Block(Parser.parse(Scanner.tokenize(code))));
    }

    public int getCurrentLine() {
        return state.getCurrentInstruction().line();
    }

    public ZorvexValue getVariable(String name) {
        return stack.peekLast().getVariable(name);
    }
    
    public Instruction step() {
        Instruction instruction = state.getCurrentInstruction().instruction();
        
        if (instruction instanceof Si si) {
            state.step();
            if (si.condition().value(stack.getLast()).asInteger() != 0) 
                state.enterBlock(si.block());
            return instruction;
        }

        else if (instruction instanceof TantQue tantQue) {
            if (tantQue.condition().value(stack.getLast()).asInteger() != 0) 
                state.enterBlock(tantQue.block());
            else
                state.step();
            return instruction;
        }
        
        else {
            instruction.interpret(stack.getLast());
            state.step();
        }

        if (state.hasBlockReachedEnd()) 
            state.exitBlock();
        
        return instruction;
    }
}
