package blockchain;

import java.util.List;

public class BlockchainImpl implements Blockchain {


    private List<Block> blockchain;

    public BlockchainImpl(List<Block> blockchain) {
        this.blockchain = blockchain;
    }

    @Override
    public List<Block> getBlocks() {
        return blockchain;
    }

    @Override
    public void addBlock(Block newBlock) {
        newBlock.setBlockIndex(this.blockchain.size());
        newBlock.setPreviousHash(this.getLatestBlock().getHash());
        this.blockchain.add(newBlock);
    }

    @Override
    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);
            if (!block.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Block getLatestBlock() {
        Block latestBlock = this.blockchain.get(this.blockchain.size() - 1);
        return latestBlock;
    }
}