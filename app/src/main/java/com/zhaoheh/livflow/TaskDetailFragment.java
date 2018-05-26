package com.zhaoheh.livflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private int mTaskId;

    private TaskData mTaskData;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskActivity activity = (TaskActivity) getActivity();
        TextView taskActivityName = activity.findViewById(R.id.task_activity_title_name);
        taskActivityName.setText("任务信息");
        mTaskId = activity.getTaskId();
        mTaskData = getTaskData(mTaskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_task_detail, container, false);

        TextView taskNameView = view.findViewById(R.id.task_name);
        TextView taskTargetView = view.findViewById(R.id.task_target);
        TextView taskDesireView = view.findViewById(R.id.task_desire);
        TextView taskStateView = view.findViewById(R.id.task_state);
        TextView taskCreatedTimeView = view.findViewById(R.id.task_create_time);

        taskNameView.setText(mTaskData.getName());
        taskTargetView.setText(mTaskData.getTarget());
        taskDesireView.setText(mTaskData.getDesire());
        taskStateView.setText(getTaskStateInString(mTaskData.getState()));
        taskCreatedTimeView.setText(mTaskData.getCreatedTime());

        return view;
    }

    private TaskData getTaskData(int taskId) {
        List<TaskData> dataSet = DataSupport.findAll(TaskData.class);
        for (TaskData data : dataSet) {
            if (data.getTaskId() == taskId) {
                return data;
            }
        }
        TaskData data = new TaskData();
        data.setName("Something wrong");
        data.setTaskId(-1);
        return data;
    }

    private String getTaskStateInString(int state) {
        if (state == TaskState.STATE_READY) return "Task Ready";
        else if (state == TaskState.STATE_EXECUTING) return "Task is being executed";
        else if (state == TaskState.STATE_SUSPENDED) return "Task is suspended";
        else if (state == TaskState.STATE_END_ABANDONED) return "Task is abandoned";
        else if (state == TaskState.STATE_END_FINISHED) return "Task is finished";
        else return "Unknown";
    }
}
