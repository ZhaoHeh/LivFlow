package com.zhaoheh.livflow;

import android.util.Log;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LightTaskInformation {

    private static final String TAG = "LightTaskInformation";

    private int mState;
    private String mName;
    private String mLastNodeInfo;
    private String mLastUpdate;

    public LightTaskInformation(TaskData taskData) {
        this.mState = taskData.getState();
        this.mName = taskData.getName();
        int numOfNodes = taskData.getNumNodes();
        int taskId = taskData.getTaskId();
        TaskNodeData nodeData = getLastNode(taskId, numOfNodes);
        this.mLastNodeInfo = nodeData.getContent();
        this.mLastUpdate = nodeData.getCreatedTime();
    }

    public LightTaskInformation(int state, String name, String info, String time) {
        this.mState = state;
        this.mName = name;
        this.mLastNodeInfo = info;
        this.mLastUpdate = time;
    }

    public int getState() { return mState; }

    public String getName() { return mName; }

    public String getLastNodeInfo() { return mLastNodeInfo; }

    public String getLastUpdate() { return mLastUpdate; }

    private TaskNodeData getLastNode(int id, int numOfNodes) {
        List<TaskNodeData> nodes = DataSupport.findAll(TaskNodeData.class);
        for (TaskNodeData node : nodes) {
            if ((node.getBelongTo() == id) && node.getSerialNum() == numOfNodes - 1) return node;
        }
        TaskNodeData nodeData = new TaskNodeData();
        nodeData.setBelongTo(-1);
        nodeData.setSerialNum(-1);
        nodeData.setContent("Something wrong");
        nodeData.setCreatedTime("0000/00/00 00:00:00");
        return nodeData;
    }
}
