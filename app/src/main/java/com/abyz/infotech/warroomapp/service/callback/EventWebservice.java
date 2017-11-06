package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;


public class EventWebservice implements IWebservice {


    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_id", intent.getStringExtra("usr_id"));
        body.put("usertype_id", intent.getStringExtra("usr_type_id"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            EventResponse eventResponse = iRetrofitWebservice.eventSpeaker(body);
            if (eventResponse.getStatusMsg().equalsIgnoreCase("Success")) {

                Log.d("loginWeb", " loginWebserviceResponse" + eventResponse);
                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                   // greenDaoManager.delete();

                    greenDaoManager.insertReplaceEventSpeakers(eventResponse.getEventSpeakerList());
                    greenDaoManager.insertReplaceEvent(eventResponse.getEventList());
                    greenDaoManager.insertReplaceSpeakers(eventResponse.getSpeakerList());

                    greenDaoManager.insertReplaceLocationArea(eventResponse.getLocareaList());
                    greenDaoManager.insertReplaceDistrict(eventResponse.getDistrictList());
                    greenDaoManager.insertReplaceState(eventResponse.getStateList());

                    SharedPreferencesUtility.savePrefBoolean(context, Constants.SHARED_PREFERENCE_EVENT, true);


                    Log.d("getSpeaker", " getSpeaker" + greenDaoManager.getSpeaker());
                    Log.d("getEventSpeaker", " getEventSpeaker" +  greenDaoManager.getEventSpeaker());
                    Log.d("getEvent", " getEvent" +  greenDaoManager.getEvent());


                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.EVENT_WEBSERVICE));
            }
            //else
               // EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_PASSWORD));

        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
