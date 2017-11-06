package com.abyz.infotech.warroomapp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.ui.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_login_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (SharedPreferencesUtility.getPrefBoolean(this,Constants.LOGIN_STATUS)) {

            Intent intent = new Intent(this, NavigationActivity.class);
            int a = 0;
            Bundle bundle = new Bundle();
            bundle.putInt("no", a);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();

        }else{
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,loginFragment, "LOGIN_FRAGMENT").commit();
        }
    }

    @Override
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        if (i > 0) {
            getSupportFragmentManager().popBackStack();

        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back button again to exit",
                    Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }}

}
