package com.zhaoheh.livflow;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class EditingShortTermTaskDataAdapter extends
        RecyclerView.Adapter<EditingShortTermTaskDataAdapter.ViewHolder> {

    private List<ShortTermTaskData> mData;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View mParent;
        EditText mEditText;
        public ViewHolder(View v) {
            super(v);
            mParent = v;
            mEditText = (EditText) v.findViewById(R.id.task_content_for_short_editing);
        }
    }


    public EditingShortTermTaskDataAdapter(List<ShortTermTaskData> data) {
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_for_short_term_editing, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShortTermTaskData shortTermTaskData = mData.get(position);
//        setColorOfItem(holder, shortTermTaskData);
        holder.mEditText.setText(shortTermTaskData.getContent());
    }

//    private void setColorOfItem(ViewHolder holder, ShortTermTaskData data) {
//        if (data.getContent().equals("Short Term task A")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task B")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task C")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task D")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task E")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task F")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task G")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task H")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task I")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        } else if (data.getContent().equals("Short Term task J")) {
//            holder.mParent.setBackgroundColor(Color.parseColor("#eeeeee"));
//        }
//    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setData(List<ShortTermTaskData> newData) {
        mData = newData;
    }


    public List<ShortTermTaskData> getData() {
        return mData;
    }


    public void temporarilySaveContent(RecyclerView rv) {
        for (int i = 0; i < mData.size(); i++) {
            View v = rv.getChildAt(i);
            EditText et = v.findViewById(R.id.task_content_for_short_editing);
            String txt = et.getText().toString();
            mData.get(i).setContent(txt);
        }
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
