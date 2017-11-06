package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;

import com.abyz.infotech.warroomapp.ui.adapter.TaskRecycleViewAdapter;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class TaskFragment extends Fragment implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private boolean flag = true;
    private TaskRecycleViewAdapter mTaskRecycleViewAdapter;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public TaskFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int task = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.CHECKTASK));
        if(task != 1) {

            if (Constants.isInternetWorking(getActivity())) {
            fetchData();
            mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
            mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
            mProgressDialog.show();
            } else {
                showAlertDialog("Internet is not working", "No Internet Connection");

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_views, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchSqliteData();

    }


    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Tasks");
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
        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type_id = Integer.toString(usertype);
        Log.d("user_type_id","user_type_id  "+user_type_id);
        Intent intentResendOtp = new Intent(getActivity(), WSUtilityIntentService.class);
        intentResendOtp.putExtra("webservice_key", Constants.TASK);
        intentResendOtp.putExtra("usr_id", user_id);
        intentResendOtp.putExtra("usr_type_id", user_type_id);
        getActivity().startService(intentResendOtp);
    }
    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case TASK_WEBSERVICE:

                Log.d("MessageEventBus", "MessageEventBus" + eventBus);
                SharedPreferencesUtility.savePrefInt(getActivity(), Constants.CHECKTASK, 1);
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

                SharedPreferencesUtility.savePrefInt(getActivity(), Constants.CHECKTASK, 1);
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

        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.popup_add_task_details);
        ImageView call = (ImageView) dialog1.findViewById(R.id.call_phone);
        call.setOnClickListener(this);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        call.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                                                getActivity().getString(R.string.number)));
                                        startActivity(intent);
                                        //d.dismiss();
                                    }
        });

        dialog1.show();
        dialog1.getWindow().setLayout(lp.width = WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    private void fetchSqliteData() {
        List<TaskModel> taskModel = null;
        try {
            taskModel = GreenDaoManager.getInstance(getActivity()).getTaskModelList();
            Log.d("data", "" + taskModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (taskModel == null) {
            flag = false;
            taskModel = new ArrayList<>();
        }
        mTaskRecycleViewAdapter = new TaskRecycleViewAdapter(taskModel, getActivity());
        mRecyclerView.setAdapter(mTaskRecycleViewAdapter);
    }
}
