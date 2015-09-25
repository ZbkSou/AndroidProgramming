package com.example.bkzhou.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        setHasOptionsMenu(true);
        getActivity().setTitle("Crimes");
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimes,getActivity());

        setListAdapter(crimeAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
       ListView listView = (ListView) v.findViewById(android.R.id.list);
//        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.HONEYCOMB){
            registerForContextMenu(listView);

//        }else {
//            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//                @Override
//                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
//
//                }
//
//                @Override
//                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                    MenuInflater inflater = actionMode.getMenuInflater();
//                    inflater.inflate(R.menu.crime_list_item_context,menu);
//                    return false;
//                }
//
//                @Override
//                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//                    return false;
//                }
//
//                @Override
//                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                    return false;
//                    switch (menuItem.getItemId()){
//                        case R.id.menu_item_delete_crime:
//                            CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
//                            CrimeLab crimeLab = CrimeLab.get(getActivity());
//                            for(int i = adapter.getCount()-1 ; i>= 0; i--){
//                                if(getListView().isItemChecked(i)){
//                                    crimeLab.deleteCrime((Crime)(adapter.getItem(i)));
//                                }
//                            }
//                            actionMode.finish();
//                            adapter.notifyDataSetChanged();
//                            return true;
//                        default:
//                            return false;
//                    }
//                }
//
//                @Override
//                public void onDestroyActionMode(ActionMode actionMode) {
//
//                }
//            });
//        }
        return  v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get((getActivity())).addCrime(crime);
                Intent i= new Intent(getActivity(),CrimePageActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID,crime.getMid());
                startActivityForResult(i,0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
        Crime crime = (Crime) adapter.getItem(position);
        switch (item.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
