package com.hta.travelassistant.services.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hta.travelassistant.activities.R;
import com.hta.travelassistant.engine.AirportUtils;
import com.hta.travelassistant.model.FlightInfo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of FlightCalendarService for the default Android calendar
 */
public class AndroidFlightCalendarService implements FlightCalendarService {

    public static final String[] EVENT_INSTANCE_PROJECTION = new String[]{
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.EVENT_LOCATION,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END
    };


    // The indices for the projection array above.
    private static final int INSTANCE_PROJECTION_ID = 0;
    private static final int INSTANCE_PROJECTION_TITLE = 1;
    private static final int INSTANCE_PROJECTION_LOCATION = 2;
    private static final int INSTANCE_PROJECTION_DESCRIPTION = 3;
    private static final int INSTANCE_PROJECTION_BEGIN = 4;
    private static final int INSTANCE_PROJECTION_END = 5;

    //  range defines how many years before and after from today we will request for getAllFlights
    public static final int DEFAULT_DATE_RANGE = 5;

    //  Ii has to be provided from Activity or Service
    private final Context activity;

    private AndroidFlightCalendarService(Context activity) {
        this.activity = activity;
        AirportUtils.init(activity.getResources().openRawResource(R.raw.iata));
    }

    public static FlightCalendarService getInstnace(Context activity) {
        return new AndroidFlightCalendarService(activity);
    }

    @Override
    public List<FlightInfo> getAllFlights() {
        DateTime dateTo = DateTime.now().plusYears(DEFAULT_DATE_RANGE);
        DateTime dateFrom = DateTime.now().minusYears(DEFAULT_DATE_RANGE);
        return getFlightInfo(dateFrom, dateTo);
    }

    @Override
    public List<FlightInfo> getFurtherFlights() {
        return getFlightInfo(DateTime.now(), DateTime.now().plusYears(DEFAULT_DATE_RANGE));
    }

    @Override
    public List<FlightInfo> getPreviousFlights() {
        return getFlightInfo(DateTime.now().minusYears(DEFAULT_DATE_RANGE), DateTime.now());
    }

    @Override
    public FlightInfo getNextFlight() {
        List<FlightInfo> futureFlights = getFurtherFlights();
        if (futureFlights != null && !futureFlights.isEmpty()) {
            return futureFlights.get(0);
        }
        return null;
    }

    private List<FlightInfo> getFlightInfo(DateTime from, DateTime to) {
        try {
            ContentResolver cr = activity.getContentResolver();
            long yearAgo = from.getMillis();
            long yearFromNow = to.getMillis();
            Uri uri = Uri.parse("content://com.android.calendar/instances/when/" + yearAgo + "/" + yearFromNow);
            // We are looking for titles contain 'flight' word
            String[] TITLE_FLIGHT_PATTERNS = new String[]{"%flight%"};
            Cursor cur = cr.query(uri, EVENT_INSTANCE_PROJECTION, "(title like ?)", TITLE_FLIGHT_PATTERNS, null);
            return parseEventCursor(cur);
        } catch (Exception e) {
            throw new RuntimeException("Can not read data from calendar", e);
        }

    }

    private List<FlightInfo> parseEventCursor(Cursor cur) {
        if (cur == null) {
            return Collections.EMPTY_LIST;
        }

        List<FlightInfo> result = new ArrayList<>(cur.getCount());
        while (cur.moveToNext()) {
            //  TODO: implement title/description parser for destination airport, or request external service?
            String title = cur.getString(INSTANCE_PROJECTION_TITLE);
            String srcAirport = cur.getString(INSTANCE_PROJECTION_LOCATION);
            String description = cur.getString(INSTANCE_PROJECTION_DESCRIPTION);
            String dstAirport = FlightDetailsService.getInstance().getDestinationAirport(description);
            long from = cur.getLong(INSTANCE_PROJECTION_BEGIN);
            long to = cur.getLong(INSTANCE_PROJECTION_END);
            DateTime startTime = new DateTime(from);
            Duration duration = Duration.millis(to - from);
            if(dstAirport != null && srcAirport != null && duration != null) {
                int offset = calculateOffset(srcAirport, dstAirport, duration, startTime);
                result.add(new FlightInfo(srcAirport, dstAirport, startTime, duration, offset));
                Log.d("Process flight event", "Process flight event: " + title);
            }else{
                Log.w("Skip calendar event","Missing information about the flight in the event: " + title);
            }
        }
        return result;
    }


    private static int calculateOffset(String from, String to, Duration duration, DateTime startTime) {
        DateTimeZone fromTZ = AirportUtils.airportToTZ(from);
        DateTimeZone toTZ = AirportUtils.airportToTZ(to);
        DateTime arrivalTime = startTime.plus(duration);
        int offsetMillis = fromTZ.getOffset(startTime) - toTZ.getOffset(arrivalTime);
        return offsetMillis / (1000 * 60 * 60);
    }

}
