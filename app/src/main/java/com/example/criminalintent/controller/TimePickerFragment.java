package com.example.criminalintent.controller;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.criminalintent.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {
    private TimePicker mTimePicker;
    // private DatePicker mDatePicker;
    private Date mTime;
    public static final String EXTRA_CRIME_Time = "com.example.criminalintent.crimeTime";
    private static final String ARG_CRIME_TIME = "crimeTime";

    //////////////////////////////////////////////////////
    public static TimePickerFragment newInstance(Time time) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mTime = (Date) getArguments().getSerializable(ARG_CRIME_DATE);
    }

    public static TimePickerFragment newInstance(int num) {

        TimePickerFragment dialogFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }

    // dialogFragment.show(ParentFragment.this.getChildFragmentManager(), "dialog_fragment");
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater
                .inflate(R.layout.fragment_time_dialog, null, false);

        mTimePicker = view.findViewById(R.id.time_picker);

     //  initTimePicker();

       return new AlertDialog.Builder(getActivity())
               .setTitle(R.string.time_picker_title)
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @RequiresApi(api = Build.VERSION_CODES.M)
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       sendResult();
                   }
               })
               .setView(view)
               .create();
   }

/*
    private void initTimePicker() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);
       // calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mTimePicker.init(year, monthOfYear, dayOfMonth, null);
    }

*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendResult() {
        int hour = mTimePicker.getHour();
        int minoute = mTimePicker.getMinute();


        GregorianCalendar calendar = new GregorianCalendar(   0, 0,  hour,minoute,0);
        Date time = calendar.getTime();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CRIME_Time, time);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }


    }
