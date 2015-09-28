package com.example.bkzhou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by bkzhou on 15-9-23.
 */
public class CrimeCameraActivity extends  SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

    }
}
