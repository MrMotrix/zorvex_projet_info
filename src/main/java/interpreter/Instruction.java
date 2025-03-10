package interpreter;

import java.util.Optional;

public sealed interface Instruction permits Retourner, Assigner, Si, TantQue, Afficher {
    Optional<ZorvexValue> interpret(Context context);
}
