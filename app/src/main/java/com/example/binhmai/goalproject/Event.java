package com.example.binhmai.goalproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by binhmai on 9/18/16.
 */
public class Event {
    private String eventName;
    private int eventPoints;
    private long eventCode;
    private int maxVisits = 1;
    private SimpleDateFormat eventDateFormat = new SimpleDateFormat("EEE mm/dd/yyyy HH:MM", Locale.ENGLISH);
    private Date eventDate;


    public Event(String n, String dateTime, int p, int c) throws ParseException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input
        eventName = n;
        eventDate = eventDateFormat.parse(dateTime);
        eventPoints = p;
        eventCode = c;
    }

    //To prevent users from abusing event codes, provide that they only get to visit once
    private int checkVisits() {
        if (maxVisits == 1) {
            maxVisits--; //Decrement
            return eventPoints;
        }
        else {
            return 0;
        }
    }

    public long getEventCode() {
        return eventCode;
    }

    public int getEventPoints() {
        return eventPoints;
    }

    @Override
    public String toString() {
        //Allows this to be printed out via Array Element
        return eventName;
    }
}
