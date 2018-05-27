package com.zhaoheh.livflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortTermTasksFragment extends Fragment {

    private MainActivity mMainActivity;

    private View mPageView;

    private List<ShortTermTaskData> mData;


    public ShortTermTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
        Button addTaskBtn = mMainActivity.getAddTaskBtn();
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mMainActivity, "Button in short term fragment.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPageView = inflater.inflate(R.layout.page_short_term_tasks, container, false);
        updateShortTasksRecyclerView(mPageView);
        return mPageView;
    }


    private void updateShortTasksRecyclerView(View v) {
        updateData();
        RecyclerView recyclerView = v.findViewById(R.id.page_short_term_tasks_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        ShortTermTaskDataAdapter adapter = new ShortTermTaskDataAdapter(mData);
        recyclerView.setAdapter(adapter);
    }

    private void updateData() {
        mData = new ArrayList<>();
        ShortTermTaskData dataA = new ShortTermTaskData();
        ShortTermTaskData dataB = new ShortTermTaskData();
        ShortTermTaskData dataC = new ShortTermTaskData();
        ShortTermTaskData dataD = new ShortTermTaskData();
        ShortTermTaskData dataE = new ShortTermTaskData();
        ShortTermTaskData dataF = new ShortTermTaskData();
        ShortTermTaskData dataG = new ShortTermTaskData();
        ShortTermTaskData dataH = new ShortTermTaskData();
        ShortTermTaskData dataI = new ShortTermTaskData();
        ShortTermTaskData dataJ = new ShortTermTaskData();
        dataA.setContent("Short Term task A");
        dataB.setContent("Short Term task B");
        dataC.setContent("Short Term task C");
        dataD.setContent("Short Term task D");
        dataE.setContent("Short Term task E");
        dataF.setContent("Short Term task F");
        dataG.setContent("Short Term task G");
        dataH.setContent("Short Term task H");
        dataI.setContent("Short Term task I");
        dataJ.setContent("Short Term task J");
        mData.add(dataA);
        mData.add(dataB);
        mData.add(dataC);
        mData.add(dataD);
        mData.add(dataE);
        mData.add(dataF);
        mData.add(dataG);
        mData.add(dataH);
        mData.add(dataI);
        mData.add(dataJ);
    }

}
