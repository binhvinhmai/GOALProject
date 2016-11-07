package com.example.binhmai.goalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by binhmai on 10/23/16.
 */

public class EventArrayAdapter extends ArrayAdapter<Event> {
    //Custom ArrayAdapter class specifically to handle placing items into the Listview

    public EventArrayAdapter(Context context, ArrayList<Event> items) {
        //Constructor
        super(context, 0, items);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        Event item = getItem(position);

        if (convertView == null) {
            //Do something here with activity_main instead
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        TextView eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
        TextView eventDate = (TextView) convertView.findViewById(R.id.eventDate);

        eventTitle.setText(item.getEventName());
        eventDate.setText(item.getEventDate());

        //The following will check if the eventDate field is null or empty
        //If it is, something has gone terribly wrong and the code will throw an Assertion
        if (eventDate == null || eventDate.getText().equals("")) {
            //Assert that the event date cannot be blank
            assert item.getEventDate() != "" : "The date was sent in incorrectly - this should not be blank!";
        }

        return convertView;
    }

}
