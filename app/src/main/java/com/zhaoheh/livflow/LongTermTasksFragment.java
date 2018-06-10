package com.zhaoheh.livflow;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTermTasksFragment extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        TaskDialog.TaskDialogListener {

    private MainActivity mMainActivity;

    private ListView mListView;

    private List<LightTaskInformation> mTaskInfoList = new ArrayList<>();

    private TaskDialog mDialog;


    public LongTermTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
        Button btn = mMainActivity.getAddTaskBtn();
        btn.setText("Add");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog();
                mDialog.show();
            }
        });
    }


    private void updateDialog() {
        mDialog = null;
        mDialog = new TaskDialog(mMainActivity, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_long_term_tasks, container, false);
        mListView = view.findViewById(R.id.page_long_term_tasks_list_view);
        updateTasksInfo();
        showTasksListView(mListView);
        return view;
    }


    private void updateTasksInfo() {
        List<LightTaskInformation> tasks = new ArrayList<>();
        List<TaskData> tasksFromDB = DataSupport.findAll(TaskData.class);
        for (TaskData task : tasksFromDB) {
            LightTaskInformation info = new LightTaskInformation(task);
            tasks.add(info);
        }
        mTaskInfoList = tasks;
    }


    private void showTasksListView(ListView listView) {
        TaskInfoAdapter adapter = new TaskInfoAdapter(mMainActivity, R.layout.task_item,
                mTaskInfoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LightTaskInformation info = mTaskInfoList.get(position);
        Intent intent = new Intent(mMainActivity, TaskActivity.class);
        intent.putExtra("taskName", info.getName());
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        LightTaskInformation info = mTaskInfoList.get(position);
        Toast.makeText(mMainActivity, "LongClick"+info.getName(),
                Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public void onSubmit(TaskDialog dialog) {
        if (mDialog != null) {
            submit(dialog);
        }
    }


    private void submit(TaskDialog dialog) {
        View view = dialog.getView();
        final EditText taskNameTxt = (EditText) view.findViewById(R.id.task_name_txt);
        final EditText taskTargetTxt = (EditText) view.findViewById(R.id.task_target_txt);
        final EditText taskDesireTxt = (EditText) view.findViewById(R.id.task_desire_txt);

        String taskName = taskNameTxt.getText().toString();
        if (taskName.equals("")) {
            Toast.makeText(mMainActivity, "The name cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            String taskTarget = taskTargetTxt.getText().toString();
            String taskDesire = taskDesireTxt.getText().toString();
            String taskTime = getCurrentTime();
            int taskId = taskName.hashCode();

            TaskData taskData = new TaskData();
            taskData.setName(taskName);
            taskData.setTarget(taskTarget);
            taskData.setDesire(taskDesire);
            taskData.setState(TaskState.STATE_READY);
            taskData.setIsCurrent(false);
            taskData.setNumNodes(1);
            taskData.setTaskId(taskId);
            taskData.setCreatedTime(taskTime);
            taskData.save();

            TaskNodeData taskNodeData = new TaskNodeData();
            taskNodeData.setBelongTo(taskData.getTaskId());
            taskNodeData.setSerialNum(0);
            taskNodeData.setContent("此任务被创建");
            taskNodeData.setCreatedTime(taskTime);
            taskNodeData.save();
        }
        updateTasksInfo();
        showTasksListView(mListView);
    }


    private String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }


    @Override
    public void onForget(TaskDialog dialog) {
    }

}
