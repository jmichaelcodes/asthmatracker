package com.jmichaelcodes.asthmatrackerkids;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jmichaelcodes.asthmatrackerkids.Models.Child;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

/**
 * Created by docto_000 on 6/22/2016.
 */
public class AddChildActivity extends ActionBarActivity {

    private Flow flow;
    private Context context;
    final String TAG = "AltDB LoginView";
    private SharedPreferences mPrefs;
    private TextView dialog_child_name;
    private TextView dialog_phone;
    private TextView dialog_email;
    private Button add_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        add_child = (Button) findViewById(R.id.add_child);
        dialog_child_name = (TextView) findViewById(R.id.dialog_child_name);
        dialog_phone = (TextView) findViewById(R.id.dialog_phone);
        dialog_email = (TextView) findViewById(R.id.dialog_email);



       mPrefs = this.getSharedPreferences("prefs", context.MODE_PRIVATE);

        add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildActivity.fromAdd = true;

                if (dialog_phone.getText().length() == 10) {
                    String childName = dialog_child_name.getText().toString();
                    String childPhone = dialog_phone.getText().toString();
                    String childEmail = dialog_email.getText().toString();
                    Child tempChild = new Child(childName, childPhone, childEmail);
                    ChildActivity.currentChild = tempChild;
                    Intent myIntent = new Intent(AddChildActivity.this, ChildActivity.class);
                    AddChildActivity.this.startActivity(myIntent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddChildActivity.this).create();
                    alertDialog.setMessage("You must enter a valid phone number");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

    }

    private Boolean isNumber(String string) {
        try {
            Integer.parseInt(string.trim());
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "You must enter a number", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
