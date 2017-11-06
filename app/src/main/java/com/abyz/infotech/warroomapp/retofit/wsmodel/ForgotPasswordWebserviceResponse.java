package com.abyz.infotech.warroomapp.retofit.wsmodel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordWebserviceResponse {

    @SerializedName("mobno")
    @Expose
    private String mobno;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("userid")
    @Expose
    private Integer userid;

    /**
     *
     * @return
     * The mobno
     */
    public String getMobno() {
        return mobno;
    }

    /**
     *
     * @param mobno
     * The mobno
     */
    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    /**
     *
     * @return
     * The otp
     */
    public Integer getOtp() {
        return otp;
    }

    /**
     *
     * @param otp
     * The otp
     */
    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}