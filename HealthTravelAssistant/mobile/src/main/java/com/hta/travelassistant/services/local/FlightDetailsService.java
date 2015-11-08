package com.hta.travelassistant.services.local;

import com.hta.travelassistant.engine.AirportUtils;
import com.hta.travelassistant.model.FlightInfo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.StringTokenizer;

/**
 * Simple text parser for getting flight info from title and description.
 */
public class FlightDetailsService {
    private static FlightDetailsService ourInstance = new FlightDetailsService();

    public static FlightDetailsService getInstance() {
        return ourInstance;
    }

    private final static String DST_AIRPORT_PATTERN = "DESTINATION";
    private final static String FLIGHT_NO_PATTERN = "REF";

    private FlightDetailsService() {
    }

    public String getDestinationAirport(String text) {
        return parseByPattern(text, DST_AIRPORT_PATTERN);
    }

    private String parseByPattern(String text, String dstAirportPattern) {
        if (text == null) {
            return null;
        }
        //  split by spaces
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        while(tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            if(dstAirportPattern.equalsIgnoreCase(token) && tokenizer.hasMoreTokens()){
                return tokenizer.nextToken();
            }
        }
        return null;
    }

    public String getFlightNumber(String text) {
        return parseByPattern(text, FLIGHT_NO_PATTERN);
    }
}
