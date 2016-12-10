package com.example.binhmai;

import android.util.Log;

import com.example.binhmai.goalproject.Event;
import com.example.binhmai.goalproject.MainActivity;

import junit.framework.TestCase;

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

public class EventTest extends TestCase {
    //Checks to see if all of the events had properly loaded.

    public MainActivity mMainActivity = new MainActivity();

    Event testEvent = new Event("http://kcparks.org/event/big-12-womens-soccer-championship-2016/", 40);

    public EventTest() throws IOException, ParseException {
        //This must throw an IO Exception as MainActivity may also throw an Exception.
        //The class must throw a ParseException as when it parses a date, it  might throw an error
    }

    @Test
    public void testEvents() {
        Assert.assertNotNull("Event array is not empty", mMainActivity.eventArrayList);
    }

    @Test
    public void testCodeExists() throws ParseException {
        Log.d("EVENT CODE", testEvent.getEventCode());
        Assert.assertEquals("Event code should have been created", testEvent.getEventCode(), "104608");
    }
}