package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class TantQue implements Instruction {
    private Block block;
    private Expression expression;

    public TantQue(Expression condition, Block block) {
        this.expression = condition;
        this.block = block;
    }

    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.empty();
    }

    public Block block() {
        return block;
    }

    @Override
    public Expression expression() {
        return expression;
    }
}
