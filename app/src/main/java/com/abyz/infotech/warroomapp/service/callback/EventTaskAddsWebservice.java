package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAddsResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAllotmentResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class EventTaskAddsWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("et_tc_id", intent.getStringExtra("taskname"));
        body.put("event_id", intent.getStringExtra("eventmane"));



        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            EventTaskAddsResponse eventTaskAddsResponse = iRetrofitWebservice.addEventTask(body);

            Log.d("getStatus", " getStatus   " + eventTaskAddsResponse.getStatus());
            if (eventTaskAddsResponse.getStatus().equals(1)) {

                Log.d("Task", " TaskWebserviceResponse" + eventTaskAddsResponse);
                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                   // greenDaoManager.delete();

                    greenDaoManager.insertReplaceEventTasks(eventTaskAddsResponse.getEventtasklist());

                    Log.d("getTaskallot", " getTaskallot" +  greenDaoManager.getEventTask());

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.EVENTTASKADD_WEBSERVICE));
            }
            if (eventTaskAddsResponse.getStatus().equals(2)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }
            if (eventTaskAddsResponse.getStatus().equals(0)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
