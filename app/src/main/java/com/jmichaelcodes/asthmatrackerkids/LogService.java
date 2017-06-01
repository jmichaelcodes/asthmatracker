package com.jmichaelcodes.asthmatrackerkids;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jmichaelcodes.asthmatrackerkids.MainActivity;
import com.jmichaelcodes.asthmatrackerkids.Models.Child;
import com.jmichaelcodes.asthmatrackerkids.Models.Entries;
import com.jmichaelcodes.asthmatrackerkids.Models.Entry;
import com.jmichaelcodes.asthmatrackerkids.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by doctorj89 on 5/31/17.
 */

public class LogService extends Service {

    private NotificationManager mManager;
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

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

//        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
//        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
//
//        Notification notification = new Notification(R.mipmap.ic_launcher, "This is a test message!", System.currentTimeMillis());
//        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
//
//        mManager.notify(0, notification);

        loadLogs();
        loadEmail();
        sendEmail();
        logEmail();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void loadLogs() {
        mPrefs = this.getSharedPreferences("prefs", LogService.this.MODE_PRIVATE);
        try {
            Gson gson = new Gson();
            String json = mPrefs.getString("entries", "");
            childEntriesModel = gson.fromJson(json, Entries.class);
            childEntries = childEntriesModel.getEntries();
        } catch(Exception e) {
            Toast.makeText(LogService.this, "There are no entries for today", Toast.LENGTH_LONG).show();
        }
    }

    public void loadEmail() {
        mPrefs = this.getSharedPreferences("prefs", LogService.this.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("child", "");
        currentChild = gson.fromJson(json, Child.class);
        parentEmail = currentChild.getEmail();
    }

    public void sendEmail() {
                Toast.makeText(LogService.this, "Entry Log Email", Toast.LENGTH_LONG).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", parentEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asthma Report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, logWhole);
                LogService.this.startActivity(Intent.createChooser(emailIntent, "Send email..."));
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

    public String currentDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        String localTime = date.format(currentLocalTime);

        System.out.println(localTime);
        return localTime;
    }

}

