package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;

public sealed interface Expression permits Literal, Variable, BinaryOperation, UnaryOperation, Grouping, FunctionCall {
    ZorvexValue value(Context context);
    String printValue();
}

