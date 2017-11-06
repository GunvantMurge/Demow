package com.abyz.infotech.warroomapp.retofit.wsmodel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusWebserviceResponse {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("hlp_id")
    @Expose
    private Integer hlpId;


    public Integer getBk_id() {
        return bk_id;
    }

    public void setBk_id(Integer bk_id) {
        this.bk_id = bk_id;
    }

    @SerializedName("bk_id")
    @Expose

    private Integer bk_id;


    public StatusWebserviceResponse() {
    }





    /**
     *
     * @return
     * The hlpId
     */
    public Integer getHlpId() {
        return hlpId;
    }

    /**
     *
     * @param hlpId
     * The hlp_id
     */
    public void setHlpId(Integer hlpId) {
        this.hlpId = hlpId;
    }


    public StatusWebserviceResponse(String status) {
        this.status = status;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
