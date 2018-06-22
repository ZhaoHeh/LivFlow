package com.zhaoheh.livflow.LongTermTask;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zhaoheh.livflow.MainActivity;
import com.zhaoheh.livflow.R;
import com.zhaoheh.livflow.TaskState;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTaskFragment extends Fragment
        implements LongTaskDialog.LongTaskDialogListener {

    private static final String TAG = "LongTaskFragment";

    private MainActivity mActivity;

    private View mFragmentView;

    private RecyclerView mRecyclerView;

    private List<LongTaskSimpleData> mData;

    private AlertDialog mDialog;


    public LongTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mActivity = (MainActivity) getActivity();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_long_task, container, false);
        mRecyclerView = mFragmentView.findViewById(R.id.long_task_rv);
        updateData();
        initViews();
        return mFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        Log.d(TAG, "onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }


    private void updateDialog() {
        mDialog = null;
        mDialog = new LongTaskDialog(mActivity, this);
    }


    private void updateData() {
        List<LongTaskSimpleData> tasks = new ArrayList<>();
        List<LongTaskData> tasksFromDB = DataSupport.findAll(LongTaskData.class);
        for (LongTaskData task : tasksFromDB) {
            if (task.getName() == null) task.delete();
            else {
                LongTaskSimpleData info = new LongTaskSimpleData(task);
                tasks.add(info);
            }
        }
        mData = tasks;
    }


    private void initViews() {
        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(llm);
        LongTaskSimpleDataAdapter adapter = new LongTaskSimpleDataAdapter(mData, mActivity);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                mActivity, DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onSubmit(LongTaskDialog dialog) {
        if (mDialog != null) {
            submit(dialog);
        }
    }


    private void submit(LongTaskDialog dialog) {
        View view = dialog.getView();
        final EditText taskNameTxt = (EditText) view.findViewById(R.id.task_name_txt);
        final EditText taskTargetTxt = (EditText) view.findViewById(R.id.task_target_txt);
        final EditText taskDesireTxt = (EditText) view.findViewById(R.id.task_desire_txt);

        String taskName = taskNameTxt.getText().toString();
        if (taskName.equals("")) {
            Toast.makeText(mActivity, "The name cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            String taskTarget = taskTargetTxt.getText().toString();
            String taskDesire = taskDesireTxt.getText().toString();
            String taskTime = getCurrentTime();
            int taskId = taskName.hashCode();

            LongTaskData taskData = new LongTaskData();
            taskData.setName(taskName);
            taskData.setTarget(taskTarget);
            taskData.setDesire(taskDesire);
            taskData.setState(TaskState.STATE_READY);
            taskData.setIsCurrent(false);
            taskData.setNumNodes(1);
            taskData.setTaskId(taskId);
            taskData.setCreatedTime(taskTime);
            taskData.save();

            LongTaskNodeData taskNodeData = new LongTaskNodeData();
            taskNodeData.setBelongTo(taskData.getTaskId());
            taskNodeData.setSerialNum(0);
            taskNodeData.setContent("此任务被创建");
            taskNodeData.setCreatedTime(taskTime);
            taskNodeData.save();
        }
        updateData();
        initViews();
    }


    private String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }


    @Override
    public void onForget(LongTaskDialog dialog) {
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_long_task_add, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.long_task_adding_task:
                updateDialog();
                mDialog.show();
                break;
            default:
                break;
        }
        return true;
    }

}
