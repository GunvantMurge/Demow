package com.abyz.infotech.warroomapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.EVENT_BUS_MESSAGE;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;

import de.greenrobot.event.EventBus;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button mLogin, mSubmitButton, mVerifyButton, mOTPSubmitButton;
    private TextView mForgotPassword, mResendOTP;
    private EditText mUserName, mPassword;
    private LinearLayout mPasswordLinearLayout;
    private CheckBox mRememberMe;
    private int logingbackvalue = 1;
    private AlphaAnimation ClickedEffect = new AlphaAnimation(1F, 0.3F);
    private ProgressDialog mProgressDialog;

    private String userName,password ;
    private String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String regexStr = "^[0-9]*$";

    public LoginFragment() {
        // Requir empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLogin = (Button) view.findViewById(R.id.login_button);
        mLogin.setOnClickListener(this);

        mSubmitButton = (Button) view.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(this);

        mVerifyButton = (Button) view.findViewById(R.id.verify_button);
        mVerifyButton.setOnClickListener(this);

        mOTPSubmitButton = (Button) view.findViewById(R.id.submit_passwors_button);
        mOTPSubmitButton.setOnClickListener(this);

        mForgotPassword = (TextView) view.findViewById(R.id.forgot_password_text_view);
        mForgotPassword.setOnClickListener(this);

        mPasswordLinearLayout = (LinearLayout) view.findViewById(R.id.password_linear_layout);
        mRememberMe = (CheckBox) view.findViewById(R.id.checkBox);
        mResendOTP = (TextView) view.findViewById(R.id.text_view_resend_otp);
        mResendOTP.setOnClickListener(this);
        mUserName = (EditText) view.findViewById(R.id.user_name_edit_text);
        mPassword = (EditText) view.findViewById(R.id.password_edit_text);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // userName = mUserName.getText().toString().trim();
      //  mUserName.setText("8975574261");
     //   mPassword.setText("123456");

        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            loginBack();
                            return false;
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.login_button:

                v.startAnimation(ClickedEffect);
                if (Constants.isInternetWorking(getActivity())) {
                login();
                } else {
                    showAlertDialog("Internet is not working", "No Internet Connection");

                }
                break;


            case R.id.forgot_password_text_view:
                v.startAnimation(ClickedEffect);
                logingbackvalue = 2;
                mLogin.setVisibility(View.GONE);
                mSubmitButton.setVisibility(View.VISIBLE);
                mPasswordLinearLayout.setVisibility(View.GONE);
                mForgotPassword.setVisibility(View.GONE);
                mRememberMe.setVisibility(View.INVISIBLE);
                mUserName.setHint("Email/Mobile No.");
                mUserName.setImeOptions(EditorInfo.IME_ACTION_DONE);
                mResendOTP.setVisibility(View.INVISIBLE);

                break;

            case R.id.submit_button:
                v.startAnimation(ClickedEffect);
                if (Constants.isInternetWorking(getActivity())) {

                if (!mUserName.getText().toString().isEmpty()) {
                    logingbackvalue = 3;
                    if (mUserName.getText().toString().trim().matches(regexStr)) {
                        if (mUserName.length() == 10) {
                            Intent intentOtp = new Intent(getActivity(), WSUtilityIntentService.class);
                            intentOtp.putExtra("webservice_key", Constants.FORGOT_PASSWORD);
                            intentOtp.putExtra("usr_name", mUserName.getText().toString().trim());
                            intentOtp.putExtra("otp", "");
                            getActivity().startService(intentOtp);

                        } else {

                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Invalid Mobile No.", "Please enter correct Mobile No.");
                            mSubmitButton.setClickable(true);
                        }
                    } else {
                        if (mUserName.getText().toString().trim().matches(EMAIL_PATTERN)) {
                            Intent intentOtp = new Intent(getActivity(), WSUtilityIntentService.class);
                            intentOtp.putExtra("webservice_key", Constants.FORGOT_PASSWORD);
                            intentOtp.putExtra("usr_name", mUserName.getText().toString().trim());
                            intentOtp.putExtra("otp", "");
                            getActivity().startService(intentOtp);

                        } else {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "invalid Email.", "Please enter correct Email Id");
                            mSubmitButton.setClickable(true);
                        }
                    }
                } else {
                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(), "Blank Fields",
                            "Please fill up the blank fields Email /Mobile No.");
                    mSubmitButton.setClickable(true);
                } } else {
                    showAlertDialog("Internet is not working", "No Internet Connection");

                }


                break;

            case R.id.text_view_resend_otp:
                v.startAnimation(ClickedEffect);
                if (Constants.isInternetWorking(getActivity())) {
                String resendotp = Constants.OTP;
                mResendOTP.setClickable(false);

                Constants.USER_NAME_RESEND = mUserName.getText().toString().trim();
              //  String name = userName;
                Intent intentResendOtp = new Intent(getActivity(), WSUtilityIntentService.class);
                intentResendOtp.putExtra("webservice_key", Constants.FORGOT_PASSWORD);
                intentResendOtp.putExtra("usr_name", Constants.MOBILE_NO_OTP);
                intentResendOtp.putExtra("otp", resendotp);
                getActivity().startService(intentResendOtp);

        } else {
            showAlertDialog("Internet is not working", "No Internet Connection");

        }
                break;

            case R.id.verify_button:

                v.startAnimation(ClickedEffect);
                logingbackvalue = 1;
                if (!mUserName.getText().toString().isEmpty()) {
                    if (Constants.OTP.equals(mUserName.getText().toString().trim())) {

                        mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        mUserName.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        EventBus.getDefault().post(new MessageEventBus(EVENT_BUS_MESSAGE.OTP_CONFIRMED));

                    } else {
                        new AlertDialogUtils().errorAlert(getActivity(),
                                "OTP Error", "Please enter the correct OTP");
                    }
                } else {
                    new AlertDialogUtils().errorAlert(getActivity(),
                            "Blank Fields", "Please fill up the blank fields OTP");
                }

                break;

            case R.id.submit_passwors_button:
                v.startAnimation(ClickedEffect);
                if (Constants.isInternetWorking(getActivity())) {
                if (!mUserName.getText().toString().isEmpty()) {

                    if (mUserName.getText().toString().trim().length() >= 6) {

                        if (!mPassword.getText().toString().isEmpty()) {

                            if (mUserName.getText().toString().trim().equals(mPassword.getText().toString().trim())) {

                                mUserName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                                Intent intentChangePass = new Intent(getActivity(), WSUtilityIntentService.class);

                                intentChangePass.putExtra("webservice_key", Constants.CHANGE_PASSWORD);
                                int user_ids = Constants.USR_ID;
                                String user_id = Integer.toString(user_ids);
                                intentChangePass.putExtra("usr_id", user_id);
                                intentChangePass.putExtra("password", mUserName.getText().toString().trim());
                                getActivity().startService(intentChangePass);
                            } else {
                                showAlertDialog("Mismatch Error", "Password and Confirmation password do not match.");
                            }
                        } else {
                            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                            alertDialogUtils.errorAlert(getActivity(), "Blank Fields", "Please fill up the blank fields confirmation password.");
                        }
                    } else {
                        showAlertDialog("Minimum Character", "Password should have minimum 6 characters");
                    }

                } else {
                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(),  "Blank Fields","Please fill up the blank fields Password");
                }} else {
                    showAlertDialog("Internet is not working", "No Internet Connection");

                }
                break;
        }
    }

    public void loginBack() {

        switch (logingbackvalue) {

            case 2:
                mLogin.setVisibility(View.VISIBLE);
                mSubmitButton.setVisibility(View.GONE);
                mPasswordLinearLayout.setVisibility(View.VISIBLE);
                mForgotPassword.setVisibility(View.VISIBLE);
                mRememberMe.setVisibility(View.VISIBLE);
                mResendOTP.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mLogin.setVisibility(View.VISIBLE);
                mSubmitButton.setVisibility(View.GONE);
                mVerifyButton.setVisibility(View.GONE);
                mPasswordLinearLayout.setVisibility(View.VISIBLE);
                mRememberMe.setVisibility(View.GONE);
                mForgotPassword.setVisibility(View.VISIBLE);
                mRememberMe.setVisibility(View.GONE);
                mResendOTP.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void login() {

        if (!mUserName.getText().toString().isEmpty()) {

            if (mUserName.getText().toString().trim().length() > 3) {

                if (!mPassword.getText().toString().isEmpty()) {

                    if (mPassword.getText().toString().trim().length() > 5) {

                        if (mUserName.getText().toString().trim().matches(regexStr)) {

                            if (mUserName.length() == 10) {
                                fetchUserInput();
                            } else {
                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "Invalid Mobile No.", "Please enter correct Mobile No.");
                            }
                        } else {
                            if (mUserName.getText().toString().trim().matches(EMAIL_PATTERN)) {
                                fetchUserInput();
                            } else {
                                AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                                alertDialogUtils.errorAlert(getActivity(), "invalid Email.", "Please enter correct Email Id");
                            }
                        }
                    } else {
                        showAlertDialog("Invalid credentials", "Please Enter Valid Password");
                    }
                } else {
                    AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
                    alertDialogUtils.errorAlert(getActivity(), "Blank Fields", "Please fill up the blank fields Password");
                }

            } else {
                showAlertDialog("Invalid credentials", "Please Enter Valid User ID");
            }
        } else {
            AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
            alertDialogUtils.errorAlert(getActivity(), "Blank Fields",
                    "Please fill up the blank fields Email /Mobile No.");
        }

    }

    public void fetchUserInput() {

        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(getActivity());
        mProgressDialog.setMessage(getActivity().getString(R.string.hold_message));
        mProgressDialog.show();

        userName = mUserName.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        Intent intent = new Intent(getActivity(), WSUtilityIntentService.class);
        intent.putExtra("webservice_key", Constants.LOGIN);
        intent.putExtra("usr_name", userName);
        intent.putExtra("usr_pwd", password);
        getActivity().startService(intent);

    }

    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(getActivity(), title, message);
    }

    private void clearEditText() {
        mUserName.setText("");
        mPassword.setText("");
    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case LOGIN_WEBSERVICE:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (mRememberMe.isChecked()) {

                    SharedPreferencesUtility.savePrefBoolean(getActivity(), Constants.LOGIN_STATUS, true);
                    SharedPreferencesUtility. savePrefString(getActivity(), Constants.SHARED_PREFERENCE_USERNAME, userName);
                    SharedPreferencesUtility. savePrefString(getActivity(), Constants.SHARED_PREFERENCE_PASSWORD, password);

                    Intent sharedPreferencesNavigationActivityt = new Intent(getActivity(), NavigationActivity.class);
                    int a = 1;

                    Bundle bundle = new Bundle();
                    bundle.putInt("no", a);
                    sharedPreferencesNavigationActivityt.putExtras(bundle);
                    startActivity(sharedPreferencesNavigationActivityt);
                    getActivity().finish();
                } else {
                    Intent notsharedPreferencesNavigationActivityt = new Intent(getActivity(), NavigationActivity.class);
                    startActivity(notsharedPreferencesNavigationActivityt);
                    getActivity().finish();
                }
                break;
            case INVALID_CREDENTIALS:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearEditText();
                new AlertDialogUtils().errorAlert(getActivity(),
                        getActivity().getString(R.string.invalid_credintials),
                        getActivity().getString(R.string.check_uname));

                mUserName.setText(userName);
                break;
            case INVALID_PASSWORD:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearEditText();
                new AlertDialogUtils().errorAlert(getActivity(),
                        getActivity().getString(R.string.invalid_credintials),
                        getActivity().getString(R.string.check_password));
                break;

            case OTP_DELIVERED:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                mUserName.setText("");
                mLogin.setVisibility(View.GONE);
                mSubmitButton.setVisibility(View.GONE);
                mVerifyButton.setVisibility(View.VISIBLE);
                mPasswordLinearLayout.setVisibility(View.INVISIBLE);
                mForgotPassword.setVisibility(View.GONE);
                mRememberMe.setVisibility(View.INVISIBLE);
                mResendOTP.setVisibility(View.VISIBLE);
                mUserName.setHint(" Enter OTP");
                mUserName.setHintTextColor(getResources().getColor(R.color.color_graylight));
                mUserName.setInputType(InputType.TYPE_CLASS_PHONE);
                break;

            case OTP_CONFIRMED:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                mUserName.setText("");
                mUserName.setHint("password");
                mPassword.setHint("Confirm password");
                mUserName.setHintTextColor(getResources().getColor(R.color.color_graylight));
                mPassword.setHintTextColor(getResources().getColor(R.color.color_graylight));
                mUserName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                mUserName.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLogin.setVisibility(View.GONE);
                mSubmitButton.setVisibility(View.GONE);
                mVerifyButton.setVisibility(View.GONE);
                mOTPSubmitButton.setVisibility(View.VISIBLE);
                mPasswordLinearLayout.setVisibility(View.VISIBLE);
                mForgotPassword.setVisibility(View.GONE);
                mRememberMe.setVisibility(View.INVISIBLE);
                mResendOTP.setVisibility(View.INVISIBLE);

                break;

            case UNABLE_TO_CHANGE_PASSWORD:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearEditText();
                showAlertDialog("Error", "Unable to change password.");
                resetView();
                break;

            case PASSWORD_CHANGE_SUCCESSFUL:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearEditText();

                AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                builde.setTitle("Password Change");
                builde.setMessage("Your password has been changed.\nPlease login with new password");
                builde.setCancelable(false);
                builde.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //   mUserName.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        dialog.dismiss();
                        resetView();
                    }
                });
                builde.create().show();
                break;

            case WEBSERVICE_DOWN:
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();}
                }catch (Exception e){
                    e.printStackTrace();
                }
                showAlertDialog("WEBSERVICE_DOWN", "Please check your internet connection and try again.");
                break;
        }
    }

    private void resetView() {
        mPassword.setText("");
        mUserName.setHint("Email/Mobile No.");
        mPassword.setHint("Password");
        mUserName.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mUserName.setInputType(InputType.TYPE_CLASS_TEXT);
        mLogin.setVisibility(View.VISIBLE);
        mSubmitButton.setVisibility(View.GONE);
        mOTPSubmitButton.setVisibility(View.GONE);
        mPasswordLinearLayout.setVisibility(View.VISIBLE);
        mForgotPassword.setVisibility(View.VISIBLE);
        mRememberMe.setVisibility(View.VISIBLE);

    }
}

