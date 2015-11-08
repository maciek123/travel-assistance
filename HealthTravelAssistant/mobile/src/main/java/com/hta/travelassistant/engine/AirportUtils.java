package com.hta.travelassistant.engine;

import org.joda.time.DateTimeZone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AirportUtils {
    private static Map<String, String> mapping;

    public static DateTimeZone airportToTZ(String airport) {
        if(mapping == null){
            throw new IllegalStateException("Please call first init method");
        }
        return DateTimeZone.forID(mapping.get(airport));
    }

    public static boolean isValidAirportId(String id) {
        if(mapping == null){
            throw new IllegalStateException("Please call first init method");
        }
        return mapping.keySet().contains(id);
    }

    public static void init (InputStream stream) {

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        Map<String, String> m = new HashMap<>();
        try {
            while ((line = in.readLine()) != null) {
                ;
                String[] split = line.split("\\s+");
                m.put(split[0], split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapping = m;
    }

}
