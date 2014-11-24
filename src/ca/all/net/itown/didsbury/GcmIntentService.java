package ca.all.net.itown.didsbury;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
   public static final int NOTIFICATION_ID = 1;
   private NotificationManager mNotificationManager;
   NotificationCompat.Builder builder;


   public GcmIntentService() {
       super("GcmIntentService");
   }
   public static final String TAG = "******GCM Notification Services******";

   @Override
   protected void onHandleIntent(Intent intent) {
       Bundle extras = intent.getExtras();
       GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
       // The getMessageType() intent parameter must be the intent you received
       // in your BroadcastReceiver.
       String messageType = gcm.getMessageType(intent);

       if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
           /*
            * Filter messages based on message type. Since it is likely that GCM will be
            * extended in the future with new message types, just ignore any message types you're
            * not interested in, or that you don't recognize.
            */
           if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
               sendNotification("Send error: " + extras.toString());
           } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
               sendNotification("Deleted messages on server: " + extras.toString());
           // If it's a regular GCM message, do some work.
           } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
               // This loop represents the service doing some work.
               for (int i = 0; i < 5; i++) {
                   Log.i(TAG, "Working... " + (i + 1)
                           + "/5 @ " + SystemClock.elapsedRealtime());
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                   }
               }
               Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
               // Post notification of received message.
               sendNotification(extras);
               Log.i(TAG, "Received: " + extras.toString());
           }
       }
       // Release the wake lock provided by the WakefulBroadcastReceiver.
       GcmBroadcastReceiver.completeWakefulIntent(intent);
   }

   // Put the message into a notification and post it.
   // This is just one simple example of what you might choose to do with
   // a GCM message.
   private void sendNotification(Bundle msg) {
	   
	    String alertMessage = msg.getString("alert");   			 	   		
   		String title = new String("Didsbury Notification");	
	   
   		DidsburyActivity.notificationMessage = alertMessage;
   		Log.i("******************Alert Message***************", alertMessage);
       mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
      // mNotificationManager.cancelAll();
      // mNotificationManager.cancel(NOTIFICATION_ID);
       
       PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, DidsburyActivity.class).putExtra("EXIT", true) , 0);
       //contentIntent.cancel();
       NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
       String[] events = new String[6];
       // Sets a title for the Inbox style big view
       inboxStyle.setBigContentTitle(title);
       inboxStyle.setSummaryText(alertMessage);
       // Moves events into the big view
       for (int i=0; i < events.length; i++) {
           inboxStyle.addLine(events[i]);
       }
       
       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
       .setContentTitle(title)
       .setStyle(inboxStyle)
       .setContentText(alertMessage)
       .setContentIntent(contentIntent);
       
       long [] vibrate = {0,100,200,300};
       mBuilder.setVibrate(vibrate);
       
       Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       mBuilder.setSound(notificationSound);

       mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
      
   }
   
   
   //For error messagae
   private void sendNotification(String msg) {
       mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

       PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DidsburyActivity.class), 0);

       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
       .setContentTitle("Didsbury Notification")
       .setStyle(new NotificationCompat.BigTextStyle()
       .bigText(msg))
       .setContentText(msg);

       mBuilder.setContentIntent(contentIntent);
       mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
   }
}