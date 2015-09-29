package com.example.bkzhou.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bkzhou.fragment.model.Crime;
import com.example.bkzhou.fragment.model.CrimeLab;
import com.example.bkzhou.fragment.model.Photo;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by bkzhou on 15-9-9.
 */
public class CrimeFragment extends Fragment {
    public  static final String EXTRA_CRIME_ID = "CRIME";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button but;
    private CheckBox mSolvedCheckBox;
    private ImageView takePhoto;
    private ImageView mPhotoView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
        UUID crimeId  = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        Log.d("TAG",crimeId.toString());
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
//        Log.d("TAG",mCrime.toString());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_crime,container,false);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setmTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        but = (Button) v.findViewById(R.id.crime_date);
        but.setText(DateFormat.getDateInstance().format(mCrime.getmDate()));
//        but.setEnabled(false);
        but.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fm  = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
//                将Crimefragment设置正datePickerFragment的目标fragment
//                这应该是在Fragment中创建Fragment的唯一情况吧，其他时候直接更换Fragment就好
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//
//        }
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setmSolved(b);
            }

        });
        mPhotoView = (ImageView) v.findViewById(R.id.crime_imageview);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo p = mCrime.getmPhoto();
                if( p==null ){
                    return;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getmFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm,DIALOG_IMAGE);

            }
        });
        takePhoto = (ImageView) v.findViewById(R.id.takephoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CrimeCameraActivity.class);
//                startActivity(i);
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });

        return v;
    }



    public static  CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode ){
            case REQUEST_DATE:
                Date date  = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setmDate(date);
                but.setText(DateFormat.getDateInstance().format(mCrime.getmDate()));
                break;
            case REQUEST_PHOTO:
                String filename =data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
                if(filename != null){
//                    Log.i("CrimeFragment","filename: "+filename);
                    Photo p = new Photo(filename);
                    mCrime.setmPhoto(p);
                    Log.i("CrimeFragment", "Crime: " + mCrime.getmTitle() + "has a photo");
                    showPhoto();

                }
                break;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    private void showPhoto(){
        Photo p   =  mCrime.getmPhoto();
        BitmapDrawable b = null;
        if(p != null ){
            String path = getActivity().getFileStreamPath(p.getmFilename()).getAbsolutePath();
            b= PictureUtils.getScaledDrawable(getActivity(),path);
        }
        mPhotoView.setImageDrawable(b);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

}
