package SmartContract.Conditions;

import main.java.taxreturns.blockchain.Blockchain;

public interface Condition {

    public default Boolean check(Blockchain blockchain) {
       return true;
    }
}
