package com.example.binhmai.goalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GOAL Project 1.0";
    private static final String PREFS_NAME = "PrefsFile";
    private static final String TOTALPOINTS = "TotalPoints";

    public static int total_points = 0;
    public static int monthly_points = 0;

    public static ArrayList<Event> eventArrayList = new ArrayList();

    public void updatePoints() {
        TextView t = (TextView)findViewById(R.id.points_count);
        t.setText(Integer.toString(total_points));
    }

    //Parse web page for HTML - will not be implemented at this time
    public static void loadSampleArray() throws ParseException {
        eventArrayList.add(new Event("Spay and Neuter KC Pet Vaccination Clinic", "Tue 10/04/2016 05:00", 30, 1040));
        eventArrayList.add(new Event("WaterFire", "Sat 10/08/2016 7:00", 50, 1080));
        eventArrayList.add(new Event("Hoots and Howls", "Sat 10/08/2016 10:00", 40, 1008));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting onCreate Function");
        Log.d("TOTAL", Integer.toString(total_points));
        setContentView(R.layout.activity_main);

        //Add points to button
        View AddPoints = findViewById(R.id.points_button);
        AddPoints.setOnClickListener(this);

        //Get shared preferences file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        total_points = settings.getInt(TOTALPOINTS,0); //Get int from shared prefs - if none found, 0

        try {
            loadSampleArray();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        updatePoints();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePoints();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.points_button: {
                // Open up new activity to get new points
                Intent intent = new Intent(this, GetPointsActivity.class);
                startActivity(intent);
                break;
            }
        }
        updatePoints();
    }

    public static void addPoints(int points) {
        total_points += points;
        monthly_points += points;
    }

    //Once I get access to database of events, create event listview from database
}

