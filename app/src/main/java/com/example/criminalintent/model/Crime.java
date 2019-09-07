package com.example.criminalintent.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Boolean getSolved() {
        return mSolved;
    }

    public void setSolved(Boolean solved) {
        mSolved = solved;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = generateRandomDate();
//        mDate = new Date();
    }

    private Date generateRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2000, 2019);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.getTime();
    }

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crime)) return false;
        Crime crime = (Crime) o;
        return Objects.equals(getId(), crime.getId());
    }
}
