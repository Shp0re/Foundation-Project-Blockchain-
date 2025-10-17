package Node;

import java.util.Comparator;

public class NumOfVotesComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return (int) (o1.getNumVotesFor() - o2.getNumVotesFor());
    }
}
