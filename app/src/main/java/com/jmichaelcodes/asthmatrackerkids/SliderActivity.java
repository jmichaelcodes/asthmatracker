package com.jmichaelcodes.asthmatrackerkids;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jmichaelcodes.asthmatrackerkids.Models.Child;
import com.jmichaelcodes.asthmatrackerkids.Models.Entries;
import com.jmichaelcodes.asthmatrackerkids.Models.Entry;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ademar.phasedseekbar.PhasedSeekBar;
import flow.Flow;

/**
 * Created by jmichaelcodes on 8/27/15.
 */
public class SliderActivity extends AppCompatActivity {
    private Flow flow;
        private Context context;
    public PhasedSeekBar psbHorizontal;
    private String sliderPosition;
    private String sliderZone;
    private Entry currentEntry;
    private ArrayList<Entry> currentEntryArray = new ArrayList<Entry>();
    private Entries currentEntries;
    private Integer typeInt = null;
    public SeekBar seekbar;
    public ImageView emoji_display;
    private ImageButton back_arrow;
    private ImageButton confirm_button;
    private ImageButton cancel_button;
    private String parentPhone;
    private SharedPreferences mPrefs;
    private Child currentChild;
    private String childName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        final Resources resources = getResources();
        mPrefs = this.getSharedPreferences("prefs", context.MODE_PRIVATE);
        emoji_display = (ImageView) findViewById(R.id.emoji_display);
        confirm_button = (ImageButton) findViewById(R.id.confirm_button);
        cancel_button = (ImageButton) findViewById(R.id.cancel_button);
        back_arrow = (ImageButton) findViewById(R.id.back_arrow);

        if (ConditionActivity.peakFlow) {
            emoji_display.setImageResource(R.drawable.greenlight);
            sliderPosition = "Green";
        } else if (ConditionActivity.howAreYou) {
            conditionBooleans(R.drawable.emjsmileteeth, R.drawable.emjsad);
            sliderPosition = "Great";
        } else {
            conditionBooleans(R.drawable.emjsmileteeth, R.drawable.emjsad);
            sliderPosition = "1";
        }

        seekbar = (SeekBar) findViewById(R.id.seekbar);

        setupSlider();
        typeToInt();
        backArrow();
        confirmButton();
        cancelButton();
        loadChild();
        loadEntries();

    }

    public void loadChild() {
        Gson gson = new Gson();
        String json = mPrefs.getString("child", "");
        currentChild = gson.fromJson(json, Child.class);
        childName = currentChild.getChildName();
        parentPhone = currentChild.getPhone();
    }

    public void loadEntries() {
        Gson gson = new Gson();
        String json = mPrefs.getString("entries", "");
        currentEntries = gson.fromJson(json, Entries.class);
        try {
            currentEntryArray = currentEntries.getEntries();
        } catch (Exception e) {

        }
    }

        public void conditionBooleans(Integer how, Integer attack) {
            if (ConditionActivity.howAreYou) {
            emoji_display.setImageResource(how);
        } else if (ConditionActivity.attackCondition) {
            emoji_display.setImageResource(attack);
        }
    }

    public void backArrow() {
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SliderActivity.this, ConditionActivity.class);
                SliderActivity.this.startActivity(myIntent);
            }
        });
    }

    public void confirmButton() {
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConditionActivity.attackCondition) {

                    final Dialog confirmDialog = new Dialog(SliderActivity.this);
                    confirmDialog.setContentView(R.layout.child_dialog);
                    ImageButton dialogConfirm = (ImageButton) confirmDialog.findViewById(R.id.dialog_confirm_button);
                    // if button is clicked, close the custom dialog
                    dialogConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                            parsePosition();
                            System.out.println("slider position " + sliderPosition);
                            if (typeInt == 2) {
                                String attackMessage = childName + " had a level " + sliderPosition + " attack at " + currentTime();
                                SmsManager.getDefault().sendTextMessage(parentPhone, null, attackMessage, null, null);
                                Intent myIntent = new Intent(SliderActivity.this, ConditionActivity.class);
                                SliderActivity.this.startActivity(myIntent);
                            } else {
                                Intent myIntent = new Intent(SliderActivity.this, ConditionActivity.class);
                                SliderActivity.this.startActivity(myIntent);
                            }
                        }
                    });
                    ImageButton dialogCancel = (ImageButton) confirmDialog.findViewById(R.id.dialog_cancel_button);
                    // if button is clicked, close the custom dialog
                    dialogCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                            Toast.makeText(SliderActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                        }
                    });

                    confirmDialog.show();
                } else {
                    parsePosition();
                    System.out.println("slider position " + sliderPosition);
                    Intent myIntent = new Intent(SliderActivity.this, ConditionActivity.class);
                    SliderActivity.this.startActivity(myIntent);
                    //            Toast.makeText(context, "Confirmed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancelButton() {
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SliderActivity.this, ConditionActivity.class);
                SliderActivity.this.startActivity(myIntent);
                Toast.makeText(SliderActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String currentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("hh:mm a");
        date.setTimeZone(TimeZone.getDefault());

        String localTime = date.format(currentLocalTime);

        return localTime;
    }

    public String currentDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        String localTime = date.format(currentLocalTime);

        System.out.println(localTime);
        return localTime;
    }

    public Integer typeToInt() {
        if (ConditionActivity.peakFlow) {
            typeInt = 0;
        } else if (ConditionActivity.howAreYou) {
            typeInt = 1;
        } else if (ConditionActivity.attackCondition) {
            typeInt = 2;
        }
        System.out.println("Slider Type = " + String.valueOf(typeInt));
        return typeInt;
    }

    public static String intToType(Integer type) {
        String typeString = null;

        switch(type){
            case 0 :
                typeString = "Peak Flow";
                break; //optional
            case 1 :
                typeString = "How are you?";
                break; //optional
            case 2 :
                typeString = "Attack";
            //You can have any number of case statements.
            default : //Optional
                //Statements
        }

        return typeString;
    }

    public void setupSlider() {
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);

                if (ConditionActivity.attackCondition) {
                    if (progress <= 20) {
//                    seekBar.setProgressDrawable(progress1);
                        seekBar.setBackgroundResource(R.color.green1);
                        conditionBooleans(R.drawable.emjsmileteeth, R.drawable.emjsad);
                        sliderPosition = "1";
                    } else if (progress > 20 && progress < 40) {
//                    seekBar.setProgressDrawable(progress2);
                        seekBar.setBackgroundResource(R.color.green2);
                        conditionBooleans(R.drawable.emjsmilewide, R.drawable.emjclosedsweat);
                        sliderPosition = "2";
                    } else if (progress > 40 && progress < 60) {
//                    seekBar.setProgressDrawable(progress3);
                        seekBar.setBackgroundResource(R.color.yellow3);
                        conditionBooleans(R.drawable.emjblank, R.drawable.emjopensweat);
                        sliderPosition = "3";
                    } else if (progress > 60 && progress < 80) {
//                    seekBar.setProgressDrawable(progress4);
                        seekBar.setBackgroundResource(R.color.orange1);
                        conditionBooleans(R.drawable.emjsadwide, R.drawable.emjmouth);
                        sliderPosition = "4";
                    } else if (progress > 80) {
//                    seekBar.setProgressDrawable(progress5);
                        seekBar.setBackgroundResource(R.color.red);
                        conditionBooleans(R.drawable.emjfrownteeth, R.drawable.emjhandsmouth);
                        sliderPosition = "5";
                    }
                } else if (ConditionActivity.howAreYou){
                    if (progress <= 20) {
//                    seekBar.setProgressDrawable(progress1);
                        seekBar.setBackgroundResource(R.color.green1);
                        conditionBooleans(R.drawable.emjsmileteeth, R.drawable.emjsad);
                        sliderPosition = "Great";
                    } else if (progress > 20 && progress < 40) {
//                    seekBar.setProgressDrawable(progress2);
                        seekBar.setBackgroundResource(R.color.green2);
                        conditionBooleans(R.drawable.emjsmilewide, R.drawable.emjclosedsweat);
                        sliderPosition = "Good";
                    } else if (progress > 40 && progress < 60) {
//                    seekBar.setProgressDrawable(progress3);
                        seekBar.setBackgroundResource(R.color.yellow3);
                        conditionBooleans(R.drawable.emjblank, R.drawable.emjopensweat);
                        sliderPosition = "So-So";
                    } else if (progress > 60 && progress < 80) {
//                    seekBar.setProgressDrawable(progress4);
                        seekBar.setBackgroundResource(R.color.orange1);
                        conditionBooleans(R.drawable.emjsadwide, R.drawable.emjmouth);
                        sliderPosition = "Bad";
                    } else if (progress > 80) {
//                    seekBar.setProgressDrawable(progress5);
                        seekBar.setBackgroundResource(R.color.red);
                        conditionBooleans(R.drawable.emjfrownteeth, R.drawable.emjhandsmouth);
                        sliderPosition = "Awful";
                    }
                } else if (ConditionActivity.peakFlow) {
                    if (progress <= 33) {
//                    seekBar.setProgressDrawable(progress1);
                        seekBar.setBackgroundResource(R.color.green1);
                        emoji_display.setImageResource(R.drawable.greenlight);
                        sliderPosition = "Green";
                    } else if (progress <= 66 && progress > 34) {
//                    seekBar.setProgressDrawable(progress2);
                        seekBar.setBackgroundResource(R.color.yellow3);
                        emoji_display.setImageResource(R.drawable.yellowlight);
                        sliderPosition = "Yellow";
                    } else if (progress > 67) {
//                    seekBar.setProgressDrawable(progress3);
                        seekBar.setBackgroundResource(R.color.red);
                        emoji_display.setImageResource(R.drawable.redlight);
                        sliderPosition = "Red";
                    }

//                    System.out.println(currentGreen);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void parsePosition() {
        if (ConditionActivity.peakFlow) {
            currentEntry = new Entry(sliderPosition, currentTime(), typeToInt(), currentDate(), currentChild);
        } else if (ConditionActivity.howAreYou) {
            currentEntry = new Entry(sliderPosition, currentTime(), typeToInt(), currentDate(), currentChild);
        } else {
            currentEntry = new Entry(sliderPosition + "/5", currentTime(), typeToInt(), currentDate(), currentChild);
        }

        currentEntries = new Entries();


        currentEntryArray.add(currentEntry);
        currentEntries.setEntries(currentEntryArray);
        currentEntries.setEntriesDate(currentDate());

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentEntries);
        prefsEditor.putString("entries", json);
        prefsEditor.commit();

        System.out.println("entries array size " + currentEntryArray.size());

        String entryString;
        for (int i=0; i < currentEntries.getEntries().size(); i++) {
            entryString = currentEntries.getEntries().get(i).getEntryTime();
            System.out.println("entry strings " + entryString);
        }
//        System.out.println("current entries" + currentEntries.getEntries().get(0).getType().toString());
        Toast.makeText(SliderActivity.this, currentEntry.getEntryTime() + "  " + intToType(currentEntry.getType()) + ": " + currentEntry.getZone(), Toast.LENGTH_LONG).show();
    }

}
