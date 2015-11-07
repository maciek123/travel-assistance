package com.hta.travelassistant.services.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hta.travelassistant.model.FlightInfo;

import org.joda.time.DateTime;
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
    private final AppCompatActivity activity;

    private AndroidFlightCalendarService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static FlightCalendarService getInstnace(AppCompatActivity activity) {
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
        if(futureFlights != null && !futureFlights.isEmpty()){
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
        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            String title = cur.getString(INSTANCE_PROJECTION_TITLE);
            Log.d("Process flight event", "Process flight event: " + title);
            String srcAirport = "LAX";
            String dstAirport = "ZHR";
            String from = cur.getString(INSTANCE_PROJECTION_BEGIN);
            String to = cur.getString(INSTANCE_PROJECTION_END);
            DateTime startTime = DateTime.now();
            Duration duration = Duration.standardHours(12);
            result.add(new FlightInfo(srcAirport, dstAirport, startTime, duration));

        }
        return result;
    }
}
