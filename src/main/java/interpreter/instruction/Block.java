package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;

public record Block(List<InstructionInfo> instructions) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        for (InstructionInfo inst : instructions())
            inst.instruction().interpret(context);
        return Optional.empty();
    } 
}
