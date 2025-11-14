package main.java.taxreturns.blockControler;

import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.List;

public class SingleChannel implements Channel {
    private String name;
    final String id;
    private BlockchainImpl ledger;

    public SingleChannel(String id, BlockchainImpl ledger){
        this.id = id;
        this.ledger = ledger;
        this.name = "Null";
    }

    public SingleChannel(String id, BlockchainImpl ledger, String name){
        this.id = id;
        this.name = name;
        this.ledger = ledger;
    }

    public SingleChannel(String id, String name){
        this.id = id;
        this.name = name;
        this.ledger = null;
    }

    public SingleChannel(String id){
        this.id = id;
        this.name = "Null";
        this.ledger = null;
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

    public void addBlock(Block blockToAdd,String fullID){
        this.ledger.addBlock(blockToAdd, fullID);
        System.out.println(STR."LOG: Transaction:\{blockToAdd.getDigitalSignature()} Added to Account:\{this.getID()}");
    }

    public void setLedger(BlockchainImpl ledger){
        if (this.ledger == null){
            this.ledger = ledger;
            System.out.println(STR."LOG: Blockchain added to account:\{this.id}");
        }else{
            System.out.println(STR."WARNING: account \{this.id} alredy has a blockchain asigned");
        }
    }


}
