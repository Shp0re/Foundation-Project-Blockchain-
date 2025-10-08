package main.java.taxreturns.smartContract.smartScripts;

import main.java.taxreturns.blockchain.Blockchain;

public class PrintScript  implements SmartScripts {
    @Override
    public void run(Blockchain blockchain) {
        System.out.println(blockchain.toString());
    }
}
