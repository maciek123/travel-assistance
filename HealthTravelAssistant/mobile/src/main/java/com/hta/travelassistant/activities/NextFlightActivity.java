package com.hta.travelassistant.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hta.travelassistant.model.FlightInfo;
import com.hta.travelassistant.services.CalendarScannerService;
import com.hta.travelassistant.services.NotificationService;
import com.hta.travelassistant.services.local.AndroidFlightCalendarService;

import java.util.List;

public class NextFlightActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_flight);

        // Assume thisActivity is the current activity
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        initNextFlight();
    }

    private void initNextFlight() {
        List<FlightInfo> all = AndroidFlightCalendarService.getInstnace(this).getAllFlights();

        ListView listView = (ListView) findViewById(R.id.listView);

        FlightInfoAdapter adapter = new FlightInfoAdapter(this, all);
        listView.setAdapter(adapter);



        startService(new Intent(getBaseContext(), NotificationService.class));
        startService(new Intent(getBaseContext(), CalendarScannerService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    initNextFlight();
                } else {
                    // Permission Denied
                    Toast.makeText(NextFlightActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
