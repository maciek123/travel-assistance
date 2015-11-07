package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.Collections;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SleepPlannerTest {
    private SleepPlanner sleepPlanner = new JetlagSleepPlanner();

    @Test
    public void shouldPlanSleep() throws Exception {
        sleepPlanner.planSleep(new FlightInfo("LAX", "ZRH", DateTime.now().plusDays(2), Duration.standardHours(12)),
                Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2), Duration.standardHours(7))));
    }

    @Test
    public void tz() {
        DateTimeZone timeZone = DateTimeZone.forID("America/Los_Angeles");
        System.out.println(timeZone.toString());
    }

}