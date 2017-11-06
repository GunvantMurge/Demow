package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ForgotPasswordWebserviceResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class ForgotPasswordWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(Intent intent, Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_name", intent.getStringExtra("usr_name"));
        Log.d("usr_name","usr_name  ????");
        String otp = intent.getStringExtra("otp").trim();
        if (otp.length() > 0) {
            body.put("otp", otp);
            Log.d("otp", "otp"+otp);
        }
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {
            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            ForgotPasswordWebserviceResponse forgotPasswordWebserviceResponse = iRetrofitWebservice.forgotPassword(body);
            if (forgotPasswordWebserviceResponse.getStatus().equals(1)) {

                if (otp.trim().length() > 0) {

                    Log.d("Not success","Not success  ???");
                    //EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.OTP_CONFIRMED));
                } else {

                    Constants.OTP = String.valueOf(forgotPasswordWebserviceResponse.getOtp());
                    Log.d("call", "OTP"+ Constants.OTP );

                    Constants.USR_ID = forgotPasswordWebserviceResponse.getUserid();
                  //  Log.d("call", "USR_ID"+ Constants.USR_ID );
                    Constants.MOBILE_NO_OTP = forgotPasswordWebserviceResponse.getMobno();

                    EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.OTP_DELIVERED));
                }
            } else  if (forgotPasswordWebserviceResponse.getStatus().equals(0)) {

                Log.d("Not success", "Not success");
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }


        } catch (Exception e) {
            Log.d("Not success","Not success  ??? ???");
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }

    }
}
