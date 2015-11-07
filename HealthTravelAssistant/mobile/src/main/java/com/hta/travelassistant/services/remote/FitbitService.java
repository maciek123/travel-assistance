package com.hta.travelassistant.services.remote;

import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.model.SleepEntry;
import com.hta.travelassistant.model.SleepSummary;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Service maps the fitbit API to internal interfaces
 *
 * Fitbit details:
 *

 Resource Path Options
 sleep/startTime
 sleep/timeInBed
 sleep/minutesAsleep
 sleep/awakeningsCount
 sleep/minutesAwake
 sleep/minutesToFallAsleep
 sleep/minutesAfterWakeup
 sleep/efficiency
 Example Requests
 */
public interface FitbitService {

    /**
     * <p/>
     * 2) GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[base-date]/[end-date].json
     * <p/>
     * api-version	The API version. Currently version 1.
     * user-id	The encoded ID of the user. Use "-" (dash) for current logged-in user.
     * resource-path	The resource path; see the Resource Path Options below for a list of options.
     * base-date	The range start date, in the format yyyy-MM-dd or today.
     * end-date	The end date of the range.
     * Resource Path Options
     *
     * @param from
     * @param to
     * @return
     */
    List<SleepEntry> getSleepEntries(DateTime from, DateTime to);


    /**
     * 1) GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[date]/[period].json
     * <p/>
     * api-version	The API version. Currently version 1.
     * user-id	The encoded ID of the user. Use "-" (dash) for current logged-in user.
     * resource-path	The resource path; see the Resource Path Options below for a list of options.
     * date	The end date of the period specified in the format yyyy-MM-dd or today.
     * period	The range for which data will be returned. Options are 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
     *
     * @param from - date from
     * @param period Options are 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
     * @return
     */
    List<SleepEntry> getSleepEntries(DateTime from, String period);

    /**
     * Returns sleep summary (Sum of particular fields) for given period.
     * @param from
     * @param to
     * @return
     */
    SleepSummary getSleepSummary(DateTime from, DateTime to);


}
