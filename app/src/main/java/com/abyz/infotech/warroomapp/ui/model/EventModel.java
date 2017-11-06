package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Android on 4/12/2016.
 */
public class EventModel {

    private Integer eventId;
    private String eventName;
    private String eventLocation;
    private Long eventDate;
    private Long eventTime;
    private String eventDesc;
    private String eventImage;
    private Integer eventStatus;
    private String epTopic;
    private String epSpeakerDesc;
    private Integer epStatus;
    private Integer speakerId;
    private String speakerTypeName;
    private String speakerImage;
    private String speakerName;
    private String speakerMobileNo;
    private String taskName;

    public String getLaocationname() {
        return laocationname;
    }

    public void setLaocationname(String laocationname) {
        this.laocationname = laocationname;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getDistname() {
        return distname;
    }

    public void setDistname(String distname) {
        this.distname = distname;
    }

    private String laocationname;
    private String statename;
    private String distname;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }




    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public Integer getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Integer eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEpTopic() {
        return epTopic;
    }

    public void setEpTopic(String epTopic) {
        this.epTopic = epTopic;
    }

    public String getEpSpeakerDesc() {
        return epSpeakerDesc;
    }

    public void setEpSpeakerDesc(String epSpeakerDesc) {
        this.epSpeakerDesc = epSpeakerDesc;
    }

    public Integer getEpStatus() {
        return epStatus;
    }

    public void setEpStatus(Integer epStatus) {
        this.epStatus = epStatus;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }

    public String getSpeakerTypeName() {
        return speakerTypeName;
    }

    public void setSpeakerTypeName(String speakerTypeName) {
        this.speakerTypeName = speakerTypeName;
    }

    public String getSpeakerImage() {
        return speakerImage;
    }

    public void setSpeakerImage(String speakerImage) {
        this.speakerImage = speakerImage;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerMobileNo() {
        return speakerMobileNo;
    }

    public void setSpeakerMobileNo(String speakerMobileNo) {
        this.speakerMobileNo = speakerMobileNo;
    }




    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
