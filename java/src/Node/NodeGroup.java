package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeGroup {
    private List<Node> nodes;
    private List<Node> witnesses;
    private List<Blockchain> blockchains;
    private int NodeNum;

    public NodeGroup() {
        nodes = new ArrayList<Node>();
        blockchains = new ArrayList<Blockchain>();
        NodeNum = 0;
    }

    public void setBlockchain(Blockchain blockchains, int index) {
        this.blockchains.set(index,blockchains);
        for (Node node : nodes) {
            node.setLocalBlockchain(blockchains,index);
        }
    }

    public void addBlockchain(Blockchain blockchain) {
        this.blockchains.add(blockchain);
        for (Node node : nodes) {
            node.addBlockchain(blockchain);
        }
    }

    public List<Blockchain> getBlockchains() {
        return blockchains;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node, int index){
        node.setindex(index);
        node.setLocalBlockchains(blockchains);
        nodes.add(node);
        NodeNum++;
        for(Node n : nodes){
            n.setLocalBlockchains(blockchains);
            n.setNodeNum(NodeNum);
        }
    }

    public List<Node> getWitnesses() {
        return witnesses;
    }

    public boolean ValidateBlockchain(int index){
        for (Node witness : witnesses){
            if (!witness.isChainValid(witness.getLocalBlockchain(index))){
                return false;
            }
        }
        return true;
    }

    public boolean checkNewBlock(Block newBlock, int index) {
        setWitnesses();
        for (Node witness : witnesses){
            if (!witness.isNewBlockValid(newBlock,index)){
                return false;
            }
        }
        setBlockchain(witnesses.getFirst().getLocalBlockchain(index),index);
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

    public Blockchain getBlockchain(int blockchainIndex) {
        return blockchains.get(blockchainIndex);
    }
}
