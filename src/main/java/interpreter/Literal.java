package interpreter;

public record Literal(ZorvexValue value) implements Expression {
    public ZorvexValue value(Context context) {
        return this.value;
    }

    public String printValue() {
        return this.value().asString();
    }
}
