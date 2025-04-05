package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import interpreter.exceptions.SyntaxErrorException;
import interpreter.expression.BinaryOperation;
import interpreter.expression.Expression;
import interpreter.expression.BinaryOperator;
import interpreter.expression.FunctionCall;
import interpreter.expression.Literal;
import interpreter.expression.Variable;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.Block;
import interpreter.instruction.FunctionDeclaration;
import interpreter.instruction.Instruction;
import interpreter.instruction.TantQue;
import interpreter.instruction.InstructionInfo;
import interpreter.instruction.Retourner;
import interpreter.instruction.Si;

public class Interpreter {
    private ArrayDeque<Context> stack = new ArrayDeque<>();
    private ArrayDeque<Block> callStack = new ArrayDeque<>();

    private ExecutionState state = new ExecutionState();
    private ZorvexValue lastReturnValue = null;
    
    private HashMap<String, FunctionDeclaration> functions = new HashMap<>();

    private int currentInstruction = 0;

    public static void main(String[] args) throws SyntaxErrorException {
        List<String> program = List.of(
            "fonction fibo(n) {",
                "si n = 0 {",
                    "retourner 0"   ,
                "}",
                "si n = 1 {",
                    "retourner 1",
                "}",
                "retourner fibo(n-1) + fibo(n-2)",
            "}",
            "afficher fibo(20)");
        
        Interpreter interpreter = new Interpreter(String.join("\n", program) + "\n");
        
        while (interpreter.state.inBlock()) {
            interpreter.step();
        }
    }
    public Interpreter() {
        stack.add(new Context());
    }

    public Interpreter(String code) throws SyntaxErrorException {
        this();
        List<InstructionInfo> instructions = Parser.parse(Scanner.tokenize(code));
        this.state.enterBlock(new Block(instructions));
    }

    public int getCurrentLine() {
        return state.getCurrentInstruction().line();
    }

    public ZorvexValue getVariable(String name) {
        return stack.peekLast().getVariable(name);
    }

    private Instruction handleFunctionCall(FunctionCall fc) {
        if (!functions.containsKey(fc.name())) {
            // exceptions
        }
        FunctionDeclaration fd = functions.get(fc.name());
        if (fc.args().size() != fd.parameters().size()) {
            // exceptions
        }
        Context currentContext = stack.getLast();
        Context newContext = new Context();
        List<String> parameters = fd.parameters();
        List<Expression> args = fc.args();

        for (int i = 0; i < args.size(); i++) {
            newContext.assignVariable(parameters.get(i), new ZorvexValue(args.get(i).value(currentContext)));
        }

        stack.addLast(newContext);
        callStack.addLast(fd.block());
        state.enterBlock(fd.block());
        return null;
    }
    
    public Instruction step() {
        Instruction instruction = state.getCurrentInstruction().instruction();
        if (!state.isEvaluated()) {
            if (lastReturnValue != null) {
                state.serveFunctionCall(lastReturnValue);
                lastReturnValue = null;
                return null;
            }

            FunctionCall fc = state.getNextFunctionCall();
            return handleFunctionCall(fc);
        }   
        ZorvexValue result = state.result(stack.getLast());        
        switch (instruction) {
            case Si si:
                state.step();
                if (result.asInteger() != 0) 
                    state.enterBlock(si.block());
                return instruction;
            case TantQue tantQue:
                if (result.asInteger() != 0) 
                    state.enterBlock(tantQue.block());
                else
                    state.step();
                return instruction;
            case FunctionDeclaration fd:
                functions.put(fd.name(), fd);
                state.step();
                break;
            case Retourner retourner:
                lastReturnValue = state.result(stack.getLast());
                while (state.getCurrentBlock() != callStack.getLast())
                    state.exitBlock();
                state.exitBlock();
                stack.removeLast();
                callStack.removeLast();
                break;
            case Assigner assigner:
                assigner.serveResult(result);
                instruction.interpret(stack.getLast());
                state.step();
                break;
            case Afficher afficher:
                afficher.setResult(result);
            default:
                instruction.interpret(stack.getLast());
                state.step();
                break;
        }

        if (state.hasBlockReachedEnd()) {
            if (callStack.size() > 0 && callStack.getLast() == state.getCurrentBlock()) {
                callStack.removeLast();
                stack.removeLast();
                // ici il faudrait un espece de NaN
            }

            state.exitBlock();
        }
        
        return instruction;
    }
}
