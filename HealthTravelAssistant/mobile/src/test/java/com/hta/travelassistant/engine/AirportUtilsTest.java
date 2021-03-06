package com.hta.travelassistant.engine;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AirportUtilsTest {

    @Before
    public void setUp(){
        AirportUtils.init(AirportUtils.class.getClassLoader().getResourceAsStream("iata.tzmap"));
    }


    @Test
    public void testAirportToTZ() throws Exception {
        assertEquals(DateTimeZone.forID("America/Los_Angeles"), AirportUtils.airportToTZ("LAX"));
        assertEquals(DateTimeZone.forID("America/Bogota"), AirportUtils.airportToTZ("ACL"));
    }
}