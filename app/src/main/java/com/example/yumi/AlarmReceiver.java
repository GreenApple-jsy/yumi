package com.example.yumi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
//NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고

        Intent intentActivity = new Intent(context, MainActivity.class); //그메세지를 클릭했을때 불러올엑티비티를 설정함



        intentActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent =  PendingIntent.getActivity(context, 1,
                intentActivity, 0);


        // Create Notification Object
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,"default")
                        .setSmallIcon(R.drawable.icsunsang)
                        .setContentTitle("제목")
                        .setContentText("내용")
                        .setContentIntent(pendingIntent )
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);



        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

}