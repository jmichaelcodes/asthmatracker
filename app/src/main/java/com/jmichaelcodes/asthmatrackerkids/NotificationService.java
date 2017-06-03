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

public class NotificationService extends Service {

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

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);

        Notification notification = new Notification(R.mipmap.ic_launcher, "This is a test message!", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);

        mManager.notify(0, notification);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}

