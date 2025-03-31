package interpreter;
import java.util.List;

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

    public boolean step() {
        BlockInfo blockInfo = blocks.peekLast();
        blockInfo.position += 1;

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
    
    private class BlockInfo {
        private final Block block;
        private int position;

        public BlockInfo(Block block) {
            this.block = block;
            this.position = 0;
        }
    }
}
