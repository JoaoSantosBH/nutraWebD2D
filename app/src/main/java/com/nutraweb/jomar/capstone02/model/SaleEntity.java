package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jomar on 28/04/18.
 */

public class SaleEntity implements Parcelable {
    private List<ProductEntity> itens;
    private int qty;
    private int total;
    private int numberSale;
    private int userId;
    private String date;

    public SaleEntity() {

    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



    public SaleEntity(Parcel in) {
        numberSale = in.readInt();
        userId = in.readInt();
        date = in.readString();
        itens = in.readArrayList(ProductEntity.class.getClassLoader());
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
        parcel.writeArray(new List[]{itens});
        parcel.writeInt(qty);
        parcel.writeInt(total);
    }

    public List<ProductEntity> getItens() {
        return itens;
    }

    public void setItens(List<ProductEntity> itens) {
        this.itens = itens;
    }
}
