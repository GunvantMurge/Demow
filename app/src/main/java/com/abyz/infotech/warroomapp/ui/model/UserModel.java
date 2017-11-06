package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;

/**
 * Created by Android on 4/12/2016.
 */
public class UserModel {


    private Integer usrId;
    private String usrName;
    private String usrMobile;
    private String usrAddress;
    private String usrImage;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrMobile() {
        return usrMobile;
    }

    public void setUsrMobile(String usrMobile) {
        this.usrMobile = usrMobile;
    }

    public String getUsrAddress() {
        return usrAddress;
    }

    public void setUsrAddress(String usrAddress) {
        this.usrAddress = usrAddress;
    }

    public String getUsrImage() {
        return usrImage;
    }

    public void setUsrImage(String usrImage) {
        this.usrImage = usrImage;
    }





    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
