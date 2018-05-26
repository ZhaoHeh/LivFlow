package com.zhaoheh.livflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public class NodeDialog extends AlertDialog implements DialogInterface.OnClickListener {

    public interface NodeDialogListener {
        void onForget(NodeDialog dialog);
        void onSubmit(NodeDialog dialog);
    }

    private static final int BUTTON_SUBMIT = DialogInterface.BUTTON_POSITIVE;
    private static final int BUTTON_FORGET = DialogInterface.BUTTON_NEUTRAL;

    private final NodeDialogListener mListener;

    private View mView;

    NodeDialog(Context context, NodeDialogListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.node_dialog_view, null);
        setView(mView);
        setTitle("Adding a new dialog");
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