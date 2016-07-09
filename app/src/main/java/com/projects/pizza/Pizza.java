package com.projects.pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by devin on 7/8/16.
 */
public class Pizza extends Item {
    private static final Map<String, Double> ToppingPrice;
    static {
        ToppingPrice = new HashMap<String, Double>(); //map toppings to prices
        ToppingPrice.put("Tomato", 0.0);
        ToppingPrice.put("Cheese", 0.0);
        ToppingPrice.put("Peppers", 0.0);
        ToppingPrice.put("Onions", 0.0);
        ToppingPrice.put("Sausage", 1.0);
        ToppingPrice.put("Ham", 1.0);
        ToppingPrice.put("Chicken", 1.0);
    }

    private static final Map<String, Double> SizePrice; //map sizes to prices
    static {
        SizePrice = new HashMap<String, Double>();
        SizePrice.put("Small", 5.0);
        SizePrice.put("Medium", 10.0);
        SizePrice.put("Large", 15.0);
    }

    private String toppingsDisplay;
    private ArrayList<String> toppingsArr;
    private String size;

    public Pizza() {}
    public Pizza(String size, ArrayList<String> toppingsArr) {
        if(size != null) {
            if(toppingsArr == null) {
                toppingsArr = new ArrayList<String>();
            }
            this.size = size;
            this.toppingsArr = toppingsArr;
            this.price = 0.0;
            this.toppingsDisplay = "";
            for (String topping : this.toppingsArr) {
                this.price += this.ToppingPrice.get(topping);
                this.toppingsDisplay += ", " + topping;
            }
            this.price += this.SizePrice.get(size);

            if(this.toppingsArr.size() > 0) {
                this.toppingsDisplay = this.toppingsDisplay.substring(1); //chomp first comma
            } else {
                this.toppingsDisplay = "plain";
            }

            this.removeBtnId = Util.newId(); //store an id that will be associated with the button to remove this pizza on review screen
            this.rowId = Util.newId(); //store id that will be associated with the row this pizza is in on the review screen
        }
    }

    //getters, setters
    public String getToppingsDisplay() {
        return toppingsDisplay;
    }

    public void setToppingsDisplay(String toppingsDisplay) {
        this.toppingsDisplay = toppingsDisplay;
    }

    public ArrayList<String> getToppingsArr() {
        return toppingsArr;
    }

    public void setToppingsArr(ArrayList<String> toppingsArr) {
        this.toppingsArr = toppingsArr;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
