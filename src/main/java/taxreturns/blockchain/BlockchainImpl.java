package blockchain;

import java.util.List;

public class BlockchainImpl implements Blockchain {

    @Override
    public List<Block> getBlocks() {
        return List.of();
    }

    @Override
    public void addBlock(Block newBlock) {

    }

    @Override
    public boolean isChainValid() {
        return false;
    }

    @Override
    public Block getLatestBlock() {
        return null;
    }
}
