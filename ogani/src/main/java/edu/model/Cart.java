package edu.model;

import edu.beans.Product;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    Map<String, Product> data = new HashMap<>();

    public Cart() {
    }

    public void put(Product p) {
        if(p == null) return;
        if(data.containsKey(p.getId())) {
            data.get(p.getId()).add();
            return;
        }
        p.setQuantity(1);
        data.put(p.getId(), p);
    }

    public void update(String id, int quantity) {
        if(quantity < 0) return;
        if(data.containsKey(id)) data.get(id).setQuantity(quantity);
    }

    public void remove(String id) {
        data.remove(id);
    }

    public long total() {
        long sum = 0;
        for(Product p : data.values()) {
            sum += p.getPriceLong() * p.getQuantity();
        }
        return sum;
    }


}
