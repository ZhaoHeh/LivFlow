package com.zhaoheh.livflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int SHORT_TERM_FRAGMENT = 0;

    private static final int  LONG_TERM_FRAGMENT = 1;

    private Button mBtn;

    private LinearLayout mLinearLayoutShortTerm;

    private LinearLayout mLinearLayoutLongTerm;

    private TextView mTextViewShortTerm;

    private TextView mTextViewLongTerm;


    Button getAddTaskBtn() { return mBtn; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        mBtn = (Button) findViewById(R.id.main_activity_top_button);

        mLinearLayoutShortTerm = (LinearLayout) findViewById(R.id.ll_short_term);
        mLinearLayoutLongTerm = (LinearLayout) findViewById(R.id.ll_long_term);
        mTextViewShortTerm = (TextView) findViewById(R.id.tv_short_term);
        mTextViewLongTerm = (TextView) findViewById(R.id.tv_long_term);

        mLinearLayoutShortTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutShortTerm.setBackgroundColor(Color.parseColor("#eeeeee"));
                mTextViewShortTerm.setTextColor(getResources().getColor(R.color.appTheme));
                mLinearLayoutLongTerm.setBackgroundColor(getResources().getColor(R.color.appTheme));
                mTextViewLongTerm.setTextColor(Color.parseColor("#eeeeee"));
                setCurrentFragment(SHORT_TERM_FRAGMENT);
            }
        });

        mLinearLayoutLongTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutShortTerm.setBackgroundColor(getResources().getColor(R.color.appTheme));
                mTextViewShortTerm.setTextColor(Color.parseColor("#eeeeee"));
                mLinearLayoutLongTerm.setBackgroundColor(Color.parseColor("#eeeeee"));
                mTextViewLongTerm.setTextColor(getResources().getColor(R.color.appTheme));
                setCurrentFragment(LONG_TERM_FRAGMENT);
            }
        });

        setCurrentFragment(SHORT_TERM_FRAGMENT);
    }


    private void setCurrentFragment(int selected) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (selected) {
            case SHORT_TERM_FRAGMENT:
                transaction.replace(R.id.tasks_frame_layout, new ShortTermTasksFragment());
                break;
            case LONG_TERM_FRAGMENT:
                transaction.replace(R.id.tasks_frame_layout, new LongTermTasksFragment());
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
