package com.example.bkzhou.fragment.model;

import android.content.Context;
import android.util.Log;

import com.example.bkzhou.fragment.CriminalIntentJSONSeriallizer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-11.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FIMENAME = "crimes.json";
    private CriminalIntentJSONSeriallizer mSer;

    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private CrimeLab(Context appContext){
        this.mAppContext = appContext;
        mSer = new CriminalIntentJSONSeriallizer(mAppContext,FIMENAME);
//        mCrimes = new ArrayList<Crime>();
        try{
            mCrimes = mSer.loadCrimes();
        }catch (Exception e){
            mCrimes = new ArrayList<Crime>();
            Log.d(TAG,"Error loading crimes:",e);
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

            if(c.getMid().equals(uuid) ){

                return c;
            }
        }
        return null;
    }
    public void addCrime(Crime c){
        mCrimes.add(c);

    }
    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }
    public boolean saveCrimes(){
        try{
            mSer.saveCrimes(mCrimes);
            Log.d(TAG,"crime saved to file");
            return true;

        }catch (Exception e){
            Log.e(TAG,"Error saving crimes : "+e);
            return false;
        }
    }
    public ArrayList<Crime> getmCrimes(){
        return mCrimes;
    }
}
