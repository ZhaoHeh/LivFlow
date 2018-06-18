package com.zhaoheh.livflow.LongTermTask;

import org.litepal.crud.DataSupport;

public class LongTaskNodeData extends DataSupport {

    private int mBelongTo;

    private int mSerialNum;

    private String mContent;

    private String mCreatedTime;

    public int getBelongTo() { return mBelongTo; }
    public int getSerialNum() { return mSerialNum; }
    public String getContent() { return mContent; }
    public String getCreatedTime() { return mCreatedTime; }

    public void setBelongTo(int taskId) { this.mBelongTo = taskId; }
    public void setSerialNum(int num) { this.mSerialNum = num; }
    public void setContent(String content) { this.mContent = content; }
    public void setCreatedTime(String time) { this.mCreatedTime = time; }
}