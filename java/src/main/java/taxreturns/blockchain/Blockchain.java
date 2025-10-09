package main.java.taxreturns.blockchain;

import java.util.List;

public interface Blockchain {

    List<Block> getBlocks();

    void addBlock(Block newBlock);

    Block getLatestBlock();

    String toString();

    String output();

    void editBlock(String hash, List<String> newTestData);

    Block getBlock(String hash);

    int size();

    Block getBlockIndex(int i);
}
