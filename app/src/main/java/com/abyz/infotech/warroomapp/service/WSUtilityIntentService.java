package com.abyz.infotech.warroomapp.service;

import android.app.IntentService;
import android.content.Intent;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.service.callback.ChangeMobileNoWebservice;
import com.abyz.infotech.warroomapp.service.callback.ChangePasswordWebservice;
import com.abyz.infotech.warroomapp.service.callback.EventTaskAddWebservice;
import com.abyz.infotech.warroomapp.service.callback.EventTaskAddsWebservice;
import com.abyz.infotech.warroomapp.service.callback.EventTaskAllotmentWebservice;
import com.abyz.infotech.warroomapp.service.callback.EventWebservice;
import com.abyz.infotech.warroomapp.service.callback.FeedbackWebservice;
import com.abyz.infotech.warroomapp.service.callback.ForgotPasswordWebservice;
import com.abyz.infotech.warroomapp.service.callback.LoginWebservice;
import com.abyz.infotech.warroomapp.service.callback.StatusWebservice;
import com.abyz.infotech.warroomapp.service.callback.TaskWebservice;

public class WSUtilityIntentService extends IntentService {

    public WSUtilityIntentService() {
        super("WSUtilityIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String service = intent.getStringExtra("webservice_key");
        IWebservice iWebservice = null;


        switch (service) {
            case Constants.LOGIN:
                iWebservice = new LoginWebservice();
                break;

            case Constants.FORGOT_PASSWORD:
                iWebservice = new ForgotPasswordWebservice();
                break;

            case Constants.CHANGE_PASSWORD:
                iWebservice = new ChangePasswordWebservice();
                break;

            case Constants.EVENT:
                iWebservice = new EventWebservice();
                break;

            case Constants.FEEDBACK:
                iWebservice = new FeedbackWebservice();
                break;

            case Constants.TASK:
                iWebservice = new TaskWebservice();
                break;

            case Constants.STATUS:
                iWebservice = new StatusWebservice();
                break;
            case Constants.EVENTTASK:
                iWebservice = new EventTaskAddWebservice();
                break;



          case Constants.EVANTTASKALLOTMENT:
                iWebservice = new EventTaskAddsWebservice();
                break;

            case Constants.EVANTTASKALLOTMENTLIST:
                iWebservice = new EventTaskAllotmentWebservice();
                break;

            case Constants.CHANGEMOBILENO:
                iWebservice = new ChangeMobileNoWebservice();
                break;
        }
        try {
            if (iWebservice != null) {
                iWebservice.fetchAndInsertData(intent, getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
