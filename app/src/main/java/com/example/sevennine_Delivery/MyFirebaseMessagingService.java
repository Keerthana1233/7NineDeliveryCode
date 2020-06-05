package com.example.sevennine_Delivery;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        //sendMyNotification(message.getNotification().getBody());

        if (message.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(message.getData().toString());
                String title = message.getData().get("message");
                System.out.println("rajafdfdffff" + title);
                JSONObject jsonObjCurrently= json.getJSONObject("message");
                System.out.println("raja" + title);
                String body=jsonObjCurrently.getString("body");
                System.out.println("rajacxcccccc" + body);
                String msg = message.getData().get("title");
                System.out.println("raja" + msg);
                //sendMyNotification(body,msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


   /* private void sendMyNotification(String message, String title) {

        Intent intent = null;

        intent = new Intent(this, Main.class);
        intent.putExtra("A_NAME", "ask");
        intent.putExtra("status", "not_tab_Question");

        long when = System.currentTimeMillis();
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_layout);
        notificationLayout.setTextViewText(R.id.app_name, title);
        notificationLayout.setTextViewText(R.id.notificationSubject, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

      //  Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.customer_not);

      //  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.customer_not);

        // Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo_not)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomBigContentView(notificationLayout)
                .setContentIntent(pendingIntent)
                .setWhen(when)
               // .setSound(sound)
                .setAutoCancel(true);;


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);


            manager.createNotificationChannel(channel);
        }
        int id = (int) System.currentTimeMillis();
        manager.notify(id, builder.build());

    }*/
}
