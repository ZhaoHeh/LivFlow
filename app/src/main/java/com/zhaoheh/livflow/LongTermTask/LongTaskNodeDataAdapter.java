package com.zhaoheh.livflow.LongTermTask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaoheh.livflow.R;

import java.util.List;

public class LongTaskNodeDataAdapter extends
        RecyclerView.Adapter<LongTaskNodeDataAdapter.ViewHolder> {

    private List<LongTaskNodeData> mData;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mParent;
        TextView mLongTaskNodeInfo;
        TextView mLongTaskNodeCreatedTime;

        public ViewHolder(View v) {
            super(v);
            mParent = v;
            mLongTaskNodeInfo = mParent.findViewById(R.id.long_task_node_info);
            mLongTaskNodeCreatedTime = mParent.findViewById(R.id.long_task_node_created_time);
        }
    }


    public LongTaskNodeDataAdapter(List<LongTaskNodeData> data) {
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rvitem_long_task_node, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LongTaskNodeData data = mData.get(position);
        holder.mLongTaskNodeInfo.setText(data.getContent());
        holder.mLongTaskNodeCreatedTime.setText(data.getCreatedTime());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
