package com.youknow.timeisgold.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Aaron on 09/09/2017.
 */
@Data
public class History implements Parcelable {
    long activityId;
    String name;
    String desc;
    int color;
    int icon;
    Type type;
    long startTime;
    long endTime;
    long elapsedTime;

    public History(Activity activity) {
        activityId = activity.getId();
        startTime = activity.getStartTime();
        endTime = activity.getEndTime();
        elapsedTime = activity.getRelElapsedTime();
        desc = activity.getDesc();
    }

    public void setCategory(Category category) {
        name = category.getName();
        color = category.getColor();
        icon = category.getIcon();
        type = category.getType();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.activityId);
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeInt(this.color);
        dest.writeInt(this.icon);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
        dest.writeLong(this.elapsedTime);
    }

    protected History(Parcel in) {
        this.activityId = in.readLong();
        this.name = in.readString();
        this.desc = in.readString();
        this.color = in.readInt();
        this.icon = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.elapsedTime = in.readLong();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
