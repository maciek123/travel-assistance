package com.hta.travelassistant.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Recommendation {
  private DateTime startTime;
  private Duration duration;
  private Iterable<Action> actions;

  public Recommendation(DateTime startTime, Duration duration, Iterable<Action> actions) {
    this.startTime = startTime;
    this.duration = duration;
    this.actions = actions;
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public Duration getDuration() {
    return duration;
  }

  public Iterable<Action> getActions() {
    return actions;
  }

  @Override
  public String toString() {
    return "Recommendation{" +
            "startTime=" + startTime +
            ", duration=" + duration +
            ", actions=" + actions +
            '}';
  }
}
