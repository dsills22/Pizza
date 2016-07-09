package com.projects.pizza;

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

    private ViewFlipper viewFlipper;
    private Button review, addMore, addPizza, addDrink, purchase;
    private TableLayout tablePizza, tableDrinks;
    private TextView lblTotal;
    private ArrayList<Pizza> pizzaList = new ArrayList<Pizza>();
    private ArrayList<Drink> drinkList = new ArrayList<Drink>();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private HashMap<Integer, Item> btnIdToPizza = new HashMap<Integer, Item>(); //used to map the "remove" btn to pizzas to remove them
    private HashMap<Integer, Item> btnIdToDrink = new HashMap<Integer, Item>(); //used to map the "remove" btn to drinks to remove them
    private Double total = 0.0;

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
        lblTotal = (TextView) findViewById(R.id.lblTotal);

        tablePizza = (TableLayout) findViewById(R.id.tblPizzas);
        tablePizza.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tablePizza.setStretchAllColumns(true);

        tableDrinks = (TableLayout) findViewById(R.id.tblDrinks);
        tableDrinks.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableDrinks.setStretchAllColumns(true);

        review.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0.0;
                tablePizza.removeAllViews(); //clear all pizzas
                tableDrinks.removeAllViews(); //clear all drinks
                btnIdToPizza.clear(); //clear hash of remove btn id to pizzas
                btnIdToDrink.clear(); //clear hash of remove btn id to drinks

                for(Pizza pizza : pizzaList) { //for each pizza
                    btnIdToPizza.put(pizza.getRemoveBtnId(), pizza); //assoc remove pizza btn id to the pizza in a hashmap
                    TableRow tr = new TableRow(MainActivity.this); //new table row
                    tr.setId(pizza.getRowId()); //set id to row id set on pizza (we need this to remove it by id)
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView pizzaDescr = new TextView(MainActivity.this); //create description of pizza
                    pizzaDescr.setHorizontallyScrolling(false);
                    pizzaDescr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    pizzaDescr.setTextSize(FONT_SIZE); //in pixels
                    pizzaDescr.setText(pizza.getSize() + " " + pizza.getToppingsDisplay() + " pizza for " + formatter.format(pizza.getPrice()));

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

                    tr.addView(pizzaDescr);
                    tr.addView(removePizzaBtn);
                    tablePizza.addView(tr);
                    total += pizza.getPrice();
                }

                for(Drink drink : drinkList) { //same for drinks
                    btnIdToDrink.put(drink.getRemoveBtnId(), drink);
                    TableRow trDr = new TableRow(MainActivity.this); //new table row
                    trDr.setId(drink.getRowId()); //set id to row id set on drink (we need this to remove it by id)
                    trDr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView drinkDescr = new TextView(MainActivity.this); //create description of drink
                    drinkDescr.setHorizontallyScrolling(false);
                    drinkDescr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    drinkDescr.setTextSize(FONT_SIZE); //in pixels
                    drinkDescr.setText(drink.getSize() + " " + drink.getType() + " drink for " + formatter.format(drink.getPrice()));

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

                    trDr.addView(drinkDescr);
                    trDr.addView(removeDrinkBtn);
                    tableDrinks.addView(trDr);
                    total += drink.getPrice();
                }

                lblTotal.setText(totalPrefix + formatter.format(total));
                viewFlipper.showNext();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });

        addPizza.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String size = "";
                ArrayList<String> toppingsArr = new ArrayList<String>();
                if(((RadioButton) findViewById(R.id.btnSmallPizza)).isChecked()) {
                    size = "Small";
                } else if(((RadioButton) findViewById(R.id.btnMedPizza)).isChecked()) {
                    size = "Medium";
                } else if(((RadioButton) findViewById(R.id.btnLargePizza)).isChecked()) {
                    size = "Large";
                } else {
                    Util.errorMsg(MainActivity.this, "Please select a topping");
                    return;
                }

                if(((CheckBox) findViewById(R.id.ckCheese)).isChecked()) {
                    toppingsArr.add("Cheese");
                }
                if(((CheckBox) findViewById(R.id.ckChicken)).isChecked()) {
                    toppingsArr.add("Chicken");
                }
                if(((CheckBox) findViewById(R.id.ckHam)).isChecked()) {
                    toppingsArr.add("Ham");
                }
                if(((CheckBox) findViewById(R.id.ckOnions)).isChecked()) {
                    toppingsArr.add("Onions");
                }
                if(((CheckBox) findViewById(R.id.ckPeppers)).isChecked()) {
                    toppingsArr.add("Peppers");
                }
                if(((CheckBox) findViewById(R.id.ckSausage)).isChecked()) {
                    toppingsArr.add("Sausage");
                }
                if(((CheckBox) findViewById(R.id.ckTomato)).isChecked()) {
                    toppingsArr.add("Tomato");
                }

                pizzaList.add(new Pizza(size, toppingsArr)); //add pizza to list
                resetPizza();
            }
        });

        addDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String size = "";
                String type = "";
                if(((RadioButton) findViewById(R.id.btnSmallDr)).isChecked()) {
                    size = "Small";
                } else if(((RadioButton) findViewById(R.id.btnMedDr)).isChecked()) {
                    size = "Medium";
                } else if(((RadioButton) findViewById(R.id.btnLargeDr)).isChecked()) {
                    size = "Large";
                } else {
                    Util.errorMsg(MainActivity.this, "Please select a drink size");
                    return;
                }

                if(((RadioButton) findViewById(R.id.btnCoke)).isChecked()) {
                    type = "Coke";
                } else if(((RadioButton) findViewById(R.id.btnOC)).isChecked()) {
                    type = "Orange Cola";
                } else if(((RadioButton) findViewById(R.id.btnCC)).isChecked()) {
                    type = "Cherry Cola";
                } else if(((RadioButton) findViewById(R.id.btn7)).isChecked()) {
                    type = "7-Up";
                } else {
                    Util.errorMsg(MainActivity.this, "Please select a drink");
                    return;
                }

                drinkList.add(new Drink(size, type)); //add drink to list
                resetDrink();
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
