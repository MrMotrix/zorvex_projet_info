package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;

public record FunctionDeclaration(String name, Block block, List<String> arguments) implements Instruction {
    @Override
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.empty();
    }
    
}
