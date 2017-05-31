package com.jmichaelcodes.asthmatrackerkids;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jmichaelcodes.asthmatrackerkids.Models.Child;
import com.jmichaelcodes.asthmatrackerkids.Models.Parent;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

/**
 * Created by docto_000 on 6/16/2016.
 */
public class ChildActivity extends AppCompatActivity {
    private Flow flow;
    private Context context;
    final String TAG = "ChildView";
    //    public static Child child;
    private String letter;
    private String name;
    private RelativeLayout child_row;
    private Button child_button;
    private TextView child_name;
    private ImageButton edit_child;
    public static Parent currentParent;
    public SharedPreferences mPrefs;
    public static Boolean fromAdd = false;

    public static Child currentChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        child_row = (RelativeLayout) findViewById(R.id.child_row);
        child_button = (Button) findViewById(R.id.child_button);
        child_name = (TextView) findViewById(R.id.child_name);
        edit_child = (ImageButton) findViewById(R.id.edit_child);


        mPrefs = this.getSharedPreferences("prefs", context.MODE_PRIVATE);

        loadChild();
        childClick();
        editChild();

    }

    public void loadChild() {
        if (!fromAdd) {
        try {
            Gson gson = new Gson();
            System.out.println("try child prefs " + mPrefs.getString("child", ""));
            System.out.println("try current child " + currentChild);
            String json = mPrefs.getString("child", "");
            currentChild = gson.fromJson(json, Child.class);
            child_button.setText(String.valueOf(currentChild.getChildName().charAt(0)));
            child_name.setText(currentChild.getChildName());
        } catch (Exception e) {
                Toast.makeText(ChildActivity.this, "No children exist", Toast.LENGTH_LONG).show();
                edit_child.setVisibility(View.INVISIBLE);
            }
        } else {
            System.out.println("else child " + currentChild.getChildName());
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(currentChild);
            child_button.setText(String.valueOf(currentChild.getChildName().charAt(0)));
            child_name.setText(currentChild.getChildName());
            prefsEditor.putString("child", json);
            prefsEditor.commit();
        }
    }

    public void childClick() {
        child_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentChild == null) {
                    Intent myIntent = new Intent(ChildActivity.this, AddChildActivity.class);
                    ChildActivity.this.startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(ChildActivity.this, ConditionActivity.class);
                    ChildActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    public void editChild() {
        edit_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder removeChildDialog = new AlertDialog.Builder(ChildActivity.this);
                removeChildDialog.setMessage("Are you sure you want to edit " + currentChild.getChildName());
                removeChildDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                removeChildDialog.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(ChildActivity.this, AddChildActivity.class);
                        ChildActivity.this.startActivity(myIntent);
                    }
                });

                removeChildDialog.show();
            }
        });
    }
}