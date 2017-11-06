package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */
import com.abyz.infotech.warroomapp.model.TABLE_DISTRICT;
import com.abyz.infotech.warroomapp.model.TABLE_LOCATION_AREA;
import com.abyz.infotech.warroomapp.model.TABLE_STATE;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.abyz.infotech.warroomapp.model.TABLE_EVENT_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import java.util.ArrayList;
import java.util.List;
public class EventResponse {

    @SerializedName("eventSpeaker_list")
    @Expose
    private List<TABLE_EVENT_SPEAKER> eventSpeakerList = new ArrayList<TABLE_EVENT_SPEAKER>();
    @SerializedName("event_list")
    @Expose
    private List<TABLE_EVENT> eventList = new ArrayList<TABLE_EVENT>();
    @SerializedName("speaker_list")
    @Expose
    private List<TABLE_SPEAKER> speakerList = new ArrayList<TABLE_SPEAKER>();

    @SerializedName("locarea_list")
    @Expose
    private List<TABLE_LOCATION_AREA> locareaList = new ArrayList<TABLE_LOCATION_AREA>();
    @SerializedName("state_list")
    @Expose
    private List<TABLE_STATE> stateList = new ArrayList<TABLE_STATE>();
    @SerializedName("district_list")
    @Expose
    private List<TABLE_DISTRICT> districtList = new ArrayList<TABLE_DISTRICT>();
    @SerializedName("status_msg")
    @Expose
    private String statusMsg;

    /**
     *
     * @return
     * The eventSpeakerList
     */
    public List<TABLE_EVENT_SPEAKER> getEventSpeakerList() {
        return eventSpeakerList;
    }

    /**
     *
     * @param eventSpeakerList
     * The eventSpeaker_list
     */
    public void setEventSpeakerList(List<TABLE_EVENT_SPEAKER> eventSpeakerList) {
        this.eventSpeakerList = eventSpeakerList;
    }

    /**
     *
     * @return
     * The eventList
     */
    public List<TABLE_EVENT> getEventList() {
        return eventList;
    }

    /**
     *
     * @param eventList
     * The event_list
     */
    public void setEventList(List<TABLE_EVENT> eventList) {
        this.eventList = eventList;
    }

    /**
     *
     * @return
     * The speakerList
     */
    public List<TABLE_SPEAKER> getSpeakerList() {
        return speakerList;
    }

    /**
     *
     * @param speakerList
     * The speaker_list
     */
    public void setSpeakerList(List<TABLE_SPEAKER> speakerList) {
        this.speakerList = speakerList;
    }

    /**
     *
     * @return
     * The statusMsg
     */
    public String getStatusMsg() {
        return statusMsg;
    }

    /**
     *
     * @param statusMsg
     * The status_msg
     */
    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    /**
     *
     * @return
     * The locareaList
     */
    public List<TABLE_LOCATION_AREA> getLocareaList() {
        return locareaList;
    }

    /**
     *
     * @param locareaList
     * The locarea_list
     */
    public void setLocareaList(List<TABLE_LOCATION_AREA> locareaList) {
        this.locareaList = locareaList;
    }


    /**
     *
     * @return
     * The stateList
     */
    public List<TABLE_STATE> getStateList() {
        return stateList;
    }

    /**
     *
     * @param stateList
     * The state_list
     */
    public void setStateList(List<TABLE_STATE> stateList) {
        this.stateList = stateList;
    }

    /**
     *
     * @return
     * The districtList
     */
    public List<TABLE_DISTRICT> getDistrictList() {
        return districtList;
    }

    /**
     *
     * @param districtList
     * The district_list
     */
    public void setDistrictList(List<TABLE_DISTRICT> districtList) {
        this.districtList = districtList;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
