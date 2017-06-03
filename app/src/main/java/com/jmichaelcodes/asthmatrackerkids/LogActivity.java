package com.jmichaelcodes.asthmatrackerkids;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jmichaelcodes.asthmatrackerkids.Models.Child;
import com.jmichaelcodes.asthmatrackerkids.Models.Entries;
import com.jmichaelcodes.asthmatrackerkids.Models.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import flow.Flow;

/**
 * Created by docto_000 on 6/30/2016.
 */
public class LogActivity extends ActionBarActivity {

    public static List<Entry> entries = new ArrayList<>();
    public static Entries childEntriesModel = new Entries();
    private ArrayList<Entry> childEntries = new ArrayList<>();
    public static Entry currentEntry;
    public String logStart;
    public String logEnd;
    public String logWhole;
    public ArrayList<String> logEnds = new ArrayList<>();
    private Child currentChild;
    private String parentEmail;
    private SharedPreferences mPrefs;
    private PendingIntent pendingIntent;

    private Flow flow;
    private Context context;
    final String TAG = "AltDB LoginView";

    private LogAdapter adapter;
    ListView log_list_view;
    private FloatingActionButton get_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        log_list_view = (ListView) findViewById(R.id.log_list_view);

        get_log = (FloatingActionButton) findViewById(R.id.send_email);

        get_log.setImageResource(R.drawable.contact_email_color);

        mPrefs = this.getSharedPreferences("prefs", context.MODE_PRIVATE);

        getCurrentEntries();
        loadLogs();
        loadEmail();
        sendEmail();
        logEmail();
        setLogTime();


        adapter = new LogAdapter(LogActivity.this, R.layout.log_list_item, childEntries);

        log_list_view.setDivider(null);
        log_list_view.setAdapter(adapter);
        System.out.println(entries);
        System.out.println(parentEmail);

    }

    public void loadLogs() {
        try {
        Gson gson = new Gson();
        String json = mPrefs.getString("entries", "");
        childEntriesModel = gson.fromJson(json, Entries.class);
            childEntries = childEntriesModel.getEntries();
        } catch(Exception e) {
            Toast.makeText(LogActivity.this, "There are no entries for today", Toast.LENGTH_LONG).show();
        }
    }

    public void loadEmail() {
        Gson gson = new Gson();
        String json = mPrefs.getString("child", "");
        currentChild = gson.fromJson(json, Child.class);
        parentEmail = currentChild.getEmail();
    }

    public void sendEmail() {
        System.out.println("log " + logWhole);
        get_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogActivity.this, "Entry Log Email", Toast.LENGTH_LONG).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", parentEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asthma Report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, logWhole);
                LogActivity.this.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    public void logEmail() {
        try {
            logStart = childEntries.get(0).getChild().getChildName() + "'s log for " + currentDate() + " reads as follows: ";
            System.out.println("log start " + logStart);


            for (int i = 0; i < childEntries.size(); i++) {
//                logEnd = childEntries.get(i).getZone() + " " + SliderActivity.intToType(childEntries.get(i).getType()) + " at " + childEntries.get(i).getEntryTime();
                logEnd = SliderActivity.intToType(childEntries.get(i).getType()) + " - " + childEntries.get(i).getZone() + " at " + childEntries.get(i).getEntryTime();
                if (currentDate().equals(childEntries.get(i).getDate())) {
                    logEnds.add(logEnd);
                }
            }
            System.out.println("LOG ENDS" + logEnds.toString());
            logWhole = logStart + "\n\n" + logEnds.toString().replace("[", "").replace("]", "").replace(", ", "\n");

//        System.out.println(logStart + childEntries.get(0).getZone() + " on " + SliderView.intToType(childEntries.get(0).getType()) + " at " + childEntries.get(0).getEntryTime());
            System.out.println("-----" + logWhole);
        } catch (Exception e) {
            System.out.println("log email catch");
        }
    }

    public void getCurrentEntries () {
        for (int i = 0; i < entries.size(); i++) {
            currentEntry = entries.get(i);
            if (entries.get(i).getChild() == currentChild) {
                childEntries.add(currentEntry);
            }
        }
    }

    private class LogAdapter extends ArrayAdapter<Entry> {

        private LayoutInflater inflater;
        private int resource;

        public LogAdapter(Context context, int resource, ArrayList<Entry> objects) {
            super(context, resource, objects);

            inflater = (LayoutInflater) LogActivity.this.getSystemService(LogActivity.this.LAYOUT_INFLATER_SERVICE);
            this.resource = resource;
            Collections.reverse(childEntries);
            childEntries = objects;
            Log.i(TAG, "Adapter Created");
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(resource, parent, false);

                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                System.out.println("position " + position);
                if (position == 0 || !childEntries.get(position).getDate().equals(childEntries.get(position - 1).getDate())) {
                    holder.logDate.setText(childEntries.get(position).getDate());
                    holder.logDate.setVisibility(View.VISIBLE);
                } else {
                    holder.logDate.setVisibility(View.GONE);
                }
                holder.logTime.setText(childEntries.get(position).getEntryTime());
                holder.logType.setText(intToType(childEntries.get(position).getType()));
                holder.logZone.setText(childEntries.get(position).getZone());

                System.out.println("entries size " + childEntries.size());

//                for (int i = 1; i < childEntries.size(); i++) {
//                if (childEntries.get(i).getDate().equals(childEntries.get(i - 1).getDate())) {
//                    holder.logDate.setVisibility(View.GONE);
//                } else {
//                    holder.logDate.setVisibility(View.VISIBLE);
//                }
//                }

            } catch (NullPointerException e) {
                //this should be done better but for now it works.
                Log.e(TAG, "NPE in layout inflater");
                e.printStackTrace();
            }

//            for (int i = 1; i < entries.size(); i++) {
//                if (entries.get(i).getDate().equals(entries.get(i - 1).getDate())) {
//                    holder.logDate.setVisibility(View.GONE);
//                }
//            }

            return convertView;
        }

        private class ViewHolder {
            public TextView logDate;
            public TextView logTime;
            public TextView logType;
            public TextView logZone;


            public ViewHolder(View view) {
                logDate = (TextView) view.findViewById(R.id.log_date);
                logTime = (TextView) view.findViewById(R.id.log_time);
                logType = (TextView) view.findViewById(R.id.log_type);
                logZone = (TextView) view.findViewById(R.id.log_zone);
//
            }
        }
    }

    public String currentDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        String localTime = date.format(currentLocalTime);

        System.out.println(localTime);
        return localTime;
    }

    public String intToType(Integer type) {
        String typeString = null;

        switch(type){
            case 0 :
                typeString = "Peak Flow";
                break; //optional
            case 1 :
                typeString = "Breathing";
                break; //optional
            case 2 :
                typeString = "Attack";
                //You can have any number of case statements.
            default : //Optional
                //Statements
        }

        return typeString;
    }

    public void setLogTime() {
        Calendar calendar = Calendar.getInstance();

        GregorianCalendar twopm = new GregorianCalendar();
        twopm.set(GregorianCalendar.HOUR_OF_DAY, 20);
        twopm.set(GregorianCalendar.MINUTE, 00);
        twopm.set(GregorianCalendar.SECOND, 0);
        twopm.set(GregorianCalendar.MILLISECOND, 0);
        if(twopm.before(new GregorianCalendar())){
            twopm.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }

        Intent myIntent = new Intent(LogActivity.this, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(LogActivity.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, twopm.getTimeInMillis(), 1000*60*60*24, pendingIntent);


    }

}
