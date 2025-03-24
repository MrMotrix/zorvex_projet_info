package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record Block(List<Instruction> instructions) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        for (Instruction inst : instructions())
            inst.interpret(context);
        return Optional.empty();
    } 
}
