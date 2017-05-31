package com.jmichaelcodes.asthmatrackerkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

/**
 * Created by docto_000 on 6/16/2016.
 */
public class ConditionActivity extends AppCompatActivity {

    public static Boolean peakFlow = false;
    public static Boolean howAreYou = false;
    public static Boolean attackCondition = false;
    public ImageButton peak_flow;
    public ImageButton how_are_you;
    public ImageButton attack;
    public ImageButton log_button;

    private Flow flow;
    private Context context;
    final String TAG = "ConditionView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        Log.d(TAG, "loginview inflate");

        peak_flow = (ImageButton) findViewById(R.id.peakflow);
        how_are_you = (ImageButton) findViewById(R.id.howareyou);
        attack = (ImageButton) findViewById(R.id.attack);
        log_button = (ImageButton) findViewById(R.id.parent_area);

        peakFlow();
        howAreYou();
        attack();
        logButton();

    }

    public void peakFlow() {
        peak_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peakFlow = true;
                howAreYou = false;
                attackCondition = false;

//        flow.goTo(new SliderScene(context));

                Intent myIntent = new Intent(ConditionActivity.this, SliderActivity.class);
                ConditionActivity.this.startActivity(myIntent);
            }
        });
    }

    public void howAreYou() {
        how_are_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peakFlow = false;
                howAreYou = true;
                attackCondition = false;
//        flow.goTo(new SliderScene(context));
                Intent myIntent = new Intent(ConditionActivity.this, SliderActivity.class);
                ConditionActivity.this.startActivity(myIntent);
            }
        });
    }

    public void attack(){
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peakFlow = false;
                howAreYou = false;
                attackCondition = true;
//        flow.goTo(new SliderScene(context));
                Intent myIntent = new Intent(ConditionActivity.this, SliderActivity.class);
                ConditionActivity.this.startActivity(myIntent);
            }
        });

    }

    public void logButton() {
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ConditionActivity.this, LogActivity.class);
                ConditionActivity.this.startActivity(myIntent);
            }
        });
    }

}
