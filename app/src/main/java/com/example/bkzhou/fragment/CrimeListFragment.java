package com.example.bkzhou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.example.bkzhou.fragment.model.Crime;
import com.example.bkzhou.fragment.model.CrimeLab;

import java.util.ArrayList;

/**
 * Created by bkzhou on 15-9-11.
 */
public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Crimes");
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimes,getActivity());

        setListAdapter(crimeAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Crime c = (Crime) ((CrimeAdapter)getListAdapter()).getItem(position);
//        使用viewpage废弃CrimeActivity
//        Intent i  = new Intent(getActivity(),CrimeActivity.class);
        Intent i =new Intent(getActivity(),CrimePageActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID,c.getMid());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }
}
