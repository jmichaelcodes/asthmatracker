package com.jmichaelcodes.asthmatrackerkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by doctorj89 on 5/31/17.
 */

public class LogReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service1 = new Intent(context, LogService.class);
        context.startService(service1);

    }
}