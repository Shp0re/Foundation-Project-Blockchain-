package main.java.taxreturns.blockControler;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MultiChannel implements Channel {
    private String name;
    private final HashMap<String, SingleChannel> subChannels = new HashMap<>();
    final String id;

    public MultiChannel(String id){
        this.id = id;
        this.name = "Null";
    }

    public MultiChannel(String id, String name){
        this.id = id;
        if(name != "") {
            this.name = name;
        }else{
            throw new RuntimeException("name cannot be empty");
        }
    }

    @Override
    public Channel[] getAllItems() {
        Channel[] channels = new Channel[subChannels.size()];
        int a = 0;
        for(Channel i : subChannels.values()){
            channels[a] = i;
            a++;
        }
        return channels;
    }

    @Override
    public SingleChannel getSpecificItem(String identifier) {
       SingleChannel value = this.subChannels.get(identifier);
       if (value == null){
           throw new NoSuchElementException("\n Item not found in "+ this.name);
       }else{
           return value;
       }
    }

    @Override
    public String getID() {
        return this.id;
    }

    public void addChannel(SingleChannel addition){
        subChannels.put(addition.getID(), addition);
    }

    public void setName(String name) {
        this.name = name;
    }
}
