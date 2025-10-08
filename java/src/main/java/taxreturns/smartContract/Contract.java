package main.java.taxreturns.smartContract;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

import java.util.List;

public class Contract extends Block {

    private final List<SmartFunctions> SmartFunctions;

    public Contract(long digitalSignature, List<String> data, List<SmartFunctions> smartFunctions, List<Modifiers> modifiers) {
        super(digitalSignature, data);
        SmartFunctions = smartFunctions;
    }

    public List<SmartFunctions> getSmartFunctions() {
        return SmartFunctions;
    }

    public void run(Blockchain blockchain) {
        for (SmartFunctions smartFunctions : SmartFunctions) {
            smartFunctions.run(blockchain);
        }
    }


}
