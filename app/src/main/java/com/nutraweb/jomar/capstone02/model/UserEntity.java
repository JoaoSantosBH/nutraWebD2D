package com.nutraweb.jomar.capstone02.model;

import java.io.Serializable;

/**
 * Created by jomar on 12/04/18.
 */

public class UserEntity implements Serializable {

    private int id;
    private String name;
    private String email;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    private int phoneNumber;

}
