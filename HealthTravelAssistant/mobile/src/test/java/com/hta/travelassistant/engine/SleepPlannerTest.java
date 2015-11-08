package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SleepPlannerTest {
    private SleepPlanner sleepPlanner = new JetlagSleepPlanner();


    @Before
    public void setUp(){
        AirportUtils.init(AirportUtils.class.getClassLoader().getResourceAsStream("iata.tzmap"));
    }

    @Test
    public void shouldPlanAnything() throws Exception {

        List<SleepEntry> sleepEntries = Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2).withTime(23, 0, 0, 0), Duration.standardHours(7)));
        DateTime startTime = DateTime.now().plusDays(2);
        Duration duration = Duration.standardHours(12);
        int offset = calculateOffset("ZHR", "LAX", duration, startTime);
        sleepPlanner.planSleep(new FlightInfo("ZRH", "LAX", startTime, duration, offset), sleepEntries);
    }

    @Test
    public void shouldPlanSleep() throws Exception {
        DateTime startTime = DateTime.now().plusDays(12);
        Duration duration = Duration.standardHours(12);
        int offset = calculateOffset("ZHR", "NRT", duration, startTime);
        FlightInfo flightInfo = new FlightInfo("ZRH", "NRT", startTime, duration, offset);
        List<SleepEntry> sleepEntries = Collections.singletonList(new SleepEntry(DateTime.now().minusDays(2).withTime(23, 0, 0, 0), Duration.standardHours(7)));
        Iterable<Recommendation> recommendations = sleepPlanner.planSleep(flightInfo, sleepEntries);

    }

    private static boolean contains(Iterable<Recommendation> recommendations, Recommendation expected) {
        for (Recommendation r : recommendations) {
            if (r.equals(expected)) return true;
        }
        return false;
    }


    private static int calculateOffset(String from, String to, Duration duration, DateTime startTime) {
        DateTimeZone fromTZ = AirportUtils.airportToTZ(from);
        DateTimeZone toTZ = AirportUtils.airportToTZ(to);
        DateTime arrivalTime = startTime.plus(duration);
        int offsetMillis = fromTZ.getOffset(startTime) - toTZ.getOffset(arrivalTime);
        return offsetMillis / (1000 * 60 * 60);
    }




}