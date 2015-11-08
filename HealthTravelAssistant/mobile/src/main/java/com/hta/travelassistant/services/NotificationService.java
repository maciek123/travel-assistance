package com.hta.travelassistant.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.hta.travelassistant.activities.R;
import com.hta.travelassistant.model.Action;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.providers.InMemoryRecommendationProvider;
import com.hta.travelassistant.providers.RecommendationProvider;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Periodically scan Recommendation table and notify User if needed
 */
public class NotificationService extends Service {

    // Notification interval - 1 min
    private static final long UPDATE_INTERVAL = 1000 * 30;

//    private RecommendationProvider recommendationProvider = new RecommendationProviderMock();
    private RecommendationProvider recommendationProvider = InMemoryRecommendationProvider.getInstance();

    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // we shedule task "showNotification" to run everyday.
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        showNotification();
                    }
                }, 0, UPDATE_INTERVAL);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void showNotification() {
        List<Recommendation> recommendations = recommendationProvider.getRecommendations();
        for (Recommendation recommendation : recommendations) {
            //  if notify
            if (!recommendation.isUserNotified() && recommendation.getStartTime().isBeforeNow()) {
                Notification notification = getNotification(recommendation.getActions());

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // notificationId allows you to update the notification later on.
                int notificationId = 123;
                mNotificationManager.notify(notificationId, notification);
            }
        }
    }

    private Notification getNotification(Iterable<Action> actions) {
        StringBuilder sb = new StringBuilder();
        for (Action action : actions) {
            switch (action) {
                case NOSLEEP:
                    sb.append(getResources().getString(R.string.no_sleep_notification));
                    break;
                case SLEEP:
                    sb.append(getResources().getString(R.string.sleep_notification));
                    break;
                case SUNLIGHT:
                    sb.append(getResources().getString(R.string.sunlight_notification));
                    break;
                case NOSUNLIGHT:
                    sb.append(getResources().getString(R.string.no_sunlight_notification));
                    break;
            }
            sb.append(" ");
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("Your trip notification.")
//                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setContentText(sb.toString());
        return mBuilder.build();
    }

}
