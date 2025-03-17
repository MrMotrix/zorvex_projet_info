package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;

public sealed interface Instruction permits Retourner, Assigner, Si, TantQue, Afficher {
    Optional<ZorvexValue> interpret(Context context);
}
