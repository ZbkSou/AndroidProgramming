package com.example.bkzhou.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by bkzhou on 15-9-11.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
