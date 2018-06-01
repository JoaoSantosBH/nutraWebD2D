package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 28/04/18.
 */

public class SaleEntity implements Parcelable {

    private int numberSale;
    private int userId;
    private String date;
    private int qty;
    private int total;

    protected SaleEntity(Parcel in) {
        numberSale = in.readInt();
        userId = in.readInt();
        date = in.readString();
        qty = in.readInt();
        total = in.readInt();
    }

    public static final Creator<SaleEntity> CREATOR = new Creator<SaleEntity>() {
        @Override
        public SaleEntity createFromParcel(Parcel in) {
            return new SaleEntity(in);
        }

        @Override
        public SaleEntity[] newArray(int size) {
            return new SaleEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numberSale);
        parcel.writeInt(userId);
        parcel.writeString(date);
        parcel.writeInt(qty);
        parcel.writeInt(total);
    }
}
