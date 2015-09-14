package com.example.bkzhou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.bkzhou.fragment.model.Crime;
import com.example.bkzhou.fragment.model.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-13.
 */
public class CrimePageActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
//        通过代码设置id,需要在values——ids创建
        mViewPager.setId(R.id.viewPage);
        setContentView(mViewPager);
        mCrimes = CrimeLab.get(this).getCrimes();
        android.support.v4.app.FragmentManager fm  = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
//                获取子Fragment
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getMid());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
//        显示选择页面而不是第一个页面
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0;i<mCrimes.size();i++){
            if(mCrimes.get(i).getMid().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }

        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Crime crime = mCrimes.get(position);
                if(crime.getmTitle() != null){
                    setTitle(crime.getmTitle() );
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}
