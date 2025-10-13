package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NodeGroup {
    private List<Node> nodes;
    private List<Node> witnesses;
    private Blockchain blockchain;
    private int NodeNum;

    public NodeGroup() {
        nodes = new ArrayList<Node>();
        blockchain = new BlockchainImpl(this);
        NodeNum = 0;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
        for (Node node : nodes) {
            node.setLocalBlockchain(blockchain);
        }
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node, int index){
        node.setindex(index);
        node.setLocalBlockchain(blockchain);
        nodes.add(node);
        NodeNum++;
        for(Node n : nodes){
            n.setNodeNum(NodeNum);
        }
    }

    public List<Node> getWitnesses() {
        return witnesses;
    }

    public boolean ValidateBlockchain(){
        for (Node witness : witnesses){
            if (!witness.isChainValid(witness.getLocalBlockchain())){
                return false;
            }
        }
        return true;
    }

    public boolean checkNewBlock(Block newBlock) {
        setWitnesses();
        for (Node witness : witnesses){
            if (!witness.isNewBlockValid(newBlock)){
                return false;
            }
        }
        setBlockchain(witnesses.getFirst().getLocalBlockchain());
        rewardWitnesses();
        rewardVoters();
        return true;
    }

    private void rewardWitnesses(){
        for (Node witness : witnesses) {
            nodes.get(witness.getindex()).increaseTokensToVoteWith((float) 0.5);
        }
    }

    private void rewardVoters(){
        for (Node node : nodes) {
            for(Node witness : witnesses) {
                if (node.getTopvotes() == witness.getindex() && node.getindex() != witness.getindex()) {
                    node.increaseTokensToVoteWith((float) 0.25);
                    node.increaseTrust(witness.getindex(), (float) 0.5);
                }
            }
        }
    }

    public void punishWitnesses(long digitalSignature) {
        for (Node node : nodes) {
            for(long validatedSignature : node.getNodesValidated()) {
                if (validatedSignature == digitalSignature) {
                    node.decreaseTokensToVoteWith((float) 1.0);
                    for (Node otherNodes : nodes) {
                        otherNodes.decreaseTrust(node.getindex(), (float) 1.0);
                    }
                }
            }
        }
    }

    public void punishVoters(){
        for (Node node : nodes) {
            for(Node witness : witnesses) {
                if (node.getTopvotes() == witness.getindex()){
                    node.decreaseTokensToVoteWith((float) 0.5);
                    node.decreaseTrust(witness.getindex(), (float) 1.0);
                }
            }
        }
    }

    private void setWitnesses() {
        for (Node node : nodes) {
            node.vote(nodes);
        }
        witnesses = new ArrayList<Node>(nodes);
        Sortnodes();
        witnesses = witnesses.subList(0, NodeNum/2 - 1);
    }

    private void Sortnodes() {
        Collections.sort(witnesses, new NumOfVotesComparator());
    }

    public int getNodeNum() {
        return NodeNum;
    }

}
