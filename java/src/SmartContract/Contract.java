package SmartContract;

import main.java.taxreturns.blockchain.Block;

import java.util.List;

public class Contract extends Block {

    private List<SmartFunctions> SmartFunctions;

    public Contract(long digitalSignature, List<String> data, List<SmartFunctions> SmartFunctions) {
        super(digitalSignature, data);
        SmartFunctions = SmartFunctions;
    }
}
