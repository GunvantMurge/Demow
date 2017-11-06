package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;

/**
 * Created by Android on 4/12/2016.
 */
public class TaskModel {

    private Integer etId;
    private Integer etTcId;
    private Integer etEventId;
    private Long et_StartTime;
    private Long et_End_Time;
    private String et_evnt_name;
    private Integer etStatus;

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    private String eventImage;

    private Integer taId;
    private Integer taEtId;
    private Integer taUsrId;
    private Long taStartTime;
    private Long taEndTime;

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

    private Long taStartDate;
    private Long taEndDate;
    private String taConfirm;
    private Integer taStaffId;
    private String taStaffName;
    private Integer taStatusId;

    private Integer ecId;
    private Integer ecUserId;
    private Integer ecEventId;
    private String ecEventName;
    private String ecTopic;
    private String ecDesc;
    private String ecUserMobileNo;
    private String ecUserName;
    private Integer ecStatusId;
    private Long timeStamp;

    public String getTskName() {
        return tskName;
    }

    public void setTskName(String tskName) {
        this.tskName = tskName;
    }

    private String tskName;

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    private Integer taskid;

    public Integer getEtId() {
        return etId;
    }

    public void setEtId(Integer etId) {
        this.etId = etId;
    }

    public Integer getEtTcId() {
        return etTcId;
    }

    public void setEtTcId(Integer etTcId) {
        this.etTcId = etTcId;
    }

    public Integer getEtEventId() {
        return etEventId;
    }

    public void setEtEventId(Integer etEventId) {
        this.etEventId = etEventId;
    }

    public Long getEt_StartTime() {
        return et_StartTime;
    }

    public void setEt_StartTime(Long et_StartTime) {
        this.et_StartTime = et_StartTime;
    }

    public Long getEt_End_Time() {
        return et_End_Time;
    }

    public void setEt_End_Time(Long et_End_Time) {
        this.et_End_Time = et_End_Time;
    }

    public String getEt_evnt_name() {
        return et_evnt_name;
    }

    public void setEt_evnt_name(String et_evnt_name) {
        this.et_evnt_name = et_evnt_name;
    }

    public Integer getEtStatus() {
        return etStatus;
    }

    public void setEtStatus(Integer etStatus) {
        this.etStatus = etStatus;
    }

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

    public Integer getEcId() {
        return ecId;
    }

    public void setEcId(Integer ecId) {
        this.ecId = ecId;
    }

    public Integer getEcUserId() {
        return ecUserId;
    }

    public void setEcUserId(Integer ecUserId) {
        this.ecUserId = ecUserId;
    }

    public Integer getEcEventId() {
        return ecEventId;
    }

    public void setEcEventId(Integer ecEventId) {
        this.ecEventId = ecEventId;
    }

    public String getEcEventName() {
        return ecEventName;
    }

    public void setEcEventName(String ecEventName) {
        this.ecEventName = ecEventName;
    }

    public String getEcTopic() {
        return ecTopic;
    }

    public void setEcTopic(String ecTopic) {
        this.ecTopic = ecTopic;
    }

    public String getEcDesc() {
        return ecDesc;
    }

    public void setEcDesc(String ecDesc) {
        this.ecDesc = ecDesc;
    }

    public String getEcUserMobileNo() {
        return ecUserMobileNo;
    }

    public void setEcUserMobileNo(String ecUserMobileNo) {
        this.ecUserMobileNo = ecUserMobileNo;
    }

    public String getEcUserName() {
        return ecUserName;
    }

    public void setEcUserName(String ecUserName) {
        this.ecUserName = ecUserName;
    }

    public Integer getEcStatusId() {
        return ecStatusId;
    }

    public void setEcStatusId(Integer ecStatusId) {
        this.ecStatusId = ecStatusId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }



    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
