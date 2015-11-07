package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;

public interface SleepPlanner {
    Iterable<Recommendation> planSleep(FlightInfo flightInfo, Iterable<SleepEntry> sleepEntries);
}
