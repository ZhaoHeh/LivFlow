package com.zhaoheh.livflow.LongTermTask;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LongTaskSimpleData {

    private int mState;
    private String mName;
    private String mLastNodeInfo;
    private String mLastUpdate;

    public LongTaskSimpleData(LongTaskData data) {
        this.mState = data.getState();
        this.mName = data.getName();
        int numOfNodes = data.getNumNodes();
        int taskId = data.getTaskId();
        LongTaskNodeData nodeData = getLastNode(taskId, numOfNodes);
        this.mLastNodeInfo = nodeData.getContent();
        this.mLastUpdate = nodeData.getCreatedTime();
    }


    public int getState() { return mState; }

    public String getName() { return mName; }

    public String getLastNodeInfo() { return mLastNodeInfo; }

    public String getLastUpdate() { return mLastUpdate; }


    private LongTaskNodeData getLastNode(int id, int numOfNodes) {
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
}
