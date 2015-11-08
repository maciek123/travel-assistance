package com.hta.travelassistant.model;

import com.hta.travelassistant.engine.AirportUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

public class FlightInfo {
    private String from;
    private String to;
    private DateTime startTime;
    private Duration duration;

    private String flightNo;
    private String terminal;
    private int offset = -1;


    public FlightInfo(String from, String to, DateTime startTime, Duration duration, int offset) {
        this.from = from;
        this.to = to;
        this.startTime = startTime;
        this.duration = duration;
        this.offset = offset;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getOffset() {
        return offset;
    }

}

