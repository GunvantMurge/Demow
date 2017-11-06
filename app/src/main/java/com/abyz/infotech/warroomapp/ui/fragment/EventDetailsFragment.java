package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.CircleTransform;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDetailsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public Integer eventId, mSpeakerSId;
    private Long date, time;
    private String eventName, eventImage, eventLocation, eventDesc, mSpeakerSelectedId, mMobileNo;
    public List<EventModel> eventModels;
    private AppCompatSpinner mEventSpeakers;
    private TextView mEventDate, mEventTime, mEvantName, eventdesc, mLocation;
    private Map<Integer, String> mEventSpeaker = new HashMap<>();
    List<String> list = new ArrayList<>();
    private ImageView mImage, mEventImage;
    Button button_feedback;

    View view;
    ByteArrayOutputStream bytearrayoutputstream;
    File file;
    FileOutputStream fileoutputstream;
    private String imagename;

    public static EventDetailsFragment newInstance() {
        return new EventDetailsFragment();
    }

    public EventDetailsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        eventId = bundle.getInt("event_id");
        date = bundle.getLong("event_date");
        time = bundle.getLong("event_time");
        eventName = bundle.getString("event_name");
        eventImage = bundle.getString("event_image");
        eventDesc = bundle.getString("event_desc");
        eventLocation = bundle.getString("event_location");

        Log.d("eventId", "eventId " + eventId);
        try {
            eventModels = GreenDaoManager.getInstance(getActivity()).getEventDetails(eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("eventModels", "eventModels " + eventModels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        mEventDate = (TextView) view.findViewById(R.id.view_date);
        mEventTime = (TextView) view.findViewById(R.id.view_time);
        mEvantName = (TextView) view.findViewById(R.id.event_name);
        eventdesc = (TextView) view.findViewById(R.id.event_desc);
        mLocation = (TextView) view.findViewById(R.id.view_location);
        mEventSpeakers = (AppCompatSpinner) view.findViewById(R.id.spinner_speaker_list);

        button_feedback = (Button) view.findViewById(R.id.feedback);

        mImage = (ImageView) view.findViewById(R.id.image);
        mEventImage = (ImageView) view.findViewById(R.id.event_names);

        button_feedback.setOnClickListener(this);
        mEventSpeakers.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEvantName.setText(eventName);
        mLocation.setText(eventLocation);
        mEventDate.setText(Constants.getStringDataFromTimestamp(date));
        mEventTime.setText(Constants.getStringTimeFromTimestamp1(time));
        eventdesc.setText(eventDesc);

        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));


        try {
            if (usertype == 4) {
                button_feedback.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Picasso.with(getActivity())
                .load(Constants.PICASSO_BASE_URL + eventImage)
                .transform((Transformation) new CircleTransform())
                .fit()
                .centerCrop()
                .into(mImage);

        Picasso.with(getActivity())
                .load(Constants.PICASSO_BASE_URL + eventImage)
                .fit()
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(mEventImage);
        for (int i = 0; i < eventModels.size(); i++) {

            mEventSpeaker.put(eventModels.get(i).getSpeakerId(), eventModels.get(i).getSpeakerName());
        }
        if (mEventSpeaker.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                    getValues(mEventSpeaker));

            mEventSpeakers.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } else {
            mEventSpeakers.setEnabled(false);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(false, "Event Detail");
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Events");
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {

        FeedbackFragment feedbackFragment = FeedbackFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("event_id", eventId);
        list.clear();
        feedbackFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, feedbackFragment,
                "feedback_Fragment").addToBackStack("")
                .commit();
    }

    private List<String> getValues(Map<Integer, String> map) {

        try {
            if (eventModels != null) {
                list.add(0, "---Show Speakers---");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Integer integer : map.keySet()) {
            list.add(map.get(integer));
        }
        Collections.sort(list);
        return list;
    }

    private int getKeyForValue(String value, Map<Integer, String> map) {
        for (Integer integer : map.keySet()) {
            if (map.get(integer).equalsIgnoreCase(value)) {
                return integer;
            }
        }
        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // mSpeakerSelectedId = eventModels.get(position + 1).getSpeakerId().toString();

        mSpeakerSelectedId = (String) mEventSpeakers.getSelectedItem();
        mSpeakerSId = getKeyForValue(mSpeakerSelectedId, mEventSpeaker);

        if (mSpeakerSId != 0) {
            speackesProfiles(mSpeakerSId);
        }
    }

    private void speackesProfiles(Integer mSpeakerSId) {

        Log.d("mSpeakerSId", "mSpeakerSId " + mSpeakerSId);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_event_image);


        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.popup_speacer_profile);

        ImageView mImage = (ImageView) dialog1.findViewById(R.id.image);
        ImageView callphone = (ImageView) dialog1.findViewById(R.id.call_phone);
        TextView speakername = (TextView) dialog1.findViewById(R.id.speaker_name);
        TextView eventname = (TextView) dialog1.findViewById(R.id.event_name);
        TextView speakertype = (TextView) dialog1.findViewById(R.id.speaker_type);
        TextView speakertopic = (TextView) dialog1.findViewById(R.id.speaker_topic);
        TextView speakerdesc = (TextView) dialog1.findViewById(R.id.speaker_desc);
        final TextView mobileno = (TextView) dialog1.findViewById(R.id.mobile_no);

        // mImage.setImageBitmap(getCircleBitmap(bm));
        String imagename = null;

        for (int i = 0; i < eventModels.size(); i++) {
            if (eventModels.get(i).getSpeakerId() == mSpeakerSId) {
                speakername.setText(eventModels.get(i).getSpeakerName());
                eventname.setText(eventModels.get(i).getEventName());
                speakertype.setText(eventModels.get(i).getSpeakerTypeName());
                speakertopic.setText(eventModels.get(i).getEpTopic());
                speakerdesc.setText(eventModels.get(i).getEpSpeakerDesc());
                mobileno.setText(eventModels.get(i).getSpeakerMobileNo());
                mMobileNo = eventModels.get(i).getSpeakerMobileNo();
                imagename = eventModels.get(i).getSpeakerImage();


                Log.d("getSpeakerId", " " + eventModels.get(i).getSpeakerId());
                Log.d("getEventName", " " + eventModels.get(i).getEventName());
                Log.d("getEpTopic", " " + eventModels.get(i).getEpTopic());
                Log.d("getSpeakerImage", " " + eventModels.get(i).getSpeakerImage());
                Log.d("getEpSpeakerDesc", " " + eventModels.get(i).getEpSpeakerDesc());
                Log.d("getSpeakerTypeName", " " + eventModels.get(i).getSpeakerTypeName());
                Log.d("getSpeakerName", " " + eventModels.get(i).getSpeakerName());
                Log.d("getSpeakerMobileNo", " " + eventModels.get(i).getSpeakerMobileNo());
            }
        }

        Picasso.with(getActivity())
                .load(Constants.PICASSO_BASE_URL + imagename)
                .transform((Transformation) new CircleTransform())
                .fit()
                .centerCrop()
                .into(mImage);

        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mMobileNo));
                startActivity(intent);

            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER_HORIZONTAL;

        dialog1.show();
        dialog1.getWindow().setLayout(lp.width = WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
