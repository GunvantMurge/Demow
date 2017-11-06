package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */

import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventTaskAllotmentResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("taskallot")
    @Expose
    private List<TABLE_TASK_ALLOTMENT> taskallot = new ArrayList<TABLE_TASK_ALLOTMENT>();

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



    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
