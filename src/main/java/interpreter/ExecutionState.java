package interpreter;
import java.util.List;

import interpreter.expression.ExpressionEvaluator;
import interpreter.expression.FunctionCall;
import interpreter.instruction.Block;
import interpreter.instruction.InstructionInfo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class ExecutionState {
    private Deque<BlockInfo> blocks = new ArrayDeque<>();

    public boolean inBlock() {
        return !blocks.isEmpty();
    }

    public void enterBlock(Block block) {
        blocks.offerLast(new BlockInfo(block));
    }

    public void exitBlock() {
        blocks.removeLast();
    }

    public Block getCurrentBlock() {
        return blocks.peekLast().block;
    }
    
    public boolean step() {
        BlockInfo blockInfo = blocks.peekLast();
        blockInfo.position += 1;
        blockInfo.expressionEvaluator = null;

        return hasBlockReachedEnd();
    }

    public boolean hasBlockReachedEnd() {
        BlockInfo blockInfo = blocks.peekLast();

        return blockInfo.position >= blockInfo.block.instructions().size();
    }
    public InstructionInfo getCurrentInstruction() {
        BlockInfo blockInfo = blocks.peekLast();
        
        return blockInfo.block.instructions().get(blockInfo.position);
    }

    public boolean isEvaluated() {
        BlockInfo blockInfo = blocks.peekLast();
        if (blockInfo.expressionEvaluator == null) 
            blockInfo.expressionEvaluator = new ExpressionEvaluator(getCurrentInstruction().instruction().expression());
        
        return blockInfo.expressionEvaluator.isEvaluated();
    }
    
    public void serveFunctionCall(ZorvexValue value) {
        blocks.peekLast().expressionEvaluator.serveNextFunctionCall(value);
    }

    public FunctionCall getNextFunctionCall() {
        return blocks.peekLast().expressionEvaluator.getNextFunctionCall();
    }

    public ZorvexValue result(Context context) {
        BlockInfo blockInfo = blocks.peekLast();
        return blocks.peekLast().expressionEvaluator.result(context);
    }

    private class BlockInfo {
        private final Block block;
        private int position;
        private ExpressionEvaluator expressionEvaluator;

        public BlockInfo(Block block) {
            this.block = block;
            this.position = 0;
            this.expressionEvaluator = null;
        }
    }
}
