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
        block.changeHash();
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
    public void addBlock(Block newBlock) {
        if (nodeGroup.checkNewBlock(newBlock)) {
            newBlock.setBlockIndex(this.blockchain.size());
            if (!(this.getLatestBlock() == null)) {
                newBlock.setPreviousHash(this.getLatestBlock().getHash());
            }
            this.blockchain.add(newBlock);
        }
    }

    @Override
    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);
            if (!block.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Block " + (i) + " is not referenced");
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