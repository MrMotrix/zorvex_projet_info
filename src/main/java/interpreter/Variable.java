package interpreter;

public record Variable(String name) implements Expression {
    public ZorvexValue value(Context context) {
        return context.getVariable(name);
    }

    public String printValue() {
        return name;
    }
}
