package com.hta.travelassistant.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hta.travelassistant.activities.R;
import com.hta.travelassistant.engine.JetlagSleepPlanner;
import com.hta.travelassistant.mocks.FitbitServiceMock;
import com.hta.travelassistant.model.Action;
import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;
import com.hta.travelassistant.providers.InMemoryRecommendationProvider;
import com.hta.travelassistant.providers.RecommendationProvider;
import com.hta.travelassistant.services.local.AndroidFlightCalendarService;
import com.hta.travelassistant.services.local.FlightCalendarService;
import com.hta.travelassistant.services.remote.FitbitService;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CalendarScannerService extends Service {
    public CalendarScannerService() {
    }

    // Notification interval - 1 min
    private static final long UPDATE_INTERVAL = 1000 * 30;


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
                        scanCalendar();
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

    public void scanCalendar() {
        FlightCalendarService flightCalendarService = AndroidFlightCalendarService.getInstnace(this);
        JetlagSleepPlanner planner = new JetlagSleepPlanner();
        FitbitService fitbitService = new FitbitServiceMock();
        RecommendationProvider recommendationProvider = InMemoryRecommendationProvider.getInstance();

        List<FlightInfo> allFlights = flightCalendarService.getAllFlights();
        List<SleepEntry> sleepEntries = fitbitService.getSleepEntries(DateTime.now().minusDays(7), DateTime.now());
        List<Recommendation> finalRecommendation = new ArrayList<>();
        for(FlightInfo fi : allFlights) {
            Iterable<Recommendation> recommendation = planner.planSleep(fi, sleepEntries);
            for(Recommendation rec : recommendation) {
                finalRecommendation.add(rec);
            }
        }
        Log.e("Updated recommendation", "Updated recommendations: " + finalRecommendation.size() + " for flights: " + allFlights.size());
        recommendationProvider.updateRecommendation(finalRecommendation);
    }


}
