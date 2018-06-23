package com.zhaoheh.livflow.LongTermTask;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaoheh.livflow.MainActivity;
import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LongTaskDataAdapter extends RecyclerView.Adapter<LongTaskDataAdapter.ViewHolder> {

    private MainActivity mActivity;

    private List<LongTaskData> mData;


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


    public LongTaskDataAdapter(List<LongTaskData> data, MainActivity activity) {
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
                LongTaskData data = mData.get(position);
                final String name = data.getName();
                // TODO: 既然数据的类型已经是LongTaskData了, 就可以直接向新fragment传递data了
                mActivity.setLongTaskDetailFragment(name);
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LongTaskData data = mData.get(position);

        if (data.getState() == TaskState.STATE_READY)
            switchToReadyState(holder, data);
        else if (data.getState() == TaskState.STATE_DOING)
            switchToDoingState(holder, data);
        else if (data.getState() == TaskState.STATE_SUSPENDED)
            switchToSuspendedState(holder, data);
        else if (data.getState() == TaskState.STATE_DONE)
            switchToDoneState(holder, data);
        else if (data.getState() == TaskState.STATE_DROPPED)
            switchToDroppedState(holder, data);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    private LongTaskNodeData getLastNode(LongTaskData data) {
        int id = data.getTaskId();
        int numOfNodes = data.getNumNodes();

        List<LongTaskNodeData> nodes = DataSupport.findAll(LongTaskNodeData.class);
        for (LongTaskNodeData node : nodes) {
            if ((node.getBelongTo() == id) && node.getSerialNum() == numOfNodes - 1) return node;
        }
        LongTaskNodeData nodeData = new LongTaskNodeData();
        nodeData.setBelongTo(-1);
        nodeData.setSerialNum(-1);
        nodeData.setContent("Something wrong");
        nodeData.setCreatedTime("0000/00/00 00:00:00");
        return nodeData;
    }


    private void switchToReadyState(final ViewHolder holder, final LongTaskData data) {
        holder.mParent.setBackgroundColor(
                holder.mParent.getContext().getResources().getColor(R.color.readyStateBgColor));
        holder.taskName.setTextColor(
                holder.taskName.getContext().getResources().getColor(R.color.readyStateTxColor));

        holder.mParent.setOnLongClickListener(new ReadyStateListener(holder, data));

        holder.taskName.setText(data.getName());
        LongTaskNodeData lastNode = getLastNode(data);
        holder.lastUpdate.setText(lastNode.getContent());
        holder.taskLastTime.setText(lastNode.getCreatedTime().substring(11, 19));
        holder.taskLastDate.setText(lastNode.getCreatedTime().substring(0, 10));
    }


    private void switchToDoingState(final ViewHolder holder, final LongTaskData data) {
        holder.mParent.setBackgroundColor(
                holder.mParent.getContext().getResources().getColor(R.color.doingStateBgColor));
        holder.taskName.setTextColor(
                holder.taskName.getContext().getResources().getColor(R.color.doingStateTxtColor));

        holder.mParent.setOnLongClickListener(new DoingStateListener(holder, data));

        data.setState(TaskState.STATE_DOING);
        data.save();

        holder.taskName.setText(data.getName());
        LongTaskNodeData lastNode = getLastNode(data);
        holder.lastUpdate.setText(lastNode.getContent());
        holder.taskLastTime.setText(lastNode.getCreatedTime().substring(11, 19));
        holder.taskLastDate.setText(lastNode.getCreatedTime().substring(0, 10));
    }


    private void switchToSuspendedState(final ViewHolder holder, final LongTaskData data) {
        holder.mParent.setBackgroundColor(
                holder.mParent.getContext().getResources().getColor(R.color.suspendedStateBgColor));
        holder.taskName.setTextColor(
                holder.taskName.getContext().getResources().getColor(R.color.suspendedStateTxtColor));

        holder.mParent.setOnLongClickListener(new SuspendedStateListener(holder, data));

        data.setState(TaskState.STATE_SUSPENDED);
        data.save();

        holder.taskName.setText(data.getName());
        LongTaskNodeData lastNode = getLastNode(data);
        holder.lastUpdate.setText(lastNode.getContent());
        holder.taskLastTime.setText(lastNode.getCreatedTime().substring(11, 19));
        holder.taskLastDate.setText(lastNode.getCreatedTime().substring(0, 10));
    }


    private void switchToDoneState(final ViewHolder holder, final LongTaskData data) {
        holder.mParent.setBackgroundColor(
                holder.mParent.getContext().getResources().getColor(R.color.doneStateBgColor));
        holder.taskName.setTextColor(
                holder.taskName.getContext().getResources().getColor(R.color.doneStateTxtColor));
        holder.taskName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        holder.mParent.setOnLongClickListener(null);

        data.setState(TaskState.STATE_DONE);
        data.save();

        holder.taskName.setText(data.getName());
        LongTaskNodeData lastNode = getLastNode(data);
        holder.lastUpdate.setText(lastNode.getContent());
        holder.taskLastTime.setText(lastNode.getCreatedTime().substring(11, 19));
        holder.taskLastDate.setText(lastNode.getCreatedTime().substring(0, 10));
    }


    private void switchToDroppedState(final ViewHolder holder, final LongTaskData data) {
        holder.mParent.setBackgroundColor(
                holder.mParent.getContext().getResources().getColor(R.color.droppedStateBgColor));
        holder.taskName.setTextColor(
                holder.taskName.getContext().getResources().getColor(R.color.droppedStateTxtColor));

        holder.mParent.setOnLongClickListener(null);

        data.setState(TaskState.STATE_DROPPED);
        data.save();

        holder.taskName.setText(data.getName());
        LongTaskNodeData lastNode = getLastNode(data);
        holder.lastUpdate.setText(lastNode.getContent());
        holder.taskLastTime.setText(lastNode.getCreatedTime().substring(11, 19));
        holder.taskLastDate.setText(lastNode.getCreatedTime().substring(0, 10));
    }


    private class ReadyStateListener implements View.OnLongClickListener {

        private ViewHolder mHolder;

        private LongTaskData mLongTaskData;

        ReadyStateListener(ViewHolder holder, LongTaskData data) {
            mHolder = holder;
            mLongTaskData = data;
        }

        @Override
        public boolean onLongClick(View v) {
            View dialogLayout = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_task_state_operation, null);
            /* 去做 */
            TextView txtBtnTodo =
                    dialogLayout.findViewById(R.id.task_state_opt_todo);
            txtBtnTodo.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 完成 */
            TextView txtBtnDone =
                    dialogLayout.findViewById(R.id.task_state_opt_done);
            txtBtnDone.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 挂起 */
            TextView txtBtnSuspend =
                    dialogLayout.findViewById(R.id.task_state_opt_suspend);
            txtBtnSuspend.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 继续 无效*/
            TextView txtBtnContinue =
                    dialogLayout.findViewById(R.id.task_state_opt_continue);
            txtBtnContinue.setTextColor(v.getContext().getResources().getColor(R.color.unSelected));
            /* 放弃 */
            TextView txtBtnDrop =
                    dialogLayout.findViewById(R.id.task_state_opt_drop);
            txtBtnDrop.setTextColor(v.getContext().getResources().getColor(R.color.selected));

            final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
            dialog.setCancelable(true);
            dialog.show();
            dialog.getWindow().setContentView(dialogLayout);

            /* Set the response of buttons in dialog */

            txtBtnTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DOING);
                    switchToDoingState(mHolder, mLongTaskData);
                }
            });

            txtBtnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DONE);
                    switchToDoneState(mHolder, mLongTaskData);
                }
            });

            txtBtnSuspend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_SUSPENDED);
                    switchToSuspendedState(mHolder, mLongTaskData);
                }
            });

            txtBtnContinue.setOnClickListener(null);

            txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DROPPED);
                    switchToDroppedState(mHolder, mLongTaskData);
                }
            });
            return false;
        }
    }


    private class DoingStateListener implements View.OnLongClickListener {

        private ViewHolder mHolder;

        private LongTaskData mLongTaskData;

        DoingStateListener(ViewHolder holder, LongTaskData data) {
            mHolder = holder;
            mLongTaskData = data;
        }

        @Override
        public boolean onLongClick(View v) {
            View dialogLayout = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_task_state_operation, null);
            /* 去做 无效*/
            TextView txtBtnTodo =
                    dialogLayout.findViewById(R.id.task_state_opt_todo);
            txtBtnTodo.setTextColor(v.getContext().getResources().getColor(R.color.unSelected));
            /* 完成 */
            TextView txtBtnDone =
                    dialogLayout.findViewById(R.id.task_state_opt_done);
            txtBtnDone.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 挂起 */
            TextView txtBtnSuspend =
                    dialogLayout.findViewById(R.id.task_state_opt_suspend);
            txtBtnSuspend.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 继续 无效 */
            TextView txtBtnContinue =
                    dialogLayout.findViewById(R.id.task_state_opt_continue);
            txtBtnContinue.setTextColor(v.getContext().getResources().getColor(R.color.unSelected));
            /* 放弃 */
            TextView txtBtnDrop =
                    dialogLayout.findViewById(R.id.task_state_opt_drop);
            txtBtnDrop.setTextColor(v.getContext().getResources().getColor(R.color.selected));

            final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
            dialog.setCancelable(true);
            dialog.show();

            dialog.getWindow().setContentView(dialogLayout);

            /* Set the response of buttons in dialog */

            txtBtnTodo.setOnClickListener(null);

            txtBtnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DONE);
                    switchToDoneState(mHolder, mLongTaskData);
                }
            });

            txtBtnSuspend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_SUSPENDED);
                    switchToSuspendedState(mHolder, mLongTaskData);
                }
            });

            txtBtnContinue.setOnClickListener(null);

            txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DROPPED);
                    switchToDroppedState(mHolder, mLongTaskData);
                }
            });
            return false;
        }
    }


    private class SuspendedStateListener implements View.OnLongClickListener {

        private ViewHolder mHolder;

        private LongTaskData mLongTaskData;

        SuspendedStateListener(ViewHolder holder, LongTaskData data) {
            mHolder = holder;
            mLongTaskData = data;
        }

        @Override
        public boolean onLongClick(View v) {
            View dialogLayout = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_task_state_operation, null);
            /* 去做 无效 */
            TextView txtBtnTodo =
                    dialogLayout.findViewById(R.id.task_state_opt_todo);
            txtBtnTodo.setTextColor(v.getContext().getResources().getColor(R.color.unSelected));
            /* 完成 */
            TextView txtBtnDone =
                    dialogLayout.findViewById(R.id.task_state_opt_done);
            txtBtnDone.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 挂起 无效 */
            TextView txtBtnSuspend =
                    dialogLayout.findViewById(R.id.task_state_opt_suspend);
            txtBtnSuspend.setTextColor(v.getContext().getResources().getColor(R.color.unSelected));
            /* 继续 */
            TextView txtBtnContinue =
                    dialogLayout.findViewById(R.id.task_state_opt_continue);
            txtBtnContinue.setTextColor(v.getContext().getResources().getColor(R.color.selected));
            /* 放弃 */
            TextView txtBtnDrop =
                    dialogLayout.findViewById(R.id.task_state_opt_drop);
            txtBtnDrop.setTextColor(v.getContext().getResources().getColor(R.color.selected));

            final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
            dialog.setCancelable(true);
            dialog.show();

            dialog.getWindow().setContentView(dialogLayout);

            /* Set the response of buttons in dialog */

            txtBtnTodo.setOnClickListener(null);

            txtBtnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DONE);
                    switchToDoneState(mHolder, mLongTaskData);
                }
            });

            txtBtnSuspend.setOnClickListener(null);

            txtBtnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DOING);
                    switchToDoingState(mHolder, mLongTaskData);
                }
            });

            txtBtnDrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    createStateChangedNode(mLongTaskData, mLongTaskData.getState(), TaskState.STATE_DROPPED);
                    switchToDroppedState(mHolder, mLongTaskData);
                }
            });
            return false;
        }
    }


    private void createStateChangedNode(LongTaskData data, int from, int to) {
        LongTaskNodeData stateChangedNode = new LongTaskNodeData();
        String content = "状态改变: 从" + getStateDescription(from)
                + "切换到" + getStateDescription(to);

        stateChangedNode.setBelongTo(data.getTaskId());
        stateChangedNode.setSerialNum(data.getNumNodes());
        stateChangedNode.setContent(content);
        stateChangedNode.setCreatedTime(getCurrentTime());
        stateChangedNode.save();

        data.setNumNodes(data.getNumNodes()+1);
        data.save();
    }


    private String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }


    private String getStateDescription(int state) {
        if (state == TaskState.STATE_READY)
            return "\"准备\"";
        else if (state == TaskState.STATE_DOING)
            return "\"在做\"";
        else if (state == TaskState.STATE_SUSPENDED)
            return "\"挂起\"";
        else if (state == TaskState.STATE_DONE)
            return "\"完成\"";
        else if (state == TaskState.STATE_DROPPED)
            return "\"放弃\"";
        else
            return "Something Wrong";
    }
}
