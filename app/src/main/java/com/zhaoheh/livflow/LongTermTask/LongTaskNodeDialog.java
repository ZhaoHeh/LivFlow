package com.zhaoheh.livflow.LongTermTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhaoheh.livflow.R;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LongTaskNodeDialog extends AlertDialog implements DialogInterface.OnClickListener {

    private static final String TAG = "LongTaskNodeDialog";

    public interface LongTaskNodeDialogListener {
        void onForget(LongTaskNodeDialog dialog);
        void onSubmit(LongTaskNodeDialog dialog);
    }

    private static final int BUTTON_SUBMIT = DialogInterface.BUTTON_POSITIVE;
    private static final int BUTTON_FORGET = DialogInterface.BUTTON_NEUTRAL;

    private View mView;

    private final LongTaskNodeDialogListener mListener;


    LongTaskNodeDialog(Context context, LongTaskNodeDialogListener listener) {
        super(context);
        mListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.dialog_long_task_node, null);
        setView(mView);
        setTitle("Add New Task Node");
        setCancelable(true);
        setButton(BUTTON_SUBMIT, "Submit", this);
        setButton(BUTTON_FORGET, "Cancel", this);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
        switch (id) {
            case BUTTON_SUBMIT:
                mListener.onSubmit(this);
                break;
            case BUTTON_FORGET:
                mListener.onForget(this);
                break;
        }
    }


    public View getView() { return mView; }


    public LongTaskData getLongTaskData(String name) {
        Log.d(TAG, "Name is :" + name);
        List<LongTaskData> dataSet = DataSupport.findAll(LongTaskData.class);
        for (LongTaskData data : dataSet) {
            if (data.getName().equals(name)) return data;
        }
        LongTaskData data = new LongTaskData();
        data.setTaskId(-1);
        return data;
    }


    public String getCurrentTime() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }
}
