package com.example.bkzhou.fragment.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bkzhou on 15-9-28.
 */
public class Photo {
    private static final String JSON_NAME = "filname";
    private String mFilename;
    public Photo(String filename){
        mFilename = filename;
    }
    public Photo(JSONObject jsonObject) throws JSONException {
        mFilename = jsonObject.getString(JSON_NAME);
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_NAME,mFilename);
        return  jsonObject;
    }
    public String getmFilename(){
        return mFilename;
    }
}
