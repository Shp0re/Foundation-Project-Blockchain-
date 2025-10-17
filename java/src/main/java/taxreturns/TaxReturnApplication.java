package main.java.taxreturns;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;
import main.java.taxreturns.frontEnd.UIController;
import main.java.taxreturns.smartContract.Contract;
import main.java.taxreturns.smartContract.SmartFunctions;
import main.java.taxreturns.smartContract.conditions.Condition;
import main.java.taxreturns.smartContract.conditions.TimeCondition;
import main.java.taxreturns.smartContract.smartScripts.PrintScript;
import main.java.taxreturns.smartContract.smartScripts.SmartScripts;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TaxReturnApplication {

    public static void main(String[] args) {

        NodeGroup nodeGroup = new NodeGroup();
        nodeGroup.addNode(new Node(),0);
        nodeGroup.addNode(new Node(),1);
        nodeGroup.addNode(new Node(),2);
        nodeGroup.addNode(new Node(),3);

        Blockchain blockchain = new BlockchainImpl(nodeGroup);

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

        System.out.println("Is chain valid? " + nodeGroup.ValidateBlockchain() + "\n");

        System.out.println("Editing a block to have new test data");

        List<String> newTestData = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");

        System.out.println("New test data: " + newTestData + "\n");

        String testHash = block.getHash();

        System.out.println("Get hash of block to edit: " + testHash + "\n");

        blockchain.editBlock(testHash, newTestData);

        System.out.println("Block has been successfully edited\n");
        System.out.println(blockchain.output());

        //System.out.println("Is chain valid? " + blockchain.isChainValid() + "\n");

         //SMART CONTRACT IMPLEMENTATION

        System.out.println("Smart contract creation steps\n");

        TimeCondition AfterJan1st99 = null;

        System.out.println("Create conditions\n");

        try {
            AfterJan1st99 = new TimeCondition("01-01-1999 00:00:00");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("This condition: " + AfterJan1st99.output());

        System.out.println("Create script\n");

        SmartScripts printScript = new PrintScript();

        System.out.println("This script: " + printScript.output());

        List<Condition> conditions = List.of(AfterJan1st99);

        System.out.println("Create SmartFunction\n");

        SmartFunctions printIfAfterJan1st99 = new SmartFunctions(conditions,printScript);

        List<SmartFunctions> smartFunctions = List.of(printIfAfterJan1st99);

        printIfAfterJan1st99.output();

        System.out.println("Create smart contract\n");

        Contract contract = new Contract(29328102,testData,smartFunctions,null);

        blockchain.addBlock(contract);

        System.out.println("Run contract\n");

        contract.run(blockchain);
        // Chanel and UI test
        // Creating Channels and populating them with data

        String[] fileNames = {"java/src/data/01020310045555.csv","java/src/data/01020310071456.csv","java/src/data/01020312242256.csv",
        "java/src/data/04050711111111.csv","java/src/data/04050712233221.csv"};
        String[][] orgData = {{"010203", "HSBC"}, {"040507"}};
        String[][] accountData = {{"10045555", "010203"}, {"10071456","010203"}, {"12242256", "010203"}, {"11111111", "040507"}, {"12233221", "040507"}};

        // creating controler
        UIController controller = new UIController(nodeGroup);

        //populating controller with data

        //first organisations
        for (int i = 0; i < 2; i++) {
            try{
                controller.manager.addOrganisation(orgData[i]);
            } catch (Exception e) {
                System.out.println("WARNING: Org "+ orgData[i][0]+ ". Could not be added");
            }
        }
        //then accounts
        for (int i = 0; i < accountData.length; i++) {
            try{
                controller.manager.addAccount(accountData[i]);
            } catch (Exception e) {
                System.out.println("WARNING: Account "+ accountData[i][0]+ ". Could not be added");
            }
        }
        //then transactions
        for(int i = 0; i < fileNames.length; i++) {
            try{
                controller.getMassTransactionData(fileNames[i]);
            }catch (IOException e){
                System.out.println("WARNING: An Error occured when looking for filename: " +fileNames[i]);
            }
        }
        //finally link accounts to taxIDs
        try{
            controller.manager.LinkIDtoAcc("TAX001","10045555", "010203");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}