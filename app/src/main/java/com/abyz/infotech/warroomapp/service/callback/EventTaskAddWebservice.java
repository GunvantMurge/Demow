package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAddResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAllotmentResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class EventTaskAddWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("usr_id", intent.getStringExtra("usr_id"));



        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            EventTaskAddResponse eventTaskAddResponse = iRetrofitWebservice.eventTaskList(body);
            Log.d("getStatus", " getStatus --------------------- " + eventTaskAddResponse.getStatus());
            if (eventTaskAddResponse.getStatus().equals(1)) {

                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                   // greenDaoManager.delete();

                    greenDaoManager.insertReplaceEventTasks(eventTaskAddResponse.getEvlist());
                    greenDaoManager.insertReplaceEventCoordinater(eventTaskAddResponse.getEventcordlist());
                    greenDaoManager.insertReplaceTasks(eventTaskAddResponse.getTasklist());
                    greenDaoManager.insertReplaceTaskCategorys(eventTaskAddResponse.getTaskcategory());
                    greenDaoManager.insertReplaceEvent(eventTaskAddResponse.getEventlist());

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.TASK_WEBSERVICE));
            }
            if (eventTaskAddResponse.getStatus().equals(0)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
