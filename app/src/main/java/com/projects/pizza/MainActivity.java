package com.projects.pizza;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.Button;
import android.view.View;
import com.projects.pizza.Pizza;
import com.projects.pizza.Drink;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button review, addMore, addPizza, addDrink, purchase;
    TableLayout tablePizza, tableDrinks;
    ArrayList<Pizza> pizzaList = new ArrayList<Pizza>();
    ArrayList<Drink> drinkList = new ArrayList<Drink>();
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    HashMap<Integer, Pizza> btnIdToPizza = new HashMap<Integer, Pizza>();

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
        tablePizza = (TableLayout) findViewById(R.id.tblPizzas);
        tablePizza.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tablePizza.setStretchAllColumns(true);
        tableDrinks = (TableLayout) findViewById(R.id.tblDrinks);
        tableDrinks.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableDrinks.setStretchAllColumns(true);

        review.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tablePizza.removeAllViews();
                btnIdToPizza.clear();
                for(Pizza pizza : pizzaList) {
                    btnIdToPizza.put(pizza.getRemoveBtnId(), pizza);
                    TableRow tr = new TableRow(MainActivity.this);
                    tr.setId(pizza.getRowId());
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView pizzaDescr = new TextView(MainActivity.this);
                    pizzaDescr.setHorizontallyScrolling(false);
                    pizzaDescr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    pizzaDescr.setTextSize(15);
                    pizzaDescr.setText(pizza.getSize() + " " + pizza.getToppingsDisplay() + " pizza for " + pizza.getPrice());

                    ImageButton removePizzaBtn = new ImageButton(MainActivity.this);
                    removePizzaBtn.setId(pizza.getRemoveBtnId());
                    removePizzaBtn.setImageResource(R.mipmap.remove_pizza);
                    removePizzaBtn.setPadding(15, 0, 0, 0);
                    removePizzaBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            tablePizza.removeView(findViewById(btnIdToPizza.get(v.getId()).getRowId()));
                            pizzaList.remove(btnIdToPizza.get(v.getId()));
                        }
                    });

                    tr.addView(pizzaDescr);
                    tr.addView(removePizzaBtn);
                    tablePizza.addView(tr);
                }
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
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("")
                            .setMessage("")
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
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

                pizzaList.add(new Pizza(size, toppingsArr));
            }
        });

        Integer maxId = 0;
        View v = findViewById(++maxId);
        while (v != null){
            v = findViewById(++maxId);
        }
        Util.setMaxId(maxId);
    }
}
