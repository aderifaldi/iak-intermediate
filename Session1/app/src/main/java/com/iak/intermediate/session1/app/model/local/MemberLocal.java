package com.iak.intermediate.session1.app.model.local;

import java.io.Serializable;

/**
 * Created by aderifaldi on 28-Apr-16.
 */
public class MemberLocal implements Serializable {

    private String photo;
    private String name;
    private String address;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
