package com.hta.travelassistant.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;

public class Period {
  private DateTime startTime;
  private Duration duration;
  private Iterable<Action> recommendations;

  public Period(DateTime startTime, Duration duration, Iterable<Action> recommendations) {
    this.startTime = startTime;
    this.duration = duration;
    this.recommendations = recommendations;
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public Duration getDuration() {
    return duration;
  }

  public Iterable<Action> getRecommendations() {
    return recommendations;
  }
}
