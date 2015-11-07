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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recommendation that = (Recommendation) o;

        if (!startTime.equals(that.startTime)) return false;
        if (!duration.equals(that.duration)) return false;
        return actions.equals(that.actions);

    }

    @Override
    public int hashCode() {
        int result = startTime.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + actions.hashCode();
        return result;
    }
}
