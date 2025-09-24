package main.java.taxreturns;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.List;

public class TaxReturnApplication {

    public static void main(String[] args) {

        Blockchain blockchain = new BlockchainImpl();

        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");

        Block block = new Block(29328102, testData);
        Block block1 = new Block(121838213, testData);

        blockchain.addBlock(block);
        blockchain.addBlock(block1);

        System.out.println(blockchain.string());

    }

}