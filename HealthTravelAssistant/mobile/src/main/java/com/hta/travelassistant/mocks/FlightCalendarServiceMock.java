package com.hta.travelassistant.mocks;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.services.local.FlightCalendarService;

import java.util.List;

/**
 * Created by kacper.zielinski on 07.11.2015.
 */
public class FlightCalendarServiceMock implements FlightCalendarService {

    @Override
    public List<FlightInfo> getAllFlights() {
        return null;
    }

    @Override
    public List<FlightInfo> getFurtherFlights() {
        return null;
    }

    @Override
    public List<FlightInfo> getPreviousFlights() {
        return null;
    }

    @Override
    public FlightInfo getNextFlight() {
        return null;
    }
}
