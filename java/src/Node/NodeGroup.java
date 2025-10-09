package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

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
        NodeNum = nodes.size();
    }

    public void addNode(Node node){
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
        return true;
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
