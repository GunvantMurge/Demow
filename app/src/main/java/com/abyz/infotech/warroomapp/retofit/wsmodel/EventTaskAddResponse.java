package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */

import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_COORDINATER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_CATEGORY;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventTaskAddResponse {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("eventcordlist")
    @Expose
    private List<TABLE_EVENT_COORDINATER> eventcordlist = new ArrayList<TABLE_EVENT_COORDINATER>();
    @SerializedName("eventlist")
    @Expose
    private List<TABLE_EVENT> eventlist = new ArrayList<TABLE_EVENT>();
    @SerializedName("evlist")
    @Expose
    private List<TABLE_EVENT_TASK> evlist = new ArrayList<TABLE_EVENT_TASK>();
    @SerializedName("taskcategory")
    @Expose
    private List<TABLE_TASK_CATEGORY> taskcategory = new ArrayList<TABLE_TASK_CATEGORY>();
    @SerializedName("tasklist")
    @Expose
    private List<TABLE_TASK> tasklist = new ArrayList<TABLE_TASK>();


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
     * The eventlist
     */
    public List<TABLE_EVENT> getEventlist() {
        return eventlist;
    }

    /**
     *
     * @param eventlist
     * The eventlist
     */
    public void setEventlist(List<TABLE_EVENT> eventlist) {
        this.eventlist = eventlist;
    }

    /**
     *
     * @return
     * The evlist
     */
    public List<TABLE_EVENT_TASK> getEvlist() {
        return evlist;
    }

    /**
     *
     * @param evlist
     * The evlist
     */
    public void setEvlist(List<TABLE_EVENT_TASK> evlist) {
        this.evlist = evlist;
    }

    /**
     *
     * @return
     * The taskcategory
     */
    public List<TABLE_TASK_CATEGORY> getTaskcategory() {
        return taskcategory;
    }

    /**
     *
     * @param taskcategory
     * The taskcategory
     */
    public void setTaskcategory(List<TABLE_TASK_CATEGORY> taskcategory) {
        this.taskcategory = taskcategory;
    }

    /**
     *
     * @return
     * The tasklist
     */
    public List<TABLE_TASK> getTasklist() {
        return tasklist;
    }

    /**
     *
     * @param tasklist
     * The tasklist
     */
    public void setTasklist(List<TABLE_TASK> tasklist) {
        this.tasklist = tasklist;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
