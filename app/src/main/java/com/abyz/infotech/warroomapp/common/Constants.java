package com.abyz.infotech.warroomapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String BASE_URL = "http://www.rbkfamily.com/war_room/webservice.svc";
    //public static final String BASE_URL = "http://www.parkwel.com/Gunvant/webservice.svc";
    //public static final String PICASSO_BASE_URL = "http://www.rbkfamily.com/parkwel/dev1/ParkImages/SpotImages/";
    public static final String PICASSO_BASE_URL = "http://www.rbkfamily.com/war_room/images/";
    public static final String LOGIN = "login_webservice";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String FORGOT_PASSWORD = "forgot_password_webservice";
    public static final String CHANGE_PASSWORD = "change_password_webservice";
    public static final String EVENT = "event_webservice";
    public static final String FEEDBACK = "feedback_webservice";
    public static final String EVENTTASK = "event_task_webservice";
    public static final String TASK = "task_webservice";
    public static final String STATUS = "status_webservice";
    public static final String CHANGEMOBILENO = "change_mobile_no_webservice";
    public static final String EVANTTASKALLOTMENT = "event_task_allotment_webservice";
    public static final String EVANTTASKALLOTMENTLIST = "event_task_allotment_list_webservice";
    public static String MOBILE_NO_CHANGE_OTP = "otp";


    public static String OTP = null;
    public static int USR_ID = 0;
    public static String USER_NAME_RESEND = null;
    public static String MOBILE_NO_OTP = null;
    public static final String SHARED_PREFERENCE_USER_ID = "usr_id";
    public static final String SHARED_PREFERENCE_EVENT = "event_data";
    public static final String SHARED_PREFERENCE_USER_TYPE_ID = "usr_type_id";
    public static final String SHARED_PREFERENCE_EVENTTASCKALLOTMENT = "task_allotment";


    public static final String SHARED_PREFERENCE_ADDRESH = "usr_addresh";
    public static final String SHARED_PREFERENCE_IMAGE = "usr_image";
    public static final String SHARED_PREFERENCE_NAME = "name";
    public static final String CHECKTASK = "task";

    public static final String SHARED_PREFERENCE_USERNAME = "usr_name";
    public static final String SHARED_PREFERENCE_PASSWORD = "usr_password";
    public static final String CHECKEVENT = "event";

    public static String getIMEI(Context context) {
        String imei = Build.SERIAL;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringTimeFromTimestamp1(long timestamp) {

        Time t1 = new Time(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(t1);

    }
    @SuppressLint("SimpleDateFormat")
    public static String getStringDataFromTimestamp(long timestamp) {


        Date d1;
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        d1=(new Date(timestamp));
        Log.d("d1","d1="+d1);

        return sdf.format(d1);


    }



    public static boolean isInternetWorking(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable());
    }


}
