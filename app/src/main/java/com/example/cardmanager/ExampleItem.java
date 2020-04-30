package com.example.cardmanager;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable {
    private String  mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(String imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    protected ExampleItem(Parcel in) {
        mImageResource = in.readString();
        mText1 = in.readString();
        mText2 = in.readString();
    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    public String getImageResource() {
        return mImageResource;
    }


    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageResource);
        dest.writeString(mText1);
        dest.writeString(mText2);
    }
}