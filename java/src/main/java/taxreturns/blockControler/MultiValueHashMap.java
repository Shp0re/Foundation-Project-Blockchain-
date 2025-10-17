package main.java.taxreturns.blockControler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiValueHashMap<Key, Value> {
    private final HashMap<Key, ArrayList<Value>> map = new HashMap<>();

    public void put(Key key, Value value){
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public List<Value> get(Key key){
        return map.getOrDefault(key, new ArrayList<>());
    }

    public void remove(Key key, Value value) {
        map.computeIfPresent(key, (k, v) -> {
            v.remove(value);
            return v;
        });
    }
}
