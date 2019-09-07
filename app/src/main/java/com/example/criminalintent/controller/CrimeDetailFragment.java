package com.example.criminalintent.controller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.example.criminalintent.R;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.repository.CrimeRepository;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeDetailFragment extends Fragment {
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String ARG_CRIME_ID = "crimeId";
    public static final String TAG_DATE_PICKER = "DatePicker";
    public static final String TAG_TIME_PICKER = "TimePicker";
    public static final int REQUEST_CODE_DATE_PICKER = 0;

    private EditText mEditTextTitle;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxSolved;
    private List<Crime> mCrimes;
    private Crime mCrime;
    private Date time;
   private Date date;
    public CrimeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * @param uuid id of crime for showing detail
     * @return
     */
    public static Fragment newInstance(UUID uuid) {
        CrimeDetailFragment fragment = new CrimeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(CrimeDetailFragment.ARG_CRIME_ID, uuid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(SingleFragmentActivity.TAG, "CrimeFragment:onCreate");

        UUID id = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeRepository.getInstance().getCrime(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        initUI(view);
        initListeners();

        return view;
    }

    private void initListeners() {
        mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCheckBoxSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mCrime.setSolved(checked);
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeDetailFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER);
            }
        });
    }

    private void initUI(View view) {
        mEditTextTitle = view.findViewById(R.id.edittext_title);
        mButtonDate = view.findViewById(R.id.button_date);
        mButtonTime = view.findViewById(R.id.button_time);
        mCheckBoxSolved = view.findViewById(R.id.checkbox_solved);


        mEditTextTitle.setText(mCrime.getTitle());

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = format.format(new Date());
        mButtonDate.setText(dateString);
        //"Time: " + dateFormat.format(date)

        SimpleDateFormat format1 = new SimpleDateFormat("hh:MM");
        String timeString = format1.format(new Date());
        mButtonTime.setText(timeString);

        mCheckBoxSolved.setChecked(mCrime.getSolved());


        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mCrime.getDate());
                timePickerFragment.setTargetFragment(CrimeDetailFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
             date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_CRIME_DATE);
            SimpleDateFormat format11 = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = format11.format(date);
            mButtonDate.setText(dateString);

            mCrime.setDate(date);
           // Toast.makeText(getActivity(),String.valueOf(date) , Toast.LENGTH_LONG).show();
            mCrime.setDate(time);
            //Toast.makeText(new CrimeListActivity(),String.valueOf(time) , Toast.LENGTH_LONG) .show();
        }

            if (requestCode == REQUEST_CODE_TIME_PICKER) {
                time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_CRIME_TIME);

                SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");
                String timeString = format1.format(time);
                mButtonTime.setText(timeString);
                mCrime.setDate(date);
                mCrime.setDate(time);


               }





            }

        }
