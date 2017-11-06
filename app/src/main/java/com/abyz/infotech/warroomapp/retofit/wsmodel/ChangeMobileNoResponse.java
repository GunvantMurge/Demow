package com.abyz.infotech.warroomapp.retofit.wsmodel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeMobileNoResponse {
    @SerializedName("rtnotp")
    @Expose
    private Integer rtnotp;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The rtnotp
     */
    public Integer getRtnotp() {
        return rtnotp;
    }

    /**
     *
     * @param rtnotp
     * The rtnotp
     */
    public void setRtnotp(Integer rtnotp) {
        this.rtnotp = rtnotp;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
