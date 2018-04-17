package com.nutraweb.jomar.capstone02.model;

import java.io.Serializable;

/**
 * Created by jomar on 10/04/18.
 */

public class ProductEntity implements Serializable {
    private int id;
    private String titulo;
    private String descricao;
    private String url;
    private int valor;

    public int getId() {
        return id;
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
}
