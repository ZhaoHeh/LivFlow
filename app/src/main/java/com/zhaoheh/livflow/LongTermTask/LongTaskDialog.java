package com.zhaoheh.livflow.LongTermTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.zhaoheh.livflow.R;

public class LongTaskDialog extends AlertDialog implements DialogInterface.OnClickListener {

    public interface LongTaskDialogListener {
        void onForget(LongTaskDialog dialog);
        void onSubmit(LongTaskDialog dialog);
    }


    private static final int BUTTON_SUBMIT = DialogInterface.BUTTON_POSITIVE;
    private static final int BUTTON_FORGET = DialogInterface.BUTTON_NEUTRAL;


    private final LongTaskDialogListener mListener;


    private View mView;


    LongTaskDialog(Context context, LongTaskDialogListener listener) {
        super(context);
        mListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.dialog_long_task, null);
        setView(mView);
        setTitle("Add New Task");
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
