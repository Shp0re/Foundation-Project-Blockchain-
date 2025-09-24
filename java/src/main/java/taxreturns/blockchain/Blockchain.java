package main.java.taxreturns.blockchain;

import java.util.List;

public interface Blockchain {

    List<Block> getBlocks();

    void addBlock(Block newBlock);

    boolean isChainValid();

    Block getLatestBlock();

}
