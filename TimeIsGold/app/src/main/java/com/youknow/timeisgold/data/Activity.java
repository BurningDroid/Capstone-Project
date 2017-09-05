package com.youknow.timeisgold.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Aaron on 30/08/2017.
 */
@Data
public class Activity implements Parcelable {

    long id;
    boolean isRunning;
    long startTime;
    long endTime;
    long relStartTime;
    long relEndTime;
    long relElapsedTime;
    String desc;
    long categoryId;

    public Activity() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.isRunning ? (byte) 1 : (byte) 0);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
        dest.writeLong(this.relStartTime);
        dest.writeLong(this.relEndTime);
        dest.writeLong(this.relElapsedTime);
        dest.writeString(this.desc);
        dest.writeLong(this.categoryId);
    }

    protected Activity(Parcel in) {
        this.id = in.readLong();
        this.isRunning = in.readByte() != 0;
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.relStartTime = in.readLong();
        this.relEndTime = in.readLong();
        this.relElapsedTime = in.readLong();
        this.desc = in.readString();
        this.categoryId = in.readLong();
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel source) {
            return new Activity(source);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}
