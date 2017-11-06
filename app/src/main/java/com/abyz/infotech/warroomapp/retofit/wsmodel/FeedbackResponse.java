package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */

import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeedbackResponse {

    @SerializedName("feed_status")
    @Expose
    private Integer feedStatus;

    /**
     *
     * @return
     * The feedStatus
     */
    public Integer getFeedStatus() {
        return feedStatus;
    }

    /**
     *
     * @param feedStatus
     * The feed_status
     */
    public void setFeedStatus(Integer feedStatus) {
        this.feedStatus = feedStatus;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
