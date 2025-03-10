package interpreter;

public sealed interface Expression permits Literal, Variable, BinaryOperation {
    ZorvexValue value(Context context);
}

