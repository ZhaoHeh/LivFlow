package com.zhaoheh.livflow;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class TaskDialog extends AlertDialog implements DialogInterface.OnClickListener {

    public interface TaskDialogListener {
        void onForget(TaskDialog dialog);
        void onSubmit(TaskDialog dialog);
    }

    private static final int BUTTON_SUBMIT = DialogInterface.BUTTON_POSITIVE;
    private static final int BUTTON_FORGET = DialogInterface.BUTTON_NEUTRAL;

    private final TaskDialogListener mListener;

    private View mView;

    TaskDialog(Context context, TaskDialogListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.task_dialog_view, null);
        setView(mView);
        setTitle("New Task");
        setCancelable(true);
        setButton(BUTTON_SUBMIT, "Submit", this);
        setButton(BUTTON_FORGET, "Cancel", this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
        if (mListener != null) {
            switch (id) {
                case BUTTON_SUBMIT:
                    mListener.onSubmit(this);
                    break;
                case BUTTON_FORGET:
                    mListener.onForget(this);
                    break;
            }
        }
    }

    public View getView() { return mView; }
}
