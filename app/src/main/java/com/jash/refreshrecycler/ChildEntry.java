package com.jash.refreshrecycler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jash
 * Date: 15-5-19
 * Time: 上午11:35
 */
public class ChildEntry implements Parcelable {
    private String text;

    public ChildEntry(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
