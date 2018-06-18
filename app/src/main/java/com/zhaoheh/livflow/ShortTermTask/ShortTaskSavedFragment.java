package com.zhaoheh.livflow.ShortTermTask;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoheh.livflow.PrimaryActivity;
import com.zhaoheh.livflow.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortTaskSavedFragment extends Fragment {

    private static final String TAG = "ShortTaskSavedFrg";

    private PrimaryActivity mActivity;

    private View mFragmentView;

    private RecyclerView mRecyclerView;

    private FloatingActionButton mFab;

    private ShortTaskDataSavedAdapter mAdapter;

    private List<ShortTaskData> mData;


    public ShortTaskSavedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mActivity = (PrimaryActivity) getActivity();
        mData = DataSupport.findAll(ShortTaskData.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_short_task_saved, container, false);
        mRecyclerView = mFragmentView.findViewById(R.id.short_task_saved_rv);
        mFab = mFragmentView.findViewById(R.id.short_task_editing_fab);
        initViews();
        return mFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        Log.d(TAG, "onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }


    private void initViews() {
        LinearLayoutManager llmForSaved = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(llmForSaved);
        mAdapter = new ShortTaskDataSavedAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setShortTaskEditingFragment();
            }
        });
    }

}
