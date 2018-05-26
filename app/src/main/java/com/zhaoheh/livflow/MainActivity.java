package com.zhaoheh.livflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDialog.TaskDialogListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "LivflowMainActivity";

    private List<LightTaskInformation> mTaskInfoList = new ArrayList<>();

    private TaskDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        Button addTaskBtn = (Button) findViewById(R.id.add_task_button);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog();
                mDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTasksInfo();
        showTasksListView();
    }

    private void updateDialog() {
        mDialog = null;
        mDialog = new TaskDialog(MainActivity.this, this);
    }

    private void updateTasksInfo() {
        List<LightTaskInformation> tasks = new ArrayList<>();

        LightTaskInformation testInfo = new LightTaskInformation(0, "The first task",
                "Task created", "2018/05/13 02:07:33");
        tasks.add(testInfo);

        List<TaskData> tasksFromDB = DataSupport.findAll(TaskData.class);
        for (TaskData task : tasksFromDB) {
            LightTaskInformation info = new LightTaskInformation(task);
            tasks.add(info);
        }
        mTaskInfoList = tasks;
    }

    private void showTasksListView() {
        TaskInfoAdapter adapter = new TaskInfoAdapter(MainActivity.this, R.layout.task_item,
                mTaskInfoList);
        ListView listView = (ListView) findViewById(R.id.tasks_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
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
            Toast.makeText(MainActivity.this, "The name cannot be empty.",
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
        showTasksListView();
    }

    private String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }

    @Override
    public void onForget(TaskDialog dialog) {
        forget();
    }

    private void forget() {
        Toast.makeText(MainActivity.this, "Creating has been abandoned.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LightTaskInformation info = mTaskInfoList.get(position);
        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
        intent.putExtra("taskName", info.getName());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        LightTaskInformation info = mTaskInfoList.get(position);
        Toast.makeText(MainActivity.this, "LongClick"+info.getName(),
                Toast.LENGTH_SHORT).show();
        return true;
    }
}
