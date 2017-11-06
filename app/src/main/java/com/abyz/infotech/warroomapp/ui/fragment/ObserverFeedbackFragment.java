package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.abyz.infotech.warroomapp.Camera.ImageChooserUtility;
import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.StatusWebserviceResponse;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Gunvant on 05-May-16.
 */
public class ObserverFeedbackFragment extends Fragment implements View.OnClickListener {
    private ProgressDialog mProgressDialog;
    private EditText mSubject, mFeedback, mPopulation, mClappingPoints;
    private Button mFeedbackButton;
    public Integer eventId;
    private boolean loadImageOnlyOnce = false;
    private int mImageViewDimension;
    private ImageView mImageView;
    private VideoView video;
    private String[] mPhotoFileName = new String[1], fileNames;
    private static final int RCBOOK_IMAGE = 1,VIDEO_UPLODE = 1;
    private String fileName;

    public static ObserverFeedbackFragment newInstance() {
        return new ObserverFeedbackFragment();
    }

    public ObserverFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ObserverFee", "ObserverFeedbackFragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        mSubject = (EditText) view.findViewById(R.id.feedback_subject);
        mFeedback = (EditText) view.findViewById(R.id.feedback_feedback);
        mPopulation = (EditText) view.findViewById(R.id.feedback_population);
        mClappingPoints = (EditText) view.findViewById(R.id.feedback_clapping_points);
        video = (VideoView)view.findViewById(R.id.video_upload);
      /*  mFeedbackButton = (Button) view.findViewById(R.id.feedback_button);
        mFeedbackButton.setOnClickListener(this);*/
        view.findViewById(R.id.feedback_button).setOnClickListener(this);
        video.setOnClickListener(this);

        mImageView = (ImageView) view.findViewById(R.id.photo_upload);
        view.findViewById(R.id.photo_upload).setOnClickListener(this);
        view.findViewById(R.id.video_uploads).setOnClickListener(this);



        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!loadImageOnlyOnce) {
                    loadImageOnlyOnce = true;
                    mImageViewDimension = mImageView.getMeasuredWidth();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
                    params.height = mImageViewDimension;
                    mImageView.setLayoutParams(params);
                    setImages();
                }


                return true;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(false, "Feedback");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == RCBOOK_IMAGE) {
                Bitmap bitmap = null;
                try {
                    bitmap = ImageChooserUtility.getImageFromResult(getActivity(), resultCode, data);
                    saveBitmapToFile(bitmap, requestCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(), "Camera Application Error", "There seems to be some issue " +
                            "with the Camera Application.\nPlease select the image file from Photo Gallery");
                }

            }

            if (requestCode == VIDEO_UPLODE) {
                try {
                   // video = ImageChooserUtility.getImageFromResult(getActivity(), resultCode, data);
                    saveVideoToFile(requestCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(), "Camera Application Error", "There seems to be some issue " +
                            "with the Camera Application.\nPlease select the Video file from Video Gallery"
                    +requestCode);
                }


            }
        }
    }
private void saveVideoToFile(final int position) throws Exception {
    fileName = Constants.getIMEI(getActivity())
            + "_" + position + "_"+ ".mp4";

    File imageFile = new File(getActivity().getCacheDir(), fileName);
    imageFile.createNewFile();

    FileOutputStream fos = new FileOutputStream(imageFile);
    fos.write(position);
    fos.flush();
    fos.close();

    RestAdapter restAdapter = new RestAdapter.Builder().
            setEndpoint("http://www.rbkfamily.com/war_room").setLogLevel(RestAdapter.LogLevel.NONE)
            .build();
    TypedFile typedFile = new TypedFile("application/octet-stream", imageFile);
    IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
    iRetrofitWebservice.uploadFile(typedFile, new Callback<StatusWebserviceResponse>() {
        @Override
        public void success(StatusWebserviceResponse statusWebserviceResponse, Response response) {
            showAlertDialog("success", "success");
            mPhotoFileName[position - 1] = fileName;
            switch (position) {
                case VIDEO_UPLODE:
                   // video.setVideoURI(ThumbnailUtils.extractThumbnail(bitmap, mImageViewDimension,
                 //           mImageViewDimension));

                    Log.d("mImageViewRCBook", "mImageViewRCBook  " + mImageView);
                    break;

            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.i("Status", error.getUrl());
            showAlertDialog("failure", "failure");
        }
    });


}
    private void saveBitmapToFile(final Bitmap bitmap, final int position) throws Exception {
        fileName = Constants.getIMEI(getActivity())
                + "_" + position + "_" + System.currentTimeMillis() + ".jpeg";

        //fileNames = fileName;

        Log.d("fileName", "------fileName-----" + fileName);

        File imageFile = new File(getActivity().getCacheDir(), fileName);
        imageFile.createNewFile();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();

        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();

        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint("http://www.rbkfamily.com/war_room").setLogLevel(RestAdapter.LogLevel.NONE)
                .build();

        TypedFile typedFile = new TypedFile("application/octet-stream", imageFile);
        IRetrofitWebservice iRetrofitWebservice = restAdapter.create(IRetrofitWebservice.class);
        iRetrofitWebservice.uploadFile(typedFile, new Callback<StatusWebserviceResponse>() {
            @Override
            public void success(StatusWebserviceResponse statusWebserviceResponse, Response response) {
                Log.i("Status", statusWebserviceResponse.getStatus());
                mPhotoFileName[position - 1] = fileName;
                switch (position) {
                    case RCBOOK_IMAGE:
                        mImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, mImageViewDimension,
                                mImageViewDimension));

                        Log.d("mImageViewRCBook", "mImageViewRCBook  " + mImageView);
                        break;

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Status", error.getUrl());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setTitleAndToggleActionBarDrawerState(true, "Event Detail");
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private void setImages() {
        try {
            Log.d("pic-", "" + "/1/" + mPhotoFileName[0]);

            if (mPhotoFileName[0].toString() != null && !mPhotoFileName[0].isEmpty()) {
                Picasso.with(getActivity())
                        .load(Constants.PICASSO_BASE_URL + mPhotoFileName[0])
                        .fit()
                        .centerCrop()
                        .into(mImageView);
            } else {

                mPhotoFileName[0] = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("feedback_button", "feedback_button 1");
        switch (v.getId()) {
            case R.id.feedback_button:

                mFeedback.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager keyboard = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.hideSoftInputFromWindow(mFeedback.getWindowToken(), 0);
                    }
                }, 1);
                if (Constants.isInternetWorking(getActivity())) {

                    if (!mSubject.getText().toString().isEmpty()) {

                        if (mSubject.getText().toString().trim().length() > 5) {

                            if (!mFeedback.getText().toString().isEmpty()) {

                                if (mFeedback.getText().toString().trim().length() > 3) {

                                    feedback();


                                } else {
                                    showAlertDialog("Invalid credentials", "Please Enter Valid feedback message");
                                }
                            } else {
                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Blank Fields",
                                        "Please enter your feedback message.");
                            }
                        } else {
                            showAlertDialog("Invalid credentials", "Please Enter feedback subject.");
                        }
                    } else {
                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                        alertDialogUtils.errorAlert(getActivity(), "Blank Fields",
                                "Please enter feedback subject.");
                    }
                } else {
                    showAlertDialog("Internet is not working", "No Internet Connection");

                }
                break;

            case R.id.photo_upload:

                Intent chooseFirst = ImageChooserUtility.getPickImageIntent(getActivity());
                startActivityForResult(chooseFirst, RCBOOK_IMAGE);
                break;


            case R.id.video_uploads:

                Log.d("video_upload","video_upload --------------------- ");
                Intent chooseVideo = ImageChooserUtility.getPickVideoIntent(getActivity());
                startActivityForResult(chooseVideo, VIDEO_UPLODE);
                break;

        }

    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case FEEDBACK_WEBSERVICE:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                showAlertDialog("Feedback", "Your feedback is successfully submitted");
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

    private void feedback() {

        String Subject = mSubject.getText().toString().trim();
        String Feedback = mFeedback.getText().toString().trim();
        String Population = mPopulation.getText().toString().trim();
        String ClappingPoints = mClappingPoints.getText().toString().trim();
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        int usertype = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type_id = Integer.toString(usertype);

        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
        intent.putExtra("webservice_key", Constants.FEEDBACK);

        intent.putExtra("feed_usrid", user_id);
        intent.putExtra("feed_usertype_id", user_type_id);
        intent.putExtra("feed_event_id", "0");
        intent.putExtra("feed_subject", Subject);
        intent.putExtra("feed_desc", Feedback);
        intent.putExtra("feed_image", fileName);
        intent.putExtra("feed_video", "nbnx");
        intent.putExtra("feed_population", Population);
        intent.putExtra("feed_clappingpoint", ClappingPoints);
        getActivity().startService(intent);

        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
        mProgressDialog.show();

    }
}

