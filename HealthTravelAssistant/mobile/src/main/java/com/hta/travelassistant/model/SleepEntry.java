package com.hta.travelassistant.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class SleepEntry {
  private DateTime startTime;
  private Duration duration;

  public SleepEntry(DateTime startTime, Duration duration) {
    this.startTime = startTime;
    this.duration = duration;
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public Duration getDuration() {
    return duration;
  }
}
