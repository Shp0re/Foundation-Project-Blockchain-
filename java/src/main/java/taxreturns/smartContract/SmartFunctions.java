package main.java.taxreturns.smartContract;


import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.smartContract.conditions.Condition;
import main.java.taxreturns.smartContract.smartScripts.SmartScripts;

import java.util.List;

// functions that the smart contract runs on the blockchain
public class SmartFunctions {

    private List<Condition> conditions;
    private SmartScripts script;


    public SmartFunctions(List<Condition> conditions, SmartScripts script) {
        this.conditions = conditions;
        this.script = script;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
    public void setConditions(List<Condition> conditions) {}

    public Boolean checkConditions(Blockchain blockchain){
        Boolean result = true;
        for (Condition condition : conditions){
            if (condition.check(blockchain) == false) {
                result = false;
            }
        }
        return result;
    }

    public void run(Blockchain blockchain){
        if (checkConditions(blockchain)){
            script.run(blockchain);
        }
    }


}
