package com.zhaoheh.livflow.ShortTermTask;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
public class ShortTaskEditingFragment extends Fragment {

    private static final String TAG = "ShortTaskEditingFrg";


    private List<ShortTaskData> mData;

    private PrimaryActivity mActivity;

    private View mFragmentView;

    private RecyclerView mRecyclerView;

    private ShortTaskDataEditingAdapter mAdapter;

    private FloatingActionButton mFAB;


    public ShortTaskEditingFragment() {
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
        mData = DataSupport.findAll(ShortTaskData.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        mActivity = (PrimaryActivity) getActivity();
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_short_task_editing,
                container, false);

        mRecyclerView = mFragmentView.findViewById(R.id.short_task_editing_rv);
        mFAB = (FloatingActionButton) mFragmentView.findViewById(R.id.short_task_adding_fab);

        initView();

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


    private void initView() {

        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new ShortTaskDataEditingAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new ShortTaskEditingFragment
                .TouchHelperCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        mFAB.setBackgroundColor(Color.parseColor("#ffffff"));
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewShortTermTask();
            }
        });
    }


    private class TouchHelperCallback extends ItemTouchHelper.Callback {

        private ShortTaskDataEditingAdapter mAdapter;

        public TouchHelperCallback(ShortTaskDataEditingAdapter adapter) {
            mAdapter = adapter;
        }

        /**
         * 设置Drag/Swipe的Flag
         * 这里我们把滑动(Drag)的四个方向全都设置上了,说明Item可以随意移动
         * 然后把删除(暂且叫删除/swipe)的方向设置为Start和End,说明可以水平拖动删除
         */
        @Override
        public int getMovementFlags(RecyclerView rv, RecyclerView.ViewHolder vh) {
            int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            int swipeFlag = ItemTouchHelper.END;
            return makeMovementFlags(dragFlag, swipeFlag);
        }


        /**
         * 回调
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            mAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }


        /**
         * 回调
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onSwipe(viewHolder.getAdapterPosition());
        }


        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }


        /**
         * Item长按是否可以拖拽
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }


    private void addNewShortTermTask() {
        mAdapter.temporarilySaveContent(mRecyclerView);
        List<ShortTaskData> tempData = mAdapter.getData();
        ShortTaskData newItem = new ShortTaskData();
        newItem.setContent("New Item Added");
        tempData.add(newItem);
        mAdapter.setData(tempData);
        mAdapter.notifyDataSetChanged();
    }

}
