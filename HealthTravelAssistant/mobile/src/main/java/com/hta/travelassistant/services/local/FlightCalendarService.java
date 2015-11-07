package com.hta.travelassistant.services.local;

import com.hta.travelassistant.model.FlightInfo;

import java.util.List;

/**
 * Created by kacper.zielinski on 07.11.2015.
 */
public interface FlightCalendarService {

    List<FlightInfo> getAllFlights();

    List<FlightInfo> getFurtherFlights();

    List<FlightInfo> getPreviousFlights();

    FlightInfo getNextFlight();

}
