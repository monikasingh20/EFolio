package com.deepbench.eventfolio.myproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tarun on 15/11/17.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.event_item, parent, false);
        }
        TextView eventTitle = (TextView) convertView.findViewById(R.id.title);
        TextView eventHost = (TextView) convertView.findViewById(R.id.host);
        TextView eventVenue = (TextView) convertView.findViewById(R.id.venue);
        TextView eventDate = (TextView) convertView.findViewById(R.id.date);
        TextView eventTime = (TextView) convertView.findViewById(R.id.time);
        TextView eventDes = (TextView) convertView.findViewById(R.id.description);
        TextView eventICount = (TextView) convertView.findViewById(R.id.i_count);
        ImageView eventIIcon = (ImageView) convertView.findViewById(R.id.i_icon);
        ImageView eventCIcon = (ImageView) convertView.findViewById(R.id.c_icon);

        Event event = getItem(position);

        eventTitle.setText(event.getEventTitle());
        eventHost.setText(event.getUserName());
        eventVenue.setText(event.getVenueName());
        eventDate.setText(event.getEventDate());
        eventTime.setText(event.getEventStartTime());
        eventDes.setText(event.getEventDescription());
        eventICount.setText(String.valueOf(event.getInterested()));


        return convertView;
    }
}

