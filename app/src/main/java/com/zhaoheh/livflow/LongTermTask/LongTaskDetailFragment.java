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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoheh.livflow.MainActivity;
import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTaskDetailFragment extends Fragment
        implements LongTaskNodeDialog.LongTaskNodeDialogListener{

    private static final String TAG = "LongTaskDetailFrg";


    private MainActivity mActivity;

    private View mFragmentView;

    private String mLongTaskName;

    private LongTaskData mTaskData;

    public LongTaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mActivity = (MainActivity) getActivity();
        mLongTaskName = mActivity.getLongTaskName();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentView = inflater.inflate(
                R.layout.fragment_long_task_detail, container, false);

        Log.d(TAG, "onCreateView");
        LinearLayout llDetail = mFragmentView.findViewById(R.id.long_task_bottom_detail);
        LinearLayout llNodes = mFragmentView.findViewById(R.id.long_task_bottom_nodes);

        llNodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchLongTaskNodesFragment();
            }
        });

        updateView();

        return mFragmentView;
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
                LongTaskNodeDialog dialog = new LongTaskNodeDialog(mActivity, this);
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onSubmit(LongTaskNodeDialog dialog) {
        View view = dialog.getView();
        final EditText editText = (EditText) view.findViewById(R.id.long_task_node_content);
        String content = editText.getText().toString();
        if (content.equals("")) {
            Log.d(TAG, "Node content is null and do nothing.");
        } else {
            LongTaskData taskData = dialog.getLongTaskData(mLongTaskName);

            if (taskData.getTaskId() == -1) return;
            Log.d(TAG, "taskData.getTaskId() != -1");

            LongTaskNodeData taskNodeData = new LongTaskNodeData();
            taskNodeData.setBelongTo(taskData.getTaskId());
            taskNodeData.setSerialNum(taskData.getNumNodes());
            taskNodeData.setContent(content);
            taskNodeData.setCreatedTime(dialog.getCurrentTime());
            taskNodeData.save();

            taskData.setNumNodes(taskData.getNumNodes()+1);
            taskData.save();

            updateView();
        }
    }


    @Override
    public void onForget(LongTaskNodeDialog dialog) {
    }


    private void updateView() {

        mTaskData = getLongTaskData(mLongTaskName);

        TextView taskNameView = mFragmentView.findViewById(R.id.task_name);
        TextView taskTargetView = mFragmentView.findViewById(R.id.task_target);
        TextView taskDesireView = mFragmentView.findViewById(R.id.task_desire);
        TextView taskStateView = mFragmentView.findViewById(R.id.task_state);
        TextView taskLastUpdateView = mFragmentView.findViewById(R.id.task_last_update_time);
        TextView taskCreatedTimeView = mFragmentView.findViewById(R.id.task_create_time);

        taskNameView.setText(mTaskData.getName());
        taskTargetView.setText(mTaskData.getTarget());
        taskDesireView.setText(mTaskData.getDesire());
        taskStateView.setText(getTaskStateInString(mTaskData.getState()));
        taskLastUpdateView.setText(getLongTaskLastUpdateTime(mTaskData));
        taskCreatedTimeView.setText(mTaskData.getCreatedTime());
    }


    private LongTaskData getLongTaskData(String name) {
        List<LongTaskData> dataSet = DataSupport.findAll(LongTaskData.class);
        for (LongTaskData data : dataSet) {
            if (data.getName() == null)
                Log.d(TAG, "getLongTaskData data name is null.");
            else if (data.getName().equals(name)) {
                return data;
            }
        }
        LongTaskData data = new LongTaskData();
        data.setName("Something wrong");
        data.setTaskId(-1);
        return data;
    }


    private String getTaskStateInString(int state) {
        if (state == TaskState.STATE_READY) return "Ready";
        else if (state == TaskState.STATE_DOING) return "Doing";
        else if (state == TaskState.STATE_SUSPENDED) return "Suspended";
        else if (state == TaskState.STATE_DROPPED) return "Dropped";
        else if (state == TaskState.STATE_DONE) return "Done";
        else return "Unknown";
    }


    private String getLongTaskLastUpdateTime(LongTaskData data) {
        List<LongTaskNodeData> dataSet = DataSupport.findAll(LongTaskNodeData.class);
        for (LongTaskNodeData nodeData : dataSet) {
            if (nodeData.getBelongTo() == data.getName().hashCode() &&
                    nodeData.getSerialNum() == data.getNumNodes() - 1)
                return nodeData.getCreatedTime();
        }
        return "something wrong";
    }
}
