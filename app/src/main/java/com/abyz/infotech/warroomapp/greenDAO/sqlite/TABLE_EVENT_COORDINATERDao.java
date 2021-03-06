package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_EVENT_COORDINATER;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__EVENT__COORDINATER.
*/
public class TABLE_EVENT_COORDINATERDao extends AbstractDao<TABLE_EVENT_COORDINATER, Long> {

    public static final String TABLENAME = "TABLE__EVENT__COORDINATER";

    /**
     * Properties of entity TABLE_EVENT_COORDINATER.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_ID");
        public final static Property EcId = new Property(1, Integer.class, "ecId", false, "EC_ID");
        public final static Property EcUserId = new Property(2, Integer.class, "ecUserId", false, "EC_USER_ID");
        public final static Property EcEventId = new Property(3, Integer.class, "ecEventId", false, "EC_EVENT_ID");
        public final static Property EcEventName = new Property(4, String.class, "ecEventName", false, "EC_EVENT_NAME");
        public final static Property EcTopic = new Property(5, String.class, "ecTopic", false, "EC_TOPIC");
        public final static Property EcDesc = new Property(6, String.class, "ecDesc", false, "EC_DESC");
        public final static Property EcUserMobileNo = new Property(7, String.class, "ecUserMobileNo", false, "EC_USER_MOBILE_NO");
        public final static Property EcUserName = new Property(8, String.class, "ecUserName", false, "EC_USER_NAME");
        public final static Property EcStatusId = new Property(9, Integer.class, "ecStatusId", false, "EC_STATUS_ID");
        public final static Property TimeStamp = new Property(10, Long.class, "timeStamp", false, "TIME_STAMP");
    };


    public TABLE_EVENT_COORDINATERDao(DaoConfig config) {
        super(config);
    }
    
    public TABLE_EVENT_COORDINATERDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__EVENT__COORDINATER' (" + //
                "'_ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "'EC_ID' INTEGER UNIQUE ," + // 1: ecId
                "'EC_USER_ID' INTEGER ," + // 2: ecUserId
                "'EC_EVENT_ID' INTEGER," + // 3: ecEventId
                "'EC_EVENT_NAME' TEXT," + // 4: ecEventName
                "'EC_TOPIC' TEXT," + // 5: ecTopic
                "'EC_DESC' TEXT," + // 6: ecDesc
                "'EC_USER_MOBILE_NO' TEXT," + // 7: ecUserMobileNo
                "'EC_USER_NAME' TEXT," + // 8: ecUserName
                "'EC_STATUS_ID' INTEGER," + // 9: ecStatusId
                "'TIME_STAMP' INTEGER);"); // 10: timeStamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__EVENT__COORDINATER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_EVENT_COORDINATER entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        Integer ecId = entity.getEcId();
        if (ecId != null) {
            stmt.bindLong(2, ecId);
        }
 
        Integer ecUserId = entity.getEcUserId();
        if (ecUserId != null) {
            stmt.bindLong(3, ecUserId);
        }
 
        Integer ecEventId = entity.getEcEventId();
        if (ecEventId != null) {
            stmt.bindLong(4, ecEventId);
        }
 
        String ecEventName = entity.getEcEventName();
        if (ecEventName != null) {
            stmt.bindString(5, ecEventName);
        }
 
        String ecTopic = entity.getEcTopic();
        if (ecTopic != null) {
            stmt.bindString(6, ecTopic);
        }
 
        String ecDesc = entity.getEcDesc();
        if (ecDesc != null) {
            stmt.bindString(7, ecDesc);
        }
 
        String ecUserMobileNo = entity.getEcUserMobileNo();
        if (ecUserMobileNo != null) {
            stmt.bindString(8, ecUserMobileNo);
        }
 
        String ecUserName = entity.getEcUserName();
        if (ecUserName != null) {
            stmt.bindString(9, ecUserName);
        }
 
        Integer ecStatusId = entity.getEcStatusId();
        if (ecStatusId != null) {
            stmt.bindLong(10, ecStatusId);
        }
 
        Long timeStamp = entity.getTimeStamp();
        if (timeStamp != null) {
            stmt.bindLong(11, timeStamp);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TABLE_EVENT_COORDINATER readEntity(Cursor cursor, int offset) {
        TABLE_EVENT_COORDINATER entity = new TABLE_EVENT_COORDINATER( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // ecId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // ecUserId
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // ecEventId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ecEventName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ecTopic
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ecDesc
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // ecUserMobileNo
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // ecUserName
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // ecStatusId
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10) // timeStamp
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_EVENT_COORDINATER entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEcId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setEcUserId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setEcEventId(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setEcEventName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEcTopic(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEcDesc(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEcUserMobileNo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setEcUserName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEcStatusId(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setTimeStamp(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_EVENT_COORDINATER entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_EVENT_COORDINATER entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
