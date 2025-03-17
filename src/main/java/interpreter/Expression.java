package interpreter;

public sealed interface Expression permits Literal, Variable, BinaryOperation, UnaryOperation, Grouping {
    ZorvexValue value(Context context);
    String printValue();
}

