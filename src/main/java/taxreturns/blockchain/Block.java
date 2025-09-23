package blockchain;

import java.util.List;

public class Block {

    private String previousHash;
    private List<String> data;
    private String hash;
    private long timeStamp;
    private long digitalSignature;
    private int blockIndex;

    // Constructor
    public Block(long digitalSignature, List<String> data) {
        this.digitalSignature = digitalSignature;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();
        this.data = data;
    }

    // hash creation method
    public String calculateHash() {
        String calculatedHash = String.valueOf(digitalSignature) + String.valueOf(timeStamp) + previousHash + data.toString();
        return String.valueOf(calculatedHash.hashCode());
    }

    // Alter hash method
    public void changeHash(){
        this.hash = calculateHash();
    }

    // Getters and Setters
    public int getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    public long getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(long digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
