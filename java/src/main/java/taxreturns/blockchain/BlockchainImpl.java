package main.java.taxreturns.blockchain;

import Node.NodeGroup;

import java.util.ArrayList;
import java.util.List;

public class BlockchainImpl implements Blockchain {

    NodeGroup nodeGroup;
    List<Block> blockchain;
    public BlockchainImpl(NodeGroup nodeGroup) {
        this.blockchain = new ArrayList<Block>();
        this.nodeGroup = nodeGroup;
        nodeGroup.addBlockchain(this);
    }

    @Override
    public List<Block> getBlocks() {
        return blockchain;
    }

    @Override
    public String toString(){
        String result = "";

        for (Block block : blockchain){
            result += block.toString() + "\n";
        }

        return result;
    }

    @Override
    public String output() {
        String result = "";

        result += "BLOCKCHAIN : " + blockchain.size() + " Blocks \n";
        for (Block block : blockchain){
            result += block.toString() + "\n";
        }

        return result;
    }

    @Override
    public void editBlock(String hash, List<String> newTestData) {
        Block block = getBlock(hash);

        block.setData(newTestData);
    }

    @Override
    public Block getBlock(String hash) {
        for (Block block : blockchain){
            if (block.getHash().equals(hash)){
                return block;
            }
        };
        return null;
    }

    @Override
    public int size() {
        return blockchain.size();
    }

    @Override
    public Block getBlockIndex(int i) {
        return blockchain.get(i);
    }

    @Override
    public void addBlock(Block newBlock, int blockchainIndex) {
        if (nodeGroup.checkNewBlock(newBlock,blockchainIndex)) {
            this.blockchain = nodeGroup.getBlockchain(blockchainIndex).getBlocks();
        }
    }


    @Override
    public Block getLatestBlock() {

        return this.blockchain.isEmpty() ? null : this.blockchain.get(this.blockchain.size() - 1);

    }
}