package com.abyz.infotech.warroomapp.greenDAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.sqlite.DaoMaster;
import com.abyz.infotech.warroomapp.greenDAO.sqlite.DaoSession;
import com.abyz.infotech.warroomapp.model.TABLE_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_DISTRICT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_COORDINATER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_LOCATION_AREA;
import com.abyz.infotech.warroomapp.model.TABLE_STATE;
import com.abyz.infotech.warroomapp.model.TABLE_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_USER;
import com.abyz.infotech.warroomapp.model.TABLE_USER_PROFILES;
import com.abyz.infotech.warroomapp.model.TABLE_USER_TYPE;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.abyz.infotech.warroomapp.ui.model.EventTaskAllotmentModel;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;
import com.abyz.infotech.warroomapp.ui.model.UserModel;
import com.abyz.infotech.warroomapp.ui.model.UserProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 4/4/2016.
 */
public class GreenDaoManager {

    private static volatile GreenDaoManager instance;
    private Context mContext;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private SQLiteDatabase mSQLiteDatabase;

    private GreenDaoManager(Context context) {
        this.mContext = context;
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, "warroom-database", null);
    }

    public static synchronized GreenDaoManager getInstance(Context context) {
        if (instance != null) {
            return instance;
        }
        return new GreenDaoManager(context);
    }

    private void openReadableDatabase() throws SQLiteException {
        mSQLiteDatabase = mDevOpenHelper.getReadableDatabase();
        mDaoMaster = new DaoMaster(mSQLiteDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    private void openWriteDatabase() throws SQLiteException {
        mSQLiteDatabase = mDevOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mSQLiteDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public void closeDatabase() throws SQLiteException {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
            mSQLiteDatabase = null;
        }
        if (mDevOpenHelper != null) {
            mDevOpenHelper.close();
            mDevOpenHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }

    public void dropAllTable() throws SQLiteException {
        openWriteDatabase();
        DaoMaster.dropAllTables(mSQLiteDatabase, true);
        mDevOpenHelper.onCreate(mSQLiteDatabase);
    }

    public void delete() throws SQLiteException {
        openWriteDatabase();


        mDaoSession.getTABLE_USERDao().deleteAll();
        mDaoSession.getTABLE_USER_PROFILESDao().deleteAll();
        mDaoSession.getTABLE_USER_TYPEDao().deleteAll();
        mDaoSession.getTABLE_EVENTDao().deleteAll();
        mDaoSession.getTABLE_EVENT_SPEAKERDao().deleteAll();
        mDaoSession.getTABLE_SPEAKERDao().deleteAll();
        mDaoSession.getTABLE_TASK_ALLOTMENTDao().deleteAll();
        mDaoSession.getTABLE_EVENT_TASKDao().deleteAll();
        mDaoSession.getTABLE_TASKDao().deleteAll();
        mDaoSession.getTABLE_TASK_CATEGORYDao().deleteAll();
        mDaoSession.getTABLE_CATEGORYDao().deleteAll();
        mDaoSession.getTABLE_EVENT_COORDINATERDao().deleteAll();

        // mDevOpenHelper.onCreate(mSQLiteDatabase);
    }
    public void deletep() throws SQLiteException {
        openWriteDatabase();



        mDaoSession.getTABLE_EVENTDao().deleteAll();
        mDaoSession.getTABLE_EVENT_SPEAKERDao().deleteAll();
        mDaoSession.getTABLE_SPEAKERDao().deleteAll();


        // mDevOpenHelper.onCreate(mSQLiteDatabase);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_CATEGORY
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceCatagorys(List<TABLE_CATEGORY> table_categories) {
        openWriteDatabase();
        mDaoSession.getTABLE_CATEGORYDao().insertOrReplaceInTx(table_categories);
        mDaoSession.clear();

    }

    public void insertReplaceCatagory(TABLE_CATEGORY table_category) {
        openWriteDatabase();
        mDaoSession.getTABLE_CATEGORYDao().insertOrReplace(table_category);
        mDaoSession.clear();
    }

    public void deleteCatagory(TABLE_CATEGORY table_category) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_CATEGORYDao().delete(table_category);
        mDaoSession.clear();
    }

    public List<TABLE_CATEGORY> getCatagory() {
        openReadableDatabase();
        List<TABLE_CATEGORY> table_categories = mDaoSession.getTABLE_CATEGORYDao().loadAll();
        mDaoSession.clear();
        return table_categories;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_USER_PROFILES
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

 /*   public void insertReplaceUserProfiles(List<TABLE_USER_PROFILES> table_user_profiles) {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_PROFILESDao().insertOrReplaceInTx(table_user_profiles);
        mDaoSession.clear();

    }

    public void insertReplaceUserProfile(TABLE_USER_PROFILES table_user_profile) {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_PROFILESDao().insertOrReplace(table_user_profile);
        mDaoSession.clear();
    }

    public void deleteUserProfile(TABLE_USER_PROFILES table_user_profile) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_PROFILESDao().delete(table_user_profile);
        mDaoSession.clear();
    }

    public List<TABLE_USER_PROFILES> getUserProfile() {
        openReadableDatabase();
        List<TABLE_USER_PROFILES> table_user_profiles = mDaoSession.getTABLE_USER_PROFILESDao().loadAll();
        mDaoSession.clear();
        return table_user_profiles;
    }*/

    public void insertReplaceUserProfiles(List<TABLE_USER_PROFILES> table_tasks) {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_PROFILESDao().insertOrReplaceInTx(table_tasks);
        mDaoSession.clear();

    }

    public List<TABLE_USER_PROFILES> getUserProfile() {
        openReadableDatabase();
        List<TABLE_USER_PROFILES> table_tasks = mDaoSession.getTABLE_USER_PROFILESDao().loadAll();
        mDaoSession.clear();
        return table_tasks;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_TASK
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceTasks(List<TABLE_TASK> table_tasks) {
        openWriteDatabase();
        mDaoSession.getTABLE_TASKDao().insertOrReplaceInTx(table_tasks);
        mDaoSession.clear();

    }

    public void insertReplaceTask(TABLE_TASK table_task) {
        openWriteDatabase();
        mDaoSession.getTABLE_TASKDao().insertOrReplace(table_task);
        mDaoSession.clear();
    }

    public void deleteTask(TABLE_TASK table_task) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_TASKDao().delete(table_task);
        mDaoSession.clear();
    }

    public List<TABLE_TASK> getTaskList() {
        openReadableDatabase();
        List<TABLE_TASK> table_tasks = mDaoSession.getTABLE_TASKDao().loadAll();
        mDaoSession.clear();
        return table_tasks;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_TASK_CATEGORY
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceTaskCategorys(List<TABLE_TASK_CATEGORY> table_task_categories) {
        openWriteDatabase();
        mDaoSession.getTABLE_TASK_CATEGORYDao().insertOrReplaceInTx(table_task_categories);
        mDaoSession.clear();

    }

    public void insertReplaceTaskCategory(TABLE_TASK_CATEGORY table_task_category) {
        openWriteDatabase();
        mDaoSession.getTABLE_TASK_CATEGORYDao().insertOrReplace(table_task_category);
        mDaoSession.clear();
    }

    public void deleteTaskCategory(TABLE_TASK_CATEGORY table_task_category) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_TASK_CATEGORYDao().delete(table_task_category);
        mDaoSession.clear();
    }

    public List<TABLE_TASK_CATEGORY> getTaskCategory() {
        openReadableDatabase();
        List<TABLE_TASK_CATEGORY> table_task_categories = mDaoSession.getTABLE_TASK_CATEGORYDao().loadAll();
        mDaoSession.clear();
        return table_task_categories;
    }

     /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_USER_TYPE
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceUserTypes(List<TABLE_USER_TYPE> table_user_types) {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_TYPEDao().insertOrReplaceInTx(table_user_types);
        mDaoSession.clear();

    }

    public void insertReplaceUserType(TABLE_USER_TYPE table_user_type) {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_TYPEDao().insertOrReplace(table_user_type);
        mDaoSession.clear();
    }

    public void deleteUserType(TABLE_USER_TYPE table_user_type) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_USER_TYPEDao().delete(table_user_type);
        mDaoSession.clear();
    }

    public List<TABLE_USER_TYPE> getUserType() {
        openReadableDatabase();
        List<TABLE_USER_TYPE> table_user_types = mDaoSession.getTABLE_USER_TYPEDao().loadAll();
        mDaoSession.clear();
        return table_user_types;
    }

      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_SPEAKER
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceSpeakers(List<TABLE_SPEAKER> table_speakers) {
        openWriteDatabase();
        mDaoSession.getTABLE_SPEAKERDao().insertOrReplaceInTx(table_speakers);
        mDaoSession.clear();

    }

    public void insertReplaceSpeaker(TABLE_SPEAKER table_speaker) {
        openWriteDatabase();
        mDaoSession.getTABLE_SPEAKERDao().insertOrReplace(table_speaker);
        mDaoSession.clear();
    }

    public void deleteSpeaker(TABLE_SPEAKER table_speaker) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_SPEAKERDao().delete(table_speaker);
        mDaoSession.clear();
    }

    public List<TABLE_SPEAKER> getSpeaker() {
        openReadableDatabase();
        List<TABLE_SPEAKER> table_speakers = mDaoSession.getTABLE_SPEAKERDao().loadAll();
        mDaoSession.clear();
        return table_speakers;
    }

      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table TABLE_SPEAKER
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceEventSpeakers(List<TABLE_EVENT_SPEAKER> table_event_speakers) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_SPEAKERDao().insertOrReplaceInTx(table_event_speakers);
        mDaoSession.clear();

    }

    public void insertReplaceEventSpeaker(TABLE_EVENT_SPEAKER table_event_speaker) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_SPEAKERDao().insertOrReplace(table_event_speaker);
        mDaoSession.clear();
    }

    public void deleteEventSpeaker(TABLE_EVENT_SPEAKER table_event_speaker) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_SPEAKERDao().delete(table_event_speaker);
        mDaoSession.clear();
    }

    public List<TABLE_EVENT_SPEAKER> getEventSpeaker() {
        openReadableDatabase();
        List<TABLE_EVENT_SPEAKER> table_event_speakers = mDaoSession.getTABLE_EVENT_SPEAKERDao().loadAll();
        mDaoSession.clear();
        return table_event_speakers;
    }


      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table LOCATION AREAD
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceLocationArea(List<TABLE_LOCATION_AREA> table_location_areas) {
        openWriteDatabase();
        mDaoSession.getTABLE_LOCATION_AREADao().insertOrReplaceInTx(table_location_areas);
        mDaoSession.clear();

    }

    public void deleteLocationArea(TABLE_LOCATION_AREA table_location_area) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_LOCATION_AREADao().delete(table_location_area);
        mDaoSession.clear();
    }

    public List<TABLE_LOCATION_AREA> getLocationArea() {
        openReadableDatabase();
        List<TABLE_LOCATION_AREA> table_location_areas = mDaoSession.getTABLE_LOCATION_AREADao().loadAll();
        mDaoSession.clear();
        return table_location_areas;
    }


      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table STATE
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceState(List<TABLE_STATE> table_states) {
        openWriteDatabase();
        mDaoSession.getTABLE_STATEDao().insertOrReplaceInTx(table_states);
        mDaoSession.clear();

    }

    public void deleteState(TABLE_STATE table_state) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_STATEDao().delete(table_state);
        mDaoSession.clear();
    }

    public List<TABLE_STATE> getState() {
        openReadableDatabase();
        List<TABLE_STATE> table_states = mDaoSession.getTABLE_STATEDao().loadAll();
        mDaoSession.clear();
        return table_states;
    }


      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Queries For Table DISTRICT
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceDistrict(List<TABLE_DISTRICT> table_districts) {
        openWriteDatabase();
        mDaoSession.getTABLE_DISTRICTDao().insertOrReplaceInTx(table_districts);
        mDaoSession.clear();

    }

    public void deleteState(TABLE_DISTRICT table_district) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_DISTRICTDao().delete(table_district);
        mDaoSession.clear();
    }

    public List<TABLE_DISTRICT> getDistrict() {
        openReadableDatabase();
        List<TABLE_DISTRICT> table_districts = mDaoSession.getTABLE_DISTRICTDao().loadAll();
        mDaoSession.clear();
        return table_districts;
    }

       /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Queries For Table USER
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceUsers(List<TABLE_USER> table_users) {
        openWriteDatabase();
        mDaoSession.getTABLE_USERDao().insertOrReplaceInTx(table_users);
        mDaoSession.clear();

    }

    public void insertReplaceUser(TABLE_USER table_user) {
        openWriteDatabase();
        mDaoSession.getTABLE_USERDao().insertOrReplace(table_user);
        mDaoSession.clear();
    }

    public void deleteUser(TABLE_USER table_user) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_USERDao().delete(table_user);
        mDaoSession.clear();
    }

    public List<TABLE_USER> getUser() {
        openReadableDatabase();
        List<TABLE_USER> table_users = mDaoSession.getTABLE_USERDao().loadAll();
        mDaoSession.clear();
        return table_users;
    }


       /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Queries For Table EVENT
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceEvent(List<TABLE_EVENT> table_events) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENTDao().insertOrReplaceInTx(table_events);
        mDaoSession.clear();

    }

    public void insertReplaceUser(TABLE_EVENT table_event) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENTDao().insertOrReplace(table_event);
        mDaoSession.clear();
    }

    public void deleteUser(TABLE_EVENT table_event) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENTDao().delete(table_event);
        mDaoSession.clear();
    }

    public List<TABLE_EVENT> getEvent() {
        openReadableDatabase();
        List<TABLE_EVENT> table_events = mDaoSession.getTABLE_EVENTDao().loadAll();
        mDaoSession.clear();
        return table_events;
    }



       /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Queries For Table EVENT TASK
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceEventTasks(List<TABLE_EVENT_TASK> table_event_tasks) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_TASKDao().insertOrReplaceInTx(table_event_tasks);
        mDaoSession.clear();

    }

    public void deleteEventTasks(TABLE_EVENT_TASK table_event_task) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_TASKDao().delete(table_event_task);
        mDaoSession.clear();
    }

    public List<TABLE_EVENT_TASK> getEventTask() {
        openReadableDatabase();
        List<TABLE_EVENT_TASK> table_event_tasks = mDaoSession.getTABLE_EVENT_TASKDao().loadAll();
        mDaoSession.clear();
        return table_event_tasks;
    }


     /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Queries For Table TABLE EVENT COORDINATER
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceEventCoordinater(List<TABLE_EVENT_COORDINATER> table_event_coordinaters) {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_COORDINATERDao().insertOrReplaceInTx(table_event_coordinaters);
        mDaoSession.clear();

    }

    public void deleteCoordinater(TABLE_EVENT_COORDINATER table_event_coordinater) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_EVENT_COORDINATERDao().delete(table_event_coordinater);
        mDaoSession.clear();
    }

    public List<TABLE_EVENT_COORDINATER> getEventCoordinater() {
        openReadableDatabase();
        List<TABLE_EVENT_COORDINATER> table_event_coordinaters = mDaoSession.getTABLE_EVENT_COORDINATERDao().loadAll();
        mDaoSession.clear();
        return table_event_coordinaters;
    }


     /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Queries For Table TABLE TASK ALLOTMENT
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void insertReplaceTaskAllotment(List<TABLE_TASK_ALLOTMENT> table_task_allotments) {
        openWriteDatabase();
        mDaoSession.getTABLE_TASK_ALLOTMENTDao().insertOrReplaceInTx(table_task_allotments);
        mDaoSession.clear();

    }

    public void deleteTaskAllotment(TABLE_TASK_ALLOTMENT table_task_allotment) throws Exception {
        openWriteDatabase();
        mDaoSession.getTABLE_TASK_ALLOTMENTDao().delete(table_task_allotment);
        mDaoSession.clear();
    }

    public List<TABLE_TASK_ALLOTMENT> getTaskAllotment() {
        openReadableDatabase();
        List<TABLE_TASK_ALLOTMENT> table_task_allotments = mDaoSession.getTABLE_TASK_ALLOTMENTDao().loadAll();
        mDaoSession.clear();
        return table_task_allotments;
    }

    //----------------------------------------------**************------------------------------------------------------------

    public List<EventModel> getEventModelList() throws Exception {
        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel;
        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));

        openReadableDatabase();

        String selectionString = "SELECT " +

                "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_LOCATION_ID, " +
                "TABLE__EVENT.EVENT_NAME, " +
                "TABLE__EVENT.EVENT_LOCATION, " +
                "TABLE__EVENT.EVENT_DATE, " +
                "TABLE__EVENT.EVENT_TIME, " +
                "TABLE__EVENT.EVENT_DESC, " +
                "TABLE__EVENT.EVENT_IMAGE, " +

                "TABLE__LOCATION__AREA.LOCAREA_ID, " +
                "TABLE__LOCATION__AREA.LOCAREA_DIST_ID, " +
                "TABLE__LOCATION__AREA.LOCAREA_NAME, " +
                "TABLE__LOCATION__AREA.LOCAREA_USER_ID, " +

                "TABLE__DISTRICT.DIST_ID, " +
                "TABLE__DISTRICT.DIST_ST_ID, " +
                "TABLE__DISTRICT.DIST_NAME, " +

                "TABLE__USER.USR_ID, " +
                "TABLE__USER.USR_LOCATION_ID, " +

                "TABLE__STATE.ST_ID, " +
                "TABLE__STATE.ST_NAME " +

                "FROM " +
                "TABLE__EVENT " +
                "INNER JOIN " +
                "TABLE__LOCATION__AREA " +
                "ON " +
                "TABLE__EVENT.EVENT_LOCATION_ID=TABLE__LOCATION__AREA.LOCAREA_ID " +

                "INNER JOIN " +
                "TABLE__DISTRICT " +
                "ON " +
                "TABLE__DISTRICT.DIST_ID=TABLE__LOCATION__AREA.LOCAREA_DIST_ID " +

                "INNER JOIN " +
                "TABLE__STATE " +
                "ON " +
                "TABLE__STATE.ST_ID=TABLE__DISTRICT.DIST_ST_ID " +

                "INNER JOIN " +
                "TABLE__USER " +
                "ON " +
                "TABLE__USER.USR_LOCATION_ID=TABLE__LOCATION__AREA.LOCAREA_ID " +
                "WHERE " +
                "TABLE__USER.USR_ID ='" + user + "';";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventModel = new EventModel();
                    eventModel.setEventId(cursor.getInt(cursor.getColumnIndex("EVENT_ID")));
                    eventModel.setEventName(cursor.getString(cursor.getColumnIndex("EVENT_NAME")));
                    eventModel.setEventLocation(cursor.getString(cursor.getColumnIndex("EVENT_LOCATION")));
                    eventModel.setEventDate(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));
                    eventModel.setEventTime(cursor.getLong(cursor.getColumnIndex("EVENT_TIME")));
                    eventModel.setEventDesc(cursor.getString(cursor.getColumnIndex("EVENT_DESC")));
                    eventModel.setEventImage(cursor.getString(cursor.getColumnIndex("EVENT_IMAGE")));
                    eventModel.setLaocationname(cursor.getString(cursor.getColumnIndex("LOCAREA_NAME")));
                    eventModel.setDistname(cursor.getString(cursor.getColumnIndex("DIST_NAME")));
                    eventModel.setStatename(cursor.getString(cursor.getColumnIndex("ST_NAME")));

                    Log.d("eventModel", "eventModel-----" + eventModel);
                    eventModels.add(eventModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return eventModels;
    }



    //----------------------------------------------**************------------------------------------------------------------

    public List<EventModel> getEventModelListSpekers() throws Exception {
        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel;

        openReadableDatabase();

        String selectionString = "SELECT " +

                "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_LOCATION_ID, " +
                "TABLE__EVENT.EVENT_NAME, " +
                "TABLE__EVENT.EVENT_LOCATION, " +
                "TABLE__EVENT.EVENT_DATE, " +
                "TABLE__EVENT.EVENT_TIME, " +
                "TABLE__EVENT.EVENT_DESC, " +
                "TABLE__EVENT.EVENT_IMAGE, " +

                "TABLE__LOCATION__AREA.LOCAREA_ID, " +
                "TABLE__LOCATION__AREA.LOCAREA_DIST_ID, " +
                "TABLE__LOCATION__AREA.LOCAREA_NAME, " +

                "TABLE__DISTRICT.DIST_ID, " +
                "TABLE__DISTRICT.DIST_ST_ID, " +
                "TABLE__DISTRICT.DIST_NAME, " +

                "TABLE__STATE.ST_ID, " +
                "TABLE__STATE.ST_NAME " +

                "FROM " +
                "TABLE__EVENT " +
                "INNER JOIN " +
                "TABLE__LOCATION__AREA " +
                "ON " +
                "TABLE__EVENT.EVENT_LOCATION_ID=TABLE__LOCATION__AREA.LOCAREA_ID " +

                "INNER JOIN " +
                "TABLE__DISTRICT " +
                "ON " +
                "TABLE__DISTRICT.DIST_ID=TABLE__LOCATION__AREA.LOCAREA_DIST_ID " +

                "INNER JOIN " +
                "TABLE__STATE " +
                "ON " +
                "TABLE__STATE.ST_ID=TABLE__DISTRICT.DIST_ST_ID ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventModel = new EventModel();
                    eventModel.setEventId(cursor.getInt(cursor.getColumnIndex("EVENT_ID")));
                    eventModel.setEventName(cursor.getString(cursor.getColumnIndex("EVENT_NAME")));
                    eventModel.setEventLocation(cursor.getString(cursor.getColumnIndex("EVENT_LOCATION")));
                    eventModel.setEventDate(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));
                    eventModel.setEventTime(cursor.getLong(cursor.getColumnIndex("EVENT_TIME")));
                    eventModel.setEventDesc(cursor.getString(cursor.getColumnIndex("EVENT_DESC")));
                    eventModel.setEventImage(cursor.getString(cursor.getColumnIndex("EVENT_IMAGE")));
                    eventModel.setLaocationname(cursor.getString(cursor.getColumnIndex("LOCAREA_NAME")));
                    eventModel.setDistname(cursor.getString(cursor.getColumnIndex("DIST_NAME")));
                    eventModel.setStatename(cursor.getString(cursor.getColumnIndex("ST_NAME")));

                    Log.d("eventModel", "eventModel-----" + eventModel);
                    eventModels.add(eventModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return eventModels;
    }



    //----------------------------------------------**************------------------------------------------------------------

    public List<EventModel> getEvenTaskList() throws Exception {
        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel;

        Log.d("EventModel","getEvenTaskList");
        openReadableDatabase();

        String selectionString = "SELECT " +

                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +

                "TABLE__TASK__CATEGORY.TC_ID, " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID, " +

                "TABLE__EVENT.EVENT_ID, " +

                "TABLE__EVENT__COORDINATER.EC_EVENT_ID, " +

                "TABLE__TASK.TSK_ID, " +
                "TABLE__TASK.TSK_NAME " +

                "FROM " +
                "TABLE__EVENT__TASK " +

                "INNER JOIN " +
                "TABLE__TASK__CATEGORY " +
                "ON " +
                "TABLE__EVENT__TASK.ET_TC_ID=TABLE__TASK__CATEGORY.TC_ID " +

                "INNER JOIN " +
                "TABLE__TASK " +
                "ON " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID=TABLE__TASK.TSK_ID " +

                "INNER JOIN " +
                "TABLE__EVENT " +
                "ON " +
                "TABLE__EVENT__TASK.ET_EVENT_ID=TABLE__EVENT.EVENT_ID " +

                "INNER JOIN " +
                "TABLE__EVENT__COORDINATER " +
                "ON " +
                "TABLE__EVENT.EVENT_ID=TABLE__EVENT__COORDINATER.EC_EVENT_ID ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventModel = new EventModel();
                    eventModel.setEventName(cursor.getString(cursor.getColumnIndex("ET_EVNT_NAME")));
                    eventModel.setTaskName(cursor.getString(cursor.getColumnIndex("TSK_NAME")));

                    Log.d("eventModel", "eventModel-----" + eventModel);
                    eventModels.add(eventModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();

        return eventModels;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List<EventModel> getEventDetails(int eventid) throws Exception {

        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel;

        openReadableDatabase();

        String selectionString = "SELECT " +


                "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_NAME, " +
                "TABLE__EVENT__SPEAKER.EP_SPEAKER_ID, " +
                "TABLE__EVENT__SPEAKER.EP_EVENT_ID, " +
                "TABLE__EVENT__SPEAKER.EP_TOPIC, " +
                "TABLE__EVENT__SPEAKER.EP_SPEAKER_DESC, " +
                "TABLE__SPEAKER.SPEAKER_ID, " +
                "TABLE__SPEAKER.SPEAKER_TYPE_NAME, " +
                "TABLE__SPEAKER.SPEAKER_IMAGE, " +
                "TABLE__SPEAKER.SPEAKER_NAME, " +
                "TABLE__SPEAKER.SPEAKER_MOBILE_NO " +
                "FROM " +
                "TABLE__EVENT " +
                "INNER JOIN " +
                "TABLE__EVENT__SPEAKER " +
                "ON " +
                "TABLE__EVENT__SPEAKER.EP_EVENT_ID=TABLE__EVENT.EVENT_ID " +
                "INNER JOIN " +
                "TABLE__SPEAKER " +
                "ON " +
                "TABLE__SPEAKER.SPEAKER_ID=TABLE__EVENT__SPEAKER.EP_SPEAKER_ID " +

                "WHERE " +
                "TABLE__EVENT.EVENT_ID='" + eventid + "';";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);


            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventModel = new EventModel();
                    eventModel.setEventId(cursor.getInt(cursor.getColumnIndex("EVENT_ID")));
                    eventModel.setEventName(cursor.getString(cursor.getColumnIndex("EVENT_NAME")));
                    eventModel.setEpTopic(cursor.getString(cursor.getColumnIndex("EP_TOPIC")));
                    eventModel.setEpSpeakerDesc(cursor.getString(cursor.getColumnIndex("EP_SPEAKER_DESC")));
                    eventModel.setSpeakerId(cursor.getInt(cursor.getColumnIndex("SPEAKER_ID")));
                    eventModel.setSpeakerName(cursor.getString(cursor.getColumnIndex("SPEAKER_NAME")));
                    eventModel.setSpeakerImage(cursor.getString(cursor.getColumnIndex("SPEAKER_IMAGE")));
                    eventModel.setSpeakerTypeName(cursor.getString(cursor.getColumnIndex("SPEAKER_TYPE_NAME")));
                    eventModel.setSpeakerMobileNo(cursor.getString(cursor.getColumnIndex("SPEAKER_MOBILE_NO")));


                    eventModels.add(eventModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return eventModels;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List<TaskModel> getTaskModelList() throws Exception {
        List<TaskModel> taskModels = new ArrayList<>();
        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        int usertype = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type = Integer.toString(usertype);

        Log.d("user_id", "user_id--==-->" + user_id + "  "+user_type);
        TaskModel taskModel;
        openReadableDatabase();
        String selectionString = "SELECT " +

                "TABLE__TASK__ALLOTMENT.TA_ET_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_START_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_END_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_START_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_END_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_USR_ID, " +
                "TABLE__TASK__ALLOTMENT.USR_TYPE_ID, " +

                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +

                "TABLE__TASK__CATEGORY.TC_ID, " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID, " +

                "TABLE__TASK.TSK_ID, " +
                "TABLE__TASK.TSK_NAME " +

                "FROM " +

                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__CATEGORY " +

                "ON " +
                "TABLE__EVENT__TASK.ET_TC_ID=TABLE__TASK__CATEGORY.TC_ID " +
                "INNER JOIN " +
                "TABLE__TASK " +
                "ON " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID=TABLE__TASK.TSK_ID " +
                "INNER JOIN " +
                "TABLE__TASK__ALLOTMENT " +
                "ON " +
                "TABLE__TASK__ALLOTMENT.TA_ET_ID=TABLE__EVENT__TASK.ET_ID " +

                "WHERE " +
                "TABLE__TASK__ALLOTMENT.TA_USR_ID='" + user + "' " +
                "AND " +
                "TABLE__TASK__ALLOTMENT.USR_TYPE_ID='" + user_type + "';";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();

                    taskModel.setEtEventId(cursor.getInt(cursor.getColumnIndex("ET_EVENT_ID")));
                    taskModel.setEtId(cursor.getInt(cursor.getColumnIndex("ET_ID")));
                    taskModel.setTaId(cursor.getInt(cursor.getColumnIndex("TA_ID")));
                    taskModel.setEt_evnt_name(cursor.getString(cursor.getColumnIndex("ET_EVNT_NAME")));
                    taskModel.setTskName(cursor.getString(cursor.getColumnIndex("TSK_NAME")));

                    //  taskModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));
                    //   taskModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("EVENT_TIME")));

                    Log.d("taskModel", "taskModel-----" + taskModel);
                    taskModels.add(taskModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

 /*   public List<TaskModel> getTaskDetails(int eventid) throws Exception {

        List<TaskModel> taskModels = new ArrayList<>();
        TaskModel taskModel;

        Log.d("eventid","eventid -- >"+eventid);

        openReadableDatabase();

        String selectionString = "SELECT " +


                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +

               *//* "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_IMAGE, " +*//*

                "TABLE__TASK__ALLOTMENT.TA_ET_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_START_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_END_TIME, " +

                "TABLE__EVENT__COORDINATER.EC_ID, " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID, " +
                "TABLE__EVENT__COORDINATER.EC_USER_MOBILE_NO, " +
                "TABLE__EVENT__COORDINATER.EC_USER_NAME, " +
                "TABLE__EVENT__COORDINATER.EC_DESC " +


                "FROM " +
                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__ALLOTMENT " +

                "ON " +
                "TABLE__TASK__ALLOTMENT.TA_ET_ID=TABLE__EVENT__TASK.ET_ID " +

                "INNER JOIN " +
                "TABLE__EVENT__COORDINATER " +

                "ON " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID=TABLE__EVENT__TASK.ET_EVNT_NAME " +

               *//* "INNER JOIN " +
                "TABLE__EVENT " +

                "ON " +
                "TABLE__EVENT.EVENT_ID=TABLE__EVENT__TASK.ET_EVENT_ID " +*//*

                "WHERE " +
                "TABLE__EVENT__TASK.ET_ID='" + eventid + "';";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);


            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();
                    taskModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("TA_START_TIME")));
                    taskModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("TA_END_TIME")));

                    taskModel.setEcUserMobileNo(cursor.getString(cursor.getColumnIndex("EC_USER_MOBILE_NO")));
                    taskModel.setEcUserName(cursor.getString(cursor.getColumnIndex("EC_USER_NAME")));
                    taskModel.setEcDesc(cursor.getString(cursor.getColumnIndex("EC_DESC")));
                   // taskModel.setEventImage(cursor.getString(cursor.getColumnIndex("EVENT_IMAGE")));

                    taskModels.add(taskModel);
                    Log.d("taskModels","taskModels----->"+taskModels);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }*/

    public List<TaskModel> getTaskDetails(int eventid) throws Exception {

        List<TaskModel> taskModels = new ArrayList<>();
        TaskModel taskModel;

        Log.d("eventid","eventid    "+eventid);

        openReadableDatabase();

        String selectionString = "SELECT " +


                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +
                "TABLE__EVENT__TASK.EVENT_IMAGE, " +

                "TABLE__TASK__ALLOTMENT.TA_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_ET_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_START_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_END_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_START_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_END_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_STATUS_ID, " +


                "TABLE__EVENT__COORDINATER.EC_ID, " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID, " +
                "TABLE__EVENT__COORDINATER.EC_USER_MOBILE_NO, " +
                "TABLE__EVENT__COORDINATER.EC_USER_NAME, " +
                "TABLE__EVENT__COORDINATER.EC_DESC " +


                "FROM " +
                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__ALLOTMENT " +
                "ON " +
                "TABLE__TASK__ALLOTMENT.TA_ET_ID=TABLE__EVENT__TASK.ET_ID " +

                "INNER JOIN " +
                "TABLE__EVENT__COORDINATER " +
                "ON " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID=TABLE__EVENT__TASK.ET_EVENT_ID " +

                "WHERE " +
                "TABLE__TASK__ALLOTMENT.TA_ID='" + eventid + "';";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);


            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();
                    taskModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("TA_START_TIME")));
                    taskModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("TA_END_TIME")));
                    taskModel.setTaStartDate(cursor.getLong(cursor.getColumnIndex("TA_START_DATE")));
                    taskModel.setTaEndDate(cursor.getLong(cursor.getColumnIndex("TA_END_DATE")));

                    taskModel.setEcUserMobileNo(cursor.getString(cursor.getColumnIndex("EC_USER_MOBILE_NO")));
                    taskModel.setEcUserName(cursor.getString(cursor.getColumnIndex("EC_USER_NAME")));
                    taskModel.setEcDesc(cursor.getString(cursor.getColumnIndex("EC_DESC")));
                    taskModel.setEventImage(cursor.getString(cursor.getColumnIndex("EVENT_IMAGE")));
                    taskModel.setTaStatusId(cursor.getInt(cursor.getColumnIndex("TA_STATUS_ID")));
                    taskModel.setTaEtId(cursor.getInt(cursor.getColumnIndex("TA_ET_ID")));


                    taskModels.add(taskModel);
                    Log.d("taskModels", "taskModels----->" + taskModels);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }


    //---------------------------------------------------------------------------------------------------------------------------------

    public List<EventTaskAllotmentModel> getEvenTaskAllotmentList() throws Exception {

        List<EventTaskAllotmentModel> eventTaskAllotmentModels = new ArrayList<>();
        EventTaskAllotmentModel eventTaskAllotmentModel;

        openReadableDatabase();

        String selectionString = "SELECT " +


                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +

                "TABLE__TASK__ALLOTMENT.TA_ET_ID, " +
                "TABLE__TASK__ALLOTMENT.TA_START_TIME, " +
                "TABLE__TASK__ALLOTMENT.TA_END_TIME, " +
                "TABLE__TASK__ALLOTMENT.USR_NAME, " +
                "TABLE__TASK__ALLOTMENT.USR_MOBILE, " +
                "TABLE__TASK__ALLOTMENT.USR_IMAGE, " +
                "TABLE__TASK__ALLOTMENT.TA_START_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_END_DATE, " +
                "TABLE__TASK__ALLOTMENT.TA_STATUS_ID, " +

                "TABLE__TASK__CATEGORY.TC_ID, " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID, " +

                "TABLE__TASK.TSK_ID, " +
                "TABLE__TASK.TSK_NAME " +

                "FROM " +
                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__ALLOTMENT " +

                "ON " +
                "TABLE__TASK__ALLOTMENT.TA_ET_ID=TABLE__EVENT__TASK.ET_ID " +

                "INNER JOIN " +
                "TABLE__TASK__CATEGORY " +

                "ON " +
                "TABLE__EVENT__TASK.ET_TC_ID=TABLE__TASK__CATEGORY.TC_ID " +

                "INNER JOIN " +
                "TABLE__TASK " +

                "ON " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID=TABLE__TASK.TSK_ID ";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);


            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventTaskAllotmentModel = new EventTaskAllotmentModel();
                    eventTaskAllotmentModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("TA_START_TIME")));
                    eventTaskAllotmentModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("TA_END_TIME")));
                    eventTaskAllotmentModel.setTaStartDate(cursor.getLong(cursor.getColumnIndex("TA_START_DATE")));
                    eventTaskAllotmentModel.setTaEndDate(cursor.getLong(cursor.getColumnIndex("TA_END_DATE")));
                    eventTaskAllotmentModel.setUsrName(cursor.getString(cursor.getColumnIndex("USR_NAME")));
                    eventTaskAllotmentModel.setUsrMobile(cursor.getString(cursor.getColumnIndex("USR_MOBILE")));
                    eventTaskAllotmentModel.setUsrImage(cursor.getString(cursor.getColumnIndex("USR_IMAGE")));
                    eventTaskAllotmentModel.setTskName(cursor.getString(cursor.getColumnIndex("TSK_NAME")));
                    eventTaskAllotmentModel.setEventName(cursor.getString(cursor.getColumnIndex("ET_EVNT_NAME")));
                    eventTaskAllotmentModel.setTaStatusId(cursor.getInt(cursor.getColumnIndex("TA_STATUS_ID")));


                    eventTaskAllotmentModels.add(eventTaskAllotmentModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return eventTaskAllotmentModels;
    }

    //======================================================================================================
    public List<UserProfileModel> getUserTypeModelList() throws Exception {
        List<UserProfileModel> userProfileModels = new ArrayList<>();
        UserProfileModel userProfileModel;

        openReadableDatabase();

        String selectionString = "SELECT * " + " FROM " + "TABLE__USER__TYPE ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    userProfileModel = new UserProfileModel();
                    userProfileModel.setUsrTypeId(cursor.getInt(cursor.getColumnIndex("USR_TYPE_ID")));
                    userProfileModel.setUsrTypeName(cursor.getString(cursor.getColumnIndex("USR_TYPE_NAME")));

                    userProfileModels.add(userProfileModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return userProfileModels;
    }


    //======================================================================================================
    public List<UserModel> getUserMode() throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        UserModel userModel;

        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        openReadableDatabase();

        String selectionString = "SELECT * " + " FROM " + "TABLE__USER " +

                                  "WHERE " +

                                 "TABLE__USER.USR_ID='" + user + "';";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    userModel = new UserModel();
                    userModel.setUsrId(cursor.getInt(cursor.getColumnIndex("USR_ID")));
                    userModel.setUsrName(cursor.getString(cursor.getColumnIndex("USR_NAME")));
                    userModel.setUsrAddress(cursor.getString(cursor.getColumnIndex("USR_ADDRESS")));
                    userModel.setUsrImage(cursor.getString(cursor.getColumnIndex("USR_IMAGE")));
                    userModel.setUsrMobile(cursor.getString(cursor.getColumnIndex("USR_MOBILE")));

                    userModels.add(userModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return userModels;
    }
//==============================================================================================================

    public void upDateMobileNo(String mobno) {

        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        try {
            final String query = String.format("" +
                    "UPDATE " +
                    "TABLE__USER " +
                    "SET " +
                    "USR_MOBILE=%s " +
                    "WHERE " +
                    "TABLE__USER.USR_ID=%s;" +
                    "", mobno, user_id);
            openWriteDatabase();
            mSQLiteDatabase.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
    }

   // =============================================================================================================//

    public void upDateTaskStatus(int etid) {

        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));
        int usertype = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        int status = 5;
        try {
            final String query = String.format("" +
                    "UPDATE " +
                    "TABLE__TASK__ALLOTMENT " +
                    "SET " +
                    "TA_STATUS_ID=%d " +
                    "WHERE " +
                    "TABLE__TASK__ALLOTMENT.TA_USR_ID=%d;" +
                    "AND " +
                    "TABLE__TASK__ALLOTMENT.TA_ET_ID=%d;" +
                    "", status, user, etid);
            openWriteDatabase();
            mSQLiteDatabase.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List<TaskModel> getTasksList() throws Exception {
        List<TaskModel> taskModels = new ArrayList<>();
        TaskModel taskModel;
        openReadableDatabase();
        String selectionString = "SELECT " +


                "TABLE__TASK.TSK_ID, " +
                "TABLE__TASK.TSK_NAME, " +

                "TABLE__TASK__CATEGORY.TC_TSK_ID, " +
                "TABLE__TASK__CATEGORY.TC_ID " +

                "FROM " +
                "TABLE__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__CATEGORY " +

                "ON " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID=TABLE__TASK.TSK_ID ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();

                    taskModel.setTaskid(cursor.getInt(cursor.getColumnIndex("TC_ID")));
                    taskModel.setTskName(cursor.getString(cursor.getColumnIndex("TSK_NAME")));

                    //  taskModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));
                    //   taskModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("EVENT_TIME")));

                    Log.d("taskModel", "taskModel-----" + taskModel);
                    taskModels.add(taskModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List<EventModel> getEventsList() throws Exception {
        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel;
        openReadableDatabase();
        Log.d("taskModel", "taskModel-----" );
        String selectionString = "SELECT " +

                "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_NAME, " +

                "TABLE__EVENT__COORDINATER.EC_ID, " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID " +


                "FROM " +
                "TABLE__EVENT " +
                "INNER JOIN " +
                "TABLE__EVENT__COORDINATER " +

                "ON " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID=TABLE__EVENT.EVENT_ID ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    eventModel = new EventModel();

                    eventModel.setEventId(cursor.getInt(cursor.getColumnIndex("EVENT_ID")));
                    eventModel.setEventName(cursor.getString(cursor.getColumnIndex("EVENT_NAME")));


                    Log.d("taskModel", "taskModel-----" + eventModel);
                    eventModels.add(eventModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return eventModels;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List<TaskModel> getEventTaskAllotmentModelList() throws Exception {
        List<TaskModel> taskModels = new ArrayList<>();
        int user = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_ID));
        String user_id = Integer.toString(user);
        int usertype = (SharedPreferencesUtility.getPrefInt(mContext, Constants.SHARED_PREFERENCE_USER_TYPE_ID));
        String user_type = Integer.toString(usertype);

        Log.d("user_id", "user_id--==-->" + user_id);
        TaskModel taskModel;
        openReadableDatabase();
        String selectionString = "SELECT " +


                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVNT_NAME, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +
                "TABLE__EVENT__TASK.ET_TC_ID, " +

                "TABLE__EVENT.EVENT_ID, " +

                "TABLE__EVENT__COORDINATER.EC_EVENT_ID, " +

                "TABLE__TASK__CATEGORY.TC_ID, " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID, " +

                "TABLE__TASK.TSK_ID, " +
                "TABLE__TASK.TSK_NAME " +

                "FROM " +

                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__TASK__CATEGORY " +

                "ON " +
                "TABLE__EVENT__TASK.ET_TC_ID=TABLE__TASK__CATEGORY.TC_ID " +

                "INNER JOIN " +
                "TABLE__TASK " +
                "ON " +
                "TABLE__TASK__CATEGORY.TC_TSK_ID=TABLE__TASK.TSK_ID " +

                "INNER JOIN " +
                "TABLE__EVENT " +
                "ON " +
                "TABLE__EVENT__TASK.ET_EVENT_ID=TABLE__EVENT.EVENT_ID " +

                "INNER JOIN " +
                "TABLE__EVENT__COORDINATER " +
                "ON " +
                "TABLE__EVENT__COORDINATER.EC_EVENT_ID=TABLE__EVENT.EVENT_ID ";


        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);

            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();

                    taskModel.setEtEventId(cursor.getInt(cursor.getColumnIndex("ET_EVENT_ID")));
                    taskModel.setEtId(cursor.getInt(cursor.getColumnIndex("ET_ID")));
                    taskModel.setEt_evnt_name(cursor.getString(cursor.getColumnIndex("ET_EVNT_NAME")));
                    taskModel.setTskName(cursor.getString(cursor.getColumnIndex("TSK_NAME")));

                    //  taskModel.setTaStartTime(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));
                    //   taskModel.setTaEndTime(cursor.getLong(cursor.getColumnIndex("EVENT_TIME")));

                    Log.d("taskModel", "taskModel-----" + taskModel);
                    taskModels.add(taskModel);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }


    //================================================================================

    public List<TaskModel> getEventDate(int eventid) throws Exception {

        List<TaskModel> taskModels = new ArrayList<>();
        TaskModel taskModel;

        Log.d("eventid","eventid    "+eventid);

        openReadableDatabase();

        String selectionString = "SELECT " +


                "TABLE__EVENT__TASK.ET_ID, " +
                "TABLE__EVENT__TASK.ET_EVENT_ID, " +


                "TABLE__EVENT.EVENT_ID, " +
                "TABLE__EVENT.EVENT_DATE " +


                "FROM " +
                "TABLE__EVENT__TASK " +
                "INNER JOIN " +
                "TABLE__EVENT " +
                "ON " +
                "TABLE__EVENT.EVENT_ID=TABLE__EVENT__TASK.ET_EVENT_ID " +

                "WHERE " +
                "TABLE__EVENT__TASK.ET_ID='" + eventid + "';";

        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(selectionString, null);


            while (cursor != null && cursor.moveToNext()) {

                try {
                    taskModel = new TaskModel();
                    taskModel.setTaStartDate(cursor.getLong(cursor.getColumnIndex("EVENT_DATE")));


                    taskModels.add(taskModel);
                    Log.d("taskModels", "taskModels----->" + taskModels);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                closeDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDatabase();
        return taskModels;
    }

    //================================================================================
}
