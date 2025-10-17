package test.Nodes;

import Node.Node;
import Node.NodeGroup;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Node Group Test")
class NodeGroupTest {

    NodeGroup nodeGroup;
    Blockchain blockchain;
    Block  block;
    Block block1;

    @BeforeEach
    void init() {
        nodeGroup = new NodeGroup();
        nodeGroup.addNode(new Node(),0);
        nodeGroup.addNode(new Node(),1);
        nodeGroup.addNode(new Node(),2);
        nodeGroup.addNode(new Node(),3);

         blockchain = new BlockchainImpl(nodeGroup);

        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");

        block = new Block(29328102, testData);
        block1 = new Block(121838213, testData);
    }

    @Test
    public void TestNodeGroupNodeNum() {
        assertEquals(4, nodeGroup.getNodeNum());

    }

    @Test
    public void TestNewNodeValidationTrue() {

        blockchain.addBlock(block);
        blockchain.addBlock(block1);

        assertTrue(nodeGroup.ValidateBlockchain());
    }

    @Test
    public void TestNewNodeValidationFalse() {

        blockchain.addBlock(block);
        blockchain.addBlock(block1);

        List<String> newTestData = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");
        String testHash = block.getHash();
        blockchain.editBlock(testHash, newTestData);

        assertFalse(nodeGroup.ValidateBlockchain());
    }

    @Test
    public void TestWitnessRewardTokens() {

        blockchain.addBlock(block);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();

        for(Node node : nodeGroup.getNodes()) {
            if (node.getindex() == firstWitnessIndex) {
                assertEquals(1.5, node.getTokensToVoteWith(), 0.0);
            }
        }
    }

    @Test
    public void TestWitnessRewardTrust() {

        blockchain.addBlock(block);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if(node.getTopvotes() == firstWitnessIndex) {
                assertEquals(Optional.of(0.5), node.getTrust().get(firstWitnessIndex));
            }
        }

    }

    @Test
    public void TestVoterRewardTokens() {

        blockchain.addBlock(block);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if (node.getTopvotes() == firstWitnessIndex) {
                assertEquals(1.25, node.getTokensToVoteWith());
            }
        }
    }

    @Test
    public void TestWitnessPunishTokens() {

        blockchain.addBlock(block);
        nodeGroup.punishWitnesses(block.getDigitalSignature());

        for (Node node : nodeGroup.getNodes()) {
            for(long validatedSignature : node.getNodesValidated()) {
                if (validatedSignature == block.getDigitalSignature()) {
                    assertEquals( 0.5,node.getTokensToVoteWith(),0.0);
                }
                }
            }

    }

    @Test
    public void TestWitnessPunishTrust() {

        blockchain.addBlock(block);
        nodeGroup.punishWitnesses(block.getDigitalSignature());

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if(node.getTopvotes() == firstWitnessIndex) {
                assertEquals(Optional.of(0.0), node.getTrust().get(firstWitnessIndex));
            }
        }

    }

    @Test
    public void TestVoterPunishTokens() {
        blockchain.addBlock(block);
        nodeGroup.punishVoters();

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if (node.getTopvotes() == firstWitnessIndex) {
                assertEquals(0.25, node.getTokensToVoteWith());
            }
        }
    }
}
