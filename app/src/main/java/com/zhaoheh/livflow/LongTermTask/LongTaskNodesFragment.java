package com.zhaoheh.livflow.LongTermTask;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhaoheh.livflow.PrimaryActivity;
import com.zhaoheh.livflow.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTaskNodesFragment extends Fragment {

    private static final String TAG = "LongTaskNodesFragment";


    private PrimaryActivity mActivity;

    private String mLongTaskName;

    private List<LongTaskNodeData> mData;

    private RecyclerView mRecyclerView;

    private LongTaskNodeDataAdapter mAdapter;


    public LongTaskNodesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (PrimaryActivity) getActivity();
        mLongTaskName = mActivity.getLongTaskName();
        mData = getLongTaskNodesData(mLongTaskName);
    }


    private List<LongTaskNodeData> getLongTaskNodesData(String name) {
        LongTaskData taskData = getLongTaskData(name);
        List<LongTaskNodeData> filteredDataSet = new ArrayList<>();
        List<LongTaskNodeData> originDataSet = DataSupport.findAll(LongTaskNodeData.class);
        for (LongTaskNodeData data : originDataSet) {
            if (data.getBelongTo() == taskData.getTaskId()) {
                filteredDataSet.add(data);
            }
        }
        Collections.sort(filteredDataSet, new Comparator<LongTaskNodeData>() {

            @Override
            public int compare(LongTaskNodeData o1, LongTaskNodeData o2) {
                return o2.getSerialNum() - o1.getSerialNum();
            }

        });
        return filteredDataSet;
    }


    private LongTaskData getLongTaskData(String name) {
        List<LongTaskData> dataSet = DataSupport.findAll(LongTaskData.class);
        for (LongTaskData data : dataSet) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        LongTaskData data = new LongTaskData();
        data.setName("Something wrong");
        data.setTaskId(-1);
        return data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_long_task_nodes, container, false);

        mRecyclerView = view.findViewById(R.id.long_task_nodes_rv);

        LinearLayout llDetail = view.findViewById(R.id.long_task_bottom_detail);
        LinearLayout llNodes = view.findViewById(R.id.long_task_bottom_nodes);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new LongTaskNodeDataAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchLongTaskDetailFragment();
            }
        });

        return view;
    }

}