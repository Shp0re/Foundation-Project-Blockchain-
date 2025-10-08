package Node;

import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.List;
import java.util.Random;

public class Node {
    private Blockchain localBlockchain;
    private int NodeNum;
    private float TokensToVoteWith;
    private float NumVotesFor;
    private List<Float> trust;

    public Node(Blockchain blockchain, int NodeNum) {
        localBlockchain = blockchain;
        this.NodeNum = NodeNum;
        trust = RandomTrust();
        TokensToVoteWith = 0;
        NumVotesFor = 0;
    }

    public void setTokensToVoteWith(float tokensToVoteWith) {
        TokensToVoteWith = tokensToVoteWith;
    }

    public void setNumVotesFor(float numVotesFor) {
        NumVotesFor = numVotesFor;
    }

    public float getTokensToVoteWith() {
        return TokensToVoteWith;
    }

    public float getNumVotesFor() {
        return NumVotesFor;
    }

    private List<Float> RandomTrust(){
        List<Float> TrustList = new java.util.ArrayList<>(List.of());
        float trustleft = 1;
        for(int i = 0; i < NodeNum; i++){
            float newTrust = getRandomFloat(0,trustleft);
            TrustList.add(newTrust);
            trustleft = trustleft - newTrust;
        }
        if (CheckTrust(TrustList)) {
            return TrustList;
        }
        else {
            return null;
        }
    }

    private Boolean CheckTrust(List<Float> Trust){
        if(Trust.size() == NodeNum){
            float trustToatal = 0;
            for(int i = 0; i < NodeNum; i++){
                trustToatal = trust.get(i) + trustToatal;
            }
            if(trustToatal == 1){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }

    private float getRandomFloat(float min, float max){
        return (float) (min + Math.random() * (max - min));
    }

    public List<Node>  vote(List<Node> nodes) {
        int postion = 0;
        for(Node node : nodes){
            float NodeTrust = TokensToVoteWith * trust.get(postion);
            node.setNumVotesFor(node.getNumVotesFor() + NodeTrust);
            postion++;
        }
        return nodes;
    }
}
