package com.hta.travelassistant.mocks;

import com.hta.travelassistant.model.SleepEntry;
import com.hta.travelassistant.model.SleepSummary;
import com.hta.travelassistant.services.remote.FitbitService;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by kacper.zielinski on 07.11.2015.
 */
public class FitbitServiceMock implements FitbitService {

    @Override
    public List<SleepEntry> getSleepEntries(DateTime from, DateTime to) {
        return null;
    }

    @Override
    public List<SleepEntry> getSleepEntries(DateTime from, String period) {
        return null;
    }

    @Override
    public SleepSummary getSleepSummary(DateTime from, DateTime to) {
        return null;
    }
}
