package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;

import android.widget.LinearLayout;

import java.util.List;


public class SocialMediaFragment extends Fragment implements View.OnClickListener {


    private LinearLayout fb, twit, gmail, whatsapp;
    private ImageView img;
    String fileName, externalStorageDirectory, myDir;
    Uri uri;
    Intent shareIntent;
    List<ResolveInfo> activityList;

    public static SocialMediaFragment newInstance() {
        return new SocialMediaFragment();
    }

    public SocialMediaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.socialmedia, container, false);
        fb = (LinearLayout) view.findViewById(R.id.fb);
        twit = (LinearLayout) view.findViewById(R.id.twit);
        gmail = (LinearLayout) view.findViewById(R.id.gmail);
        whatsapp = (LinearLayout) view.findViewById(R.id.whatsapp);

        fb.setOnClickListener(this);
        twit.setOnClickListener(this);
        gmail.setOnClickListener(this);
        whatsapp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Social Media");
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "War Room");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onClick(View v) {

        fileName = "image-3116.jpg";
        externalStorageDirectory = Environment.getExternalStorageDirectory().toString();
        myDir = externalStorageDirectory + "/saved_images/"; // the
        // file will be in saved_images
        Uri uri = Uri.parse("file:///" + myDir + fileName);

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        PackageManager pm = v.getContext().getPackageManager();
        activityList = pm.queryIntentActivities(shareIntent, 0);

        switch (v.getId()) {
            case R.id.fb:

                boolean a = true;
                for (final ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).startsWith("com.facebook")) {
                        a = false;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        v.getContext().startActivity(shareIntent);
                        startActivity(shareIntent);
                        break;
                    }

                }
                if (a == true) {
                   // showAlertDialog("FACEBOOK", "FACEBOOK is not available in Your Mobile Phone.");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en-gb.facebook.com/login/"));
                    startActivity(browserIntent);
                }
                break;

            case R.id.twit:
                boolean b = true;
                try {


                    for (final ResolveInfo app : activityList) {
                        if ((app.activityInfo.name).contains("com.twitter")) {
                            b = false;
                            final ActivityInfo activity = app.activityInfo;
                            final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                            shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                            shareIntent.setComponent(name);
                            //v.getContext().startActivity(shareIntent);
                            startActivity(shareIntent);
                            break;
                        }

                    }
                    if (b == true) {
                        //showAlertDialog("TWITTER", "TWITTER is not available in Your Mobile Phone.");
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/login/"));
                        startActivity(browserIntent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case R.id.gmail:
                boolean c = true;
                for (final ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).contains("android.gm")) {
                        c = false;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        v.getContext().startActivity(shareIntent);

                        break;
                    }

                }
                if (c == true) {
                    showAlertDialog("GMAIL", "GMAIL is not available in Your Mobile Phone.");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/ServiceLogin#identifier/"));
                    startActivity(browserIntent);
                }
                break;

            case R.id.whatsapp:
                boolean d = true;

                for (final ResolveInfo app : activityList) {
                    if ((app.activityInfo.name).contains("com.whatsapp")) {
                        d = false;
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, " ");
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        shareIntent.setComponent(name);
                        v.getContext().startActivity(shareIntent);

                        break;
                    }

                }
                if (d == true) {
                    showAlertDialog("WHATSAPP", "WHATSAPP is not available in Your Mobile Phone.");
                }
                break;
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(getActivity(), title, message);
    }
}
