package main.java.taxreturns.blockControler;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

import java.util.List;

public class SingleChannel implements Channel {
    private String name;
    final String id;
    private Blockchain ledger;

    public SingleChannel(String id, Blockchain ledger){
        this.id = id;
        this.ledger = ledger;
        this.name = "Null";
    }

    public SingleChannel(String id, Blockchain ledger, String name){
        this.id = id;
        this.name = name;
        this.ledger = ledger;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public List<Block> getAllItems() {
        return ledger.getBlocks();
    }

    @Override
    public Object getSpecificItem(String identifier) {
       return ledger.getBlock(identifier);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBlock(Block blockToAdd){
        this.ledger.addBlock(blockToAdd);
        System.out.println("LOG: Transaction:"+ blockToAdd.getDigitalSignature()+ " Added to Account:"+this.getID());
    }
}
