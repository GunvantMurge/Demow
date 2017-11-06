package com.abyz.infotech.warroomapp.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.AlertDialogUtils;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.MessageEventBus;
import com.abyz.infotech.warroomapp.common.ProgressDialogUtils;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.service.WSUtilityIntentService;
import com.abyz.infotech.warroomapp.ui.adapter.CoordinaterNavigationRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.adapter.NavigationRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.adapter.ObserverNavigationRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.adapter.SpeakerNavigationRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.adapter.VolunteerNavigationRecycleViewAdapter;
import com.abyz.infotech.warroomapp.ui.fragment.AllotmentEventTaskLists;
import com.abyz.infotech.warroomapp.ui.fragment.EventListFragment;
import com.abyz.infotech.warroomapp.ui.fragment.EventTaskLists;
import com.abyz.infotech.warroomapp.ui.fragment.HomeFragment;
import com.abyz.infotech.warroomapp.ui.fragment.ObserverFeedbackFragment;
import com.abyz.infotech.warroomapp.ui.fragment.ProfilesFragment;
import com.abyz.infotech.warroomapp.ui.fragment.SocialMediaFragment;
import com.abyz.infotech.warroomapp.ui.fragment.TaskFragment;
import com.abyz.infotech.warroomapp.ui.model.NavigationItemModel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class NavigationActivity extends AppCompatActivity implements
        VolunteerNavigationRecycleViewAdapter.OnNavigationItemSelectedListener,
        SpeakerNavigationRecycleViewAdapter.OnNavigationItemSelectedListener,
        CoordinaterNavigationRecycleViewAdapter.OnNavigationItemSelectedListener,
        ObserverNavigationRecycleViewAdapter.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;


    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_navigation);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        int usertype = (SharedPreferencesUtility.getPrefInt(this, Constants.SHARED_PREFERENCE_USER_TYPE_ID));

        switch (usertype) {
            case 3:
                setUpNavigationVolunterView();
                break;
            case 4:
                setUpNavigationSpeakerView();
                break;
            case 5:
                setUpNavigationObserverView();
                break;
            case 6:
                setUpNavigationCoordinaterView();
                break;

        }

        setUpToolbar();

        setUpDrawerLayout();

        String user_name = (SharedPreferencesUtility.getPrefString(this, Constants.SHARED_PREFERENCE_USERNAME));
        String password = (SharedPreferencesUtility.getPrefString(this, Constants.SHARED_PREFERENCE_PASSWORD));

        if (Constants.isInternetWorking(this)) {
            try {
                Bundle bundle = getIntent().getExtras();
                int no = bundle.getInt("no");

                if (SharedPreferencesUtility.getPrefBoolean(this, Constants.LOGIN_STATUS)) {

                    if (no != 1) {
                        Log.d("LOGIN_CHECK", "LOGIN_CHECK----" + user_name + "   " + password);
                        Intent intent = new Intent(this, WSUtilityIntentService.class);
                        intent.putExtra("webservice_key", Constants.LOGIN);
                        intent.putExtra("usr_name", user_name);
                        intent.putExtra("usr_pwd", password);
                        this.startService(intent);
                        mProgressDialog = (new ProgressDialogUtils()).getProgressDialog(this);
                        mProgressDialog.setMessage(this.getString(R.string.hold_message));
                        mProgressDialog.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog("Internet is not working", "No Internet Connection");

        }

        launchFragment();
    }
  /*  @Override
    public void onRestart() {
        super.onRestart();
        launchFragment();
    }*/
    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setTitle(getString(R.string.warroom));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpNavigationVolunterView() {
        RecyclerView navigationLayoutRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_navigation_layout);
        navigationLayoutRecyclerView.setHasFixedSize(true);
        navigationLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLayoutRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<NavigationItemModel> navigationItemModels = new ArrayList<>();
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_h, "Home"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_p, "Profiles"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_t, "Task"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_s, "Social Media"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_l, "Logout"));

        VolunteerNavigationRecycleViewAdapter VolunteerNavigationRecycleViewAdapter = new VolunteerNavigationRecycleViewAdapter(navigationItemModels, this);
        VolunteerNavigationRecycleViewAdapter.setOnNavigationItemSelectedListener(this);

        navigationLayoutRecyclerView.setAdapter(VolunteerNavigationRecycleViewAdapter);
    }

    private void setUpNavigationSpeakerView() {
        RecyclerView navigationLayoutRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_navigation_layout);
        navigationLayoutRecyclerView.setHasFixedSize(true);
        navigationLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLayoutRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<NavigationItemModel> navigationItemModels = new ArrayList<>();
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_h, "Home"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_p, "Profiles"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_s, "Social Media"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_l, "Logout"));

        SpeakerNavigationRecycleViewAdapter speakerNavigationRecycleViewAdapter = new SpeakerNavigationRecycleViewAdapter(navigationItemModels, this);
        speakerNavigationRecycleViewAdapter.setOnNavigationItemSelectedListener(this);

        navigationLayoutRecyclerView.setAdapter(speakerNavigationRecycleViewAdapter);
    }

    private void setUpNavigationObserverView() {
        RecyclerView navigationLayoutRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_navigation_layout);
        navigationLayoutRecyclerView.setHasFixedSize(true);
        navigationLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLayoutRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<NavigationItemModel> navigationItemModels = new ArrayList<>();
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_h, "Home"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_p, "Profiles"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_t, "Task"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_f, "Feedback"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_s, "Social Media"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_l, "Logout"));

        ObserverNavigationRecycleViewAdapter observerNavigationRecycleViewAdapter = new ObserverNavigationRecycleViewAdapter(navigationItemModels, this);
        observerNavigationRecycleViewAdapter.setOnNavigationItemSelectedListener(this);

        navigationLayoutRecyclerView.setAdapter(observerNavigationRecycleViewAdapter);
    }

    private void setUpNavigationCoordinaterView() {
        RecyclerView navigationLayoutRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_navigation_layout);
        navigationLayoutRecyclerView.setHasFixedSize(true);
        navigationLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLayoutRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<NavigationItemModel> navigationItemModels = new ArrayList<>();
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_h, "Home"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_p, "Profiles"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_t, "Task"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event Task"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_e, "Event Task Allotment"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_s, "Social Media"));
        navigationItemModels.add(new NavigationItemModel(R.mipmap.ic_l, "Logout"));

        CoordinaterNavigationRecycleViewAdapter coordinaterNavigationRecycleViewAdapter = new CoordinaterNavigationRecycleViewAdapter(navigationItemModels, this);
        coordinaterNavigationRecycleViewAdapter.setOnNavigationItemSelectedListener(this);

        navigationLayoutRecyclerView.setAdapter(coordinaterNavigationRecycleViewAdapter);
    }

    private void setUpDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mActionBarDrawerToggle.syncState();
    }

    public void setTitleAndToggleActionBarDrawerState(boolean homeEnabled, String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(homeEnabled);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemSelected(View view, int position) {

        mDrawerLayout.closeDrawers();

        int usertype = (SharedPreferencesUtility.getPrefInt(this, Constants.SHARED_PREFERENCE_USER_TYPE_ID));

        switch (usertype) {
            // -------------------------------- VOLUNTEER ------------------------------------------
            case 3:
                switch (position) {
                    case 1:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        HomeFragment homeFragment = HomeFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                homeFragment,
                                "homeFragment").addToBackStack("").commit();

                        break;

                    case 2:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ProfilesFragment profilesFragment = ProfilesFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                profilesFragment,
                                "profiles_Fragment").addToBackStack("").commit();

                        break;

                    case 3:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        EventListFragment eventListFragment = EventListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                eventListFragment,
                                "eventListFragment").addToBackStack("").commit();
                        break;

                    case 4:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        TaskFragment taskFragment = TaskFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                taskFragment,
                                "task_Fragment").addToBackStack("").commit();
                        break;

                    case 5:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        SocialMediaFragment socialMediaFragment = SocialMediaFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                socialMediaFragment,
                                "social_Media_Fragment").addToBackStack("").commit();
                        break;

                    case 6:

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Confirmation");

                        builder.setMessage("Do you want to LOG OUT ?");

                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferencesUtility.savePrefBoolean(NavigationActivity.this, Constants.LOGIN_STATUS, false);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKEVENT, 0);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKTASK, 0);
                                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                        break;

                    case 7:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abyzinfotech.com/"));
                        startActivity(browserIntent);
                        break;
                }
                break;
            // -------------------------------- SPEAKER ------------------------------------------
            case 4:
                switch (position) {

                    case 1:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        HomeFragment homeFragment = HomeFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                homeFragment,
                                "homeFragment").addToBackStack("").commit();

                        break;
                    case 2:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ProfilesFragment profilesFragment = ProfilesFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                profilesFragment,
                                "profiles_Fragment").addToBackStack("").commit();

                        break;

                    case 3:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        EventListFragment eventListFragment = EventListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                eventListFragment,
                                "eventListFragment").addToBackStack("").commit();
                        break;


                    case 4:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        SocialMediaFragment socialMediaFragment = SocialMediaFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                socialMediaFragment,
                                "social_Media_Fragment").addToBackStack("").commit();
                        break;

                    case 5:

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Confirmation");

                        builder.setMessage("Do you want to LOG OUT ?");

                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferencesUtility.savePrefBoolean(NavigationActivity.this, Constants.LOGIN_STATUS, false);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKEVENT, 0);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKTASK, 0);
                                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                        break;

                    case 6:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abyzinfotech.com/"));
                        startActivity(browserIntent);
                        break;
                }
                break;
            // -------------------------------- OBSERVER ------------------------------------------
            case 5:
                switch (position) {

                    case 1:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        HomeFragment homeFragment = HomeFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                homeFragment,
                                "homeFragment").addToBackStack("").commit();

                        break;
                    case 2:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ProfilesFragment profilesFragment = ProfilesFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                profilesFragment,
                                "profiles_Fragment").addToBackStack("").commit();

                        break;

                    case 3:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        EventListFragment eventListFragment = EventListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                eventListFragment,
                                "eventListFragment").addToBackStack("").commit();
                        break;

                    case 4:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        TaskFragment taskFragment = TaskFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                taskFragment,
                                "task_Fragment").addToBackStack("").commit();
                        break;

                    case 5:
                        /*getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ObserverFeedbackFragment observerFeedbackFragment = ObserverFeedbackFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                observerFeedbackFragment,
                                "observer_Feedback_Fragment").addToBackStack("").commit();*/


                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ObserverFeedbackFragment allotmentEventTaskLists = ObserverFeedbackFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                allotmentEventTaskLists,
                                "allotmentEventTaskLists").addToBackStack("").commit();


                        break;

                    case 6:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        SocialMediaFragment socialMediaFragment = SocialMediaFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                socialMediaFragment,
                                "social_Media_Fragment").addToBackStack("").commit();
                        break;

                    case 7:

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Confirmation");

                        builder.setMessage("Do you want to LOG OUT ?");

                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                GreenDaoManager greenDaoManager = null;
                                try {
                                    greenDaoManager = GreenDaoManager.getInstance(NavigationActivity.this);
                                    greenDaoManager.dropAllTable();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                } finally {
                                    if (greenDaoManager != null) {
                                        greenDaoManager.closeDatabase();
                                    }
                                }

                                SharedPreferencesUtility.savePrefBoolean(NavigationActivity.this, Constants.LOGIN_STATUS, false);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKEVENT, 0);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKTASK, 0);
                                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                        break;

                    case 8:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abyzinfotech.com/"));
                        startActivity(browserIntent);
                        break;
                }
                break;
            // -------------------------------- COORDINATER ------------------------------------------

            case 6:

                switch (position) {

                    case 1:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        HomeFragment homeFragment = HomeFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                homeFragment,
                                "homeFragment").addToBackStack("").commit();

                        break;
                    case 2:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ProfilesFragment profilesFragment = ProfilesFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                profilesFragment,
                                "profiles_Fragment").addToBackStack("").commit();

                        break;

                    case 3:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        EventListFragment eventListFragment = EventListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                eventListFragment,
                                "eventListFragment").addToBackStack("").commit();
                        break;

                    case 4:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        TaskFragment taskFragment = TaskFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                taskFragment,
                                "task_Fragment").addToBackStack("").commit();


                        break;

                    case 5:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        EventTaskLists eventTaskLists = EventTaskLists.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                eventTaskLists,
                                "event_TaskLists").addToBackStack("").commit();
                        break;
                    case 6:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        AllotmentEventTaskLists allotmentEventTaskLists = AllotmentEventTaskLists.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                allotmentEventTaskLists,
                                "allotmentEventTaskLists").addToBackStack("").commit();
                        break;

                    case 7:

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        SocialMediaFragment socialMediaFragment = SocialMediaFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                socialMediaFragment,
                                "social_Media_Fragment").addToBackStack("").commit();
                        break;

                    case 8:

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Confirmation");

                        builder.setMessage("Do you want to LOG OUT ?");

                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferencesUtility.savePrefBoolean(NavigationActivity.this, Constants.LOGIN_STATUS, false);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKEVENT, 0);
                                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKTASK, 0);
                                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                        break;

                    case 9:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abyzinfotech.com/"));
                        startActivity(browserIntent);
                        break;
                }
                break;

        }
    }

    private void launchFragment() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HomeFragment homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                homeFragment,
                "homeFragment").addToBackStack("").commit();


    }

    public void onEventMainThread(MessageEventBus eventBus) {

        Log.d("MessageEventBus", "MessageEventBus" + eventBus);
        switch (eventBus.message) {

            case LOGIN_WEBSERVICE:

                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case WEBSERVICE_DOWN:

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
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        if (i > 0) {
            getSupportFragmentManager().popBackStack();

        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                SharedPreferencesUtility.savePrefBoolean(NavigationActivity.this, Constants.SHARED_PREFERENCE_EVENT, false);
                SharedPreferencesUtility.savePrefInt(this, Constants.SHARED_PREFERENCE_USER_TYPE_ID, 3);
                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKEVENT, 0);
                SharedPreferencesUtility.savePrefInt(NavigationActivity.this, Constants.CHECKTASK, 0);
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
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialogUtils alertDialogUtils = new AlertDialogUtils();
        alertDialogUtils.errorAlert(this, title, message);
    }

}
