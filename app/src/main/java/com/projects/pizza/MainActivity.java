package com.projects.pizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ViewFlipper;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button review, addMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.adapterViewFlipper);
        review = (Button) findViewById(R.id.btnReview);
        addMore = (Button) findViewById(R.id.btnAddMore);

        review.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });
    }
}
