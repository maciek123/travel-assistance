package com.hta.travelassistant.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hta.travelassistant.engine.JetlagSleepPlanner;
import com.hta.travelassistant.mocks.FitbitServiceMock;
import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;
import com.hta.travelassistant.providers.InMemoryRecommendationProvider;
import com.hta.travelassistant.providers.RecommendationProvider;
import com.hta.travelassistant.services.local.AndroidFlightCalendarService;
import com.hta.travelassistant.services.remote.FitbitService;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CalendarUpdateReceiver extends BroadcastReceiver {
    public CalendarUpdateReceiver() {
    }

    JetlagSleepPlanner planner = new JetlagSleepPlanner();
    FitbitService fitbitService = new FitbitServiceMock();
    RecommendationProvider recommendationProvider = InMemoryRecommendationProvider.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("Calendar changed","Calendar changed");
        List<FlightInfo> allFlights = AndroidFlightCalendarService.getInstnace(context).getAllFlights();
        List<SleepEntry> sleepEntries = fitbitService.getSleepEntries(DateTime.now().minusDays(7), DateTime.now());
        List<Recommendation> finalRecommendation = new ArrayList<>();
        for(FlightInfo fi : allFlights) {
            Iterable<Recommendation> recommendation = planner.planSleep(fi, sleepEntries);
            for(Recommendation rec : recommendation) {
                finalRecommendation.add(rec);
            }
        }

            recommendationProvider.updateRecommendation(finalRecommendation);
    }

}
