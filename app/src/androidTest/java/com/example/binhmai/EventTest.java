package com.example.binhmai.goalproject;

import android.util.Log;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by binhmai on 11/16/16.
 */

public class EventTest {
    //Checks to see if all of the events had properly loaded.

    public MainActivity mMainActivity = new MainActivity();
    Event testEvent = new Event("Hoots and Howls", "2016-10-08", 40);

    public EventTest() throws IOException, ParseException {
        //This must throw an IO Exception as MainActivity may also throw an Exception.
        //The class must throw a ParseException as when it parses a date, it  might throw an error
        checkEvents();
        checkCodeExists();
    }

    @Test
    public void checkEvents() {
        Assert.assertNotNull("Event array is not empty", mMainActivity.eventArrayList);
    }

    @Test
    public void checkCodeExists() throws ParseException {
        Log.d("EVENT CODE", testEvent.getEventCode());
        Assert.assertEquals("Event code should have been created", testEvent.getEventCode(), "104608");
    }
}