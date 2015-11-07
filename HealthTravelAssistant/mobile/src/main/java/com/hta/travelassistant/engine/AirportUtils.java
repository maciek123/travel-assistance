package com.hta.travelassistant.engine;

import org.joda.time.DateTimeZone;

public class AirportUtils {
    public static DateTimeZone airportToTZ(String airport){
        return  DateTimeZone.UTC;
    }
}
