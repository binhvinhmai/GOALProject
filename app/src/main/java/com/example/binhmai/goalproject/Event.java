package com.example.binhmai.goalproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import java.util.Calendar;


/**
 * Created by binhmai on 9/18/16.
 */
public class Event {
    private string eventName;
    private int eventPoints;
    private int eventCode;
    private int maxVisits = 1;

    //Create a constructor - am unsure of what data will be coming from KC Parks and Rec database

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

}
