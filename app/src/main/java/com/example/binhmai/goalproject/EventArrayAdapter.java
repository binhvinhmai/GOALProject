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
            //viewHolder.itemView = (TextView) convertView.findViewById(R.id.eventItem);
            //convertView.setTag(viewHolder);
        }
        else {
            //viewHolder = (ViewHolder) convertView.getTag();
        }
        TextView eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
        //TextView eventLocation = (TextView) convertView.findViewById(R.id.eventLocation);
        TextView eventDate = (TextView) convertView.findViewById(R.id.eventDate);

        eventTitle.setText(item.getEventName());
        eventDate.setText(item.getEventDate());

        /*
        if (item!=null) {
            //The layout only has one ListView
            viewHolder.itemView.setText(String.format(item.toString()));
        }*/

        return convertView;
    }

}
