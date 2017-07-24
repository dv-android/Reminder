package com.hfad.reminder;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by DEVANG on 7/21/2017.
 */



public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // assumes WordService is a registered service
        //Intent intent = new Intent(context, WordService.class);
        //context.startService(intent);
       // Toast.makeText(context, "Don't panik but your time is up!!!!.",
        //        Toast.LENGTH_LONG).show();
        Calendar cal = Calendar.getInstance();
         Log.d("Receiver","called");
         Log.d("Receiver timestamp",""+cal.getTimeInMillis());
        int requestCode = intent.getExtras().getInt("requesteCode");

        if(requestCode==0)
       {


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context);

//Create the intent that’ll fire when the user taps the notification//

            Intent intentN = new Intent(context,MainActivity.class);
            intentN.putExtra("openFrmRmndNtfn",true);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(intentN);
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            mBuilder.setContentIntent(pendingIntent);

            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("My notification");
            mBuilder.setContentText("Hello World!");

            NotificationManager mNotificationManager =

                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder.setAutoCancel(true);
            mNotificationManager.notify(001, mBuilder.build());

        }else if(requestCode==1){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Upcoming Alarm");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
            View dialogView = inflater.inflate(R.layout.birhday_layout, null);

            AlertDialog alertDialog = builder.create();
            alertDialog.setView(dialogView);
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alertDialog.show();


        }


    }
}
