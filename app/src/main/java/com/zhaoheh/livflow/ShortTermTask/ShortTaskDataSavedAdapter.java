package com.zhaoheh.livflow.ShortTermTask;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import java.util.List;

public class ShortTaskDataSavedAdapter extends
        RecyclerView.Adapter<ShortTaskDataSavedAdapter.ViewHolder> {

    static String ready_state_short_task_bg_color = "#ffffff";
    static String doing_state_short_task_bg_color = "#ffffff";
    static String suspended_state_short_task_bg_color = "#eeeeee";
    static String done_state_short_task_bg_color = "#eeeeee";
    static String dropped_state_short_task_bg_color = "#eeeeee";

    static String ready_state_short_task_txt_color = "#ff9aa9";
    static String doing_state_short_task_txt_color = "#bc2332";
    static String suspended_state_short_task_txt_color = "#bc2332";
    static String done_state_short_task_txt_color = "#c3c3c3";
    static String dropped_state_short_task_txt_color = "#c3c3c3";

    static String validTextButtonColor = "#bc2332";
    static String invalidTextButtonColor = "#c3c3c3";

//    private List<ShortTermTaskData> mData;
    private List<ShortTaskData> mData;


    static class ViewHolder extends RecyclerView.ViewHolder {

        View mParent;
        TextView mTextView;
        TextView mStateInstr;
        ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mParent = v;
            mTextView = (TextView) v.findViewById(R.id.task_content_for_short_saved);
            mStateInstr = (TextView) v.findViewById(R.id.state_instruction_for_short_saved);
            mImageView = (ImageView) v.findViewById(R.id.opration_menu_for_short_saved);
        }
    }


    public ShortTaskDataSavedAdapter(List<ShortTaskData> data) {
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rvitem_short_task_saved, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ShortTaskDataSavedAdapter.ViewHolder holder, int position) {
        final ShortTaskData shortTaskData = mData.get(position);
        holder.mTextView.setText(shortTaskData.getContent());
        if (shortTaskData.getState() == TaskState.STATE_READY)
            switchToReadyState(holder, position);
        else if (shortTaskData.getState() == TaskState.STATE_DOING)
            switchToDoingState(holder, position);
        else if (shortTaskData.getState() == TaskState.STATE_DONE)
            switchToDoneState(holder, position);
        else if (shortTaskData.getState() == TaskState.STATE_SUSPENDED)
            switchToSuspendedState(holder, position);
        else if (shortTaskData.getState() == TaskState.STATE_DROPPED)
            switchToDroppedState(holder, position);
    }


    private void switchToReadyState(final ShortTaskDataSavedAdapter.ViewHolder holder, final int position) {

        /* Set the Style */

        holder.mParent.setBackgroundColor(Color.parseColor(ready_state_short_task_bg_color));
        holder.mTextView.setTextColor(Color.parseColor(ready_state_short_task_txt_color));
        holder.mStateInstr.setText("准备");

        /* Set the Response of the clicking on ImageView */

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View dialogLayout = LayoutInflater.from(v.getContext())
//                        .inflate(R.layout.operation_dialog_for_short_term_task, null);
                View dialogLayout = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.dialog_short_task_saved_operation, null);
                /* 去做 */
                TextView txtBtnTodo =
                        dialogLayout.findViewById(R.id.todo_btn_op_dialog_short_task);
                txtBtnTodo.setTextColor(Color.parseColor(validTextButtonColor));
                /* 完成 */
                TextView txtBtnDone =
                        dialogLayout.findViewById(R.id.done_btn_op_dialog_short_task);
                txtBtnDone.setTextColor(Color.parseColor(validTextButtonColor));
                /* 挂起 */
                TextView txtBtnSuspend =
                        dialogLayout.findViewById(R.id.suspend_btn_op_dialog_short_task);
                txtBtnSuspend.setTextColor(Color.parseColor(validTextButtonColor));
                /* 继续 无效*/
                TextView txtBtnContinue =
                        dialogLayout.findViewById(R.id.continue_btn_op_dialog_short_task);
                txtBtnContinue.setTextColor(Color.parseColor(invalidTextButtonColor));
                /* 放弃 */
                TextView txtBtnDrop =
                        dialogLayout.findViewById(R.id.drop_btn_op_dialog_short_task);
                txtBtnDrop.setTextColor(Color.parseColor(validTextButtonColor));

                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                dialog.setCancelable(true);
                dialog.show();

                dialog.getWindow().setContentView(dialogLayout);

                /* Set the response of buttons in dialog */

                txtBtnTodo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDoingState(holder, position);
                    }
                });

                txtBtnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDoneState(holder, position);
                    }
                });

                txtBtnSuspend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToSuspendedState(holder, position);
                    }
                });

                txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDroppedState(holder, position);
                    }
                });
            }
        });
    }


    private void switchToDoingState(final ShortTaskDataSavedAdapter.ViewHolder holder, final int position) {

        /* Set the Style */

        holder.mParent.setBackgroundColor(Color.parseColor(doing_state_short_task_bg_color));
        holder.mTextView.setTextColor(Color.parseColor(doing_state_short_task_txt_color));
        holder.mStateInstr.setText("在做");

        /* Set the Response of the clicking on ImageView */

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View dialogLayout = LayoutInflater.from(v.getContext())
//                        .inflate(R.layout.operation_dialog_for_short_term_task, null);
                View dialogLayout = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.dialog_short_task_saved_operation, null);
                /* 去做 无效*/
                TextView txtBtnTodo =
                        dialogLayout.findViewById(R.id.todo_btn_op_dialog_short_task);
                txtBtnTodo.setTextColor(Color.parseColor(invalidTextButtonColor));
                /* 完成 */
                TextView txtBtnDone =
                        dialogLayout.findViewById(R.id.done_btn_op_dialog_short_task);
                txtBtnDone.setTextColor(Color.parseColor(validTextButtonColor));
                /* 挂起 */
                TextView txtBtnSuspend =
                        dialogLayout.findViewById(R.id.suspend_btn_op_dialog_short_task);
                txtBtnSuspend.setTextColor(Color.parseColor(validTextButtonColor));
                /* 继续 无效 */
                TextView txtBtnContinue =
                        dialogLayout.findViewById(R.id.continue_btn_op_dialog_short_task);
                txtBtnContinue.setTextColor(Color.parseColor(invalidTextButtonColor));
                /* 放弃 */
                TextView txtBtnDrop =
                        dialogLayout.findViewById(R.id.drop_btn_op_dialog_short_task);
                txtBtnDrop.setTextColor(Color.parseColor(validTextButtonColor));

                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                dialog.setCancelable(true);
                dialog.show();

                dialog.getWindow().setContentView(dialogLayout);

                /* Set the response of buttons in dialog */

                txtBtnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDoneState(holder, position);
                    }
                });

                txtBtnSuspend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToSuspendedState(holder, position);
                    }
                });

                txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDroppedState(holder, position);
                    }
                });
            }
        });

        ShortTaskData shortTermTaskData = mData.get(position);
        shortTermTaskData.setState(TaskState.STATE_DOING);
        shortTermTaskData.save();

    }


    private void switchToSuspendedState(final ShortTaskDataSavedAdapter.ViewHolder holder, final int position) {

        /* Set the Style */

        holder.mParent.setBackgroundColor(Color.parseColor(suspended_state_short_task_bg_color));
        holder.mTextView.setTextColor(Color.parseColor(suspended_state_short_task_txt_color));
        holder.mStateInstr.setText("挂起");

        /* Set the Response of the clicking on ImageView */

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View dialogLayout = LayoutInflater.from(v.getContext())
//                        .inflate(R.layout.operation_dialog_for_short_term_task, null);
                View dialogLayout = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.dialog_short_task_saved_operation, null);
                /* 去做 无效 */
                TextView txtBtnTodo =
                        dialogLayout.findViewById(R.id.todo_btn_op_dialog_short_task);
                txtBtnTodo.setTextColor(Color.parseColor(invalidTextButtonColor));
                /* 完成 */
                TextView txtBtnDone =
                        dialogLayout.findViewById(R.id.done_btn_op_dialog_short_task);
                txtBtnDone.setTextColor(Color.parseColor(validTextButtonColor));
                /* 挂起 无效 */
                TextView txtBtnSuspend =
                        dialogLayout.findViewById(R.id.suspend_btn_op_dialog_short_task);
                txtBtnSuspend.setTextColor(Color.parseColor(invalidTextButtonColor));
                /* 继续 */
                TextView txtBtnContinue =
                        dialogLayout.findViewById(R.id.continue_btn_op_dialog_short_task);
                txtBtnContinue.setTextColor(Color.parseColor(validTextButtonColor));
                /* 放弃 */
                TextView txtBtnDrop =
                        dialogLayout.findViewById(R.id.drop_btn_op_dialog_short_task);
                txtBtnDrop.setTextColor(Color.parseColor(validTextButtonColor));

                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                dialog.setCancelable(true);
                dialog.show();

                dialog.getWindow().setContentView(dialogLayout);

                /* Set the response of buttons in dialog */

                txtBtnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDoneState(holder, position);
                    }
                });

                txtBtnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDoingState(holder, position);
                    }
                });

                txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        switchToDroppedState(holder, position);
                    }
                });
            }
        });

        ShortTaskData shortTermTaskData = mData.get(position);
        shortTermTaskData.setState(TaskState.STATE_SUSPENDED);
        shortTermTaskData.save();

    }


    private void switchToDoneState(final ShortTaskDataSavedAdapter.ViewHolder holder, int position) {

        /* Set the Style */

        holder.mParent.setBackgroundColor(Color.parseColor(done_state_short_task_bg_color));
        holder.mTextView.setTextColor(Color.parseColor(done_state_short_task_txt_color));
        holder.mTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.mStateInstr.setText("完成");

        /* Set the Response of the clicking on ImageView */

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ShortTaskData shortTermTaskData = mData.get(position);
        shortTermTaskData.setState(TaskState.STATE_DONE);
        shortTermTaskData.save();

    }


    private void switchToDroppedState(final ShortTaskDataSavedAdapter.ViewHolder holder, int position) {

        /* Set the Style */

        holder.mParent.setBackgroundColor(Color.parseColor(dropped_state_short_task_bg_color));
        holder.mTextView.setTextColor(Color.parseColor(dropped_state_short_task_txt_color));
        holder.mStateInstr.setText("放弃");

        /* Set the Response of the clicking on ImageView */

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ShortTaskData shortTermTaskData = mData.get(position);
        shortTermTaskData.setState(TaskState.STATE_DROPPED);
        shortTermTaskData.save();

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
}
