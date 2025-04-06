package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.SyntaxErrorException;
import interpreter.expression.BinaryOperation;
import interpreter.expression.Expression;
import interpreter.expression.BinaryOperator;
import interpreter.expression.FunctionCall;
import interpreter.expression.Literal;
import interpreter.expression.Variable;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.Function;
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

    public static void main(String[] args) throws SyntaxErrorException, RuntimeError {
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
            "fonction ack(m,n) {",
                    "si m = 0 {",
                        "retourner n+1",
                    "}",
                    "si n = 0 {",
                        "retourner ack(m-1, 1)",
                    "}",
                    "retourner ack(m-1, ack(m, n-1))",
                "}", 
            "x <- fibo(1,2)"
            );
        
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

    public int getId(String name) {
        return stack.peekLast().getId(name);
    }

    public ZorvexValue getVariable(int id) {
        for (Context context : stack)
            if (context.getValueById(id) != null)
                return context.getValueById(id);
        return null;
    }

    private Instruction handleFunctionCall(FunctionCall fc) throws RuntimeError {
        if (!functions.containsKey(fc.name())) 
            throw new RuntimeError(-1, "Function " + fc.name() + " doesn't exist");
        
        FunctionDeclaration fd = functions.get(fc.name());
        if (fc.args().size() != fd.parameters().size()) 
            throw new RuntimeError(-1, "Wrong arguments for " + fc.name() + " call, expected " + fd.parameters().size() + " got " + fc.args().size());
        
        Context currentContext = stack.getLast();
        Context newContext = new Context();
        List<String> parameters = fd.parameters();
        List<Expression> args = fc.args();
        List<ZorvexValue> values = new ArrayList<>();

        for (int i = 0; i < args.size(); i++) {
            ZorvexValue value = new ZorvexValue(args.get(i).value(currentContext));
            newContext.assignVariable(parameters.get(i), value);
            values.add(value);
        }

        stack.addLast(newContext);
        callStack.addLast(fd.block());
        state.enterBlock(fd.block());
        return new Function(values, fc.name());
    }
    
    public Instruction step() throws RuntimeError {
        try {
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
                    retourner.setResult(lastReturnValue);
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
        catch (RuntimeError error) {
            error.setLineNumber(getCurrentLine());
            throw error;
        }
    }
}
