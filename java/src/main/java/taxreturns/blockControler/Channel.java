package main.java.taxreturns.blockControler;

public interface Channel {
     Object getAllItems();
     Object getSpecificItem(String identifier);
     String toString();
     String getID();
}
