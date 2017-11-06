package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;

/**
 * Created by Android on 4/12/2016.
 */
public class UserProfileModel {


    private Integer usrTypeId;
    private String usrTypeName;

    public Integer getUsrTypeId() {
        return usrTypeId;
    }

    public void setUsrTypeId(Integer usrTypeId) {
        this.usrTypeId = usrTypeId;
    }

    public String getUsrTypeName() {
        return usrTypeName;
    }

    public void setUsrTypeName(String usrTypeName) {
        this.usrTypeName = usrTypeName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
