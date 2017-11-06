package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.abyz.infotech.warroomapp.ui.adapter.EventTaskRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class EventTaskLists extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EventTaskRecycleViewAdapter mEventTaskRecycleViewAdapter;
    private boolean flag = true;

    public static EventTaskLists newInstance() {
        return new EventTaskLists();
    }

    public EventTaskLists() {
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


        View view = inflater.inflate(R.layout.recycler_views_task_allotment, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button_add);
        floatingActionButton.attachToRecyclerView(mRecyclerView);
        floatingActionButton.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.purple, R.color.green, R.color.orange);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Constants.isInternetWorking(getActivity())) {

            Log.d("onCreate","onCreate ");
            fetchData();
            mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
            mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
            mProgressDialog.show();
        } else {
            showAlertDialog("Internet is not working", "No Internet Connection");

        }

        fetchSqliteData();


    }

        @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Add Event Task List");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "War Room");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fetchData() {
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        Intent intentResendOtp = new Intent(getActivity(), WSUtilityIntentService.class);
        intentResendOtp.putExtra("webservice_key", Constants.EVENTTASK);
        intentResendOtp.putExtra("usr_id", user_id);
        getActivity().startService(intentResendOtp);
    }

    private void fetchSqliteData() {
        List<EventModel> eventModels = null;
        try {
            eventModels = GreenDaoManager.getInstance(getActivity()).getEvenTaskList();
            Log.d("data", "eventModels " + eventModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (eventModels == null) {
            flag = false;
            eventModels = new ArrayList<>();
        }
        mEventTaskRecycleViewAdapter = new EventTaskRecycleViewAdapter(eventModels, getActivity());
        mRecyclerView.setAdapter(mEventTaskRecycleViewAdapter);

    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case TASK_WEBSERVICE:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                    if(flag == true)
                    {
                        fetchSqliteData();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case INVALID_CREDENTIALS:
                showAlertDialog("No Record Found", "Please Add Event Task for Event.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case WEBSERVICE_DOWN:

                showAlertDialog("WEBSERVICE_DOWN", "Please check your internet connection and try again.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(getActivity(), title, message);
    }

    @Override
    public void onClick(View v) {

        EventTaskAddFragment eventTaskAddFragment = EventTaskAddFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, eventTaskAddFragment,
                "eventTaskAllotmentFragment").addToBackStack("")
                .commit();
    }

    @Override
    public void onRefresh() {
        fetchSqliteData();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
