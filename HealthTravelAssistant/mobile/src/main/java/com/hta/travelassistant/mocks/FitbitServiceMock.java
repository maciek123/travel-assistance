package com.hta.travelassistant.mocks;

import com.hta.travelassistant.model.SleepEntry;
import com.hta.travelassistant.model.SleepSummary;
import com.hta.travelassistant.services.remote.FitbitService;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kacper.zielinski on 07.11.2015.
 */
public class FitbitServiceMock implements FitbitService {

    List<SleepEntry> entries = Arrays.asList(new SleepEntry(DateTime.now(), Duration.millis(1000)),
            new SleepEntry(DateTime.now().minusDays(1), Duration.millis(1000)),
            new SleepEntry(DateTime.now().minusDays(2), Duration.millis(2000)),
            new SleepEntry(DateTime.now().minusDays(3), Duration.millis(3000)));

    @Override
    public List<SleepEntry> getSleepEntries(DateTime from, DateTime to) {
        return entries;
    }

    @Override
    public List<SleepEntry> getSleepEntries(DateTime from, String period) {
        return entries;
    }

    @Override
    public SleepSummary getSleepSummary(DateTime from, DateTime to) {
        return null;
    }
}
