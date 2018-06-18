package com.zhaoheh.livflow.DailyLifeRecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoheh.livflow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyRecordMainFragment extends Fragment {


    public DailyRecordMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_record_main, container, false);
    }

}
