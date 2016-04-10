package com.iak.intermediate.session1.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 09-Apr-16.
 */
public class Member implements Serializable {

    private MemberGeneralInfo general_info;
    private MemberEducation[] education;

    public MemberGeneralInfo getGeneral_info() {
        return general_info;
    }

    public MemberEducation[] getEducation() {
        return education;
    }
}
