package com.zhaoheh.livflow.LongTermTask;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhaoheh.livflow.MainActivity;
import com.zhaoheh.livflow.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LongTaskNodesFragment extends Fragment
        implements LongTaskNodeDialog.LongTaskNodeDialogListener{

    private static final String TAG = "LongTaskNodesFragment";


    private MainActivity mActivity;

    private View mFragmentView;

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
        mActivity = (MainActivity) getActivity();
        mLongTaskName = mActivity.getLongTaskName();
        setHasOptionsMenu(true);
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
        mFragmentView = inflater.inflate(R.layout.fragment_long_task_nodes, container, false);

        mRecyclerView = mFragmentView.findViewById(R.id.long_task_nodes_rv);

        LinearLayout llDetail = mFragmentView.findViewById(R.id.long_task_bottom_detail);
        LinearLayout llNodes = mFragmentView.findViewById(R.id.long_task_bottom_nodes);

        updateView();

        llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchLongTaskDetailFragment();
            }
        });

        return mFragmentView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_long_task_node_add, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.long_task_adding_node:
                Log.d(TAG, "onOptionsItemSelected long_task_adding_node");
                LongTaskNodeDialog dialog = new LongTaskNodeDialog(mActivity, this);
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onSubmit(LongTaskNodeDialog dialog) {
        View view = dialog.getView();
        final EditText editText = (EditText) view.findViewById(R.id.long_task_node_content);
        String content = editText.getText().toString();
        if (content.equals("")) {
            Log.d(TAG, "Node content is null and do nothing.");
        } else {
            LongTaskData taskData = dialog.getLongTaskData(mLongTaskName);

            if (taskData.getTaskId() == -1) return;
            Log.d(TAG, "taskData.getTaskId() != -1");

            LongTaskNodeData taskNodeData = new LongTaskNodeData();
            taskNodeData.setBelongTo(taskData.getTaskId());
            taskNodeData.setSerialNum(taskData.getNumNodes());
            taskNodeData.setContent(content);
            taskNodeData.setCreatedTime(dialog.getCurrentTime());
            taskNodeData.save();

            taskData.setNumNodes(taskData.getNumNodes()+1);
            taskData.save();

            updateView();
        }
    }


    @Override
    public void onForget(LongTaskNodeDialog dialog) {
    }


    private void updateView() {

        mData = getLongTaskNodesData(mLongTaskName);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new LongTaskNodeDataAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

}
