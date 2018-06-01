package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 10/04/18.
 */

public class ProductEntity implements Parcelable {
    private String titulo;
    private String descricao;
    private String url;
    private int valor;

    public ProductEntity(Parcel in) {
        titulo = in.readString();
        descricao = in.readString();
        url = in.readString();
        valor = in.readInt();
    }
    public static final Creator<ProductEntity> CREATOR = new Creator<ProductEntity>() {
        @Override
        public ProductEntity createFromParcel(Parcel in) {
            return new ProductEntity(in);
        }

        @Override
        public ProductEntity[] newArray(int size) {
            return new ProductEntity[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(url);
        dest.writeInt(valor);
    }
}
