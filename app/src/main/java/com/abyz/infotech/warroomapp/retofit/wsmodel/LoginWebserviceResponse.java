package com.abyz.infotech.warroomapp.retofit.wsmodel;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.abyz.infotech.warroomapp.model.TABLE_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_USER_PROFILES;
import com.abyz.infotech.warroomapp.model.TABLE_USER_TYPE;
import com.abyz.infotech.warroomapp.model.TABLE_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_USER;

import java.util.ArrayList;
import java.util.List;

public class LoginWebserviceResponse {

    @SerializedName("category_list")
    @Expose
    private List<TABLE_CATEGORY> mTABLE_CATEGORY = new ArrayList<TABLE_CATEGORY>();
    @SerializedName("profile_list")
    @Expose
    private List<TABLE_USER_PROFILES> profileList = new ArrayList<TABLE_USER_PROFILES>();
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("task_list")
    @Expose
    private List<TABLE_TASK> mTABLE_TASK = new ArrayList<TABLE_TASK>();
    @SerializedName("taskcategory_list")
    @Expose
    private List<TABLE_TASK_CATEGORY> mTABLE_TASK_CATEGORY = new ArrayList<TABLE_TASK_CATEGORY>();
    @SerializedName("userlist")
    @Expose
    private List<TABLE_USER> userlist = new ArrayList<TABLE_USER>();
    @SerializedName("usertype_list")
    @Expose
    private List<TABLE_USER_TYPE> mTABLE_USER_TYPE = new ArrayList<TABLE_USER_TYPE>();
    @SerializedName("usrspeaker_list")
    @Expose
    private List<TABLE_SPEAKER> mTABLE_SPEAKER = new ArrayList<TABLE_SPEAKER>();



    /**
     *
     * @return
     * The categoryList
     */
    public List<TABLE_CATEGORY> getCategoryList() {
        return mTABLE_CATEGORY;

    }

    /**
     *
     * @param tABLE_CATEGORY
     * The category_list
     */
    public void setTABLE_CATEGORY(List<TABLE_CATEGORY> tABLE_CATEGORY) {
        this.mTABLE_CATEGORY = tABLE_CATEGORY;

    }

    /**
     *
     * @return
     * The profileList
     */
    public List<TABLE_USER_PROFILES> getProfileList() {
        return profileList;
    }

    /**
     *
     * @param profileList
     * The profile_list
     */
    public void setProfileList(List<TABLE_USER_PROFILES> profileList) {
        this.profileList = profileList;
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


    /**
     *
     * @return
     * The taskList
     */
    public List<TABLE_TASK> getTaskList() {
        return mTABLE_TASK;
    }

    /**
     *
     * @param taskList
     * The task_list
     */
    public void setTaskList(List<TABLE_TASK> taskList) {
        this.mTABLE_TASK = taskList;
    }

    /**
     *
     * @return
     * The taskcategoryList
     */
    public List<TABLE_TASK_CATEGORY> getTaskcategoryList() {
        return mTABLE_TASK_CATEGORY;
    }

    /**
     *
     * @param taskcategoryList
     * The taskcategory_list
     */
    public void setTaskcategoryList(List<TABLE_TASK_CATEGORY> taskcategoryList) {
        this.mTABLE_TASK_CATEGORY = taskcategoryList;
    }

    /**
     *
     * @return
     * The userlist
     */
    public List<TABLE_USER> getUserlist() {
        return userlist;
    }

    /**
     *
     * @param userlist
     * The userlist
     */
    public void setUserlist(List<TABLE_USER> userlist) {
        this.userlist = userlist;
    }
    /**
     *
     * @return
     * The usertypeList
     */
    public List<TABLE_USER_TYPE> getUsertypeList() {
        return mTABLE_USER_TYPE;
    }

    /**
     *
     * @param usertypeList
     * The usertype_list
     */
    public void setUsertypeList(List<TABLE_USER_TYPE> usertypeList) {
        this.mTABLE_USER_TYPE = usertypeList;
    }


    /**
     *
     * @return
     * The usrspeakerList
     */
    public List<TABLE_SPEAKER> getUsrspeakerList() {
        return mTABLE_SPEAKER;
    }

    /**
     *
     * @param usrspeakerList
     * The usrspeaker_list
     */
    public void setUsrspeakerList(List<TABLE_SPEAKER> usrspeakerList) {
        this.mTABLE_SPEAKER = usrspeakerList;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}