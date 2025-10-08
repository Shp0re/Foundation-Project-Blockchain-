package main.java.taxreturns.smartContract.smartScripts;

import main.java.taxreturns.blockchain.Blockchain;

public interface SmartScripts {

    void run(Blockchain blockchain);

    public String name();

    default String output() {
        return ("What is run if conditions are meet\n");
    }
}
