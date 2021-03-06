package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.abyz.infotech.warroomapp.model.TABLE_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_DISTRICT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_COORDINATER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_EVENT_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_LOCATION_AREA;
import com.abyz.infotech.warroomapp.model.TABLE_SPEAKER;
import com.abyz.infotech.warroomapp.model.TABLE_STATE;
import com.abyz.infotech.warroomapp.model.TABLE_STATUS;
import com.abyz.infotech.warroomapp.model.TABLE_TASK;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;
import com.abyz.infotech.warroomapp.model.TABLE_TASK_CATEGORY;
import com.abyz.infotech.warroomapp.model.TABLE_USER;
import com.abyz.infotech.warroomapp.model.TABLE_USER_PROFILES;
import com.abyz.infotech.warroomapp.model.TABLE_USER_TYPE;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tABLE_USERDaoConfig;
    private final DaoConfig tABLE_USER_PROFILESDaoConfig;
    private final DaoConfig tABLE_USER_TYPEDaoConfig;
    private final DaoConfig tABLE_EVENTDaoConfig;
    private final DaoConfig tABLE_EVENT_SPEAKERDaoConfig;
    private final DaoConfig tABLE_SPEAKERDaoConfig;
    private final DaoConfig tABLE_TASK_ALLOTMENTDaoConfig;
    private final DaoConfig tABLE_EVENT_TASKDaoConfig;
    private final DaoConfig tABLE_TASKDaoConfig;
    private final DaoConfig tABLE_TASK_CATEGORYDaoConfig;
    private final DaoConfig tABLE_CATEGORYDaoConfig;
    private final DaoConfig tABLE_EVENT_COORDINATERDaoConfig;
    private final DaoConfig tABLE_STATUSDaoConfig;
    private final DaoConfig tABLE_LOCATION_AREADaoConfig;
    private final DaoConfig tABLE_DISTRICTDaoConfig;
    private final DaoConfig tABLE_STATEDaoConfig;

    private final TABLE_USERDao tABLE_USERDao;
    private final TABLE_USER_PROFILESDao tABLE_USER_PROFILESDao;
    private final TABLE_USER_TYPEDao tABLE_USER_TYPEDao;
    private final TABLE_EVENTDao tABLE_EVENTDao;
    private final TABLE_EVENT_SPEAKERDao tABLE_EVENT_SPEAKERDao;
    private final TABLE_SPEAKERDao tABLE_SPEAKERDao;
    private final TABLE_TASK_ALLOTMENTDao tABLE_TASK_ALLOTMENTDao;
    private final TABLE_EVENT_TASKDao tABLE_EVENT_TASKDao;
    private final TABLE_TASKDao tABLE_TASKDao;
    private final TABLE_TASK_CATEGORYDao tABLE_TASK_CATEGORYDao;
    private final TABLE_CATEGORYDao tABLE_CATEGORYDao;
    private final TABLE_EVENT_COORDINATERDao tABLE_EVENT_COORDINATERDao;
    private final TABLE_STATUSDao tABLE_STATUSDao;
    private final TABLE_LOCATION_AREADao tABLE_LOCATION_AREADao;
    private final TABLE_DISTRICTDao tABLE_DISTRICTDao;
    private final TABLE_STATEDao tABLE_STATEDao;


    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tABLE_USERDaoConfig = daoConfigMap.get(TABLE_USERDao.class).clone();
        tABLE_USERDaoConfig.initIdentityScope(type);

        tABLE_USER_PROFILESDaoConfig = daoConfigMap.get(TABLE_USER_PROFILESDao.class).clone();
        tABLE_USER_PROFILESDaoConfig.initIdentityScope(type);

        tABLE_USER_TYPEDaoConfig = daoConfigMap.get(TABLE_USER_TYPEDao.class).clone();
        tABLE_USER_TYPEDaoConfig.initIdentityScope(type);

        tABLE_EVENTDaoConfig = daoConfigMap.get(TABLE_EVENTDao.class).clone();
        tABLE_EVENTDaoConfig.initIdentityScope(type);

        tABLE_EVENT_SPEAKERDaoConfig = daoConfigMap.get(TABLE_EVENT_SPEAKERDao.class).clone();
        tABLE_EVENT_SPEAKERDaoConfig.initIdentityScope(type);

        tABLE_SPEAKERDaoConfig = daoConfigMap.get(TABLE_SPEAKERDao.class).clone();
        tABLE_SPEAKERDaoConfig.initIdentityScope(type);

        tABLE_TASK_ALLOTMENTDaoConfig = daoConfigMap.get(TABLE_TASK_ALLOTMENTDao.class).clone();
        tABLE_TASK_ALLOTMENTDaoConfig.initIdentityScope(type);

        tABLE_EVENT_TASKDaoConfig = daoConfigMap.get(TABLE_EVENT_TASKDao.class).clone();
        tABLE_EVENT_TASKDaoConfig.initIdentityScope(type);

        tABLE_TASKDaoConfig = daoConfigMap.get(TABLE_TASKDao.class).clone();
        tABLE_TASKDaoConfig.initIdentityScope(type);

        tABLE_TASK_CATEGORYDaoConfig = daoConfigMap.get(TABLE_TASK_CATEGORYDao.class).clone();
        tABLE_TASK_CATEGORYDaoConfig.initIdentityScope(type);

        tABLE_CATEGORYDaoConfig = daoConfigMap.get(TABLE_CATEGORYDao.class).clone();
        tABLE_CATEGORYDaoConfig.initIdentityScope(type);

        tABLE_EVENT_COORDINATERDaoConfig = daoConfigMap.get(TABLE_EVENT_COORDINATERDao.class).clone();
        tABLE_EVENT_COORDINATERDaoConfig.initIdentityScope(type);

        tABLE_STATUSDaoConfig = daoConfigMap.get(TABLE_STATUSDao.class).clone();
        tABLE_STATUSDaoConfig.initIdentityScope(type);

        tABLE_LOCATION_AREADaoConfig = daoConfigMap.get(TABLE_LOCATION_AREADao.class).clone();
        tABLE_LOCATION_AREADaoConfig.initIdentityScope(type);

        tABLE_DISTRICTDaoConfig = daoConfigMap.get(TABLE_DISTRICTDao.class).clone();
        tABLE_DISTRICTDaoConfig.initIdentityScope(type);

        tABLE_STATEDaoConfig = daoConfigMap.get(TABLE_STATEDao.class).clone();
        tABLE_STATEDaoConfig.initIdentityScope(type);

        tABLE_USERDao = new TABLE_USERDao(tABLE_USERDaoConfig, this);
        tABLE_USER_PROFILESDao = new TABLE_USER_PROFILESDao(tABLE_USER_PROFILESDaoConfig, this);
        tABLE_USER_TYPEDao = new TABLE_USER_TYPEDao(tABLE_USER_TYPEDaoConfig, this);
        tABLE_EVENTDao = new TABLE_EVENTDao(tABLE_EVENTDaoConfig, this);
        tABLE_EVENT_SPEAKERDao = new TABLE_EVENT_SPEAKERDao(tABLE_EVENT_SPEAKERDaoConfig, this);
        tABLE_SPEAKERDao = new TABLE_SPEAKERDao(tABLE_SPEAKERDaoConfig, this);
        tABLE_TASK_ALLOTMENTDao = new TABLE_TASK_ALLOTMENTDao(tABLE_TASK_ALLOTMENTDaoConfig, this);
        tABLE_EVENT_TASKDao = new TABLE_EVENT_TASKDao(tABLE_EVENT_TASKDaoConfig, this);
        tABLE_TASKDao = new TABLE_TASKDao(tABLE_TASKDaoConfig, this);
        tABLE_TASK_CATEGORYDao = new TABLE_TASK_CATEGORYDao(tABLE_TASK_CATEGORYDaoConfig, this);
        tABLE_CATEGORYDao = new TABLE_CATEGORYDao(tABLE_CATEGORYDaoConfig, this);
        tABLE_EVENT_COORDINATERDao = new TABLE_EVENT_COORDINATERDao(tABLE_EVENT_COORDINATERDaoConfig, this);
        tABLE_STATUSDao = new TABLE_STATUSDao(tABLE_STATUSDaoConfig, this);
        tABLE_LOCATION_AREADao = new TABLE_LOCATION_AREADao(tABLE_LOCATION_AREADaoConfig, this);
        tABLE_DISTRICTDao = new TABLE_DISTRICTDao(tABLE_DISTRICTDaoConfig, this);
        tABLE_STATEDao = new TABLE_STATEDao(tABLE_STATEDaoConfig, this);


        registerDao(TABLE_USER.class, tABLE_USERDao);
        registerDao(TABLE_USER_PROFILES.class, tABLE_USER_PROFILESDao);
        registerDao(TABLE_USER_TYPE.class, tABLE_USER_TYPEDao);
        registerDao(TABLE_EVENT.class, tABLE_EVENTDao);
        registerDao(TABLE_EVENT_SPEAKER.class, tABLE_EVENT_SPEAKERDao);
        registerDao(TABLE_SPEAKER.class, tABLE_SPEAKERDao);
        registerDao(TABLE_TASK_ALLOTMENT.class, tABLE_TASK_ALLOTMENTDao);
        registerDao(TABLE_EVENT_TASK.class, tABLE_EVENT_TASKDao);
        registerDao(TABLE_TASK.class, tABLE_TASKDao);
        registerDao(TABLE_TASK_CATEGORY.class, tABLE_TASK_CATEGORYDao);
        registerDao(TABLE_CATEGORY.class, tABLE_CATEGORYDao);
        registerDao(TABLE_EVENT_COORDINATER.class, tABLE_EVENT_COORDINATERDao);
        registerDao(TABLE_STATUS.class, tABLE_STATUSDao);
        registerDao(TABLE_LOCATION_AREA.class, tABLE_LOCATION_AREADao);
        registerDao(TABLE_DISTRICT.class, tABLE_DISTRICTDao);
        registerDao(TABLE_STATE.class, tABLE_STATEDao);
    }
    
    public void clear() {
        tABLE_USERDaoConfig.getIdentityScope().clear();
        tABLE_USER_PROFILESDaoConfig.getIdentityScope().clear();
        tABLE_USER_TYPEDaoConfig.getIdentityScope().clear();
        tABLE_EVENTDaoConfig.getIdentityScope().clear();
        tABLE_EVENT_SPEAKERDaoConfig.getIdentityScope().clear();
        tABLE_SPEAKERDaoConfig.getIdentityScope().clear();
        tABLE_TASK_ALLOTMENTDaoConfig.getIdentityScope().clear();
        tABLE_EVENT_TASKDaoConfig.getIdentityScope().clear();
        tABLE_TASKDaoConfig.getIdentityScope().clear();
        tABLE_TASK_CATEGORYDaoConfig.getIdentityScope().clear();
        tABLE_CATEGORYDaoConfig.getIdentityScope().clear();
        tABLE_EVENT_COORDINATERDaoConfig.getIdentityScope().clear();
        tABLE_STATUSDaoConfig.getIdentityScope().clear();
        tABLE_LOCATION_AREADaoConfig.getIdentityScope().clear();
        tABLE_DISTRICTDaoConfig.getIdentityScope().clear();
        tABLE_STATEDaoConfig.getIdentityScope().clear();
    }

    public TABLE_USERDao getTABLE_USERDao() {
        return tABLE_USERDao;
    }

    public TABLE_USER_PROFILESDao getTABLE_USER_PROFILESDao() {
        return tABLE_USER_PROFILESDao;
    }

    public TABLE_USER_TYPEDao getTABLE_USER_TYPEDao() {
        return tABLE_USER_TYPEDao;
    }

    public TABLE_EVENTDao getTABLE_EVENTDao() {
        return tABLE_EVENTDao;
    }

    public TABLE_EVENT_SPEAKERDao getTABLE_EVENT_SPEAKERDao() {
        return tABLE_EVENT_SPEAKERDao;
    }

    public TABLE_SPEAKERDao getTABLE_SPEAKERDao() {
        return tABLE_SPEAKERDao;
    }

    public TABLE_TASK_ALLOTMENTDao getTABLE_TASK_ALLOTMENTDao() {
        return tABLE_TASK_ALLOTMENTDao;
    }

    public TABLE_EVENT_TASKDao getTABLE_EVENT_TASKDao() {
        return tABLE_EVENT_TASKDao;
    }

    public TABLE_TASKDao getTABLE_TASKDao() {
        return tABLE_TASKDao;
    }

    public TABLE_TASK_CATEGORYDao getTABLE_TASK_CATEGORYDao() {
        return tABLE_TASK_CATEGORYDao;
    }

    public TABLE_CATEGORYDao getTABLE_CATEGORYDao() {
        return tABLE_CATEGORYDao;
    }

    public TABLE_EVENT_COORDINATERDao getTABLE_EVENT_COORDINATERDao() {
        return tABLE_EVENT_COORDINATERDao;
    }

    public TABLE_STATUSDao getTABLE_STATUSDao() {
        return tABLE_STATUSDao;
    }
    public TABLE_LOCATION_AREADao getTABLE_LOCATION_AREADao() {
        return tABLE_LOCATION_AREADao;
    }

    public TABLE_DISTRICTDao getTABLE_DISTRICTDao() {
        return tABLE_DISTRICTDao;
    }

    public TABLE_STATEDao getTABLE_STATEDao() {
        return tABLE_STATEDao;
    }


}
