package com.squalala.hardgamerdz.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.squalala.hardgamerdz.common.AppConstants;
import com.squalala.hardgamerdz.service.NotificationService;

import java.util.Calendar;

import hugo.weaving.DebugLog;

/**
 * Created by Back Packer
 * Date : 29/09/15
 */
public class BootReceiver extends BroadcastReceiver {

    @DebugLog
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") ||
                intent.getAction().equals("com.squalala.hardgamerdz_ACTION")) {
            // Set the alarm here.
            Calendar cal = Calendar.getInstance();

            Intent intentService = new Intent(context, NotificationService.class);
            PendingIntent pintent = PendingIntent.getService(context, 0, intentService, 0);

            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            // schedule for every 30 seconds
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AppConstants.TIME_FETCH, pintent);

        }
    }
}
