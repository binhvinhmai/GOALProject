package com.example.binhmai.goalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
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
    Document currentlink = null;

    public MainActivity() throws IOException {
        //Throws an IOException as there is a chance that parsing an HTML webpage can have a bad connection
        //This allows the MainActivity to be ready to handle an IOException if it occurs
    }

    public void updatePoints() {
        //Updates the Points label
        TextView t = (TextView)findViewById(R.id.points_count);
        t.setText(Integer.toString(total_points));
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

        //Get List View
        ListView listView = (ListView) findViewById(R.id.event_listView);
        EventArrayAdapter eventArrayAdapter = new EventArrayAdapter(this, eventArrayList);
        listView.setAdapter(eventArrayAdapter);


        //Add event handler to items on clic
        listView.setOnItem
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
            case R.id.addEventButton: {
                //Create an intent to connect to Calendar
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
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

        //It is inheriting from the AsyncTask because this will be a class that will load in data
        //After the main UI has loaded - if it tries to parse HTML while the UI is loading, it will crash

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String calendarURL = "";
            try {
                doc = Jsoup.connect(url).timeout(10*1000).get(); //Code that may or may not break, depending if the phone has internet connection

                links = doc.select("a[href]");
                //I originally did links.size() but due to the large amount of links, it crashes the program.
                for (int i = 0; i < links.size(); i++) {
                    calendarURL = links.get(i).attr("abs:href");
                    if (calendarURL.startsWith("http://kcparks.org/event/")) {
                        //If it fits the above, it has an event name
                        //Connect to the database
                        //The below will throw an exception if the connection is broken
                        eventArrayList.add(new Event(calendarURL, 25));
                    }
                }
            } catch (IOException e) {
                //Throws an IOException if the URL cannot be retrieved successfully
                e.printStackTrace();
                Log.d("UNABLE", "TO CONNECT");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

