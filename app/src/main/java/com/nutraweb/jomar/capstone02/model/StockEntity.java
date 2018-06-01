package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 28/04/18.
 */

public class StockEntity implements Parcelable {

    private int productId;
    private String productName;
    private String thumb;
    private int qty;

    protected StockEntity(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        thumb = in.readString();
        qty = in.readInt();
    }

    public static final Creator<StockEntity> CREATOR = new Creator<StockEntity>() {
        @Override
        public StockEntity createFromParcel(Parcel in) {
            return new StockEntity(in);
        }

        @Override
        public StockEntity[] newArray(int size) {
            return new StockEntity[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(productId);
        parcel.writeString(productName);
        parcel.writeString(thumb);
        parcel.writeInt(qty);
    }
}
