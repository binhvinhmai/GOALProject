package com.example.binhmai.goalproject;

import android.content.Context;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
/**
 * Created by binhmai on 9/18/16.
 */


public class Event {
    private String eventName;
    private String eventDayofWeek;
    private String tempDateString;
    private String eventCalendarURL;
    private String eventCode;
    private String eventDateString;
    private int eventPoints;
    private int maxVisits = 1;
    private Date creationDate; //The creation date that's listed in the URL is actually when the event was created - not the actual date
    private Date eventDate;
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    public Event(String calendarURL, int p) throws ParseException, IOException {
        //This function throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input

        eventCalendarURL = calendarURL;
        Document currentLink = Jsoup.connect(calendarURL).timeout(10 * 1000).get();

        //Extract data from the current link
        Elements eventNameLink = currentLink.select("h1");
        Elements DateLink = currentLink.select("time");

        //Date formats for the events
        SimpleDateFormat eventDayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);

        //Start getting the correct and necessary data from the link
        eventName= Jsoup.parse(String.valueOf(eventNameLink.get(0))).text();

        //Use the data below to create a unique event code
        creationDate = eventDateFormat.parse(DateLink.attr("datetime"));
        tempDateString = eventDateFormat.format(creationDate);
        createEventCode();

        tempDateString = DateLink.html();
        eventDate = eventDateFormat.parse(createDate(tempDateString));
        eventDateString = eventDateFormat.format(eventDate);
        eventDayofWeek = eventDayFormat.format(eventDate);
        eventPoints = p;
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

    public String getEventDayandDate() { return eventDayofWeek + " " + eventDateString + " code: " + eventCode; }

    public int getEventCode() {
        return Integer.parseInt(eventCode);
    }

    public int getEventPoints() {
        return eventPoints;
    }

    public String returnURL() {
        return eventCalendarURL;
    }

    public void createEventCode() {
        //Take the event month and day
        //Mix it with the first two letters of the event
        //Change the first two letters to their respective numbers eventCode
        eventCode = tempDateString.substring(5,7) + string_to_number(eventName.charAt(0)) + string_to_number(eventName.charAt(1)) + tempDateString.substring(8);
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

    public String createDate(String longDateString) throws ParseException {
        //Takes in a parameter that's like January 1
        String[] dateArray = longDateString.trim().split(" ");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("MMM").parse(dateArray[0]));

        String monthFormat = String.format("%02d", (cal.get(Calendar.MONTH) + 1));
        String dayFormat = String.format("%02d",Integer.parseInt(dateArray[1]));
        String yearFormat = String.valueOf(cal.getInstance().get(Calendar.YEAR));

        return (yearFormat + "-" + monthFormat + "-" + dayFormat);
    }

}

