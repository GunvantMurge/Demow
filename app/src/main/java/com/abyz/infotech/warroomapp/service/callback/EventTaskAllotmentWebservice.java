package com.abyz.infotech.warroomapp.service.callback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAllotmentResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class EventTaskAllotmentWebservice implements IWebservice {

    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("ta_user_id", intent.getStringExtra("ta_user_id"));
        body.put("ta_et_id", intent.getStringExtra("ta_et_id"));
        body.put("ta_startdate", intent.getStringExtra("ta_startdate"));
        body.put("ta_enddate", intent.getStringExtra("ta_enddate"));
        body.put("ta_starttime", intent.getStringExtra("ta_starttime"));
        body.put("ta_endtime", intent.getStringExtra("ta_endtime"));
        body.put("status_id", intent.getStringExtra("status_id"));
        body.put("ta_usertypeid", intent.getStringExtra("ta_usertypeid"));

        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            EventTaskAllotmentResponse eventTaskAllotmentResponse = iRetrofitWebservice.addTaskAllot(body);
            if (eventTaskAllotmentResponse.getStatus().equals(1)) {

                Log.d("Task", " TaskWebserviceResponse" + eventTaskAllotmentResponse);
                GreenDaoManager greenDaoManager = null;
                try {
                    greenDaoManager = GreenDaoManager.getInstance(context);
                   // greenDaoManager.delete();

                    greenDaoManager.insertReplaceTaskAllotment(eventTaskAllotmentResponse.getTaskallot());

                    Log.d("getTaskallot", " getTaskallot" +  greenDaoManager.getTaskAllotment());

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (greenDaoManager != null) {
                        greenDaoManager.closeDatabase();
                    }
                }
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.EVENTTASKALLOTMENT_WEBSERVICE));
            }
            if (eventTaskAllotmentResponse.getStatus().equals(2)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID_CREDENTIALS));
            }
            if (eventTaskAllotmentResponse.getStatus().equals(0)) {
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.INVALID));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
