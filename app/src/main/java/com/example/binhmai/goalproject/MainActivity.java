package com.example.binhmai.goalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Overall the design of MainActivity is very poor - it's doing too many things at once
    //It's both retrieving HTML and then loading the UI
    //However, in order to retrieve the HTML, the Jsoup class must be in the same Main Activity, so it was unavoidable

    //Shared Preferences variables
    private static final String TAG = "GOAL Project 1.0";
    private static final String PREFS_NAME = "PrefsFile";
    private static final String TOTALPOINTS = "TotalPoints";

    public static int total_points = 0;
    public static int monthly_points = 0;

    //To hold events
    public static ArrayList<Event> eventArrayList = new ArrayList();

    //Web Page Information
    Elements links = null;
    String url = "http://kcparks.org/calendar/";
    Document doc = null;

    public MainActivity() throws IOException {
        //Throws an IOException as there is a chance that parsing an HTML webpage can have a bad connection
        //This allows the MainActivity to be ready to handle an IOException if it occurs
    }

    public void updatePoints() {
        //Updates the Points label
        TextView t = (TextView)findViewById(R.id.points_count);
        t.setText(Integer.toString(total_points));
    }

    public static void loadSampleArray() throws ParseException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input
        eventArrayList.add(new Event("Spay and Neuter KC Pet Vaccination Clinic", "Tue 10/04/2016 05:00", 30, 1040));
        eventArrayList.add(new Event("WaterFire", "Sat 10/08/2016 7:00", 50, 1080));
        eventArrayList.add(new Event("Hoots and Howls", "Sat 10/08/2016 10:00", 40, 1008));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting onCreate Function");
        setContentView(R.layout.activity_main);
        new WebParserLinks().execute();

        //Add points to button
        View AddPoints = findViewById(R.id.points_button);
        AddPoints.setOnClickListener(this);

        //Get shared preferences file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        total_points = settings.getInt(TOTALPOINTS,0); //Get int from shared prefs - if none found, 0

        try {
            loadSampleArray();
        } catch (ParseException e) {
            //This must throw a ParseException because there is a good chance that the Event will be unable
            //to parse the Date provided in the string - thus ensuring this doesn't break the program
            e.printStackTrace();
        }

        //Get List View
        EventArrayAdapter eventArrayAdapter = new EventArrayAdapter(this,R.layout.activity_main, eventArrayList);
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_main, eventArrayList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(eventArrayAdapter);

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

    private class WebParserLinks extends AsyncTask<Void, Void, Void> {

        //As previously mentioned, this addition of a class within the MainActivity makes this design poor
        //However, it was a necessary 'evil' as I was unable to have this class separate without it messing
        //up code and crashing the program - and placing it within the MainActivity allows it to have direct
        //access to the variables anyways.

        //It is inheriting from the AsyncTask because this will be a class that will load in data
        //After the main UI has loaded - if it tries to parse HTML while the UI is loading, it will crash

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String temp = "";
            try {
                doc = Jsoup.connect(url).get(); //Code that may or may not break, depending if the phone has internet connection

                links = doc.select("a[href]");
                for (int i = 0; i < links.size(); i++) {
                    temp = String.valueOf(links.get(i));
                    System.out.println(links.get(i).text());
                }

            } catch (IOException e) {
                //Throws an IOException if the URL cannot be retrieved successfully
                e.printStackTrace();
            }
            return null;
        }
    }
}

