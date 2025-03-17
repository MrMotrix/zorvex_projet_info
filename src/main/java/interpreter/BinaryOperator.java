package interpreter;

public interface BinaryOperator {
    ZorvexValue apply(ZorvexValue obj1, ZorvexValue obj2);
    String toString();
}