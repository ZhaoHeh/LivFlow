package com.zhaoheh.livflow;

import org.litepal.crud.DataSupport;

public class ShortTermTaskData extends DataSupport {

    private String mContent;
    private int mState;
    private int mTaskId;
    private String mCreatedTime;

    public String getContent() { return mContent; }
    public int getState() { return mState; }
    public int getTaskId() { return mTaskId; }
    public String getCreatedTime() { return mCreatedTime; }

    public void setContent(String content) { this.mContent = content; }
    public void setState(int state) { this.mState = state; }
    public void setTaskId(int taskId) { this.mTaskId = taskId; }
    public void setCreatedTime(String time) { this.mCreatedTime = time; }
}
