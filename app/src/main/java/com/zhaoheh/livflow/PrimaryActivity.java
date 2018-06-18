package com.zhaoheh.livflow;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.zhaoheh.livflow.DailyLifeRecord.DailyRecordMainFragment;
import com.zhaoheh.livflow.LongTermTask.LongTaskData;
import com.zhaoheh.livflow.LongTermTask.LongTaskDetailFragment;
import com.zhaoheh.livflow.LongTermTask.LongTaskFragment;
import com.zhaoheh.livflow.LongTermTask.LongTaskNodeData;
import com.zhaoheh.livflow.LongTermTask.LongTaskNodesFragment;
import com.zhaoheh.livflow.ShortTermTask.ShortTaskData;
import com.zhaoheh.livflow.ShortTermTask.ShortTaskEditingFragment;
import com.zhaoheh.livflow.ShortTermTask.ShortTaskSavedFragment;
import com.zhaoheh.livflow.TaskCalendar.TaskCalendarMainFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PrimaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "PrimaryActivity";

    private static final int SHORT_TASK = 0;
    private static final int LONG_TASK = 1;
    private static final int TASK_CALENDAR = 2;
    private static final int DAILY_RECORD = 3;

    private DrawerLayout mDrawerLayout;

    private CoordinatorLayout mCoordinatorLayout;


    private String mLongTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        // 获取两个关键的Layout布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // 设置标题栏actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 设置标题栏弹出滑动菜单的按钮HomeAsUp
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        // 设置滑动菜单(由NavigationView实现)的点击响应事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_short_task);
        navigationView.setNavigationItemSelectedListener(this);

        // 设置初始的fragment
        setFragment(SHORT_TASK);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_primary, menu);
//        return true;
//    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_short_task:
                setFragment(SHORT_TASK);
                break;
            case R.id.nav_long_task:
                setFragment(LONG_TASK);
                break;
            case R.id.nav_task_calendar:
                setFragment(TASK_CALENDAR);
                break;
            case R.id.nav_daily_record:
                setFragment(DAILY_RECORD);
                break;
            case R.id.nav_data_sync:
                dataSync();
                break;
            case R.id.nav_check_update:
                Snackbar.make(mCoordinatorLayout, R.string.nav_check_update_en, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_settings:
                Snackbar.make(mCoordinatorLayout, R.string.nav_settings_en, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }


    private void setFragment(int selected) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (int i=0; i<fragments.size(); i++) fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (selected) {
            case SHORT_TASK:
                transaction.replace(R.id.fragment, new ShortTaskSavedFragment());
                break;
            case LONG_TASK:
                transaction.replace(R.id.fragment, new LongTaskFragment());
                break;
            case TASK_CALENDAR:
                transaction.replace(R.id.fragment, new TaskCalendarMainFragment());
                break;
            case DAILY_RECORD:
                transaction.replace(R.id.fragment, new DailyRecordMainFragment());
            default:
                break;
        }
        transaction.commit();
    }


    private void dataSync() {
        Snackbar.make(mCoordinatorLayout, R.string.nav_data_sync_en, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public void setShortTaskEditingFragment() {
        ShortTaskEditingFragment fragment = new ShortTaskEditingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void setLongTaskDetailFragment(String name) {
        mLongTaskName = name;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, new LongTaskDetailFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public String getLongTaskName() {
        return mLongTaskName;
    }


    public void switchLongTaskDetailFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, new LongTaskDetailFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void switchLongTaskNodesFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, new LongTaskNodesFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
