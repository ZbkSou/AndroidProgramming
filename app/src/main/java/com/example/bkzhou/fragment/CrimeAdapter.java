package com.example.bkzhou.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bkzhou.fragment.model.Crime;

import java.util.ArrayList;

/**
 * Created by bkzhou on 15-9-11.
 */
public class CrimeAdapter extends BaseAdapter {
    private ArrayList<Crime> crimes;
    private Context context;
    public CrimeAdapter(ArrayList<Crime> crimes,Context context) {
        this.crimes = crimes;
        this.context = context;
    }
    @Override
    public int getCount() {
        return crimes.size();
    }

    @Override
    public Object getItem(int i) {
        return crimes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder holder = null;
        if(view == null){
            holder = new ViewHoder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item_crime,null);
            holder.data = (TextView) view.findViewById(R.id.crime_list_itme_date);
            holder.sovel = (CheckBox) view.findViewById(R.id.crime__list_itme_solved);
            holder.title = (TextView) view.findViewById(R.id.crimr_list_itme_title);
            view.setTag(holder);
        }else{
            holder = (ViewHoder)view.getTag();
        }
       holder.data.setText(crimes.get(i).getmDate().toString());
        holder.sovel.setChecked(crimes.get(i).ismSolved());
        holder.title.setText(crimes.get(i).getmTitle());

        return view;
    }
    private final class ViewHoder{
        public TextView title;
        public TextView data;
        public CheckBox sovel;
    }
}
