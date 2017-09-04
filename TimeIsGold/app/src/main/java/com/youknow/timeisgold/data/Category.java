package com.youknow.timeisgold.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Aaron on 02/09/2017.
 */
@Data
public class Category implements Parcelable {

    long id;
    String name;
    int color;
    int icon;
    Type type;
    boolean isFavorite;

    public Category() {
    }

    public Category(String name, int color, int icon, Type type) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.type = type;
        this.isFavorite = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.color);
        dest.writeInt(this.icon);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
    }

    protected Category(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.color = in.readInt();
        this.icon = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.isFavorite = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
