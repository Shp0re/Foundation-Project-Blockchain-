package main.java.taxreturns;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockControler.MultiChannel;
import main.java.taxreturns.blockControler.SingleChannel;
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
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaxReturnApplication {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        String accountNumber;
        String sortCode;
        String name;
        String bankName;
        String uniqueTaxID;
        boolean flag = false;

        NodeGroup nodeGroup = new NodeGroup(); // Here we create the nodes that validate the integrity of the Blockchain
        nodeGroup.addNode(new Node(),0);
        nodeGroup.addNode(new Node(),1);
        nodeGroup.addNode(new Node(),2);
        nodeGroup.addNode(new Node(),3);
        nodeGroup.addNode(new Node(),4);
        nodeGroup.addNode(new Node(),5);
        nodeGroup.addNode(new Node(),6);
        nodeGroup.addNode(new Node(),7);
        nodeGroup.addNode(new Node(),8);
        nodeGroup.addNode(new Node(),9); // 10 in total means that for each transaction 3 or 4 should validate

        UIController controller = new UIController(nodeGroup);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        do{
            System.out.println("Enter Account Number");
            accountNumber = reader.nextLine();
            if (accountNumber.length() == 8){
                flag = true;
            }
        }while(!flag);
        flag = false;

        do{
            System.out.println("Enter sort code");
            sortCode = reader.nextLine();
            if (sortCode.length() == 6){
                flag = true;
            }
        }while(!flag);

        System.out.println("Whats your name?");
        name = reader.nextLine();
        System.out.println("What is the name of your Bank?");
        bankName = reader.nextLine();
        System.out.println("Please enter a unique tax ID");
        uniqueTaxID = reader.nextLine();

        BlockchainImpl blockchainGood = new BlockchainImpl(nodeGroup, accountNumber);
        BlockchainImpl blockchainBad = new BlockchainImpl(nodeGroup, "90006553");

        nodeGroup.setBlockchain(blockchainGood, sortCode + accountNumber);
        nodeGroup.setBlockchain(blockchainBad, "90006553");

        System.out.println("Empty Blockchain Created for account number: "+accountNumber+"\n");

        List<String> transaction1 = List.of(accountNumber, "13/10/2024","£504.24","Expense");
        List<String> transaction2 = List.of(accountNumber, "12/10/2024","£52000.24","Income");
        List<String> dodgyTransaction = List.of(accountNumber, "12/10/2024","£0.24","Income");

        System.out.println("Test data: " + transaction1 + "\n");
        System.out.println("Test data: " + transaction2 + "\n");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }


        Block block = new Block(Long.parseLong("416321344512"), transaction1);

        System.out.println("First block to be added: " + block.output() + "\n");

        Block block1 = new Block(Long.parseLong("516884344512"), transaction2);

        System.out.println("Second block to be added: " + block1.output() + "\n");

        try {
            blockchainGood.addBlock(block,sortCode + accountNumber);
            blockchainGood.addBlock(block1,sortCode + accountNumber);
        }catch (NoSuchElementException e){
            System.out.println("WARNING: Unable to add Blocks to chain threw ERROR:"+e);
        }

        System.out.println("Blocks has been successfully added\n");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        System.out.println(blockchainGood.output());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        System.out.println("Is chain valid? " + nodeGroup.ValidateBlockchain(sortCode + accountNumber) + "\n");

        System.out.println("Now we try to add an invalid block to the chain to see if it gets rejected");
        Block block2 = new Block(Long.parseLong("416321344512"), transaction1);
        Block block3 = new Block(Long.parseLong("516884344512"), transaction2);

        blockchainBad.addBlock(block2, "90006553");
        blockchainBad.addBlock(block3, "90006553");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        System.out.println("In an identical Blockchain to the one we just created someone just tried to set their income to £0\nLets see if this will validate");
        String blockToEdit = block2.getHash();
        blockchainBad.editBlock(blockToEdit, dodgyTransaction);
        System.out.println("Is chain valid? " +  nodeGroup.ValidateBlockchain("90006553") + "\nIt would seem that this chain isnt valid");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        System.out.println("Now we add our blockchain to an account that is registered under a Bank Branch");
        SingleChannel newAccount = new SingleChannel(accountNumber, blockchainGood, name);
        MultiChannel newOrg = new MultiChannel(sortCode, bankName);

        newOrg.addChannel(newAccount);
        controller.manager.addOrganisation(newOrg);
        System.out.println("We have added a new bank organisation and a new account with your blockchain and the transactions within it");
        controller.manager.LinkIDtoAcc(uniqueTaxID, accountNumber, sortCode);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Well that didnt work");
        }

        // Chanel and UI test
        // Creating Channels and populating them with data

        String[] fileNames = {"java/src/data/01020310045555.csv","java/src/data/01020310071456.csv","java/src/data/01020312242256.csv",
        "java/src/data/04050711111111.csv","java/src/data/04050712233221.csv"};
        String[][] orgData = {{"010203", "HSBC"}, {"040507"}};
        String[][] accountData = {{"10045555", "010203"}, {"10071456","010203"}, {"12242256", "010203"}, {"11111111", "040507"}, {"12233221", "040507"}};

        // creating controler


        //populating controller with data

        //first add organisations
        for (int i = 0; i < 2; i++) {
            try{
                controller.manager.addOrganisation(orgData[i]);
            } catch (Exception e) {
                System.out.println("WARNING: Org "+ orgData[i][0]+ ". Could not be added due to ERROR:"+e);
            }
        }
        //then accounts
        for (int i = 0; i < accountData.length; i++) {
            try{
                controller.manager.addAccount(accountData[i]);
            } catch (Exception e) {
                System.out.println("WARNING: Account "+ accountData[i][0]+ ". Could not be added ERRROR Cause: "+e);
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
            controller.manager.LinkIDtoAcc("TAX002","10071456","010203");
            controller.manager.LinkIDtoAcc("TAX002","12233221", "040507");
        } catch (Exception e) {
            System.out.println("WARNING: Issue in linking accounts to tax number. Case:"+e);
        }
        try {
            nodeGroup.validateAll();
        }catch (RuntimeException e){
            System.out.println(e);
        }



    }

}