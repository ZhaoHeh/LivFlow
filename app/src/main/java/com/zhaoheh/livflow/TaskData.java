package com.zhaoheh.livflow;

import org.litepal.crud.DataSupport;

public class TaskData extends DataSupport {

    private String mName;
    private String mTarget;
    private String mDesire;
    private int mState;
    private boolean mIsCurrent;
    private int mNumNodes;
    private int mTaskId;
    private String mCreatedTime;

    public String getName() { return mName; }
    public String getTarget() { return mTarget; }
    public String getDesire() { return mDesire; }
    public int getState() { return mState; }
    public boolean getIsCurrent() { return mIsCurrent; }
    public int getNumNodes() { return mNumNodes; }
    public int getTaskId() { return mTaskId; }
    public String getCreatedTime() { return mCreatedTime; }

    public void setName(String name) { this.mName = name; }
    public void setTarget(String target) { this.mTarget = target; }
    public void setDesire(String desire) { this.mDesire = desire; }
    public void setState(int state) { this.mState = state; }
    public void setIsCurrent(boolean isCurrent) { this.mIsCurrent = isCurrent; }
    public void setNumNodes(int numNodes) { this.mNumNodes = numNodes; }
    public void setTaskId(int taskId) { this.mTaskId = taskId; }
    public void setCreatedTime(String time) { this.mCreatedTime = time; }
}
