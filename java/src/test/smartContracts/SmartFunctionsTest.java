package test.smartContracts;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;
import main.java.taxreturns.smartContract.Contract;
import main.java.taxreturns.smartContract.SmartFunctions;
import main.java.taxreturns.smartContract.conditions.Condition;
import main.java.taxreturns.smartContract.conditions.TimeCondition;
import main.java.taxreturns.smartContract.smartScripts.PrintScript;
import main.java.taxreturns.smartContract.smartScripts.SmartScripts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Smart Functions Test")
class SmartFunctionsTest {

    @Test
    void whenConditionsAreMet_ThenReturnsTrue() {

        NodeGroup testNodeGroup = new NodeGroup();
        testNodeGroup.addNode(new Node(),0);
        testNodeGroup.addNode(new Node(),1);
        testNodeGroup.addNode(new Node(),2);
        testNodeGroup.addNode(new Node(),3);

        // Create Blockchain
        Blockchain blockchain = new BlockchainImpl(testNodeGroup);
        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");
        Block block = new Block(29328102, testData);
        Block block1 = new Block(121838213, testData);
        blockchain.addBlock(block,0);
        blockchain.addBlock(block1,0);

        Boolean exceptionthrown = false;
        // Create Smart Function;
        TimeCondition AfterJan1st99 = null;
        try {
            AfterJan1st99 = new TimeCondition("01-01-1999 00:00:00");
        } catch (Exception e) {
            exceptionthrown = true;
        }

        SmartScripts printScript = new PrintScript();

        List<Condition> conditions = List.of(AfterJan1st99);

        SmartFunctions printIfAfterJan1st99 = new SmartFunctions(conditions,printScript);

        assertTrue(printIfAfterJan1st99.checkConditions(blockchain));
        assertFalse(exceptionthrown);
    }
}