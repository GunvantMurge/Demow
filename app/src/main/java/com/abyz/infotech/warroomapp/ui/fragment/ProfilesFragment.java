package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abyz.infotech.warroomapp.Camera.ImageChooserUtility;
import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.CircleTransform;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.retofit.IRetrofitWebservice;
import com.abyz.infotech.warroomapp.retofit.wsmodel.StatusWebserviceResponse;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.adapter.TaskRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;
import com.abyz.infotech.warroomapp.ui.model.UserModel;
import com.squareup.picasso.Picasso;
import com.abyz.infotech.warroomapp.ui.model.UserProfileModel;
import com.abyz.infotech.warroomapp.ui.adapter.UserTypeRecycleViewAdapter;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class ProfilesFragment extends Fragment implements View.OnClickListener {

    private TextView mChangePhoto, mChangePassword, mChangeMobileno, mMobile_no, mUserName, mUserAddre;
    private ImageView mProfilePhoto;
    private LinearLayout linearLayout;
    private boolean loadImageOnlyOnce = false;
    private int mImageViewDimension;
    private String[] mPhotoFileName = new String[1];
    private static final int RCBOOK_IMAGE = 1;
    private RecyclerView mRecyclerView;
    private UserTypeRecycleViewAdapter userTypeRecycleViewAdapter;
    private ProgressDialog mProgressDialog;
    private List<UserModel> userModels = null;
    private int OTP;

    String newPass, OldPass, password, mNo;

    View view;
    ByteArrayOutputStream bytearrayoutputstream;
    File file;
    FileOutputStream fileoutputstream;
    //final Bitmap bitmap;

    public static ProfilesFragment newInstance() {
        return new ProfilesFragment();
    }

    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            userModels = GreenDaoManager.getInstance(getActivity()).getUserMode();
            Log.d("data", "---------------" + userModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exp, container, false);
        mChangePhoto = (TextView) view.findViewById(R.id.change_photo);
        mChangePassword = (TextView) view.findViewById(R.id.change_password);
        mChangeMobileno = (TextView) view.findViewById(R.id.change_mobile_no);
        mUserName = (TextView) view.findViewById(R.id.user_name);
        mUserAddre = (TextView) view.findViewById(R.id.user_addre);
        mMobile_no = (TextView) view.findViewById(R.id.mobile_no);
        mProfilePhoto = (ImageView) view.findViewById(R.id.image);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mChangePhoto.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        mChangeMobileno.setOnClickListener(this);

        mPhotoFileName[0] = userModels.get(0).getUsrImage();

        Log.d("-------", "=========>" + userModels.get(0).getUsrImage());
        mMobile_no.setText(userModels.get(0).getUsrMobile());
        mUserName.setText(userModels.get(0).getUsrName());
        mUserAddre.setText(userModels.get(0).getUsrAddress());

        SharedPreferencesUtility.savePrefString(getActivity(), Constants.SHARED_PREFERENCE_ADDRESH, userModels.get(0).getUsrAddress());
        SharedPreferencesUtility.savePrefString(getActivity(), Constants.SHARED_PREFERENCE_IMAGE, userModels.get(0).getUsrImage());
        SharedPreferencesUtility.savePrefString(getActivity(), Constants.SHARED_PREFERENCE_NAME, userModels.get(0).getUsrName());

        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!loadImageOnlyOnce) {
                    loadImageOnlyOnce = true;
                    mImageViewDimension = mProfilePhoto.getMeasuredWidth();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mProfilePhoto.getLayoutParams();
                    params.height = mImageViewDimension;
                    mProfilePhoto.setLayoutParams(params);
                    setImages();
                }
                return true;
            }
        });
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
        navigationActivity.setTitleAndToggleActionBarDrawerState(false, "Profiles");
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.change_photo:
                Intent chooseFirst = ImageChooserUtility.getPickImageIntent(getActivity());
                startActivityForResult(chooseFirst, RCBOOK_IMAGE);
                break;

            case R.id.change_password:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                password = (SharedPreferencesUtility.getPrefString(getActivity(), Constants.SHARED_PREFERENCE_PASSWORD));

                Log.d("password", "password  ---->" + password);
                builder.setIcon(R.drawable.logo).setTitle("Change Password");
                builder.setCancelable(false);

                final EditText oldpass = new EditText(getActivity());
                // oldpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                oldpass.setHint("Old Password");
                oldpass.setPadding(35, 20, 20, 30);
                layout.addView(oldpass);

                final EditText newpass = new EditText(getActivity());
                // newpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                newpass.setHint("New Password");
                newpass.setPadding(35, 20, 20, 30);
                layout.addView(newpass);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                layout.setLayoutParams(lp);

                builder.setView(layout);

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPass = newpass.getEditableText().toString();
                        OldPass = oldpass.getEditableText().toString();

                        Log.d("newPass", "newPass  ---->" + newPass);
                        Log.d("OldPass", "OldPass  ---->" + OldPass);

                        if (!OldPass.isEmpty()) {

                            if (!newPass.isEmpty()) {

                                if (newPass.length() >= 6) {


                                    if (password.equals(OldPass)) {

                                        Intent intentChangePass = new Intent(getActivity(), WSUtilityIntentService.class);

                                        intentChangePass.putExtra("webservice_key", Constants.CHANGE_PASSWORD);
                                        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
                                        String user_id = Integer.toString(user);

                                        Log.d("user_id", "user_id  " + user_id);
                                        intentChangePass.putExtra("usr_id", user_id);
                                        intentChangePass.putExtra("password", newPass);
                                        getActivity().startService(intentChangePass);

                                        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
                                        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
                                        mProgressDialog.show();


                                    } else {

                                        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                        alertDialogUtils.errorAlert(getActivity(), "MISMATCH!", "Old password should not match please try again");
                                    }
                                } else {
                                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                    alertDialogUtils.errorAlert(getActivity(), "Minimum Character", "Password should have minimum 6 characters");
                                }
                            } else {
                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Blank Fields", "Please fill up the blank fields New Password");
                            }
                        } else {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Blank Fields", "Please fill up the blank fields Old Password");
                        }
                    }

                });
                builder.create().show();

                break;

            case R.id.change_mobile_no:

                ChangeMobileNumberAlert();
                break;
        }
    }

    private void ChangeMobileNumberAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("User mobile no. change");
        builder.setMessage("Please enter new mobile no.");
        builder.setCancelable(false);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter 10 Digit Mobile No.");
        input.setPadding(35, 20, 20, 30);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNo = input.getEditableText().toString();

                String primaryno = mNo;
                mMobile_no.setInputType(InputType.TYPE_CLASS_PHONE);

                if (mNo.length() == 10) {

                    uploadData(primaryno);
                    mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
                    mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
                    mProgressDialog.show();
                    //  EnterOTP();
                } else {

                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(), "Mobile No.", "Please enter the correct Mobile No.");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void uploadData(String number) {
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
        intent.putExtra("webservice_key", Constants.CHANGEMOBILENO);
        intent.putExtra("user_id", user_id);
        intent.putExtra("usr_mob", number);
        intent.putExtra("status", "1");
        getActivity().startService(intent);

    }

    private void setImages() {
        try {
            Log.d("pic-", "" + "/1/ ========>" + mPhotoFileName[0]);


            if (mPhotoFileName[0].toString() != null && !mPhotoFileName[0].isEmpty()) {

                Picasso.with(getActivity())
                        .load(Constants.PICASSO_BASE_URL + mPhotoFileName[0])
                        .transform((Transformation) new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(mProfilePhoto);


            } else {

                mPhotoFileName[0] = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }}

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
        }
    }

    private void saveBitmapToFile(final Bitmap bitmap, final int position) throws Exception {
        //  final String fileName = Constants.getIMEI(getActivity())
        //          + "_" + position + "_" + System.currentTimeMillis() + ".jpeg"; meeting.jpg
        final String fileName = userModels.get(0).getUsrImage();
        //fileNames = fileName;
        Log.d("fileName", "------fileName----->>>>" + fileName);

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
                        mProfilePhoto.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, mImageViewDimension,
                                mImageViewDimension));


                        Log.d("mImageViewRCBook", "mImageViewRCBook  " + mProfilePhoto);


                        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        final Canvas canvas = new Canvas(output);

                        final int color = Color.RED;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        final RectF rectF = new RectF(rect);

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawOval(rectF, paint);

                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);

                        bitmap.recycle();

                        mProfilePhoto.setImageBitmap(output);

                        break;

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Status", error.getUrl());
            }
        });
    }

    private void fetchSqliteData() {
        List<UserProfileModel> userProfileModels = null;
        try {
            userProfileModels = GreenDaoManager.getInstance(getActivity()).getUserTypeModelList();
            Log.d("data", "" + userProfileModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userProfileModels == null) {
            userProfileModels = new ArrayList<>();
        }
        userTypeRecycleViewAdapter = new UserTypeRecycleViewAdapter(userProfileModels, getActivity());
        mRecyclerView.setAdapter(userTypeRecycleViewAdapter);
    }

    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(getActivity(), title, message);
    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {
            case UNABLE_TO_CHANGE_PASSWORD:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                showAlertDialog("Error", "Unable to change password.");

                break;

            case PASSWORD_CHANGE_SUCCESSFUL:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("newPass", "newPass  --->" + newPass);
                SharedPreferencesUtility.savePrefString(getActivity(), Constants.SHARED_PREFERENCE_PASSWORD, newPass);
                String password = (SharedPreferencesUtility.getPrefString(getActivity(), Constants.SHARED_PREFERENCE_PASSWORD));
                Log.d("newPass", "newPass  --->>>" + password);

                AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                builde.setTitle("Password Change");
                builde.setMessage("Your password has been changed.");
                builde.setCancelable(false);
                builde.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();

                    }
                });
                builde.create().show();
                break;

            case MOBILENO_CHANGE_SUCCESSFUL:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // mMobile_no.setText(mNo);
                callalertEnterOTP();
                //  showAlertDialog("MOBILE NO", "Your Mobile No successfuly change.");
                break;

            case MOBILE_NO_CHANGE_SUCCESSFUL:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mMobile_no.setText(mNo);
                GreenDaoManager.getInstance(getActivity()).upDateMobileNo(mNo);
                showAlertDialog("MOBILE NO", "Your Mobile No successfuly change.");
                break;
            case UNABLE_TO_CHANGE_MOBILENO:

                Log.d("", "UNABLE_TO_CHANGE_MOBILENO  ");
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showAlertDialog("MOBILE NO", "Your Mobile no is already exists please try again.");
                break;

            case WEBSERVICE_DOWN:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showAlertDialog("WEBSERVICE_DOWN", "Please check your internet connection and try again.");
                break;
        }

    }

    public void callalertEnterOTP() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        OTP = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.MOBILE_NO_CHANGE_OTP));
        builder.setTitle("Once Time Password ");
        builder.setMessage("Enter the OTP ");
        builder.setCancelable(false);
        final EditText otp = new EditText(getActivity());
        otp.setInputType(InputType.TYPE_CLASS_PHONE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        otp.setLayoutParams(lp);
        builder.setView(otp);
        Log.d("sucess", "sucess=====" + OTP);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("sucess", "sucess" + OTP);
                String OTPs = Integer.toString(OTP);
                if (OTPs.equals(otp.getEditableText().toString())) {
                    Log.d("call", "MOBILE_NO_OTP====>>>");

                    uploadchangeNo(mNo);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void uploadchangeNo(String number) {
        int user = (SharedPreferencesUtility.getPrefInt(getActivity(), Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
        intent.putExtra("webservice_key", Constants.CHANGEMOBILENO);
        intent.putExtra("user_id", user_id);
        intent.putExtra("usr_mob", number);
        intent.putExtra("status", "2");
        getActivity().startService(intent);

    }
}
