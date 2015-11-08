package com.hta.travelassistant.engine;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import java.util.LinkedList;
import java.util.List;

import static com.hta.travelassistant.model.Action.NOSLEEP;
import static com.hta.travelassistant.model.Action.NOSUNLIGHT;
import static com.hta.travelassistant.model.Action.SLEEP;
import static com.hta.travelassistant.model.Action.SUNLIGHT;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.joda.time.Duration.standardHours;

public class JetlagSleepPlanner implements SleepPlanner {
    @Override
    public Iterable<Recommendation> planSleep(FlightInfo flightInfo, Iterable<SleepEntry> sleepEntries) {
        double offset = flightInfo.getOffset();
        System.out.println("offset = " + offset);
        boolean fwd = offset < 0;
        double step;
        if (fwd) {
            step = 1;
        } else {
            step = -1.5;
        }
        SleepEntry normal = sleepEntries.iterator().next();
        LocalTime normalSleep = new LocalTime(normal.getStartTime());
        Duration normalDuration = normal.getDuration();
        System.out.println("normalSleep = " + normalSleep);
        DateTime timeToSleep = flightInfo.getStartTime().minusDays(abs((int) (offset / step))).withTime(normalSleep);
        while (timeToSleep.isBeforeNow()) timeToSleep = timeToSleep.plusDays(1);
        List<Recommendation> recommendations = new LinkedList<>();
        while (offset * step < 0) {
            timeToSleep = timeToSleep.plusMinutes((int) (60 * step));
            if (fwd) {
                recommendations.add(new Recommendation(timeToSleep.minusHours(3), standardHours(3), asList(SUNLIGHT, NOSLEEP)));
                recommendations.add(new Recommendation(timeToSleep.plus(normalDuration), standardHours(1), asList(NOSUNLIGHT, NOSLEEP)));
            } else {
                recommendations.add(new Recommendation(timeToSleep.plus(normalDuration), standardHours(3), asList(SUNLIGHT, NOSLEEP)));
            }
            recommendations.add(new Recommendation(timeToSleep, normalDuration, singletonList(SLEEP)));
            offset += step;
            timeToSleep = timeToSleep.plusDays(1);
            System.out.println("offset = " + offset);
        }
        for (Recommendation r : recommendations) {
            System.out.println("r = " + r);
        }
        return recommendations;
    }
}
