package com.example.justjava.helpatease;

/**
 * Created by hp on 22-02-2018.
 */



import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by hp on 21-02-2018.
 */

public class EventModelAdapter extends RealmBaseAdapter<EventModel> implements ListAdapter {

    private static class ViewHolder {

        TextView textView, textView2, textView3, textView4;
    }

    public EventModelAdapter(OrderedRealmCollection<EventModel> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.eventdisplay, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView5);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView9);
            viewHolder.textView4 = (TextView) convertView.findViewById(R.id.textView10);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView11);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (adapterData != null) {

            EventModel item = adapterData.get(position);
            viewHolder.textView.setText((item.getTitle()));
            viewHolder.textView3.setText((item.getDatefrom()));
            viewHolder.textView4.setText((item.getDateto()));
            viewHolder.textView2.setText(String.valueOf(item.getEid()));
        }

        return convertView;
    }
}