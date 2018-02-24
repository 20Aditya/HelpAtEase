package com.example.justjava.helpatease;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 25-02-2018.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, ArrayList<Event> events){
        super(context,0,events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listitemview = convertView;

        if(listitemview==null){
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.eventdisplay,parent,false);
        }

        Event currentevent = getItem(position);

        TextView textView = (TextView) listitemview.findViewById(R.id.textView5);
        textView.setText(currentevent.getTitle());

        TextView textView3 = (TextView) listitemview.findViewById(R.id.textView9);
        textView3.setText(currentevent.getDatefrom());

        TextView textView4 = (TextView) listitemview.findViewById(R.id.textView10);
        textView4.setText(currentevent.getDateto());

        return  listitemview;
    }
}
