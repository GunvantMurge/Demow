package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */

import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_COORDINATER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaskResponse {

    @SerializedName("eventcordlist")
    @Expose
    private List<TABLE_EVENT_COORDINATER> eventcordlist = new ArrayList<TABLE_EVENT_COORDINATER>();
    @SerializedName("taskallot")
    @Expose
    private List<TABLE_TASK_ALLOTMENT> taskallot = new ArrayList<TABLE_TASK_ALLOTMENT>();
    @SerializedName("taskeventlist")
    @Expose
    private List<TABLE_EVENT_TASK> taskeventlist = new ArrayList<TABLE_EVENT_TASK>();

    @SerializedName("msg_status")
    @Expose
    private String msgStatus;

    /**
     *
     * @return
     * The eventcordlist
     */
    public List<TABLE_EVENT_COORDINATER> getEventcordlist() {
        return eventcordlist;
    }

    /**
     *
     * @param eventcordlist
     * The eventcordlist
     */
    public void setEventcordlist(List<TABLE_EVENT_COORDINATER> eventcordlist) {
        this.eventcordlist = eventcordlist;
    }

    /**
     *
     * @return
     * The taskallot
     */
    public List<TABLE_TASK_ALLOTMENT> getTaskallot() {
        return taskallot;
    }

    /**
     *
     * @param taskallot
     * The taskallot
     */
    public void setTaskallot(List<TABLE_TASK_ALLOTMENT> taskallot) {
        this.taskallot = taskallot;
    }

    /**
     *
     * @return
     * The taskeventlist
     */
    public List<TABLE_EVENT_TASK> getTaskeventlist() {
        return taskeventlist;
    }

    /**
     *
     * @param taskeventlist
     * The taskeventlist
     */
    public void setTaskeventlist(List<TABLE_EVENT_TASK> taskeventlist) {
        this.taskeventlist = taskeventlist;
    }

    /**
     *
     * @return
     * The msgStatus
     */
    public String getMsgStatus() {
        return msgStatus;
    }

    /**
     *
     * @param msgStatus
     * The msg_status
     */
    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
