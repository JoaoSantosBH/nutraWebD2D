package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 12/04/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class UserEntity implements Parcelable {
    private int _id;
    private String name;
    private String email;
    private int phoneNumber;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    private int rank;

    public UserEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private UserEntity(Parcel in) {
        _id = in.readInt();
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readInt();
        rank = in.readInt();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeInt(phoneNumber);
        parcel.writeInt(rank);
    }
    @Override
    public String toString() {
        return name;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
