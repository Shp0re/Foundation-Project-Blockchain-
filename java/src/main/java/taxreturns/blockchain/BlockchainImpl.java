package main.java.taxreturns.blockchain;

import java.util.ArrayList;
import java.util.List;

public class BlockchainImpl implements Blockchain {

    List<Block> blockchain;
    public BlockchainImpl() {
        this.blockchain = new ArrayList<Block>();
    }

    @Override
    public List<Block> getBlocks() {
        return blockchain;
    }

    @Override
    public String string(){
        String result = "";

        for (Block block : blockchain){
            result += block.toString() + "\n";
        }

        return result;
    }

    @Override
    public void addBlock(Block newBlock) {
        newBlock.setBlockIndex(this.blockchain.size());
        if (!(this.getLatestBlock() == null)) {
            newBlock.setPreviousHash(this.getLatestBlock().getHash());
        }
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

        return this.blockchain.isEmpty() ? null : this.blockchain.get(this.blockchain.size() - 1);

    }
}