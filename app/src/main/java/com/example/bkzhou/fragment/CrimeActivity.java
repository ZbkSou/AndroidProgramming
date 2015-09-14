package com.example.bkzhou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;


/**
 * Created by bkzhou on 15-9-9.
 */
public class CrimeActivity extends SingleFragmentActivity {
//添加viewpage不再使用此类


    @Override
    protected Fragment createFragment() {
        UUID crimeid = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
