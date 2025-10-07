package main.java.taxreturns;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;
import main.java.taxreturns.smartContract.Contract;
import main.java.taxreturns.smartContract.SmartFunctions;
import main.java.taxreturns.smartContract.conditions.Condition;
import main.java.taxreturns.smartContract.conditions.TimeCondition;
import main.java.taxreturns.smartContract.smartScripts.PrintScript;
import main.java.taxreturns.smartContract.smartScripts.SmartScripts;


import java.text.ParseException;
import java.util.List;

public class TaxReturnApplication {

    public static void main(String[] args) {

        Blockchain blockchain = new BlockchainImpl();

        System.out.println("Empty Blockchain Created \n");

        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");

        System.out.println("Test data: " + testData + "\n");

        Block block = new Block(29328102, testData);

        System.out.println("First block to be added: " + block.output() + "\n");

        Block block1 = new Block(121838213, testData);

        System.out.println("Second block to be added: " + block1.output() + "\n");

        blockchain.addBlock(block);
        blockchain.addBlock(block1);

        System.out.println("Blocks has been successfully added\n");

        System.out.println(blockchain.output());

        System.out.println("Is chain valid? " + blockchain.isChainValid() + "\n");

        System.out.println("Editing a block to have new test data");

        List<String> newTestData = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");

        System.out.println("New test data: " + newTestData + "\n");

        String testHash = block.getHash();

        System.out.println("Get hash of block to edit: " + testHash + "\n");

        blockchain.editBlock(testHash, newTestData);

        System.out.println("Block has been successfully edited\n");
        System.out.println(blockchain.output());

        System.out.println("Is chain valid? " + blockchain.isChainValid());

        // SMART CONTRACT IMPLEMENTATION

//        TimeCondition AfterJan1st99 = null;
//
//        try {
//            AfterJan1st99 = new TimeCondition("01-01-1999 00:00:00");
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        SmartScripts printScript = new PrintScript();
//
//        List<Condition> conditions = List.of(AfterJan1st99);
//
//        SmartFunctions printIfAfterJan1st99 = new SmartFunctions(conditions,printScript);
//
//        List<SmartFunctions> smartFunctions = List.of(printIfAfterJan1st99);
//
//        Contract contract = new Contract(29328102,testData,smartFunctions,null);
//
//        contract.run(blockchain);

    }

}