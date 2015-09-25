package com.example.bkzhou.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by bkzhou on 15-9-23.
 */
public class CrimeCameraActivity extends  SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }


}
