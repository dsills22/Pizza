package com.projects.pizza;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.Button;
import android.view.View;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final Integer FONT_SIZE = 15;
    private final String totalPrefix = "Total: ";
    private final Integer MAINVIEW = 0;
    private final Integer REVIEW = 1;
    private final Integer UPDATEDRINK = 2;
    private final Integer UPDATEPIZZA = 3;

    private ViewFlipper viewFlipper;
    private Button review, addMore, addPizza, addDrink, purchase, cancelPizzaUpdate, cancelDrinkUpdate, pizzaUpdate, drinkUpdate;
    private TableLayout tablePizza, tableDrinks;
    private TextView lblTotal;
    private ArrayList<Pizza> pizzaList = new ArrayList<Pizza>();
    private ArrayList<Drink> drinkList = new ArrayList<Drink>();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private HashMap<Integer, Item> btnIdToPizza = new HashMap<Integer, Item>(); //used to map the "remove" btn to pizzas to remove them
    private HashMap<Integer, Pizza> btnUpdateIdToPizza = new HashMap<Integer, Pizza>(); //used to map the "update" btn to pizzas to update them
    private HashMap<Integer, Item> btnIdToDrink = new HashMap<Integer, Item>(); //used to map the "remove" btn to drinks to remove them
    private HashMap<Integer, Drink> btnUpdateIdToDrink = new HashMap<Integer, Drink>(); //used to map the "update" btn to pizzas to update them
    private Double total = 0.0;
    private Pizza pizzaInUpdate;
    private Drink drinkInUpdate;

    public View.OnClickListener cancelUpdateListener = new View.OnClickListener(){
        public void onClick(View v) {
            viewFlipper.setDisplayedChild(REVIEW);
        }
    };

    public Integer getId(String name) {
        return getResources().getIdentifier(name, "id", getPackageName());
    }

    public Pizza getPizzaFromUi(Boolean isUpdate) {
        Integer index = isUpdate ? pizzaInUpdate.getIndex() : pizzaList.size();
        String extraToken = isUpdate ? "Update" : "";
        String size = "";
        ArrayList<String> toppingsArr = new ArrayList<String>();
        if(((RadioButton) findViewById(getId("btn" + extraToken + "SmallPizza"))).isChecked()) {
            size = "Small";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "MedPizza"))).isChecked()) {
            size = "Medium";
        } else if(((RadioButton) findViewById(getId("btn"  + extraToken + "LargePizza"))).isChecked()) {
            size = "Large";
        } else {
            Util.errorMsg(MainActivity.this, "Please select a topping");
            return new Pizza();
        }

        if(((CheckBox) findViewById(getId("ck" + extraToken + "Cheese"))).isChecked()) {
            toppingsArr.add("Cheese");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Chicken"))).isChecked()) {
            toppingsArr.add("Chicken");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Ham"))).isChecked()) {
            toppingsArr.add("Ham");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Onions"))).isChecked()) {
            toppingsArr.add("Onions");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Peppers"))).isChecked()) {
            toppingsArr.add("Peppers");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Sausage"))).isChecked()) {
            toppingsArr.add("Sausage");
        }
        if(((CheckBox) findViewById(getId("ck" + extraToken + "Tomato"))).isChecked()) {
            toppingsArr.add("Tomato");
        }

        return new Pizza(size, toppingsArr, index); //return pizza
    }

    public Drink getDrinkFromUI(Boolean isUpdate) {
        Integer index = isUpdate ? drinkInUpdate.getIndex() : drinkList.size();
        String extraToken = isUpdate ? "Update" : "";
        String size = "";
        String type = "";
        if(((RadioButton) findViewById(getId("btn" + extraToken + "SmallDr"))).isChecked()) {
            size = "Small";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "MedDr"))).isChecked()) {
            size = "Medium";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "LargeDr"))).isChecked()) {
            size = "Large";
        } else {
            Util.errorMsg(MainActivity.this, "Please select a drink size");
            return new Drink();
        }

        if(((RadioButton) findViewById(getId("btn" + extraToken + "Coke"))).isChecked()) {
            type = "Coke";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "OC"))).isChecked()) {
            type = "Orange Cola";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "CC"))).isChecked()) {
            type = "Cherry Cola";
        } else if(((RadioButton) findViewById(getId("btn" + extraToken + "7"))).isChecked()) {
            type = "7-Up";
        } else {
            Util.errorMsg(MainActivity.this, "Please select a drink");
            return new Drink();
        }

        return new Drink(size, type, index); //return drink
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.adapterViewFlipper);
        review = (Button) findViewById(R.id.btnReview);
        addMore = (Button) findViewById(R.id.btnAddMore);
        addPizza = (Button) findViewById(R.id.btnAddPizza);
        addDrink = (Button) findViewById(R.id.btnAddDrink);
        purchase = (Button) findViewById(R.id.btnPurchase);
        cancelPizzaUpdate = (Button) findViewById(R.id.btnCancelUpdatePizza);
        cancelDrinkUpdate = (Button) findViewById(R.id.btnCancelUpdateDrink);
        pizzaUpdate = (Button) findViewById(R.id.btnUpdatePizza);
        drinkUpdate = (Button) findViewById(R.id.btnUpdateDrink);
        lblTotal = (TextView) findViewById(R.id.lblTotal);

        tablePizza = (TableLayout) findViewById(R.id.tblPizzas);
        tablePizza.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
        tablePizza.setStretchAllColumns(true);

        tableDrinks = (TableLayout) findViewById(R.id.tblDrinks);
        tableDrinks.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableDrinks.setStretchAllColumns(true);

        //review order
        review.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0.0;
                tablePizza.removeAllViews(); //clear all pizzas
                tableDrinks.removeAllViews(); //clear all drinks
                btnIdToPizza.clear(); //clear hash of remove btn id to pizzas
                btnIdToDrink.clear(); //clear hash of remove btn id to drinks
                btnUpdateIdToPizza.clear(); //clear hash of btn id to pizzas for update btn
                btnUpdateIdToDrink.clear(); //clear hash of btn id to drinks for update btn

                for(Pizza pizza : pizzaList) { //for each pizza
                    btnIdToPizza.put(pizza.getRemoveBtnId(), pizza); //assoc remove pizza btn id to the pizza in a hashmap
                    btnUpdateIdToPizza.put(pizza.getUpdateBtnId(), pizza);
                    TableRow tr = new TableRow(MainActivity.this); //new table row
                    tr.setId(pizza.getRowId()); //set id to row id set on pizza (we need this to remove it by id)
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));

                    TextView pizzaDescr = new TextView(MainActivity.this); //create description of pizza
                    pizzaDescr.setHorizontallyScrolling(false);
                    pizzaDescr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                    pizzaDescr.setTextSize(FONT_SIZE); //in pixels
                    final String toppingsDisplay = pizza.getToppingsDisplay();
                    final String size = pizza.getSize();
                    pizzaDescr.setText(size + " " + toppingsDisplay + " pizza for " + formatter.format(pizza.getPrice()));

                    ImageButton removePizzaBtn =  Util.createRemoveButton(MainActivity.this, pizza);
                    removePizzaBtn.setOnClickListener(new View.OnClickListener() { //on click of remove pizza button
                        public void onClick(View v) {
                            Item item = btnIdToPizza.get(v.getId());
                            tablePizza.removeView(findViewById(item.getRowId())); //remove the row associated with the btn
                            pizzaList.remove(btnIdToPizza.get(v.getId())); //remove the pizza from the pizza array
                            total -= item.getPrice();
                            lblTotal.setText(totalPrefix + formatter.format(total));
                        }
                    });

                    ImageButton updatePizzaBtn =  Util.createUpdateButton(MainActivity.this, pizza);
                    updatePizzaBtn.setOnClickListener(new View.OnClickListener() { //on click of remove pizza button
                        public void onClick(View v) {
                            ((RadioButton) findViewById(R.id.btnUpdateSmallPizza)).setChecked(size.contains("Small"));
                            ((RadioButton) findViewById(R.id.btnUpdateMedPizza)).setChecked(size.contains("Medium"));
                            ((RadioButton) findViewById(R.id.btnUpdateLargePizza)).setChecked(size.contains("Large"));
                            ((CheckBox) findViewById(R.id.ckUpdateChicken)).setChecked(toppingsDisplay.contains("Chicken"));
                            ((CheckBox) findViewById(R.id.ckUpdateCheese)).setChecked(toppingsDisplay.contains("Cheese"));
                            ((CheckBox) findViewById(R.id.ckUpdateTomato)).setChecked(toppingsDisplay.contains("Tomato"));
                            ((CheckBox) findViewById(R.id.ckUpdateSausage)).setChecked(toppingsDisplay.contains("Sausage"));
                            ((CheckBox) findViewById(R.id.ckUpdatePeppers)).setChecked(toppingsDisplay.contains("Peppers"));
                            ((CheckBox) findViewById(R.id.ckUpdateOnions)).setChecked(toppingsDisplay.contains("Onions"));
                            ((CheckBox) findViewById(R.id.ckUpdateHam)).setChecked(toppingsDisplay.contains("Ham"));
                            pizzaInUpdate = btnUpdateIdToPizza.get(v.getId());
                            viewFlipper.setDisplayedChild(UPDATEPIZZA);
                        }
                    });

                    tr.addView(pizzaDescr);
                    tr.addView(removePizzaBtn);
                    tr.addView(updatePizzaBtn);
                    tablePizza.addView(tr);
                    total += pizza.getPrice();
                }

                for(Drink drink : drinkList) { //same for drinks
                    btnIdToDrink.put(drink.getRemoveBtnId(), drink);
                    btnUpdateIdToDrink.put(drink.getUpdateBtnId(), drink);
                    TableRow trDr = new TableRow(MainActivity.this); //new table row
                    trDr.setId(drink.getRowId()); //set id to row id set on drink (we need this to remove it by id)
                    trDr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));

                    TextView drinkDescr = new TextView(MainActivity.this); //create description of drink
                    drinkDescr.setHorizontallyScrolling(false);
                    drinkDescr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                    drinkDescr.setTextSize(FONT_SIZE); //in pixels
                    final String size = drink.getSize();
                    final String type = drink.getType();
                    drinkDescr.setText(size + " " + type + " drink for " + formatter.format(drink.getPrice()));

                    ImageButton removeDrinkBtn =  Util.createRemoveButton(MainActivity.this, drink);
                    removeDrinkBtn.setOnClickListener(new View.OnClickListener() { //on click of remove drink button
                        public void onClick(View v) {
                            Item item = btnIdToDrink.get(v.getId());
                            tableDrinks.removeView(findViewById(item.getRowId())); //remove the row associated with the btn
                            drinkList.remove(btnIdToDrink.get(v.getId())); //remove the drink from the drink array
                            total -= item.getPrice();
                            lblTotal.setText(totalPrefix + formatter.format(total));
                        }
                    });

                    ImageButton updateDrinkBtn =  Util.createUpdateButton(MainActivity.this, drink);
                    updateDrinkBtn.setOnClickListener(new View.OnClickListener() { //on click of remove pizza button
                        public void onClick(View v) {
                            ((RadioButton) findViewById(R.id.btnUpdateSmallDr)).setChecked(size.contains("Small"));
                            ((RadioButton) findViewById(R.id.btnUpdateMedDr)).setChecked(size.contains("Medium"));
                            ((RadioButton) findViewById(R.id.btnUpdateLargeDr)).setChecked(size.contains("Large"));
                            ((RadioButton) findViewById(R.id.btnUpdate7)).setChecked(type.contains("7-Up"));
                            ((RadioButton) findViewById(R.id.btnUpdateOC)).setChecked(type.contains("Orange"));
                            ((RadioButton) findViewById(R.id.btnUpdateCC)).setChecked(type.contains("Cherry"));
                            ((RadioButton) findViewById(R.id.btnUpdateCoke)).setChecked(type.contains("Coke"));
                            drinkInUpdate = btnUpdateIdToDrink.get(v.getId());
                            viewFlipper.setDisplayedChild(UPDATEDRINK);
                        }
                    });

                    trDr.addView(drinkDescr);
                    trDr.addView(removeDrinkBtn);
                    trDr.addView(updateDrinkBtn);
                    tableDrinks.addView(trDr);
                    total += drink.getPrice();
                }

                lblTotal.setText(totalPrefix + formatter.format(total)); //set total
                viewFlipper.setDisplayedChild(REVIEW);
            }
        });

        //flip to main
        addMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(MAINVIEW);
            }
        });




        //Additions
        addPizza.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Pizza p = getPizzaFromUi(false);
                if(p.getSize() != null) {
                    pizzaList.add(p); //add pizza to list
                    resetPizza();
                }
            }
        });

        addDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drink drink = getDrinkFromUI(false);
                if(drink.getSize() != null) {
                    drinkList.add(drink); //add drink to list
                    resetDrink();
                }
            }
        });




        //Cancel updates
        cancelPizzaUpdate.setOnClickListener(cancelUpdateListener);
        cancelDrinkUpdate.setOnClickListener(cancelUpdateListener);




        //Updates
        pizzaUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Pizza pUI = getPizzaFromUi(true);
                if(pUI.getSize() != null) {
                    pizzaList.set(pizzaInUpdate.getIndex(), pUI); //update pizza
                    review.performClick(); //redo review screen
                }
            }
        });

        drinkUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drink dUI = getDrinkFromUI(true);
                if(dUI.getSize() != null) {
                    drinkList.set(drinkInUpdate.getIndex(), dUI); //update drink
                    review.performClick(); //redo review screen
                }
            }
        });



        //purchase action
        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Util.purchaseMsg(MainActivity.this);
                resetDrink();
                resetPizza();
                pizzaList.clear();
                drinkList.clear();
                viewFlipper.setDisplayedChild(MAINVIEW);
            }
        });


        //after rendering, find largest view id so we can add new views dynamically and set their id via Util.newId
        Integer maxId = 0;
        View v = findViewById(++maxId);
        while (v != null){
            v = findViewById(++maxId);
        }
        Util.setMaxId(maxId); //set this max id (all newId will now be larger than this)
    }

    public void resetPizza() {
        ((RadioGroup) findViewById(R.id.grpPizza)).clearCheck();
        ((CheckBox) findViewById(R.id.ckCheese)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckChicken)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckHam)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckOnions)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckPeppers)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckSausage)).setChecked(false);
        ((CheckBox) findViewById(R.id.ckTomato)).setChecked(false);
    }

    public void resetDrink() {
        ((RadioGroup) findViewById(R.id.grpDrink)).clearCheck();
        ((RadioGroup) findViewById(R.id.grpDrinkSize)).clearCheck();
    }
}
