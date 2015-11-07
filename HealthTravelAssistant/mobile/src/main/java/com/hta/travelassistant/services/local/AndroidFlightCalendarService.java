package com.hta.travelassistant.services.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.hta.travelassistant.model.FlightInfo;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: add description...
 */
public class AndroidFlightCalendarService implements FlightCalendarService {

    // Projection array. Creating indices for this array instead of doing dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.STATUS,

    };

    public static final String[] EVENT_INSTANCE_PROJECTION = new String[]{
//            CalendarContract.Instances.EVENT_ID,
//            CalendarContract.Instances.EVENT_LOCATION,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.END_DAY
    };



    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    //  Ii has to be provided from Activity or Service
    private final ContentResolver contentResolver;

    private AndroidFlightCalendarService(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static FlightCalendarService getInstnace(ContentResolver contentResolver){
        return new AndroidFlightCalendarService(contentResolver);
    }

    @Override
    public List<FlightInfo> getAllFlights() {

        // Run query
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;

/*        String selection = "((" + CalendarContract.Instances.TITLE + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";

        String[] selectionArgs = new String[]{"sampleuser@gmail.com", "com.google",
                "sampleuser@gmail.com"};*/

        Cursor cur = null;
        try {
            cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseCursor(cur);
    }

    @Override
    public List<FlightInfo> getFurtherFlights() {
        return null;
    }

    @Override
    public List<FlightInfo> getPreviousFlights() {
        return null;
    }

    @Override
    public FlightInfo getNextFlight() {
        return null;
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    private List<FlightInfo> parseCursor(Cursor cur) {
        if(cur == null){
            return Collections.EMPTY_LIST;
        }

        List<FlightInfo> result = new ArrayList<>(cur.getCount());
        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            String srcAirport = "LAX";
            String dstAirport = "ZHR";
            DateTime startTime = DateTime.now();
            Duration duration = Duration.standardHours(12);
            result.add(new FlightInfo(srcAirport, dstAirport, startTime, duration));
        }
        return result;
    }
}
