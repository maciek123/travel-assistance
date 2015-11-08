package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SleepPlannerTest {
    private SleepPlanner sleepPlanner = new JetlagSleepPlanner();

    @Test
    public void shouldPlanAnything() throws Exception {
        sleepPlanner.planSleep(new FlightInfo("ZRH", "LAX", DateTime.now().plusDays(2), Duration.standardHours(12)),
                Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2), Duration.standardHours(7))));
    }

    @Test
    public void shouldPlanSleep() throws Exception {
        FlightInfo flightInfo = new FlightInfo("ZRH", "NRT", DateTime.now().plusDays(12), Duration.standardHours(12));
        List<SleepEntry> sleepEntries = Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2).withTime(23, 0, 0,         0), Duration.standardHours(7)));
        Iterable<Recommendation> recommendations = sleepPlanner.planSleep(flightInfo, sleepEntries);

    }

    private static boolean contains(Iterable<Recommendation> recommendations, Recommendation expected) {
        for (Recommendation r : recommendations) {
            if (r.equals(expected)) return true;
        }
        return false;
    }


}