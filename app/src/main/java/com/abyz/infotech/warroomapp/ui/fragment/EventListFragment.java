package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.adapter.EventRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.model.EventModel;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class EventListFragment extends Fragment {

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private boolean flag = true;
    private com.abyz.infotech.warroomapp.ui.adapter.EventRecycleViewAdapter mEventRecycleViewAdapter;
    private List<EventModel> eventModels = null;

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    public EventListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_views, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Log.d("onCreateView", "onCreateView");


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int event = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.CHECKEVENT));
        if (event != 1) {

            if (Constants.isInternetWorking(getActivity())) {
                fetchData();
                mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
                mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
                mProgressDialog.show();

            } else {
                showAlertDialog("Internet is not working", "No Internet Connection");
            }
        }

        fetchSqliteData();

    }

    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Events");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "War Room");
        EventBus.getDefault().unregister(this);
        eventModels.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void fetchData() {
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);

        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type_id = Integer.toString(usertype);

        Intent intentResendOtp = new Intent(getActivity(), WSUtilityIntentService.class);
        intentResendOtp.putExtra("webservice_key", Constants.EVENT);
        intentResendOtp.putExtra("usr_id", user_id);
        intentResendOtp.putExtra("usr_type_id", user_type_id);
        getActivity().startService(intentResendOtp);
    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case EVENT_WEBSERVICE:
                SharedPreferencesUtility.savePrefInt(getActivity(), Constants.CHECKEVENT, 1);
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    if (flag == true) {
                        fetchSqliteData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case WEBSERVICE_DOWN:

                showAlertDialog("WEBSERVICE_DOWN", "Please check your internet connection and try again.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(getActivity(), title, message);
    }

    private void fetchSqliteData() {

        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));

        try {
            if(usertype == 4){

                eventModels = GreenDaoManager.getInstance(getActivity()).getEventModelListSpekers();
            }else {
                //eventModels.clear();
                eventModels = GreenDaoManager.getInstance(getActivity()).getEventModelList();
            }
            Log.d("data", "" + eventModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (eventModels == null) {
            flag = false;
            eventModels = new ArrayList<>();
        }
        mEventRecycleViewAdapter = new EventRecycleViewAdapter(eventModels, getActivity());
        mRecyclerView.setAdapter(mEventRecycleViewAdapter);
    }

}
