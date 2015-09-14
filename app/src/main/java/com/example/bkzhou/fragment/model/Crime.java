package com.example.bkzhou.fragment.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-9.
 */
public class Crime {
    private UUID mid;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Crime (){
        mid = UUID.randomUUID();
        mDate = new Date();

    }

    public UUID getMid() {
        return mid;
    }

    public void setMid(UUID mid) {
        this.mid = mid;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
