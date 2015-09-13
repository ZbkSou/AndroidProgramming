package com.example.bkzhou.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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

        Intent i  = new Intent(getActivity(),CrimeActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID,c.getMid());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }
}
