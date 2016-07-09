package com.projects.pizza;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by devin on 7/8/16.
 */
public class Pizza {
    private static final Map<String, Double> ToppingPrice;
    static {
        ToppingPrice = new HashMap<String, Double>();
        ToppingPrice.put("Tomato", 0.0);
        ToppingPrice.put("Cheese", 0.0);
        ToppingPrice.put("Peppers", 0.0);
        ToppingPrice.put("Onions", 0.0);
        ToppingPrice.put("Sausage", 1.0);
        ToppingPrice.put("Ham", 1.0);
        ToppingPrice.put("Chicken", 1.0);
    }

    private static final Map<String, Double> SizePrice;
    static {
        SizePrice = new HashMap<String, Double>();
        SizePrice.put("Small", 5.0);
        SizePrice.put("Medium", 10.0);
        SizePrice.put("Large", 15.0);
    }

    private Integer removeBtnId;
    private Integer rowId;
    private Double price;
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
                this.toppingsDisplay = this.toppingsDisplay;
            } else {
                this.toppingsDisplay = "plain";
            }

            this.removeBtnId = Util.newId();
            this.rowId = Util.newId();
        }
    }

    public Integer getRemoveBtnId() {
        return removeBtnId;
    }

    public void setRemoveBtnId(Integer removeBtnId) {
        this.removeBtnId = removeBtnId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<String> getToppingsArr() {
        return toppingsArr;
    }

    public void setToppingsArr(ArrayList<String> toppingsArr) {
        this.toppingsArr = toppingsArr;
    }

    public String getToppingsDisplay() {
        return toppingsDisplay;
    }

    public void setToppingsDisplay(String toppingsDisplay) {
        this.toppingsDisplay = toppingsDisplay;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
