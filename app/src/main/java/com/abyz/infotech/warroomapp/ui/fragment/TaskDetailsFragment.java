package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


public class TaskDetailsFragment extends Fragment implements View.OnClickListener {


    public static TaskDetailsFragment newInstance() {
        return new TaskDetailsFragment();
    }

    private int et_eventId, layoutposition;
    private ProgressDialog mProgressDialog;
    private Long startdate,enddate,starttime,endtime;
    private String task_name, mobileNo, mCoordiname, mCoordiDesc;
    private ImageView callphone, eventImage, statu;
    private List<TaskModel> taskModels;
    private AppCompatSpinner mTaskStatus;
    private TextView mtaskstartTime, mTaskendTime,mtaskstartdate, mTaskenddate, mTaskName, mCoordinaterMobileNo, mPosition, coordiname, coordiDesc, taskstatus;
    private Map<Integer, String> TaskStatus = new HashMap<>();
    List<String> list = new ArrayList<>();
    private Switch mSwitch;
    private int status, et_id;

    public TaskDetailsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        et_eventId = bundle.getInt("et_id");
        task_name = bundle.getString("task_name");
        layoutposition = bundle.getInt("layout_position");

        layoutposition++;
        Log.d("layoutposition", "layoutposition <>>>" + et_eventId);

        try {
            taskModels = GreenDaoManager.getInstance(getActivity()).getTaskDetails(et_eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("taskModels", "taskModels ......" + taskModels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_add_task_details, container, false);
        mtaskstartTime = (TextView) view.findViewById(R.id.view_time);
        mTaskendTime = (TextView) view.findViewById(R.id.view_end_time);
        mtaskstartdate = (TextView) view.findViewById(R.id.view_date);
        mTaskenddate = (TextView) view.findViewById(R.id.view_end_date);
        mTaskName = (TextView) view.findViewById(R.id.text_view_task_name);
        callphone = (ImageView) view.findViewById(R.id.call_phone);
        eventImage = (ImageView) view.findViewById(R.id.event_image);
        statu = (ImageView) view.findViewById(R.id.statu);
        coordiname = (TextView) view.findViewById(R.id.coordinates_name);
        coordiDesc = (TextView) view.findViewById(R.id.coordinates_desc);
        taskstatus = (TextView) view.findViewById(R.id.task_status);
        mCoordinaterMobileNo = (TextView) view.findViewById(R.id.coordinater_mobile_no);
        mSwitch = (Switch) view.findViewById(R.id.switch1);
        //  mSwitch.setOnCheckedChangeListener(this);

        mPosition = (TextView) view.findViewById(R.id.position);
        callphone.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String a = null;
        try {
            startdate = taskModels.get(0).getTaStartTime();
            endtime = taskModels.get(0).getTaEndTime();
            enddate = taskModels.get(0).getTaEndDate();
            starttime = taskModels.get(0).getTaStartTime();
            mobileNo = taskModels.get(0).getEcUserMobileNo();
            mCoordiname = taskModels.get(0).getEcUserName();
            mCoordiDesc = taskModels.get(0).getEcDesc();

             a = taskModels.get(0).getEventImage();
            status = taskModels.get(0).getTaStatusId();
            et_id = taskModels.get(0).getTaEtId();


        Log.d("status", "status " + status + "  " + et_id);

        Picasso.with(getActivity())
                .load(Constants.PICASSO_BASE_URL + a)
                .fit()
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(eventImage);

        mtaskstartTime.setText(Constants.getStringTimeFromTimestamp1(starttime));
        mTaskendTime.setText(Constants.getStringTimeFromTimestamp1(endtime));
        mtaskstartdate.setText(Constants.getStringDataFromTimestamp(startdate));
        mTaskenddate.setText(Constants.getStringDataFromTimestamp(enddate));

        mTaskName.setText(task_name);
        mCoordinaterMobileNo.setText(mobileNo);
        coordiname.setText(mCoordiname);
        coordiDesc.setText(mCoordiDesc);
        if (status == 5) {
            mSwitch.setChecked(true);
            mSwitch.setClickable(false);
            taskstatus.setText("Complete");
            showAlertDialog("Task", "This Task is already successfully done.");
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

                if (status != 5) {
                    if (bChecked) {
                        taskstatus.setText("Complete");

                        taskstatus();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                statu.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        statu.setVisibility(View.INVISIBLE);
                                    }
                                }, 1000);
                            }
                        }, 500);

                    } else {
                        statu.setVisibility(View.INVISIBLE);
                        taskstatus.setText("Pending");

                    }
                } else {
                    mSwitch.setChecked(true);

                    showAlertDialog("Task Status", "Task Status is already successfully done.");
                }
            }

        });
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(false, "Tasks Details");
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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.call_phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
            startActivity(intent);
        }

    }

    private void taskstatus() {

        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type_id = Integer.toString(usertype);
        String etid = Integer.toString(et_id);
        Log.d("user_type_id", "user_type_id  " + user_type_id);
        Intent intentResendOtp = new Intent(getActivity(), WSUtilityIntentService.class);
        intentResendOtp.putExtra("webservice_key", Constants.STATUS);
        intentResendOtp.putExtra("user_id", user_id);
        intentResendOtp.putExtra("usr_type_id", user_type_id);
        intentResendOtp.putExtra("status_id", "5");
        intentResendOtp.putExtra("ta_et_id", etid);
        getActivity().startService(intentResendOtp);

        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
        mProgressDialog.show();
    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case TASK_STATUS_WEBSERVICE:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                showAlertDialog("Task Status", "Congratulations your tasks is successfully done.");

                GreenDaoManager.getInstance(getActivity()).upDateTaskStatus(et_id);
                break;


            case INVALID_CREDENTIALS:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showAlertDialog("Time Mismatch", "You can not change status in this time.");
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


}
