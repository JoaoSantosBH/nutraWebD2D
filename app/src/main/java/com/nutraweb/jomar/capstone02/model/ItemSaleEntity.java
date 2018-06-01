package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 28/04/18.
 */

public class ItemSaleEntity implements Parcelable {

    private int numberSale;
    private int userId;
    private int productId;
    private String productName;
    private int productValor;

    protected ItemSaleEntity(Parcel in) {
        numberSale = in.readInt();
        userId = in.readInt();
        productId = in.readInt();
        productName = in.readString();
        productValor = in.readInt();
    }

    public static final Creator<ItemSaleEntity> CREATOR = new Creator<ItemSaleEntity>() {
        @Override
        public ItemSaleEntity createFromParcel(Parcel in) {
            return new ItemSaleEntity(in);
        }

        @Override
        public ItemSaleEntity[] newArray(int size) {
            return new ItemSaleEntity[size];
        }
    };

    public int getNumberSale() {
        return numberSale;
    }

    public void setNumberSale(int numberSale) {
        this.numberSale = numberSale;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public int getProductValor() {
        return productValor;
    }

    public void setProductValor(int productValor) {
        this.productValor = productValor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numberSale);
        parcel.writeInt(userId);
        parcel.writeInt(productId);
        parcel.writeString(productName);
        parcel.writeInt(productValor);
    }
}
