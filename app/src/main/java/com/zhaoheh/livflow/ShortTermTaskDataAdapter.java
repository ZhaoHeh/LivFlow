package com.zhaoheh.livflow;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShortTermTaskDataAdapter extends
        RecyclerView.Adapter<ShortTermTaskDataAdapter.ViewHolder> {

    private List<ShortTermTaskData> mData;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View mParent;
        TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mParent = v;
            mTextView = (TextView) v.findViewById(R.id.task_content_for_short_term);
        }
    }


    public ShortTermTaskDataAdapter(List<ShortTermTaskData> data) {
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_for_short_term, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShortTermTaskData shortTermTaskData = mData.get(position);
        setColorOfItem(holder, shortTermTaskData);
        holder.mTextView.setText(shortTermTaskData.getContent());
    }

    private void setColorOfItem(ViewHolder holder, ShortTermTaskData data) {
        if (data.getContent().equals("Short Term task A")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#ff0000"));
        } else if (data.getContent().equals("Short Term task B")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#00ff00"));
        } else if (data.getContent().equals("Short Term task C")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#0000ff"));
        } else if (data.getContent().equals("Short Term task D")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#00ffff"));
        } else if (data.getContent().equals("Short Term task E")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#eeffff"));
        } else if (data.getContent().equals("Short Term task F")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#ff00ff"));
        } else if (data.getContent().equals("Short Term task G")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#ffeeff"));
        } else if (data.getContent().equals("Short Term task H")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#ffff00"));
        } else if (data.getContent().equals("Short Term task I")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#ffffee"));
        } else if (data.getContent().equals("Short Term task J")) {
            holder.mParent.setBackgroundColor(Color.parseColor("#eeffee"));
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
