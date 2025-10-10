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
        NodeNum = nodes.size();
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
        for (Node node : nodes) {
            node.setLocalBlockchain(blockchain);
        }
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
                if (node.getTopvotes() == witness.getindex()){
                    node.increaseTokensToVoteWith((float) 0.25);
                }
            }
        }
    }

    private void setWitnesses() {
        for (Node node : nodes) {
            node.vote(nodes);
        }
        Sortnodes();
        witnesses = nodes.subList(0, 1);
    }

    private void Sortnodes() {
        Collections.sort(nodes, new NumOfVotesComparator());
    }

}
