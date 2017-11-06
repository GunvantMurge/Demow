package com.abyz.infotech.warroomapp.retofit.wsmodel;

/**
 * Created by Android on 4/12/2016.
 */
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VolunteerListResponse {

    @SerializedName("status_msg")
    @Expose
    private String statusMsg;
    @SerializedName("vollist")
    @Expose
    private List<Vollist> vollist = new ArrayList<Vollist>();

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
     * The vollist
     */
    public List<Vollist> getVollist() {
        return vollist;
    }

    /**
     *
     * @param vollist
     * The vollist
     */
    public void setVollist(List<Vollist> vollist) {
        this.vollist = vollist;
    }
    public class Vollist {

        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("usertypeid")
        @Expose
        private Integer usertypeid;

        /**
         *
         * @return
         * The userid
         */
        public Integer getUserid() {
            return userid;
        }

        /**
         *
         * @param userid
         * The userid
         */
        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        /**
         *
         * @return
         * The username
         */
        public String getUsername() {
            return username;
        }

        /**
         *
         * @param username
         * The username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         *
         * @return
         * The usertypeid
         */
        public Integer getUsertypeid() {
            return usertypeid;
        }

        /**
         *
         * @param usertypeid
         * The usertypeid
         */
        public void setUsertypeid(Integer usertypeid) {
            this.usertypeid = usertypeid;
        }
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
