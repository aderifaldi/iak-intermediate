package com.iak.intermediate.session1.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by aderifaldi on 09-Apr-16.
 */
public class MemberGeneralInfo implements Serializable{

    private String name;
    private String palce_born;
    private Date birth_date;
    private String address;
    private String status;
    private String email;
    private String contact;
    private String facebook;
    private String twitter;
    private String photo;

    public String getName() {
        return name;
    }

    public String getPalce_born() {
        return palce_born;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getPhoto() {
        return photo;
    }
}
