package com.example.binhmai.goalproject;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by binhmai on 10/23/16.
 */

public class EventArrayAdapter extends ArrayAdapter<Event> {
    private final ArrayList<Event> list;
    Context context;
    //Custom ArrayAdapter class specifically to handle placing items into the Listview

    public EventArrayAdapter(Context context, ArrayList<Event> items) {
        //Constructor
        super(context, 0, items);
        this.list = items;
        this.context = context;

    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        final Event item = getItem(position);

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
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CALENDAR", item.returnURL());


                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, item.getEventName());

                //Parse the string from YYYY-mm-dd format and into the Gregorian Calendar
                GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(item.getEventDate().substring(0,4)), Integer.parseInt(item.getEventDate().substring(5,7)), Integer.parseInt(item.getEventDate().substring(8)));

                //KC Parks and Rec does not give the exact start and end times for their events in their web page consistently - thus I will make it an all day event
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
                context.startActivity(calIntent);
            }
        });
        //The following will check if the eventDate field is null or empty
        //If it is, something has gone terribly wrong and the code will throw an Assertion
        if (eventDate == null || eventDate.getText().equals("")) {
            //Assert that the event date cannot be blank
            assert item.getEventDate() != "" : "The date was sent in incorrectly - this should not be blank!";
        }

        return convertView;
    }
}
