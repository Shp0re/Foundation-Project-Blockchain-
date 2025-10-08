package main.java.taxreturns.smartContract.conditions;

import main.java.taxreturns.blockchain.Blockchain;

public interface Condition {

    public default Boolean check(Blockchain blockchain) {
       return true;
    }
}
