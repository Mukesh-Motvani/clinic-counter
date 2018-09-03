package com.bma.counter.clinic.notificationUtils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bma.counter.clinic.ActivityPatientDetails;
import com.bma.counter.clinic.ActivitySplash;
import com.bma.counter.clinic.R;
import com.bma.counter.clinic.preference.ClinicPref;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PushNotificationService extends FirebaseMessagingService {
    public static MediaPlayer player ;
    private static final String TAG = "PushNotificationService";
    private String Time ;
    private String Messgage, title;

    //private AppPreference preference;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Called when message is received :: " + remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message Time :: " + remoteMessage.getData().get("title"));
            Log.d(TAG, "Message Message Payload :: " + remoteMessage.getData().get("message"));
            Time = remoteMessage.getData().get("time");
            title= remoteMessage.getData().get("title");
            Messgage = remoteMessage.getData().get("message");
            sendNotification();
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification() {
        int icon = R.drawable.ic_stat_name;
        long when = System.currentTimeMillis();
        String currentDateandTime = new SimpleDateFormat("hh mm a").format(new Date());
        Notification notification = new Notification(icon, "Custom Notification", when);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setImageViewResource(R.id.image, icon);
        contentView.setTextViewText(R.id.txt_Title,title);
        contentView.setTextViewText(R.id.txt_Message, Messgage);
      //  preference.setScreenLog(1);
        if(ClinicPref.getInstance(this).getScreenLog() == 1){
            if (isRunning(getApplicationContext())){

                notification.contentView = contentView;
                Intent notificationIntent = new Intent(this, ActivityPatientDetails.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                notification.contentIntent = contentIntent;
                notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(1, notification);
            } /*else {
                Intent intent = new Intent(getApplicationContext(), ActivitySplash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }*/
        }else {
            Intent intent = new Intent(getApplicationContext(), ActivitySplash.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }





        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
            player.start();
        } catch (Exception e) {
        }
    }

    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }
        return false;
    }

}
