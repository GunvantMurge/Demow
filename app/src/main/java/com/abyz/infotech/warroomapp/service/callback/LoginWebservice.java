package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.LoginWebserviceResponse;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;

import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class LoginWebservice implements IWebservice {


    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_name", intent.getStringExtra("usr_name"));
        body.put("usr_pwd", intent.getStringExtra("usr_pwd"));
        body.put("usr_device_id", Build.SERIAL);

        Log.d("body", "body Login " + body);
        Log.d("URL", "" + Constants.BASE_URL);


        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            LoginWebserviceResponse loginWebserviceResponse = iRetrofitWebservice.checkLogin(body);

            Log.d("loginWeb", " loginWebserviceResponse------------------  " + loginWebserviceResponse.getStatus());
            if (loginWebserviceResponse.getStatus().equals(1)) {


                SharedPreferencesUtility.savePrefInt(context, Constants.SHARED_PREFERENCE_USER_ID, loginWebserviceResponse
                        .getUserlist().get(0).getUsrId());
                SharedPreferencesUtility. savePrefString(context, Constants.SHARED_PREFERENCE_ADDRESH,
                        loginWebserviceResponse.getUserlist().get(0).getUsrAddress());
                SharedPreferencesUtility. savePrefString(context, Constants.SHARED_PREFERENCE_IMAGE,
                        loginWebserviceResponse.getUserlist().get(0).getUsrImage());
                SharedPreferencesUtility. savePrefString(context, Constants.SHARED_PREFERENCE_NAME,
                        loginWebserviceResponse.getUserlist().get(0).getUsrName());


                SharedPreferencesUtility.savePrefInt(context, Constants.SHARED_PREFERENCE_USER_TYPE_ID, 3);

                int user_id = (SharedPreferencesUtility.getPrefInt(context, Constants.SHARED_PREFERENCE_USER_ID));
                Log.d("getUsrId","getUsrId"+ user_id);
                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                    greenDaoManager.delete();


                    greenDaoManager.insertReplaceCatagorys(loginWebserviceResponse.getCategoryList());
                    greenDaoManager.insertReplaceUserProfiles(loginWebserviceResponse.getProfileList());
                    greenDaoManager.insertReplaceTasks(loginWebserviceResponse.getTaskList());
                    greenDaoManager.insertReplaceTaskCategorys(loginWebserviceResponse.getTaskcategoryList());
                    greenDaoManager.insertReplaceUserTypes(loginWebserviceResponse.getUsertypeList());
                    greenDaoManager.insertReplaceSpeakers(loginWebserviceResponse.getUsrspeakerList());

                    greenDaoManager.insertReplaceUsers(loginWebserviceResponse.getUserlist());

                    Log.d("Speaker", " Speaker" + greenDaoManager.getSpeaker());
                    Log.d("getCatagory", " getCatagory" +  greenDaoManager.getCatagory());
                    Log.d("getTaskList", " getTaskList" +  greenDaoManager.getTaskList());
                    Log.d("getUserProfile", " getUserProfile" +  greenDaoManager.getUserProfile());
                    Log.d("getTaskList", " getTaskList" +  greenDaoManager.getTaskList());
                    Log.d("getTableUser", " getTableUser" +  greenDaoManager.getUser());
                    Log.d("getTaskcategoryList", " getTaskcategoryList" +  greenDaoManager.getTaskCategory());
                    Log.d("getUsertypeList", " getUsertypeList " +  greenDaoManager.getUserType());


                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.LOGIN_WEBSERVICE));
            } else if ((loginWebserviceResponse.getStatus().equals(2))) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_PASSWORD));
            } else if((loginWebserviceResponse.getStatus().equals(0))) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
