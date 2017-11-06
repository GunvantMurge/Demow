package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class TaskWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("user_id", intent.getStringExtra("usr_id"));
        body.put("usertype_id", intent.getStringExtra("usr_type_id"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            TaskResponse taskResponse = iRetrofitWebservice.taskSpeaker(body);
            Log.d("Task", " TaskWebserviceResponse" + taskResponse.getMsgStatus());
            if (taskResponse.getMsgStatus().equalsIgnoreCase("success")) {

                Log.d("Task", " TaskWebserviceResponse" + taskResponse);
                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                    //greenDaoManager.delete();

                    greenDaoManager.insertReplaceEventTasks(taskResponse.getTaskeventlist());
                    greenDaoManager.insertReplaceEventCoordinater(taskResponse.getEventcordlist());
                    greenDaoManager.insertReplaceTaskAllotment(taskResponse.getTaskallot());

                    Log.d("getTaskeventlist", " getTaskeventlist" + greenDaoManager.getEventTask());
                    Log.d("getEventcordlist", " getEventcordlist" +  greenDaoManager.getEventCoordinater());
                    Log.d("getTaskallot", " getTaskallot" +  greenDaoManager.getTaskAllotment());

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.TASK_WEBSERVICE));
            }else {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
