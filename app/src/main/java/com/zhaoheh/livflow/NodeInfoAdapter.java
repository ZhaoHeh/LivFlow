package com.zhaoheh.livflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NodeInfoAdapter extends ArrayAdapter<TaskNodeData> {

    private int mResId;

    public NodeInfoAdapter(Context context, int textViewResId, List<TaskNodeData> objects) {
        super(context, textViewResId, objects);
        mResId = textViewResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskNodeData info = getItem(position);

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResId, parent, false);
        } else {
            view = convertView;
        }

        TextView nodeName = (TextView) view.findViewById(R.id.node_name);
        TextView nodeContent = (TextView) view.findViewById(R.id.node_content);
        TextView createdTime = (TextView) view.findViewById(R.id.node_created_time);

        nodeName.setText(String.valueOf(info.getSerialNum()));
        nodeContent.setText(info.getContent());
        createdTime.setText(info.getCreatedTime());

        return  view;
    }
}
