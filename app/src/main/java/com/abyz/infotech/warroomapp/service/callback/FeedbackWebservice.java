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
import com.abyz.infotech.warroomapp.retofit.wsmodel.FeedbackResponse;
import com.abyz.infotech.warroomapp.service.IWebservice;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;


public class FeedbackWebservice implements IWebservice {


    @Override
    public void fetchAndInsertData(final Intent intent, final Context context) {
        Map<String, String> body = new HashMap<>();
        body.put("feed_usrid", intent.getStringExtra("feed_usrid"));
        body.put("feed_usertype_id", intent.getStringExtra("feed_usertype_id"));
        body.put("feed_event_id", intent.getStringExtra("feed_event_id"));
        body.put("feed_subject", intent.getStringExtra("feed_subject"));
        body.put("feed_desc", intent.getStringExtra("feed_desc"));
        body.put("feed_image", intent.getStringExtra("feed_image"));
        body.put("feed_video", intent.getStringExtra("feed_video"));
        body.put("feed_population", intent.getStringExtra("feed_population"));
        body.put("feed_clappingpoint", intent.getStringExtra("feed_clappingpoint"));


        Log.d("body", "body  " + body);

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE).
                build();
        try {

            IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
            FeedbackResponse feedbackResponse = iRetrofitWebservice.feedback(body);

            Log.d("getFeedStatus","getFeedStatus "+feedbackResponse.getFeedStatus());
            if (feedbackResponse.getFeedStatus().equals(1)) {

                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.FEEDBACK_WEBSERVICE));
            }else{
                EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.WEBSERVICE_DOWN));
        }
    }
}
