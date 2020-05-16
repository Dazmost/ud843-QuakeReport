package com.example.android.quakereport;

import androidx.annotation.NonNull;

public class Earthquake {

    private double mMagnitude;

    private String mLocation;

//    private String mDate;
    private long mTimeInMilliseconds;

    private String mURL;

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url){
        mMagnitude=magnitude;
        mLocation=location;
        mTimeInMilliseconds=timeInMilliseconds;
        mURL = url;
    }


    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmURL(){
        return mURL;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
