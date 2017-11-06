package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ChangeMobileNoResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ChangePasswordResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class ChangeMobileNoWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(Intent intent, Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_id", intent.getStringExtra("user_id"));
        body.put("usr_mob", intent.getStringExtra("usr_mob"));
        body.put("status", intent.getStringExtra("status"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {
            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            ChangeMobileNoResponse changeMobileNoResponse = iRetrofitWebservice.changeMobileNo(body);
            Log.d("RestAdapter", "RestAdapter ---> " + changeMobileNoResponse.getStatus());

           if (changeMobileNoResponse.getStatus().equals("1")) {
                Log.d("getStatus","OTP  "+changeMobileNoResponse.getRtnotp());

               SharedPreferencesUtility.savePrefInt(context, Constants.MOBILE_NO_CHANGE_OTP, changeMobileNoResponse.getRtnotp());

               EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.MOBILENO_CHANGE_SUCCESSFUL));
            }
            if (changeMobileNoResponse.getStatus().equals("2")) {
                Log.d("getStatus", "getStatus 2");
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.UNABLE_TO_CHANGE_MOBILENO));
            }
            if (changeMobileNoResponse.getStatus().equals("3")) {
                Log.d("getStatus","getStatus 2");
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.MOBILE_NO_CHANGE_SUCCESSFUL));
            }
            else if (changeMobileNoResponse.getStatus().equals("0")) {
                Log.d("getStatus","getStatus 0");
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }

    }
}
