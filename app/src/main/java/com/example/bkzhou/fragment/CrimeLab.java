package com.example.bkzhou.fragment;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-11.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private CrimeLab(Context appContext){
        this.mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        for(int i =0;i<100;i++){
            Crime c = new Crime();
            c.setmTitle("Crime #"+i);
            c.setmSolved(i%2 == 0);
            mCrimes.add(c);
        }
    }
    public static  CrimeLab get(Context c){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(c);
        }
        return sCrimeLab;
    }
    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime (UUID uuid){
        for(Crime c: mCrimes){
            Log.d("mCrimes",c.getMid().toString());
            if(c.getMid().equals(uuid) ){

                return c;
            }
        }
        return null;
    }
}
