package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */

import com.abyz.infotech.warroomapp.model.TABLE_EVENT_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventTaskAddsResponse {


    @SerializedName("eventtasklist")
    @Expose
    private List<TABLE_EVENT_TASK> eventtasklist = new ArrayList<TABLE_EVENT_TASK>();
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     *
     * @return
     * The eventtasklist
     */
    public List<TABLE_EVENT_TASK> getEventtasklist() {
        return eventtasklist;
    }

    /**
     *
     * @param eventtasklist
     * The eventtasklist
     */
    public void setEventtasklist(List<TABLE_EVENT_TASK> eventtasklist) {
        this.eventtasklist = eventtasklist;
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
