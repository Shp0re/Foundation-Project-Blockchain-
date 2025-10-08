package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NodeGroup {
    private List<Node> nodes;
    private List<Node> witnesses;
    private Blockchain blockchain;
    private int NodeNum;

    public NodeGroup(List<Node> nodes) {
        nodes = nodes;
        NodeNum = nodes.size();
    }

    public void addNode(Node node){
        nodes.add(node);
        NodeNum++;
    }

    public boolean checkNewBlock(Block newBlock) {
        setWitnesses();
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
