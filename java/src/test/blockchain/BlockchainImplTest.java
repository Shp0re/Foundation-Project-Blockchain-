package test.blockchain;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.BlockchainImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        blockchain.addBlock(testBlock);

        assertTrue(testNodeGroup.ValidateBlockchain());
    }

    @Test
    void whenBlockAdded_ThenItHasAUniqueID() {
        blockchain.addBlock(testBlock);
        blockchain.addBlock(testBlock2);
        assertNotEquals(testBlock.getHash(), testBlock2.getHash());
    }

    @Test
    void whenBlockEdited_ThenItsHashChanges() {
        blockchain.addBlock(testBlock);
        String initialBlockHash = testBlock.getHash();

        blockchain.editBlock(initialBlockHash,testData2);
        assertNotEquals(initialBlockHash, testBlock2.getHash());
    }
}