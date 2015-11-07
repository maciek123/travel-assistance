package jetlag;

import org.joda.time.DateTime;
import org.joda.time.Duration;


public class FlightInfo {
  private String from;
  private String to;
  private DateTime startTime;
  private Duration duration;

  public FlightInfo(String from, String to, DateTime startTime, Duration duration) {
    this.from = from;
    this.to = to;
    this.startTime = startTime;
    this.duration = duration;
  }

  public DateTime getStartTime() {
    return startTime;
  }
}
