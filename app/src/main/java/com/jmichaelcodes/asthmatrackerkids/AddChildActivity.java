package com.jmichaelcodes.asthmatrackerkids;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jmichaelcodes.asthmatrackerkids.Models.Child;

import java.sql.SQLOutput;

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
    private EditText peak_flow_am;
    private EditText peak_flow_pm;
    private Boolean isAm;
    private TimePicker time_picker;
    private int hour;
    private int minute;
    private Integer morningHour;
    private Integer morningMinute;
    private Integer eveningHour;
    private Integer eveningMinute;
    private String suffix;
    private String hourString;
    private String minuteString;
    private String formattedTime;

    static final int TIME_DIALOG_ID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        add_child = (Button) findViewById(R.id.add_child);
        dialog_child_name = (TextView) findViewById(R.id.dialog_child_name);
        dialog_phone = (TextView) findViewById(R.id.dialog_phone);
        dialog_email = (TextView) findViewById(R.id.dialog_email);
        peak_flow_am = (EditText) findViewById(R.id.peak_flow_am);
        peak_flow_pm = (EditText) findViewById(R.id.peak_flow_pm);

        mPrefs = this.getSharedPreferences("prefs", context.MODE_PRIVATE);

        addChild();
        timeDialog();

    }

    public void timeDialog() {

        peak_flow_am.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isAm = true;

                showDialog(TIME_DIALOG_ID);

            }

        });

        peak_flow_pm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isAm = false;

                showDialog(TIME_DIALOG_ID);

            }

        });


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    switch(hour){

                        case 0:
                            hourString = "12";
                            suffix = "am";
                            break;
                        case 13:
                            hourString = "1";
                            suffix = "pm";
                            break;
                        case 14:
                            hourString = "2";
                            suffix = "pm";
                            break;
                        case 15:
                            hourString = "3";
                            suffix = "pm";
                            break;
                        case 16:
                            hourString = "4";
                            suffix = "pm";
                            break;
                        case 17:
                            hourString = "5";
                            suffix = "pm";
                            break;
                        case 18:
                            hourString = "6";
                            suffix = "pm";
                            break;
                        case 19:
                            hourString = "7";
                            suffix = "pm";
                            break;
                        case 20:
                            hourString = "8";
                            suffix = "pm";
                            break;
                        case 21:
                            hourString = "9";
                            suffix = "pm";
                            break;
                        case 22:
                            hourString = "10";
                            suffix = "pm";
                            break;
                        case 23:
                            hourString = "11";
                            suffix = "pm";
                            break;
                        default:
                            hourString = String.valueOf(hour);
                            suffix = "am";
                            break;
                    }

                    switch(minute){

                        case 0:
                            minuteString = "00";
                            break;
                        case 1:
                            minuteString = "01";
                            break;
                        case 2:
                            minuteString = "02";
                            break;
                        case 3:
                            minuteString = "03";
                            break;
                        case 4:
                            minuteString = "04";
                            break;
                        case 5:
                            minuteString = "05";
                            break;
                        case 6:
                            minuteString = "06";
                            break;
                        case 7:
                            minuteString = "07";
                            break;
                        case 8:
                            minuteString = "08";
                            break;
                        case 9:
                            minuteString = "09";
                            break;
                        default:
                            minuteString = String.valueOf(minute);
                            break;
                    }

                    formattedTime = hourString + ":" + minuteString + suffix;

                    if (isAm) {
                        morningHour = hour;
                        morningMinute = minute;
                        peak_flow_am.setText(formattedTime);
                    } else {
                        eveningHour = hour;
                        eveningMinute = minute;
                        peak_flow_pm.setText(formattedTime);
                    }

                    System.out.println("time " + formattedTime);



                }
            };


    public void addChild() {
        add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildActivity.fromAdd = true;

                if (dialog_phone.getText().length() != 10) {
                    dialog("You must enter a valid phone number");
                } else if (morningHour == null || eveningHour == null) {
                    dialog("You must enter peak flow times");
                } else {
                    String childName = dialog_child_name.getText().toString();
                    String childPhone = dialog_phone.getText().toString();
                    String childEmail = dialog_email.getText().toString();
                    Child tempChild = new Child(childName, childPhone, childEmail, morningHour, morningMinute, eveningHour, eveningMinute);
                    System.out.println("morning/evening ints " + morningHour + " " + morningMinute + " " + eveningHour + " " + eveningMinute);
                    ChildActivity.currentChild = tempChild;
                    Intent myIntent = new Intent(AddChildActivity.this, ChildActivity.class);
                    AddChildActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    public void dialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(AddChildActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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
