package main.java.taxreturns.blockchain;

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
        this.data = data;
        this.hash = calculateHash();
        this.blockIndex = 0;
        this.previousHash = "";
    }

    // hash creation method
    public String calculateHash() {

        return String.valueOf(digitalSignature) + String.valueOf(timeStamp) + data.toString().hashCode();

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

    // to string method
    @Override
    public String toString() {
        return "Block{" +
                "previousHash='" + previousHash + '\'' +
                ", data=" + data +
                ", hash='" + hash + '\'' +
                ", timeStamp=" + timeStamp +
                ", digitalSignature=" + digitalSignature +
                ", blockIndex=" + blockIndex +
                '}';
    }
}
