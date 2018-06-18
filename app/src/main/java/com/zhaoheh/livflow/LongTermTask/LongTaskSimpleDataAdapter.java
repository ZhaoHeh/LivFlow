package com.zhaoheh.livflow.LongTermTask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoheh.livflow.PrimaryActivity;
import com.zhaoheh.livflow.R;

import java.util.List;

public class LongTaskSimpleDataAdapter
        extends RecyclerView.Adapter<LongTaskSimpleDataAdapter.ViewHolder> {

    private int mResId;

    private PrimaryActivity mActivity;

    private List<LongTaskSimpleData> mData;


    static class ViewHolder extends RecyclerView.ViewHolder {

        View mParent;
        TextView taskName;
        TextView lastUpdate;
        TextView taskLastDate;
        TextView taskLastTime;

        public ViewHolder(View v) {
            super(v);
            mParent = v;
            taskName = (TextView) mParent.findViewById(R.id.task_name);;
            lastUpdate = (TextView) mParent.findViewById(R.id.last_update);
            taskLastDate = (TextView) mParent.findViewById(R.id.last_update_date);
            taskLastTime = (TextView) mParent.findViewById(R.id.last_update_time);
        }
    }


    public LongTaskSimpleDataAdapter(List<LongTaskSimpleData> data, PrimaryActivity activity) {
        mData = data;
        mActivity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rvitem_long_task, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                LongTaskSimpleData info = mData.get(position);
                mActivity.setLongTaskDetailFragment(info.getName());
            }
        });
        holder.mParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                LongTaskSimpleData info = mData.get(position);
                Toast.makeText(v.getContext(), "LongClick: "+info.getName(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(final LongTaskSimpleDataAdapter.ViewHolder holder, int position) {
        LongTaskSimpleData info = mData.get(position);

        holder.taskName.setText(info.getName());
        holder.lastUpdate.setText(info.getLastNodeInfo());
        holder.taskLastDate.setText(info.getLastUpdate().substring(0, 10));
        holder.taskLastTime.setText(info.getLastUpdate().substring(11, 19));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
