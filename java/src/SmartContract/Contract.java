package SmartContract;

import SmartContract.Conditions.Condition;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

import java.util.List;

public class Contract extends Block {

    private final List<SmartFunctions> SmartFunctions;

    public Contract(long digitalSignature, List<String> data, List<SmartFunctions> smartFunctions) {
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
