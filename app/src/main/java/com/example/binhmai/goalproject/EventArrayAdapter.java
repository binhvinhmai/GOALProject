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
    private static class ViewHolder {
        private TextView itemView;
    }

    public EventArrayAdapter(Context context, int textViewResourceID, ArrayList<Event> items) {
        //Constructor
        super(context, textViewResourceID, items);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_main, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.eventItem);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event item = getItem(position);
        if (item!=null) {
            //The layout only has one ListView
            viewHolder.itemView.setText(String.format(item.toString()));
        }

        return convertView;
    }

}
