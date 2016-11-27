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


    public static void loadSampleArray() throws ParseException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input
        eventArrayList.clear();
        eventArrayList.add(new Event("Spay and Neuter KC Pet Vaccination Clinic", "2016-10-04", 30));
        eventArrayList.add(new Event("WaterFire", "2016-10-08", 50));
        eventArrayList.add(new Event("Hoots and Howls", "2016-10-08", 40));
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

        /*
        try {
            loadSampleArray();
        } catch (ParseException e) {
            //This must throw a ParseException because there is a good chance that the Event will be unable
            //to parse the Date provided in the string - thus ensuring this doesn't break the program
            e.printStackTrace();
        }*/

        //Get List View
        ListView listView = (ListView) findViewById(R.id.event_listView);
        EventArrayAdapter eventArrayAdapter = new EventArrayAdapter(this, eventArrayList);
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
            Calendar c = Calendar.getInstance();
            String calendarURL = "";
            Elements eventName = null;
            String eventNameString = "";
            Elements eventDate = null;
            String eventDateString = "";
            Date currentEventDate = null;
            int count = 0;
            try {
                doc = Jsoup.connect(url).timeout(10*1000).get(); //Code that may or may not break, depending if the phone has internet connection

                links = doc.select("a[href]");
                //I originally did links.size() but due to the large amount of links, it crashes the program.
                for (int i = 0; i < links.size(); i++) {
                    calendarURL = links.get(i).attr("abs:href");
                    if (calendarURL.startsWith("http://kcparks.org/event/")) {
                        Log.d("URL", calendarURL);
                        //If it fits the above, it has an event name
                        //Connect to the database
                        //The below will throw an exception if the connection is broken
                        currentlink = Jsoup.connect(calendarURL).timeout(10*1000).get();

                        //Extract data from the current link
                        //Extract the name of the event
                        eventName = currentlink.select("h1");
                        eventNameString = Jsoup.parse(String.valueOf(eventName.get(0))).text();
                        eventDate = currentlink.select("time");
                        eventDateString = eventDate.attr("datetime");

                        //For now each new event will be worth 25 points for attending
                        //Currently each event code will be determined by a basic number - eventually, I will come up with an algorithm to encrypt them
                        try {
                            //This may throw an error as this event, if incorrectly parsed or if there is a bad connection, may throw a ParseException
                            //Checked ParseException
                            eventArrayList.add(new Event(eventNameString, eventDateString, 25));
                        } catch (ParseException e) {
                            e.printStackTrace(); //Print the error if it's an issue
                        }

                        /*
                        if (count == 10) {
                            break;
                        }
                        else {
                            count+=1;
                        }*/
                    }
                }
            } catch (IOException e) {
                //Throws an IOException if the URL cannot be retrieved successfully
                e.printStackTrace();
                Log.d("UNABLE", "TO CONNECT");
            }

            return null;
        }
    }
}

