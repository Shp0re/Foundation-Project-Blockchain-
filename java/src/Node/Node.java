package Node;

import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.util.*;

public class Node {
    private HashMap<String, Blockchain> localBlockchains;
    private int index;
    private int NodeNum;
    private float TokensToVoteWith;
    private float NumVotesFor;
    private List<Float> trust;
    private List<Long> NodesValidated;

    public Node() {
        localBlockchains = new HashMap<>();
        this.NodeNum = 0;
        trust = RandomTrust();
        TokensToVoteWith = 1;
        NumVotesFor = 0;
        NodesValidated = new ArrayList<Long>();
    }

    public void setTokensToVoteWith(float tokensToVoteWith) {
        TokensToVoteWith = tokensToVoteWith;
    }

    public Blockchain getLocalBlockchain(String index) {
        return localBlockchains.get(index);
    }

    public void setLocalBlockchains(HashMap<String, Blockchain> localBlockchains) {
        this.localBlockchains = localBlockchains;
    }

    public void setLocalBlockchain(Blockchain localBlockchain, String index) {
        localBlockchains.put(index, localBlockchain);
    }

    public List<Long> getNodesValidated() {
        return NodesValidated;
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
            try {
                if (trust == null){
                    System.out.println("WARNING: trust values null setting them to random values");
                    trust = RandomTrust();
                }
                float NodeTrust = TokensToVoteWith * trust.get(postion);
                node.setNumVotesFor(node.getNumVotesFor() + NodeTrust);
                postion++;
            }catch (Exception e){
                System.out.println("ERROR: "+e);
            }
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
            if (block.getPreviousHash().isEmpty() && previousBlock == null){
                System.out.println("WARNING: first block detected in a non first block space");
            }
            if (!block.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println(STR."WARNING: Block \{block.getDigitalSignature()} dose not referance block \{previousBlock.getDigitalSignature()}");
                return false;
            }
        }
        return true;
    }

    public boolean isNewBlockValid(Block newblock, String blockchainIndex) {
        try {
            newblock.setBlockIndex(localBlockchains.get(blockchainIndex).size());
            if (!(localBlockchains.get(blockchainIndex).getLatestBlock() == null)) {
                newblock.setPreviousHash(localBlockchains.get(blockchainIndex).getLatestBlock().getHash());
            }else{
                System.out.println("LOG: no previous block detected");
            }

            if (isChainValid(localBlockchains.get(blockchainIndex))) {
                NodesValidated.add(newblock.getDigitalSignature());
                System.out.println("LOG: Node "+ this.index +" has decided that block "+ newblock.getDigitalSignature()+ " is valid.");
                return true;
            } else {
                localBlockchains.get(blockchainIndex).getBlocks().remove(newblock);
                System.out.println("LOG: Node "+ this.index +" has decided that block "+ newblock.getDigitalSignature()+ " is NOT valid.");
                return false;
            }
        }catch (Exception e){
            throw new NoSuchElementException("ERROR: There is no such blockchain with index"+ blockchainIndex);
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

    public void decreaseTokensToVoteWith(float v) {
        if (TokensToVoteWith <= v) {
            TokensToVoteWith = 0;
        }
        else {
            TokensToVoteWith -= v;
        }
    }

    public List<Float> getTrust() {
        return trust;
    }

    public void increaseTrust(int index,float v) {
        System.out.println("LOG: Node "+this.index+" has gained trust");
        for(int j = 0; j < NodeNum; j++){
            if (j == index){
                trust.set(j, trust.get(j) + v);
            }
            else{
                trust.set(j, trust.get(j) - (v / (NodeNum-1)));
            }
        }
    }

    public void decreaseTrust(int index,float v) {
        System.out.println("LOG: Node "+this.index+" has lost trust");
        if(trust.get(index) <= v){
            trust.set(index, 0.0F);
        }
        else{
            trust.set(index, trust.get(index) - v);
        }
    }

    public void addBlockchain(Blockchain blockchains, String fullID) {
        localBlockchains.put(fullID, blockchains);
    }

//    public List<Blockchain> getLocalBlockchains() {
//        List<Blockchain> returnList = localBlockchains.values();
//    }
}
