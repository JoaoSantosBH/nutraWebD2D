package com.nutraweb.jomar.capstone02.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jomar on 10/04/18.
 */

public class ProductEntity implements Parcelable {
    private String productid;
    private String titulo;
    private String descricao;
    private String url;
    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    private int valor;

    public ProductEntity(Parcel in) {
        productid = in.readString();
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

    public ProductEntity() {

    }

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
        dest.writeString(productid);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(url);
        dest.writeInt(valor);
    }
}
