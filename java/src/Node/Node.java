package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    private Blockchain localBlockchain;
    private int index;
    private int NodeNum;
    private float TokensToVoteWith;
    private float NumVotesFor;
    private List<Float> trust;

    public Node() {
        localBlockchain = new BlockchainImpl(new NodeGroup());
        this.NodeNum = 0;
        trust = RandomTrust();
        TokensToVoteWith = 1;
        NumVotesFor = 0;
    }

    public void setTokensToVoteWith(float tokensToVoteWith) {
        TokensToVoteWith = tokensToVoteWith;
    }

    public Blockchain getLocalBlockchain() {
        return localBlockchain;
    }

    public void setLocalBlockchain(Blockchain localBlockchain) {
        this.localBlockchain = localBlockchain;
    }

    public void setNodeNum(int nodeNum) {
        NodeNum = nodeNum;
        trust = RandomTrust();
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
                trustToatal = Trust.get(i) + trustToatal;
            }
            if(trustToatal < 1){
                return true;
            }
            else{
                System.out.println("Trust wrong total. should be: 1 actual: " + trustToatal );
                return false;
            }
        }
        else {
            System.out.println("Trust wrong length. should be:" + NodeNum + " actual: " + Trust.size() );
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

    public int getTopvotes(){
        int topvotes = 0;
        int topvoteIndex = 0;
        for(int i = 0; i < NodeNum; i++){
            if (trust.get(i) > topvotes){
                topvoteIndex = i;
            }
        }
        return topvoteIndex;
    }

    public boolean isChainValid(Blockchain blockchain) {
        for (int i = 1; i < blockchain.size(); i++) {
            Block block = blockchain.getBlockIndex(i);
            Block previousBlock = blockchain.getBlockIndex(i - 1);
            if (!block.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Block " + (i) + " is not referenced");
                return false;
            }
        }
        return true;
    }

    public boolean isNewBlockValid(Block newblock) {
        Blockchain tempChain = localBlockchain;
        newblock.setBlockIndex(tempChain.size());
        if (!(tempChain.getLatestBlock() == null)) {
            newblock.setPreviousHash(tempChain.getLatestBlock().getHash());
        }
        tempChain.getBlocks().add(newblock);
        if (isChainValid(tempChain)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setindex(int index) {
        this.index = index;
    }

    public int getindex() {
        return index;
    }

    public void increaseTokensToVoteWith(float v) {
        TokensToVoteWith += v;
    }
}
