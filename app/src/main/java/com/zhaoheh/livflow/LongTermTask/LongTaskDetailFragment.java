package com.zhaoheh.livflow.LongTermTask;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoheh.livflow.PrimaryActivity;
import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTaskDetailFragment extends Fragment {

    private static final String TAG = "LongTaskDetailFrg";


    private PrimaryActivity mActivity;

    private String mLongTaskName;

    private LongTaskData mTaskData;

    public LongTaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (PrimaryActivity) getActivity();
        mLongTaskName = mActivity.getLongTaskName();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_long_task_detail, container, false);

        LinearLayout llDetail = view.findViewById(R.id.long_task_bottom_detail);
        LinearLayout llNodes = view.findViewById(R.id.long_task_bottom_nodes);

        TextView taskNameView = view.findViewById(R.id.task_name);
        TextView taskTargetView = view.findViewById(R.id.task_target);
        TextView taskDesireView = view.findViewById(R.id.task_desire);
        TextView taskStateView = view.findViewById(R.id.task_state);
        TextView taskCreatedTimeView = view.findViewById(R.id.task_create_time);

        llNodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchLongTaskNodesFragment();
            }
        });

        mTaskData = getLongTaskData(mLongTaskName);

        taskNameView.setText(mTaskData.getName());
        taskTargetView.setText(mTaskData.getTarget());
        taskDesireView.setText(mTaskData.getDesire());
        taskStateView.setText(getTaskStateInString(mTaskData.getState()));
        taskCreatedTimeView.setText(mTaskData.getCreatedTime());

        return view;
    }


    private LongTaskData getLongTaskData(String name) {
        List<LongTaskData> dataSet = DataSupport.findAll(LongTaskData.class);
        for (LongTaskData data : dataSet) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        LongTaskData data = new LongTaskData();
        data.setName("Something wrong");
        data.setTaskId(-1);
        return data;
    }


    private String getTaskStateInString(int state) {
        if (state == TaskState.STATE_READY) return "Task Ready";
        else if (state == TaskState.STATE_DOING) return "Task is being executed";
        else if (state == TaskState.STATE_SUSPENDED) return "Task is suspended";
        else if (state == TaskState.STATE_DROPPED) return "Task is abandoned";
        else if (state == TaskState.STATE_DONE) return "Task is finished";
        else return "Unknown";
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_long_task_node_add, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.long_task_adding_node:
                Log.d(TAG, "onOptionsItemSelected long_task_adding_node");
                break;
            default:
                break;
        }
        return true;
    }

}
