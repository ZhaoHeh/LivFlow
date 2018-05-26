package com.zhaoheh.livflow;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NodesListFragment extends Fragment {

    private int mTaskId;

    private List<TaskNodeData> mTaskNodesData;

    public NodesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskActivity activity = (TaskActivity) getActivity();
        TextView taskActivityName = activity.findViewById(R.id.task_activity_title_name);
        taskActivityName.setText("任务节点");
        mTaskId = activity.getTaskId();
        mTaskNodesData = getTaskNodesData(mTaskId);
    }

    private List<TaskNodeData> getTaskNodesData(int taskId) {
        List<TaskNodeData> filteredDataSet = new ArrayList<>();
        List<TaskNodeData> originDataSet = DataSupport.findAll(TaskNodeData.class);
        for (TaskNodeData data : originDataSet) {
            if (data.getBelongTo() == taskId) {
                filteredDataSet.add(data);
            }
        }
        Collections.sort(filteredDataSet, new Comparator<TaskNodeData>() {

            @Override
            public int compare(TaskNodeData o1, TaskNodeData o2) {
                return o2.getSerialNum() - o1.getSerialNum();
            }

        });
        return filteredDataSet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_nodes_list, container, false);
        TaskActivity activity = (TaskActivity) getActivity();
        NodeInfoAdapter adapter = new NodeInfoAdapter(activity, R.layout.node_item,
                mTaskNodesData);
        ListView listView = (ListView) view.findViewById(R.id.nodes_list_view);
        listView.setAdapter(adapter);
        return view;
    }
}
