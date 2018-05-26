package com.zhaoheh.livflow;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener,
        NodeDialog.NodeDialogListener {

    private String TAG = "TaskActivity";

    private String mTaskName;

    private int mTaskId;

    private TaskData mTaskData;

    private NodeDialog mDialog;

    private LinearLayout mLinearLayoutTaskDetail;
    private LinearLayout mLinearLayoutNodesList;
    private LinearLayout mLinearLayoutAdding;
    private LinearLayout mLinearLayoutSetting;

    private ImageView mImageViewTaskDetail;
    private ImageView mImageViewNodesList;
    private ImageView mImageViewAdding;
    private ImageView mImageViewSetting;

    private TaskDetailFragment mTaskDetailFragment;
    private NodesListFragment mNodesListFragment;
    private AddingFragment mAddingFragment;
    private SettingFragment mSettingFragment;

    public int getTaskId() { return mTaskId; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity_layout);

        Intent intent = getIntent();
        mTaskName = intent.getStringExtra("taskName");
        mTaskId = mTaskName.hashCode();

        mTaskData = getTaskData(mTaskId);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        initBottomViews();

        initClickEvent();

        setCurrentFragment(0);
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

    private void initBottomViews() {
        this.mLinearLayoutTaskDetail = (LinearLayout) findViewById(R.id.ll_task_detail);
        this.mLinearLayoutNodesList = (LinearLayout) findViewById(R.id.ll_nodes_list);
        this.mLinearLayoutAdding = (LinearLayout) findViewById(R.id.ll_adding);
        this.mLinearLayoutSetting = (LinearLayout) findViewById(R.id.ll_setting);

        this.mImageViewTaskDetail = (ImageView) findViewById(R.id.iv_task_detail);
        this.mImageViewNodesList = (ImageView) findViewById(R.id.iv_nodes_list);
        this.mImageViewAdding = (ImageView) findViewById(R.id.iv_adding);
        this.mImageViewSetting = (ImageView) findViewById(R.id.iv_setting);
    }

    private void initClickEvent() {
        Button addNodeBtn = findViewById(R.id.add_node_button);
        addNodeBtn.setOnClickListener(this);
        mLinearLayoutTaskDetail.setOnClickListener(this);
        mLinearLayoutNodesList.setOnClickListener(this);
        mLinearLayoutAdding.setOnClickListener(this);
        mLinearLayoutSetting.setOnClickListener(this);
    }

    private void setCurrentFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (index) {
            case 0:
                if (mTaskDetailFragment == null) {
                    mTaskDetailFragment = new TaskDetailFragment();
                    transaction.add(R.id.fl_content, mTaskDetailFragment);
                } else {
                    transaction.show(mTaskDetailFragment);
                }
                break;
            case 1:
                if (mNodesListFragment == null) {
                    mNodesListFragment = new NodesListFragment();
                    transaction.add(R.id.fl_content, mNodesListFragment);
                } else {
                    transaction.show(mNodesListFragment);
                }
                break;
            case 2:
                if (mAddingFragment == null) {
                    mAddingFragment = new AddingFragment();
                    transaction.add(R.id.fl_content, mAddingFragment);
                } else {
                    transaction.show(mAddingFragment);
                }
                break;
            case 3:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    transaction.add(R.id.fl_content, mSettingFragment);
                } else {
                    transaction.show(mSettingFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mTaskDetailFragment != null) {
            transaction.hide(mTaskDetailFragment);
        }
        if (mNodesListFragment != null) {
            transaction.hide(mNodesListFragment);
        }
        if (mAddingFragment != null) {
            transaction.hide(mAddingFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
    }

    @Override
    public void onSubmit(NodeDialog dialog) {
        if (mDialog != null) {
            submit(dialog);
        }
    }

    private void submit(NodeDialog dialog) {
        View view = dialog.getView();
        final EditText nodeContentTxt = (EditText) view.findViewById(R.id.node_content_txt);
        String nodeContent = nodeContentTxt.getText().toString();
        if (nodeContent.equals("")) {
            Toast.makeText(TaskActivity.this, "The name cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            mTaskData.setNumNodes(mTaskData.getNumNodes() + 1);
            mTaskData.save();
            TaskNodeData taskNodeData = new TaskNodeData();
            taskNodeData.setBelongTo(mTaskData.getTaskId());
            taskNodeData.setSerialNum(mTaskData.getNumNodes()-1);
            taskNodeData.setContent(nodeContent);
            taskNodeData.setCreatedTime(getCurrentTime());
            taskNodeData.save();
        }
    }

    private String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }

    @Override
    public void onForget(NodeDialog dialog) {
        Toast.makeText(TaskActivity.this, "You abandoned the submit.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            updateDialog();
            mDialog.show();
        } else {
            restartButton();
            switch (v.getId()) {
                case R.id.ll_task_detail:
                    mLinearLayoutTaskDetail.setBackgroundColor(Color.parseColor("#ffffff"));
                    mImageViewTaskDetail.setImageResource(R.drawable.iv_task_detail_red_48);
                    setCurrentFragment(0);
                    break;
                case R.id.ll_nodes_list:
                    mLinearLayoutNodesList.setBackgroundColor(Color.parseColor("#ffffff"));
                    mImageViewNodesList.setImageResource(R.drawable.iv_nodes_list_red_48);
                    setCurrentFragment(1);
                    break;
                case R.id.ll_adding:
                    mLinearLayoutAdding.setBackgroundColor(Color.parseColor("#ffffff"));
                    mImageViewAdding.setImageResource(R.drawable.iv_adding_red_48);
                    setCurrentFragment(2);
                    break;
                case R.id.ll_setting:
                    mLinearLayoutSetting.setBackgroundColor(Color.parseColor("#ffffff"));
                    mImageViewSetting.setImageResource(R.drawable.iv_setting_red_48);
                    setCurrentFragment(3);
                    break;
                default:
                    break;
            }
        }
    }

    private void updateDialog() {
        mDialog = null;
        mDialog = new NodeDialog(TaskActivity.this, this);
    }

    private void restartButton() {
        mImageViewTaskDetail.setImageResource(R.drawable.iv_task_detail_white_48);
        mImageViewNodesList.setImageResource(R.drawable.iv_nodes_list_white_48);
        mImageViewAdding.setImageResource(R.drawable.iv_adding_white_48);
        mImageViewSetting.setImageResource(R.drawable.iv_setting_white_48);

        mLinearLayoutTaskDetail.setBackgroundColor(Color.parseColor("#bc2332"));
        mLinearLayoutNodesList.setBackgroundColor(Color.parseColor("#bc2332"));
        mLinearLayoutAdding.setBackgroundColor(Color.parseColor("#bc2332"));
        mLinearLayoutSetting.setBackgroundColor(Color.parseColor("#bc2332"));
    }
}
