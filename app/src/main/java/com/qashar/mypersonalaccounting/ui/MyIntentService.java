package com.qashar.mypersonalaccounting.ui;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.qashar.mypersonalaccounting.Activities.MainActivity;
import com.qashar.mypersonalaccounting.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.qashar.mypersonalaccounting.ui.action.FOO";
    private static final String ACTION_BAZ = "com.qashar.mypersonalaccounting.ui.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.qashar.mypersonalaccounting.ui.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.qashar.mypersonalaccounting.ui.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("QQQQ","intent.getStringExtra");
        sendNotification("s","dd","aa");
        Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();
    }
    private void sendNotification(String brandId,String category,String body) {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "TAC")
                        .setContentTitle("رسالة من متجر قشار")
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_launcher_background);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("brandId", brandId);
        intent.putExtra("category", category);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int idd =  (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","demo",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(idd,notificationBuilder.build());


    }


}