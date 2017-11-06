package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.VolunteerListResponse;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventTaskAllotmentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Callback<VolunteerListResponse> {


    private VolunteerListResponse volunteerListResponses;
    private Map<Integer, String> mvolunteerList = new HashMap<>();
    private Map<Integer, String> mEventTaskList = new HashMap<>();
    private AppCompatSpinner mSpinnerVolunteerList, mSpinnerEventTaskNameList;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute, user_id, eventtask_id;
    private Long mStartDate, mEndDates, mStartTimes, mEndTimes, mcurrent, mEventdate;
    private TextView mStartDates, mEndDate, mStartTime, mEndTime;
    private ImageView mImageViewStartDates, mImageViewEndDate, mImageViewStartTime, mImageViewEndTime;
    private String userid, eventtaskid;
    private ProgressDialog mProgressDialog;

    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    private List<TaskModel> taskModels;

    public static EventTaskAllotmentFragment newInstance() {
        return new EventTaskAllotmentFragment();
    }

    public EventTaskAllotmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.isInternetWorking(getActivity())) {
            fetchVolunteerInput();
            mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
            mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
            mProgressDialog.show();

        } else {
            showAlertDialog("Internet is not working", "No Internet Connection");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_task_allotment, container, false);

        mImageViewStartDates = (ImageView) view.findViewById(R.id.image_view_start_date);
        mImageViewEndDate = (ImageView) view.findViewById(R.id.image_view_end_date);
        mImageViewStartTime = (ImageView) view.findViewById(R.id.image_view_start_time);
        mImageViewEndTime = (ImageView) view.findViewById(R.id.image_view_end_time);

        mStartDates = (TextView) view.findViewById(R.id.text_view_start_date);
        mEndDate = (TextView) view.findViewById(R.id.text_view_end_date);
        mStartTime = (TextView) view.findViewById(R.id.text_view_start_time);
        mEndTime = (TextView) view.findViewById(R.id.text_view_end_time);

        mImageViewStartDates.setOnClickListener(this);
        mImageViewEndDate.setOnClickListener(this);
        mImageViewStartTime.setOnClickListener(this);
        mImageViewEndTime.setOnClickListener(this);

        mImageViewEndDate.setClickable(false);
        mImageViewEndTime.setClickable(false);

        Button mEventTaskAllote = (Button) view.findViewById(R.id.event_task_allote);
        mEventTaskAllote.setOnClickListener(this);

        mSpinnerVolunteerList = (AppCompatSpinner) view.findViewById(R.id.volun_name_spinner_list);
        mSpinnerEventTaskNameList = (AppCompatSpinner) view.findViewById(R.id.event_list_spinner_list);
        mSpinnerVolunteerList.setOnItemSelectedListener(this);
        mSpinnerEventTaskNameList.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mStartDate = 0L;
        mcurrent = System.currentTimeMillis();

        List<TaskModel> taskModel = null;
        try {
            taskModel = GreenDaoManager.getInstance(getActivity()).getEventTaskAllotmentModelList();
            Log.d("data", "" + taskModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (taskModel == null) {

            taskModel = new ArrayList<>();
        }
        for (int i = 0; i < taskModel.size(); i++) {
            String eventname = taskModel.get(i).getEt_evnt_name();
            String taskname = taskModel.get(i).getTskName();

            String evenytaskname = (String.format("%s-%s", (String.valueOf(eventname)),
                    (String.valueOf(taskname))));

            mEventTaskList.put(taskModel.get(i).getEtId(), evenytaskname);
        }
        if (taskModel.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                    getValuesForMapEventTaskList(mEventTaskList));

            mSpinnerEventTaskNameList.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } else {
            mSpinnerEventTaskNameList.setEnabled(false);
            showAlertDialog("No Record Found", "Please Add Event Task first.");
        }
    }


    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(false, "Event Task Allotment");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Events");
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fetchVolunteerInput() {
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        Map<String, String> body = new TreeMap<>();
        body.put("user_id", user_id);
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.BASE_URL).setLogLevel(RestAdapter.LogLevel.NONE)
                .build();

        IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
        iRetrofitWebservice.volunteerlist(body, new Callback<VolunteerListResponse>() {
            @Override
            public void success(VolunteerListResponse volunteerListResponse, Response response) {

                Log.d("VolunteerListResponse", "VolunteerListResponse " + volunteerListResponse);

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                volunteerListResponses = volunteerListResponse;

                for (int i = 0; i < volunteerListResponse.getVollist().size(); i++) {

                    mvolunteerList.put(volunteerListResponse.getVollist().get(i).getUserid(), volunteerListResponse.getVollist().get(i).getUsername());
                }
                if (mvolunteerList.size() > 0) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                            getValuesForMapVolunteerList(mvolunteerList));

                    mSpinnerVolunteerList.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    mSpinnerVolunteerList.setEnabled(false);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                showAlertDialog("WEBSERVICE_DOWN", "Please check your internet connection and try again.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<String> getValuesForMapVolunteerList(Map<Integer, String> map) {


        list.add(0, "--Select Volunteer--");


        for (Integer integer : map.keySet()) {
            list.add(map.get(integer));
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    private List<String> getValuesForMapEventTaskList(Map<Integer, String> map) {


        list1.add(0, "--Select Event Task--");


        for (Integer integer : map.keySet()) {
            list1.add(map.get(integer));
        }
        Collections.sort(list1, String.CASE_INSENSITIVE_ORDER);
        return list1;
    }

    private int getKeyForValueVolunteerList(String value, Map<Integer, String> map) {
        for (Integer integer : map.keySet()) {
            if (map.get(integer).equalsIgnoreCase(value)) {
                return integer;
            }
        }
        return 0;
    }

    private int getKeyForValuemEventTaskList(String value, Map<Integer, String> map) {
        for (Integer integer : map.keySet()) {
            if (map.get(integer).equalsIgnoreCase(value)) {
                return integer;
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_start_date:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(calendar.HOUR_OF_DAY, 0);
                        calendar.set(calendar.MINUTE, 0);
                        calendar.set(calendar.SECOND, 0);
                        calendar.set(calendar.MILLISECOND, 0);

                        mStartDate = calendar.getTimeInMillis();

                        mImageViewEndDate.setClickable(true);

                        String a = (Constants.getStringDataFromTimestamp(calendar.getTimeInMillis()));
                        String b = (Constants.getStringDataFromTimestamp(System.currentTimeMillis()));


                        try {
                            mEventdate = taskModels.get(0).getTaStartDate();

                            if (mStartDate > mcurrent || a.equals(b)) {

                                if (mEventdate > mStartDate) {

                                    mStartDates.setText(Constants.getStringDataFromTimestamp(mStartDate));

                                } else {

                                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                    alertDialogUtils.errorAlert(getActivity(), "Date Error", "Event Date and task Allotment Date is mismatch Please check and try again ");
                                    mStartDates.setHint("DD/MM/YYYY");

                                }

                            } else {

                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Date Error", "Start Date should be equal and greater than Current Date ");
                                mStartDates.setHint("DD/MM/YYYY");

                            }
                        } catch (Exception e) {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Event Task",
                                    "Please Select Event Task Name.");
                        }

                    }
                }, mYear, mMonth, mDay).show();

                break;
            case R.id.image_view_end_date:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(calendar.HOUR_OF_DAY, 0);
                        calendar.set(calendar.MINUTE, 0);
                        calendar.set(calendar.SECOND, 0);
                        calendar.set(calendar.MILLISECOND, 0);

                        mEndDates = calendar.getTimeInMillis();
                        String a = (Constants.getStringDataFromTimestamp(calendar.getTimeInMillis()));
                        String b = (Constants.getStringDataFromTimestamp(System.currentTimeMillis()));


                        try {
                            mEventdate = taskModels.get(0).getTaStartDate();

                            if (mEndDates > mcurrent || a.equals(b)) {

                                if (mEventdate > mEndDates) {

                                    mEndDate.setText(Constants.getStringDataFromTimestamp(mEndDates));

                                } else {

                                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                    alertDialogUtils.errorAlert(getActivity(), "Date Error", "Event Date and task Allotment Date is mismatch Please check and try again ");
                                    mStartDates.setHint("DD/MM/YYYY");

                                }

                            } else {

                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Date Error", "Start Date should be equal and greater than Current Date ");

                            }
                        } catch (Exception e) {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Event Task",
                                    "Please Select Event Task Name.");
                        }

                    }
                }, mYear, mMonth, mDay).show();

                break;
            case R.id.image_view_start_time:
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        mStartTimes = calendar.getTimeInMillis();


                        mImageViewEndTime.setClickable(true);

                        Log.d("mStartTimes", "mStartTimes " + mStartTimes);

                        mStartTime.setText(Constants.getStringTimeFromTimestamp1(mStartTimes));

                    }
                }, mHour, mMinute, false).show();
                break;

            case R.id.image_view_end_time:
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        mEndTimes = calendar.getTimeInMillis();


                        Log.d("mEndTimes", "mEndTimes " + mEndTimes);


                        Long a = mEndTimes - mStartTimes;

                        Log.d("a", "a  " + a);

                        if (a >= 3600000) {

                            mEndTime.setText(Constants.getStringTimeFromTimestamp1(mEndTimes));

                        } else {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Time Error", "Time Error ");
                        }

                    }
                }, mHour, mMinute, false).show();
                break;

            case R.id.event_task_allote:


                if (Constants.isInternetWorking(getActivity())) {

                    if (!mSpinnerVolunteerList.getSelectedItem().equals("--Select Volunteer--")) {

                        if (!mSpinnerEventTaskNameList.getSelectedItem().equals("--Select Event Task--")) {

                            if (!mStartDates.getText().toString().isEmpty()) {

                                if (!mEndDate.getText().toString().isEmpty()) {

                                    if (!mStartTime.getText().toString().isEmpty()) {

                                        if (!mEndTime.getText().toString().isEmpty()) {

                                            webservicecall();
                                        } else {
                                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                            alertDialogUtils.errorAlert(getActivity(), "End Time",
                                                    "Please Select End Time");
                                        }
                                    } else {
                                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                        alertDialogUtils.errorAlert(getActivity(), "Star Time",
                                                "Please Select Star Time.");

                                    }
                                } else {
                                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                    alertDialogUtils.errorAlert(getActivity(), "End Date",
                                            "Please Select End Date");
                                }
                            } else {
                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Star Date",
                                        "Please Select Star Date.");

                            }
                        } else {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Event Task",
                                    "Please Select Event Task Name.");
                        }
                    } else {
                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                        alertDialogUtils.errorAlert(getActivity(), "Volunteer",
                                "Please Select Volunteer Name.");

                    }

                } else {
                    showAlertDialog("Internet is not working", "No Internet Connection");

                }

                break;

        }

    }

    @Override
    public void success(VolunteerListResponse volunteerListResponse, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.volun_name_spinner_list) {
            try {
                // userid = volunteerListResponses.getVollist().get(position - 1).getUserid().toString();

                userid = (String) mSpinnerVolunteerList.getSelectedItem();
                user_id = getKeyForValueVolunteerList(userid, mvolunteerList);

                Log.d("userid ", "userid  -- " + user_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (parent.getId() == R.id.event_list_spinner_list) {

            eventtaskid = (String) mSpinnerEventTaskNameList.getSelectedItem();
            eventtask_id = getKeyForValuemEventTaskList(eventtaskid, mEventTaskList);
            Log.d("eventtask_id ", "eventtask_id  -- " + eventtask_id);

            try {
                if (eventtask_id != 0) {
                    taskModels = GreenDaoManager.getInstance(getActivity()).getEventDate(eventtask_id);

                    Log.d("taskModels ", "taskModels  -- " + taskModels);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case EVENTTASKALLOTMENT_WEBSERVICE:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("TASK ALLOT ");
                    builder.setMessage("Event Task is allotted successfully. ");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                getActivity().onBackPressed();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case INVALID_CREDENTIALS:

                showAlertDialog("Task Already Allot", "Please check this Volunteer task is already allot.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INVALID:

                showAlertDialog("No Record Add", "Please try again.");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
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

    private void webservicecall() {

        String usr_id = Integer.toString(user_id);
        String eventtaskid = Integer.toString(eventtask_id);
        String mStart_Date = Long.toString(mStartDate);
        String mStart_Times = Long.toString(mStartTimes);
        String mEnd_Dates = Long.toString(mEndDates);
        String mEnd_Times = Long.toString(mEndTimes);


        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
        intent.putExtra("webservice_key", Constants.EVANTTASKALLOTMENTLIST);
        intent.putExtra("ta_user_id", usr_id);
        intent.putExtra("ta_et_id", eventtaskid);
        intent.putExtra("ta_startdate", mStart_Date);
        intent.putExtra("ta_enddate", mEnd_Dates);
        intent.putExtra("ta_starttime", mStart_Times);
        intent.putExtra("ta_endtime", mEnd_Times);

        intent.putExtra("status_id", "1");
        intent.putExtra("ta_usertypeid", "3");

        getActivity().startService(intent);

        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
        mProgressDialog.show();
    }
}
