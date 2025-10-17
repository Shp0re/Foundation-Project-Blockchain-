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
    Blockchain blockchain2;
    Block  block;
    Block block1;
    Block block2;
    Block block3;

    @BeforeEach
    void init() {
        nodeGroup = new NodeGroup();
        nodeGroup.addNode(new Node(),0);
        nodeGroup.addNode(new Node(),1);
        nodeGroup.addNode(new Node(),2);
        nodeGroup.addNode(new Node(),3);

         blockchain = new BlockchainImpl(nodeGroup);
         blockchain2 = new BlockchainImpl(nodeGroup);

        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");

        block = new Block(29328102, testData);
        block1 = new Block(121838213, testData);
        block2 = new Block(467377732, testData);
        block3 = new Block(627367382, testData);
    }

    @Test
    public void TestNodeGroupNodeNum() {
        assertEquals(4, nodeGroup.getNodeNum());

    }

    @Test
    public void TestNewNodeValidationTrue() {

        blockchain.addBlock(block,0);
        blockchain.addBlock(block1,0);

        assertTrue(nodeGroup.ValidateBlockchain(0));
    }

    @Test
    public void TestNewNodeValidationFalse() {

        blockchain.addBlock(block,1);
        blockchain.addBlock(block1,1);

        List<String> newTestData = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");
        String testHash = block.getHash();
        blockchain.editBlock(testHash, newTestData);

        assertFalse(nodeGroup.ValidateBlockchain(1));
    }

    @Test
    public void TestWitnessRewardTokens() {

        blockchain.addBlock(block,0);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();

        for(Node node : nodeGroup.getNodes()) {
            if (node.getindex() == firstWitnessIndex) {
                assertEquals(1.5, node.getTokensToVoteWith(), 0.0);
            }
        }
    }

    @Test
    public void TestWitnessRewardTrust() {

        blockchain.addBlock(block,0);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if(node.getTopvotes() == firstWitnessIndex) {
                assertEquals(Optional.of(0.5), node.getTrust().get(firstWitnessIndex));
            }
        }

    }

    @Test
    public void TestVoterRewardTokens() {

        blockchain.addBlock(block,0);

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if (node.getTopvotes() == firstWitnessIndex) {
                assertEquals(1.25, node.getTokensToVoteWith());
            }
        }
    }

    @Test
    public void TestWitnessPunishTokens() {

        blockchain.addBlock(block,0);
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

        blockchain.addBlock(block,0);
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
        blockchain.addBlock(block,0);
        nodeGroup.punishVoters();

        int firstWitnessIndex = nodeGroup.getWitnesses().get(0).getindex();
        for(Node node : nodeGroup.getNodes()) {
            if (node.getTopvotes() == firstWitnessIndex) {
                assertEquals(0.25, node.getTokensToVoteWith());
            }
        }
    }

    @Test
    public void TestTwoBlockchains(){

        blockchain.addBlock(block,0);
        blockchain.addBlock(block1,0);
        blockchain.addBlock(block2,1);
        blockchain.addBlock(block3,1);

        List<String> newTestData = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");
        String testHash = block3.getHash();
        blockchain.editBlock(testHash, newTestData);

        assertTrue(nodeGroup.ValidateBlockchain(0));

    }
}
