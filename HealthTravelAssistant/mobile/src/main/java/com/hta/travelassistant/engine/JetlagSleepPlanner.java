package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.Action;
import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.Collections;

public class JetlagSleepPlanner implements SleepPlanner {
    @Override
    public Iterable<Recommendation> planSleep(FlightInfo flightInfo, Iterable<SleepEntry> sleepEntries) {
        return Collections.singletonList(new Recommendation(DateTime.now(), Duration.standardHours(2), Collections.singletonList(Action.NOSLEEP)));
    }
}
