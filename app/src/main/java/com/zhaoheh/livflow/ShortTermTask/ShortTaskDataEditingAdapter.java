package com.zhaoheh.livflow.ShortTermTask;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import java.util.Collections;
import java.util.List;

public class ShortTaskDataEditingAdapter extends
        RecyclerView.Adapter<ShortTaskDataEditingAdapter.ViewHolder> {

    private List<ShortTaskData> mData;

    private int mEditEdge = -1;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View mParent;
        EditText mEditText;
        ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mParent = v;
            mEditText = (EditText) v.findViewById(R.id.task_content_for_short_editing);
            mImageView = (ImageView) v.findViewById(R.id.dragging_task_item);
        }
    }


    public ShortTaskDataEditingAdapter(List<ShortTaskData> data) {
        mData = data;
        for (int i = mData.size(); i > 0; i--) {
            if (mData.get(i-1).getState() != TaskState.STATE_READY) {
                mEditEdge = i;
                break;
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rvitem_short_task_editing, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShortTaskData shortTermTaskData = mData.get(position);
        holder.mEditText.setText(shortTermTaskData.getContent());
        if (position < mEditEdge) {
            holder.mEditText.setFocusable(false);
            holder.mEditText.setTextColor(Color.parseColor("#c3c3c3"));
            holder.mImageView.setImageResource(R.drawable.ic_dehaze_black_24dp_gray);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setData(List<ShortTaskData> newData) {
        mData = newData;
    }


    public List<ShortTaskData> getData() {
        return mData;
    }


    public void saveAllItemContent(RecyclerView rv) {
        for (int i = 0; i < mData.size(); i++) {
            View v = rv.getChildAt(i);
            EditText et = v.findViewById(R.id.task_content_for_short_editing);
            String txt = et.getText().toString();
            mData.get(i).setContent(txt);
        }
    }


    public void addNewShortTermTask() {
        ShortTaskData newItem = new ShortTaskData();
        newItem.setContent("");
        mData.add(newItem);
        notifyDataSetChanged();
    }


    public void onMove(int src, int dst) {
        /**
         * 在这里进行给原数组数据的移动
         */
        Collections.swap(mData, src, dst);
        /**
         * 通知数据移动
         */
        notifyItemMoved(src, dst);
    }


    public void onSwipe(int position) {
        /**
         * 原数据移除数据
         */
        mData.remove(position);
        /**
         * 通知移除
         */
        notifyItemRemoved(position);
    }

}
