package com.zhaoheh.livflow.ShortTermTask;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoheh.livflow.MainActivity;
import com.zhaoheh.livflow.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortTaskEditingFragment extends Fragment {

    private static final String TAG = "ShortTaskEditingFrg";


    private List<ShortTaskData> mData;

    private MainActivity mActivity;

    private View mFragmentView;

    private RecyclerView mRecyclerView;

    private ShortTaskDataEditingAdapter mAdapter;


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
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        mActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_short_task_editing,
                container, false);

        mRecyclerView = mFragmentView.findViewById(R.id.short_task_editing_rv);

        mData = DataSupport.findAll(ShortTaskData.class);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_short_task_editing_to_saved, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.short_task_adding_task:
                addNewShortTermTask();
                break;
            case R.id.short_task_editing_to_saved:
                saveDataAndBack();
                break;
            default:
                break;
        }
        return true;
    }


    /**向当前正在显示的RecyclerView中添加一个item
     * 由于我们实现了拖曳排序和滑动删除的功能，而这两个功能修改的其实是mAdapter中的数据
     * 因此把mAdapter的成员mData作为修改和保存的对象才是最直接和方便的
     */
    private void addNewShortTermTask() {
        Log.d(TAG, "addNewShortTermTask");
        mAdapter.saveAllItemContent(mRecyclerView);
        mAdapter.addNewShortTermTask();
    }


    /**LitePal十分强大, 对于一个数据对象如shortTaskData，它会视情况使用save()方法:
     * 如果此对象未保存过，则save()会添加此对象到数据库, 如果已保存过, 则save()方法会修改与此对象对应的数据表元素
     * 而且如果数据对象在程序进行过几次复制操作而产生的一系列对象，那么它们对应的数据表元素也将是同一个, 举例来讲:
     * mData = DataSupport.findAll(ShortTaskData.class);
     * dataA = mData.get(x); dataB = dataA;
     * 那么dataB和dataA对应的数据表元素是相同的,
     * 也就是说dataB.save()不会在数据表中新建元素, 而是将数据保存到dataA对应的元素中
     *
     * 然而, 这也会有一些问题, 那就是我们在通过拖曳排序和滑动删除操作的时候, 数据的顺序以及数据的被被删除是无法通过
     * save()方法完成的, 我们必须将数据库原有的数据全部清除, 把新的数据保存进去才可以将顺序信息, 被删除信息也保存到数据库.
     * 同时, 又因为mAdapter中的数据mData由this.mData赋值, 因此也是和数据库有映射的, 如果我们已经把数据库清空,
     * 对这些"记录在案"的数据对象, save()方法不再有效, 因此我们必须用new出来的数据对象替换原来的数据对象才能使用save()
     */
    private void saveDataAndBack() {
        Log.d(TAG, "saveDataAndBack");
        DataSupport.deleteAll(ShortTaskData.class);
        mAdapter.saveAllItemContent(mRecyclerView);
        List<ShortTaskData> dataSetFromAdapter = mAdapter.getData();
        for (ShortTaskData data : dataSetFromAdapter) {
            ShortTaskData dataToDatabase = new ShortTaskData();
            dataToDatabase.setContent(data.getContent());
            dataToDatabase.setCreatedTime(data.getCreatedTime());
            dataToDatabase.setState(data.getState());
            dataToDatabase.setTaskId(data.getTaskId());
            dataToDatabase.save();
        }
        mActivity.backLastFragment();
    }

}
