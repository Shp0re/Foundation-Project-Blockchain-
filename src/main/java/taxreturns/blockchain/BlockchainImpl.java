package blockchain;

import java.util.List;

public class BlockchainImpl implements Blockchain {

    private List<Block> blockchain;

    @Override
    public List<Block> getBlocks() {
        return blockchain;
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