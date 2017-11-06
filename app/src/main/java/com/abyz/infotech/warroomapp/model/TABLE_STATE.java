package com.abyz.infotech.warroomapp.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity mapped to table TABLE__STATE.
 */
public class TABLE_STATE {

    private Long id;
    @SerializedName("st_id")
    @Expose
    private Integer st_id;
    @SerializedName("st_status_id")
    @Expose
    private Integer st_status_id;
    @SerializedName("st_name")
    @Expose
    private String st_name;

    public TABLE_STATE() {
    }

    public TABLE_STATE(Long id) {
        this.id = id;
    }

    public TABLE_STATE(Long id, Integer st_id, Integer st_status_id, String st_name) {
        this.id = id;
        this.st_id = st_id;
        this.st_status_id = st_status_id;
        this.st_name = st_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSt_id() {
        return st_id;
    }

    public void setSt_id(Integer st_id) {
        this.st_id = st_id;
    }

    public Integer getSt_status_id() {
        return st_status_id;
    }

    public void setSt_status_id(Integer st_status_id) {
        this.st_status_id = st_status_id;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}