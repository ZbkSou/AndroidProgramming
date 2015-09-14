package com.example.bkzhou.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by bkzhou on 15-9-14.
 */
public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        setPositiveButton设置确认按钮具体位置与系统版本有关
        return new AlertDialog.Builder(getActivity()).setTitle("Date of crime").setPositiveButton(android.R.string.ok,null).create();

    }
}
