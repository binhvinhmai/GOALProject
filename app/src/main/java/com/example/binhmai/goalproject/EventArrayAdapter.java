package com.example.binhmai.goalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by binhmai on 10/23/16.
 */

public class EventArrayAdapter extends ArrayAdapter<Event> {
    private final ArrayList<Event> list;
    //Custom ArrayAdapter class specifically to handle placing items into the Listview

    public EventArrayAdapter(Context context, ArrayList<Event> items) {
        //Constructor
        super(context, 0, items);
        this.list = items;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        Event item = getItem(position);

        if (convertView == null) {
            //Do something here with activity_main instead
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        //Fill in data
        TextView eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
        TextView eventDate = (TextView) convertView.findViewById(R.id.eventDate);
        eventTitle.setText(item.getEventName());
        eventDate.setText(item.getEventDayandDate());

        //Handle buttons and add onClickListeners
        Button addEventButton = (Button) convertView.findViewById(R.id.addEventButton);
        addEventButton.setTag(position);
        //The following will check if the eventDate field is null or empty
        //If it is, something has gone terribly wrong and the code will throw an Assertion
        if (eventDate == null || eventDate.getText().equals("")) {
            //Assert that the event date cannot be blank
            assert item.getEventDate() != "" : "The date was sent in incorrectly - this should not be blank!";
        }

        /*

        addEventButton.setOnClickListener(new View.OnClickListener() {

            private int position;

            @Override
            public void onClick(View v) {
                //Get the current position

                //Create calendar

            }
        });
        */

        return convertView;
    }

}
