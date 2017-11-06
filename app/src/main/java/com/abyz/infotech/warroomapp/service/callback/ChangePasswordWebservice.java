package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ChangePasswordResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class ChangePasswordWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(Intent intent, Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_id", intent.getStringExtra("usr_id"));
        body.put("password", intent.getStringExtra("password"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {
            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            ChangePasswordResponse changePasswordResponse = iRetrofitWebservice.changePassword(body);
            if (changePasswordResponse.getStatus().equals(2)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.PASSWORD_CHANGE_SUCCESSFUL));
            } else {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.UNABLE_TO_CHANGE_PASSWORD));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }

    }
}
