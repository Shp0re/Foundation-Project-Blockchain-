package main.java.taxreturns.smartContract.conditions;

import main.java.taxreturns.blockchain.Blockchain;

public interface Condition {

    public String name();

    public default Boolean check(Blockchain blockchain) {
       return true;
    }

    public default String output() {return "check if blockchain meets conditions"; }
}
