package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;

/**
 * Created by Android on 4/12/2016.
 */
public class EventTaskAllotmentModel {


    private Integer taId;
    private Integer taEtId;
    private Integer taUsrId;
    private Long taStartTime;
    private Long taEndTime;
    private String taConfirm;
    private Integer taStaffId;
    private String taStaffName;
    private Integer taStatusId;
    private Long timeStamp;
    private Long taStartDate;
    private Long taEndDate;
    private String usrName;
    private String usrMobile;
    private String usrImage;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private String eventName;
    private String tskName;

    public Integer getTaId() {
        return taId;
    }

    public void setTaId(Integer taId) {
        this.taId = taId;
    }

    public Integer getTaEtId() {
        return taEtId;
    }

    public void setTaEtId(Integer taEtId) {
        this.taEtId = taEtId;
    }

    public Integer getTaUsrId() {
        return taUsrId;
    }

    public void setTaUsrId(Integer taUsrId) {
        this.taUsrId = taUsrId;
    }

    public Long getTaStartTime() {
        return taStartTime;
    }

    public void setTaStartTime(Long taStartTime) {
        this.taStartTime = taStartTime;
    }

    public Long getTaEndTime() {
        return taEndTime;
    }

    public void setTaEndTime(Long taEndTime) {
        this.taEndTime = taEndTime;
    }

    public String getTaConfirm() {
        return taConfirm;
    }

    public void setTaConfirm(String taConfirm) {
        this.taConfirm = taConfirm;
    }

    public Integer getTaStaffId() {
        return taStaffId;
    }

    public void setTaStaffId(Integer taStaffId) {
        this.taStaffId = taStaffId;
    }

    public String getTaStaffName() {
        return taStaffName;
    }

    public void setTaStaffName(String taStaffName) {
        this.taStaffName = taStaffName;
    }

    public Integer getTaStatusId() {
        return taStatusId;
    }

    public void setTaStatusId(Integer taStatusId) {
        this.taStatusId = taStatusId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getTaStartDate() {
        return taStartDate;
    }

    public void setTaStartDate(Long taStartDate) {
        this.taStartDate = taStartDate;
    }

    public Long getTaEndDate() {
        return taEndDate;
    }

    public void setTaEndDate(Long taEndDate) {
        this.taEndDate = taEndDate;
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

    public String getUsrImage() {
        return usrImage;
    }

    public void setUsrImage(String usrImage) {
        this.usrImage = usrImage;
    }

    public String getTskName() {
        return tskName;
    }

    public void setTskName(String tskName) {
        this.tskName = tskName;
    }




    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
