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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.VolunteerListResponse;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;

import java.util.ArrayList;
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

public class EventTaskAddFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Callback<VolunteerListResponse> {


    private Map<Integer, String> mvolunteerList = new HashMap<>();
    private Map<Integer, String> mEventTaskList = new HashMap<>();
    private AppCompatSpinner mSpinnerVolunteerList, mSpinnerEventTaskNameList;

    private int Event_name, task_name;
    private String userid, eventtaskid;
    private ProgressDialog mProgressDialog;

    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();

    public static EventTaskAddFragment newInstance() {
        return new EventTaskAddFragment();
    }

    public EventTaskAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* fetchVolunteerInput();
        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
        mProgressDialog.show();*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_task_add, container, false);


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


        List<TaskModel> taskModel = null;
        try {
            taskModel = GreenDaoManager.getInstance(getActivity()).getTasksList();
            Log.d("data", "" + taskModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (taskModel == null) {
            taskModel = new ArrayList<>();
        }
        for (int i = 0; i < taskModel.size(); i++) {
            mEventTaskList.put(taskModel.get(i).getTaskid(), taskModel.get(i).getTskName());
        }
        if (taskModel.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                    getValuesForMapEventTaskList(mEventTaskList));

            mSpinnerEventTaskNameList.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } else {
            mSpinnerEventTaskNameList.setEnabled(false);
        }


        List<EventModel> eventModels = null;
        try {
            eventModels = GreenDaoManager.getInstance(getActivity()).getEventsList();
            Log.d("data", "" + eventModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (eventModels == null) {
            eventModels = new ArrayList<>();
        }
        for (int i = 0; i < eventModels.size(); i++) {
            mvolunteerList.put(eventModels.get(i).getEventId(), eventModels.get(i).getEventName());
        }
        if (eventModels.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                    getValuesForMapVolunteerList(mvolunteerList));

            mSpinnerVolunteerList.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } else {
            mSpinnerVolunteerList.setEnabled(false);
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


    private List<String> getValuesForMapVolunteerList(Map<Integer, String> map) {


        list.add(0, "--Select Event--");


        for (Integer integer : map.keySet()) {
            list.add(map.get(integer));
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    private List<String> getValuesForMapEventTaskList(Map<Integer, String> map) {


        list1.add(0, "--Select Task--");


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

            case R.id.event_task_allote:
                if (Constants.isInternetWorking(getActivity())) {
                    if (!mSpinnerVolunteerList.getSelectedItem().equals("--Select Event--")) {
                        if (!mSpinnerEventTaskNameList.getSelectedItem().equals("--Select Task--")) {
                        String Eventname = Integer.toString(Event_name);
                        String taskname = Integer.toString(task_name);


                        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
                        intent.putExtra("webservice_key", Constants.EVANTTASKALLOTMENT);
                        intent.putExtra("eventmane", Eventname);
                        intent.putExtra("taskname", taskname);

                        getActivity().startService(intent);

                        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
                        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
                        mProgressDialog.show();
                    } else {
                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                        alertDialogUtils.errorAlert(getActivity(), "Task",
                                "Please Select Task Name.");
                    }
                    } else {
                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                        alertDialogUtils.errorAlert(getActivity(), "Event",
                                "Please Select Event Name.");
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

                userid = (String) mSpinnerVolunteerList.getSelectedItem();
                Event_name = getKeyForValueVolunteerList(userid, mvolunteerList);

                Log.d("userid ", "Task Cat Id  -- " + Event_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (parent.getId() == R.id.event_list_spinner_list) {

            eventtaskid = (String) mSpinnerEventTaskNameList.getSelectedItem();
            task_name = getKeyForValuemEventTaskList(eventtaskid, mEventTaskList);
            Log.d("eventtask_id ", "eventtask_id  -- " + task_name);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case EVENTTASKADD_WEBSERVICE:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("EVENT TASK");
                    builder.setMessage("Add Event Task successfully. ");
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

                showAlertDialog("Event Task Already Add", "Please check Event name and Task name.");
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
}
