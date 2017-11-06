package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskStatusResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class StatusWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("user_id", intent.getStringExtra("user_id"));
        body.put("ustype_id", intent.getStringExtra("usr_type_id"));
        body.put("status_id", intent.getStringExtra("status_id"));
        body.put("et_id", intent.getStringExtra("ta_et_id"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            TaskStatusResponse taskStatusResponse = iRetrofitWebservice.AddTaskstatus(body);
            Log.d("getStatus", "getStatus  " + taskStatusResponse.getStatus());

            if (taskStatusResponse.getStatus().equals(1)) {

                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.TASK_STATUS_WEBSERVICE));
            }else if (taskStatusResponse.getStatus().equals(2)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }
            else if (taskStatusResponse.getStatus().equals(0)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
