package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;
import interpreter.expression.FunctionCall;

public sealed interface Instruction permits Afficher, Assigner, Block, FunctionDeclaration, Retourner, Si, TantQue {
    Optional<ZorvexValue> interpret(Context context);
    Expression expression();
}
