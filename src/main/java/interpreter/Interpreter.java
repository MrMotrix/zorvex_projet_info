package interpreter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.SyntaxErrorException;
import interpreter.expression.Expression;
import interpreter.expression.FunctionCall;
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

    private static Set<FunctionDeclaration> reservedFunctionList = Set.of(
        new FunctionDeclaration("ajouter_liste", null, List.of("liste", "element")), 
        new FunctionDeclaration("inserer_liste", null, List.of("liste", "indice", "element")),
        new FunctionDeclaration("supprimer_liste", null, List.of("liste", "indice")),
        new FunctionDeclaration("taille_liste", null, List.of("liste")),
        new FunctionDeclaration("recuperer_liste", null, List.of("liste", "indice")),
        new FunctionDeclaration("modifier_liste", null, List.of("liste", "indice", "valeur")),
        new FunctionDeclaration("pile_vide", null, List.of()),
        new FunctionDeclaration("empiler", null, List.of("pile", "element")),
        new FunctionDeclaration("depiler", null, List.of("pile")),
        new FunctionDeclaration("est_pile_vide", null, List.of("pile"))
    );

    private int currentInstruction = 0;

    public Interpreter() {
        stack.add(new Context());
    }

    public Interpreter(String code) throws SyntaxErrorException {
        this();
        List<InstructionInfo> instructions = Parser.parse(Scanner.tokenize(code));
        reservedFunctionList.forEach(x -> functions.put(x.name(), x));
        this.state.enterBlock(new Block(instructions));
    }

    public int getCurrentLine() {
        while (state.hasBlockReachedEnd()) {
            if (callStack.size() > 0 && callStack.getLast() == state.getCurrentBlock()) {
                callStack.removeLast();
                stack.removeLast();
                lastReturnValue = ZorvexValue.nullValue();
            }

            state.exitBlock();
        }

        return state.getCurrentInstruction().line();
    }

    public ZorvexValue getVariable(String name) {
        return stack.peekLast().getVariable(name);
    }

    public int getId(String name) {
        return stack.peekLast().getId(name);
    }
    
    public boolean isDone() {
        return !state.inBlock();
    }

    public ZorvexValue getVariable(int id) {
        for (Context context : stack)
            if (context.getValueById(id) != null)
                return context.getValueById(id);
        return null;
    }

    private Instruction handleFunctionCall(FunctionCall fc) throws RuntimeError {

        if (!functions.containsKey(fc.name())) 
            throw new RuntimeError("Function " + fc.name() + " doesn't exist");
        
        FunctionDeclaration fd = functions.get(fc.name());
        if (fc.args().size() != fd.parameters().size()) 
            throw new RuntimeError("Wrong arguments for " + fc.name() + " call, expected " + fd.parameters().size() + " got " + fc.args().size());
        
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
        int variableId = -1;
        if (args.size() >= 1 && args.get(0) instanceof Variable var)  
            variableId = getId(var.name());
        
        if (fc.name().equals("ajouter_liste")) {
            values.get(0).add(values.get(1));
            lastReturnValue = ZorvexValue.nullValue();
            return new Function(List.of(new ZorvexValue(variableId), values.get(1)), fc.name());
        }
        else if (fc.name().equals("supprimer_liste")) {
            values.get(0).remove(values.get(1).asInteger());
            lastReturnValue = ZorvexValue.nullValue();
            return new Function(List.of(new ZorvexValue(variableId), values.get(1)), fc.name());
        }
        else if (fc.name().equals("inserer_liste")) {
            values.get(0).insert(values.get(1).asInteger(), values.get(2));
            lastReturnValue = ZorvexValue.nullValue();
            return new Function(List.of(new ZorvexValue(variableId), values.get(1), values.get(2)), fc.name());
        }
        else if (fc.name().equals("modifier_liste")) {
            values.get(0).set(values.get(1).asInteger(), values.get(2));
            lastReturnValue = ZorvexValue.nullValue();
            return new Function(List.of(new ZorvexValue(variableId), values.get(1), values.get(2)), fc.name());
        }
        else if (fc.name().equals("taille_liste")) {
            lastReturnValue = new ZorvexValue(values.get(0).size());
            return new Function(values, fc.name());
        }
        else if (fc.name().equals("recuperer_liste")) {
            lastReturnValue = new ZorvexValue(values.get(0).get(values.get(1).asInteger()));
            return new Function(values, fc.name());
        }
        else if (fc.name().equals("pile_vide")) {
            lastReturnValue = ZorvexValue.emptyStack();
            return new Function(List.of(), fc.name());
        }
        else if (fc.name().equals("empiler")) {
            lastReturnValue = ZorvexValue.nullValue();
            values.get(0).empiler(values.get(1));
            return new Function(List.of(new ZorvexValue(variableId), values.get(1)), fc.name());
        }
        else if (fc.name().equals("depiler")) {
            lastReturnValue = values.get(0).depiler();
            return new Function(List.of(new ZorvexValue(variableId), lastReturnValue), fc.name());
        }
        else if (fc.name().equals("est_pile_vide")) {
            lastReturnValue = new ZorvexValue(values.get(0).isEmpty() ? 1 : 0);
            return new Function(values, fc.name());
        }

        lastReturnValue = null;
        stack.addLast(newContext);
        callStack.addLast(fd.block());
        state.enterBlock(fd.block());
        return new Function(values, fc.name());
    }
    
    public Instruction step() throws RuntimeError {
        if (state.hasBlockReachedEnd()) {
            if (callStack.size() > 0 && callStack.getLast() == state.getCurrentBlock()) {
                callStack.removeLast();
                stack.removeLast();
                lastReturnValue = ZorvexValue.nullValue();
            }

            state.exitBlock();
            return null;
        }

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
                else if (si.elseBlock() != null)
                    state.enterBlock(si.elseBlock());
                return instruction;
            case TantQue tantQue:
                if (result.asInteger() != 0) 
                    state.enterBlock(tantQue.block());
                else
                    state.step();
                return instruction;
            case FunctionDeclaration fd:
                if (functions.containsKey(fd.name()) || reservedFunctionList.contains(fd.name()))
                    throw new RuntimeError("Function "+fd.name()+" is already defined.");
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
                lastReturnValue = ZorvexValue.nullValue();
            }

            state.exitBlock();
        }
        
        return instruction;
    }
}
