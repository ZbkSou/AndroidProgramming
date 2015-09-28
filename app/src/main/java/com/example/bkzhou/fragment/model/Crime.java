package com.example.bkzhou.fragment.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-9.
 */
public class Crime {
    private static  final String JSON_ID = "id";
    private static  final String JSON_TITLE = "title";
    private static  final String JSON_SOLVED = "solved";
    private static  final String JSON_DATE = "date";
    private static  final String JSON_PHOTO = "photo";

    private UUID mid;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;

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


    public Crime (JSONObject jsonObject) throws JSONException{
        mid = UUID.fromString(jsonObject.getString(JSON_ID));
        if (jsonObject.has(JSON_TITLE)){
            mTitle = jsonObject.getString(JSON_TITLE);
        }
        mSolved = jsonObject.getBoolean(JSON_SOLVED);
        mDate = new Date(jsonObject.getLong(JSON_DATE));
        if(jsonObject.has(JSON_PHOTO)){
            mPhoto = new Photo(jsonObject.getJSONObject(JSON_PHOTO));
        }
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID,mid.toString());
        jsonObject.put(JSON_TITLE,mTitle.toString());
        jsonObject.put(JSON_SOLVED,mSolved);
        jsonObject.put(JSON_DATE,mDate.toString());
        if (mPhoto != null){
            jsonObject.put(JSON_PHOTO,mPhoto.toJSON());
        }
        return jsonObject;
    }

    public Photo getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }
}
