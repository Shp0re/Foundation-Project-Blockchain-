package test.blockchain;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.BlockchainImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Blockchain Implementation Test")
class BlockchainImplTest {

    BlockchainImpl blockchain;
    Block testBlock;
    Block testBlock2;
    List<String> testData;
    List<String> testData2;
    NodeGroup testNodeGroup;

    @BeforeEach
    void init() {

        testNodeGroup = new NodeGroup();
        testNodeGroup.addNode(new Node(),0);
        testNodeGroup.addNode(new Node(),1);
        testNodeGroup.addNode(new Node(),2);
        testNodeGroup.addNode(new Node(),3);

        blockchain = new BlockchainImpl(testNodeGroup);

       // Anything needing to be done at the start
        testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");
        testData2 = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");

        testBlock = new Block(29328102, testData);
        testBlock2 = new Block(121838213, testData);
    }

    @Test
    void whenBlockAdded_ThenItMaintainsBlockchainIntegrity() {

        blockchain.addBlock(testBlock,0);

        assertTrue(testNodeGroup.ValidateBlockchain(0));
    }

    @Test
    void whenBlockAdded_ThenItHasAUniqueID() {
        blockchain.addBlock(testBlock,0);
        blockchain.addBlock(testBlock2,0);
        assertNotEquals(testBlock.getHash(), testBlock2.getHash());
    }

    @Test
    void whenBlockEdited_ThenItsHashChanges() {
        blockchain.addBlock(testBlock,0);
        String initialBlockHash = testBlock.getHash();

        blockchain.editBlock(initialBlockHash,testData2);
        assertNotEquals(initialBlockHash, testBlock2.getHash());
    }
}