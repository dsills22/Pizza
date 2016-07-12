package com.projects.pizza;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by devin on 7/8/16.
 */
public class Drink extends Item {
    private static final Map<String, Double> SizePrice;
    static {
        SizePrice = new HashMap<String, Double>(); //map sized to prices
        SizePrice.put("Small", 2.0);
        SizePrice.put("Medium", 3.0);
        SizePrice.put("Large", 4.0);
    }

    private String type;
    private String size;

    public Drink() {}
    public Drink(String size, String type, Integer index) {
        if(size != null && type != null) {
            this.size = size;
            this.index = index;
            this.type = type;
            this.price = SizePrice.get(this.size);
            this.removeBtnId = Util.newId(); //store an id that will be associated with the button to remove this drink on review screen
            this.rowId = Util.newId(); //store id that will be associated with the row this drink is in on the review screen
            this.updateBtnId = Util.newId();
        }
    }

    //getters, setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
