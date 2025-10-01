package SmartContract.SmartScripts;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

import java.util.List;

public class ReadScript implements SmartScripts {

    private final String toBeRead;
    private List<Block> blocks;
    public ReadScript(String toBeRead) {
        this.toBeRead = toBeRead;
    }

    @Override
    public void run(Blockchain blockchain) {
        List<Block> blocks = blockchain.getBlocks();
        for (Block block : blocks) {
            List<String> data = block.getData();
            for (String dataLine : data) {
                if (dataLine.contains(toBeRead)) {
                        blocks.add(block);
                    }
                }
            }
        }
}

