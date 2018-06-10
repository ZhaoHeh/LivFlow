package com.zhaoheh.livflow;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortTermTasksFragment extends Fragment {

    private MainActivity mMainActivity;

    private View mPageView;

    private RecyclerView mSavedRecyclerView;

    private RecyclerView mEditingRecyclerView;

    private SavedShortTermTaskDataAdapter mSavedAdapter;

    private EditingShortTermTaskDataAdapter mEditingAdapter;

    private List<ShortTermTaskData> mData;

    private List<ShortTermTaskData> mEmptyData = new ArrayList<>();

    private Button mBtn;

    private FloatingActionButton mFAB;

    public ShortTermTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void clickOnEditingBtn() {

        updateData();

        mSavedAdapter.setData(mEmptyData);
        mEditingAdapter.setData(mData);

        mSavedAdapter.notifyDataSetChanged();
        mEditingAdapter.notifyDataSetChanged();

        mBtn.setText("Save");
        mFAB.show();
    }


    private void clickOnSavingBtn() {

        InputMethodManager im = (InputMethodManager) mMainActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(
                mMainActivity.getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        mEditingAdapter.temporarilySaveContent(mEditingRecyclerView);

        DataSupport.deleteAll(ShortTermTaskData.class);

        List<ShortTermTaskData> tempDataSet = mEditingAdapter.getData();
        for (ShortTermTaskData tempData : tempDataSet) {
            ShortTermTaskData newData = new ShortTermTaskData();
            newData.setContent(tempData.getContent());
            newData.setState(tempData.getState());
            newData.setTaskId(tempData.getTaskId());
            newData.setCreatedTime(tempData.getCreatedTime());
            newData.save();
        }

        updateData();

        mSavedAdapter.setData(mData);
        mEditingAdapter.setData(mEmptyData);

        mSavedAdapter.notifyDataSetChanged();
        mEditingAdapter.notifyDataSetChanged();

        mBtn.setText("Edit");
        mFAB.hide();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        mBtn = mMainActivity.getAddTaskBtn();

        mPageView = inflater.inflate(R.layout.page_short_term_tasks, container, false);

        mSavedRecyclerView = mPageView.findViewById(R.id.page_short_term_tasks_rv_saved);
        mEditingRecyclerView = mPageView.findViewById(R.id.page_short_term_tasks_rv_editing);
        mFAB = (FloatingActionButton) mPageView.findViewById(R.id.page_short_term_tasks_fab);
        initViews();
        return mPageView;
    }

    private void initViews() {

        updateData();

        mBtn.setText("Edit");
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtn.getText().equals("Edit")) {
                    clickOnEditingBtn();
                } else if (mBtn.getText().equals("Save")) {
                    clickOnSavingBtn();
                }
            }
        });

        LinearLayoutManager llmForSaved = new LinearLayoutManager(mMainActivity);
        mSavedRecyclerView.setLayoutManager(llmForSaved);
        mSavedAdapter = new SavedShortTermTaskDataAdapter(mData);
        mSavedRecyclerView.setAdapter(mSavedAdapter);

        LinearLayoutManager llmForEditing = new LinearLayoutManager(mMainActivity);
        mEditingRecyclerView.setLayoutManager(llmForEditing);
        mEditingAdapter = new EditingShortTermTaskDataAdapter(mEmptyData);
        mEditingRecyclerView.setAdapter(mEditingAdapter);

        ItemTouchHelper.Callback callback = new TouchHelperCallback(mEditingAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mEditingRecyclerView);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewShortTermTask();
            }
        });
        mFAB.hide();
    }


    private void updateData() {
        List<ShortTermTaskData> sTaskDataListFromDB = DataSupport.findAll(ShortTermTaskData.class);
        mData = sTaskDataListFromDB;
    }


    private class TouchHelperCallback extends ItemTouchHelper.Callback {

        private EditingShortTermTaskDataAdapter mAdapter;

        public TouchHelperCallback(EditingShortTermTaskDataAdapter adapter) {
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
        InputMethodManager im = (InputMethodManager) mMainActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(
                mMainActivity.getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        mEditingAdapter.temporarilySaveContent(mEditingRecyclerView);
        List<ShortTermTaskData> tempData = mEditingAdapter.getData();
        ShortTermTaskData newItem = new ShortTermTaskData();
        newItem.setContent("New Item Added");
        tempData.add(newItem);
        mEditingAdapter.setData(tempData);
        mEditingAdapter.notifyDataSetChanged();
    }
}
