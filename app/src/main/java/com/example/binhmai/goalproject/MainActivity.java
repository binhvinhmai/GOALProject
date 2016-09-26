package com.example.binhmai.goalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GOAL Project 1.0";
    private static final String PREFS_NAME = "PrefsFile";
    private static final String TOTALPOINTS = "TotalPoints";

    private int total_points = 0;
    private int monthly_points = 0;

    private void updatePoints() {
        TextView t = (TextView)findViewById(R.id.points_count);
        t.setText(Integer.toString(total_points));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting onCreate Function");
        setContentView(R.layout.activity_main);

        //Add points to button
        View AddPoints = findViewById(R.id.points_button);
        AddPoints.setOnClickListener(this);

        //Get shared preferences file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        total_points = settings.getInt(TOTALPOINTS,0); //Get int from shared prefs - if none found, 0

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

    //Once I get access to database of events, create event listview from database
}

