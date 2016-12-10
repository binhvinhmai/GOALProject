package com.example.binhmai.goalproject;

import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ProxySelector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Created by binhmai on 9/18/16.
 */


public class Event {
    private String eventName;
    private String eventDayofWeek;
    private int eventPoints;
    private String eventCode;
    private int maxVisits = 1;
    private Date eventDate;
    private String eventDateString;
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    /*
    public Event(String n, String dateTime, int p, ) throws ParseException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input

        //Date formats for the events

        SimpleDateFormat eventDayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);

        //Set variables
        eventName = n;
        eventDate = eventDateFormat.parse(dateTime);
        eventDateString = eventDateFormat.format(eventDate);
        eventDayofWeek = eventDayFormat.format(eventDate);
        eventPoints = p;
        createEventCode();
    }*/


    public Event(String calendarURL, int p) throws ParseException, IOException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input

        Document currentlink = Jsoup.connect(calendarURL).timeout(10 * 1000).get();

        //Extract data from the current link
        //Extract the name of the event
        Elements eventNameLink = currentlink.select("h1");
        Elements eventDateLink = currentlink.select("time");

        //Date formats for the events
        SimpleDateFormat eventDayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);

        eventName= Jsoup.parse(String.valueOf(eventNameLink.get(0))).text();
        eventDate = eventDateFormat.parse(eventDateLink.attr("datetime"));
        eventDateString = eventDateFormat.format(eventDate);
        eventDayofWeek = eventDayFormat.format(eventDate);
        eventPoints = p;
        createEventCode();
        Log.d("DAY", eventDayofWeek);
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

    public String getEventName() { return eventName; }

    public String getEventDate() { return eventDateString; }

    public String getEventDayandDate() { return eventDayofWeek + " " + eventDateString; }

    public String getEventCode() {
        return eventCode;
    }

    public int getEventPoints() {
        return eventPoints;
    }

    public void createEventCode() {
        //Take the event month and day
        //Mix it with the first two letters of the event
        //Change the first two letters to their respective numbers eventCode
        eventCode = eventDateString.substring(5,7) + string_to_number(eventName.charAt(0)) + string_to_number(eventName.charAt(1)) + eventDateString.substring(8);
    }

    @Override
    public String toString() {
        //Allows this to be printed out via Array Element
        return eventName;
    }

    public static String string_to_number(char ch) {
        // Function which turns a letter into it's respective number
        String number = "";
        char phone_char = Character.toUpperCase(ch);

        switch(phone_char) {
            case 'A' :
            case 'B' :
            case 'C' :
                number = "2";
                break;
            case 'D' :
            case 'E' :
            case 'F' :
                number = "3";
                break;
            case 'G' :
            case 'H' :
            case 'I' :
                number = "4";
                break;
            case 'J' :
            case 'K' :
            case 'L' :
                number = "5";
                break;
            case 'M' :
            case 'N' :
            case 'O' :
                number = "6";
                break;
            case 'P' :
            case 'Q' :
            case 'R' :
            case 'S' :
                number = "7";
                break;
            case 'T' :
            case 'U' :
            case 'V' :
                number = "8";
                break;
            case 'W' :
            case 'X' :
            case 'Y' :
            case 'Z' :
                number = "9";
                break;
        }

        return number;

    }

    public void addToCalenadr() {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, getEventName());
    }
}
