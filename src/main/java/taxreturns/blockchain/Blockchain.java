package blockchain;

import java.util.List;

public interface Blockchain {
    List<Block> getBlocks();

    void addBlock(Block newBlock);

    boolean isChainValid();

    Block getLatestBlock();

}
