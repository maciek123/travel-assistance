package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SleepPlannerTest {
    private SleepPlanner sleepPlanner = new JetlagSleepPlanner();

    @Test
    public void shouldPlanAnything() throws Exception {
        sleepPlanner.planSleep(new FlightInfo("LAX", "ZRH", DateTime.now().plusDays(2), Duration.standardHours(12)),
                Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2), Duration.standardHours(7))));
    }

    @Test
    public void shouldPlanSleep() throws Exception {
        FlightInfo flightInfo = new FlightInfo("LAX", "ZRH", DateTime.now().plusDays(2), Duration.standardHours(12));
        List<SleepEntry> sleepEntries = Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2).withTime(23, 0, 0, 0), Duration.standardHours(7)));
        sleepPlanner.planSleep(flightInfo, sleepEntries);
    }



}