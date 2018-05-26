package com.zhaoheh.livflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskInfoAdapter extends ArrayAdapter<LightTaskInformation> {

    private int mResId;

    public TaskInfoAdapter(Context context, int textViewResId, List<LightTaskInformation> objects) {
        super(context, textViewResId, objects);
        mResId = textViewResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LightTaskInformation info = getItem(position);

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResId, parent, false);
        } else {
            view = convertView;
        }

        TextView taskName = (TextView) view.findViewById(R.id.task_name);
        TextView lastUpdate = (TextView) view.findViewById(R.id.last_update);
        TextView taskLastDate = (TextView) view.findViewById(R.id.last_update_date);
        TextView taskLastTime = (TextView) view.findViewById(R.id.last_update_time);

        taskName.setText(info.getName());
        lastUpdate.setText(info.getLastNodeInfo());
        taskLastDate.setText(info.getLastUpdate().substring(0, 10));
        taskLastTime.setText(info.getLastUpdate().substring(11, 19));

        return  view;
    }
}
