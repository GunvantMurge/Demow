package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_TASK_ALLOTMENT;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__TASK__ALLOTMENT.
*/
public class TABLE_TASK_ALLOTMENTDao extends AbstractDao<TABLE_TASK_ALLOTMENT, Long> {

    public static final String TABLENAME = "TABLE__TASK__ALLOTMENT";

    /**
     * Properties of entity TABLE_TASK_ALLOTMENT.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_ID");
        public final static Property TaId = new Property(1, Integer.class, "taId", false, "TA_ID");
        public final static Property TaEtId = new Property(2, Integer.class, "taEtId", false, "TA_ET_ID");
        public final static Property TaUsrId = new Property(3, Integer.class, "taUsrId", false, "TA_USR_ID");
        public final static Property TaStartTime = new Property(4, Long.class, "taStartTime", false, "TA_START_TIME");
        public final static Property TaEndTime = new Property(5, Long.class, "taEndTime", false, "TA_END_TIME");
        public final static Property TaConfirm = new Property(6, String.class, "taConfirm", false, "TA_CONFIRM");
        public final static Property TaStaffId = new Property(7, Integer.class, "taStaffId", false, "TA_STAFF_ID");
        public final static Property TaStaffName = new Property(8, String.class, "taStaffName", false, "TA_STAFF_NAME");
        public final static Property TaStatusId = new Property(9, Integer.class, "taStatusId", false, "TA_STATUS_ID");
        public final static Property TimeStamp = new Property(10, Long.class, "timeStamp", false, "TIME_STAMP");
        public final static Property TaStartDate = new Property(11, Long.class, "taStartDate", false, "TA_START_DATE");
        public final static Property TaEndDate = new Property(12, Long.class, "taEndDate", false, "TA_END_DATE");
        public final static Property UsrName = new Property(13, String.class, "usrName", false, "USR_NAME");
        public final static Property UsrMobile = new Property(14, String.class, "usrMobile", false, "USR_MOBILE");
        public final static Property UsrImage = new Property(15, String.class, "usrImage", false, "USR_IMAGE");
        public final static Property UsrTypeId = new Property(16, Integer.class, "usrTypeId", false, "USR_TYPE_ID");
    };


    public TABLE_TASK_ALLOTMENTDao(DaoConfig config) {
        super(config);
    }

    public TABLE_TASK_ALLOTMENTDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__TASK__ALLOTMENT' (" + //
                "'_ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "'TA_ID' INTEGER UNIQUE ," + // 1: taId
                "'TA_ET_ID' INTEGER ," + // 2: taEtId
                "'TA_USR_ID' INTEGER ," + // 3: taUsrId
                "'TA_START_TIME' INTEGER," + // 4: taStartTime
                "'TA_END_TIME' INTEGER," + // 5: taEndTime
                "'TA_CONFIRM' TEXT," + // 6: taConfirm
                "'TA_STAFF_ID' INTEGER," + // 7: taStaffId
                "'TA_STAFF_NAME' TEXT," + // 8: taStaffName
                "'TA_STATUS_ID' INTEGER," + // 9: taStatusId
                "'TIME_STAMP' INTEGER," + // 10: timeStamp
                "'TA_START_DATE' INTEGER," + // 11: taStartDate
                "'TA_END_DATE' INTEGER," + // 12: taEndDate
                "'USR_NAME' TEXT," + // 13: usrName
                "'USR_MOBILE' TEXT," + // 14: usrMobile
                "'USR_IMAGE' TEXT," + // 15: usrImage
                "'USR_TYPE_ID' INTEGER);"); // 15: usrImage
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__TASK__ALLOTMENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_TASK_ALLOTMENT entity) {
        stmt.clearBindings();

        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }

        Integer taId = entity.getTaId();
        if (taId != null) {
            stmt.bindLong(2, taId);
        }

        Integer taEtId = entity.getTaEtId();
        if (taEtId != null) {
            stmt.bindLong(3, taEtId);
        }

        Integer taUsrId = entity.getTaUsrId();
        if (taUsrId != null) {
            stmt.bindLong(4, taUsrId);
        }

        Long taStartTime = entity.getTaStartTime();
        if (taStartTime != null) {
            stmt.bindLong(5, taStartTime);
        }

        Long taEndTime = entity.getTaEndTime();
        if (taEndTime != null) {
            stmt.bindLong(6, taEndTime);
        }

        String taConfirm = entity.getTaConfirm();
        if (taConfirm != null) {
            stmt.bindString(7, taConfirm);
        }

        Integer taStaffId = entity.getTaStaffId();
        if (taStaffId != null) {
            stmt.bindLong(8, taStaffId);
        }

        String taStaffName = entity.getTaStaffName();
        if (taStaffName != null) {
            stmt.bindString(9, taStaffName);
        }

        Integer taStatusId = entity.getTaStatusId();
        if (taStatusId != null) {
            stmt.bindLong(10, taStatusId);
        }

        Long timeStamp = entity.getTimeStamp();
        if (timeStamp != null) {
            stmt.bindLong(11, timeStamp);
        }

        Long taStartDate = entity.getTaStartDate();
        if (taStartDate != null) {
            stmt.bindLong(12, taStartDate);
        }

        Long taEndDate = entity.getTaEndDate();
        if (taEndDate != null) {
            stmt.bindLong(13, taEndDate);
        }

        String usrName = entity.getUsrName();
        if (usrName != null) {
            stmt.bindString(14, usrName);
        }

        String usrMobile = entity.getUsrMobile();
        if (usrMobile != null) {
            stmt.bindString(15, usrMobile);
        }

        String usrImage = entity.getUsrImage();
        if (usrImage != null) {
            stmt.bindString(16, usrImage);
        }

        Integer usrTypeId = entity.getTaUsertypeId();
        if (usrTypeId != null) {
            stmt.bindLong(17, usrTypeId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TABLE_TASK_ALLOTMENT readEntity(Cursor cursor, int offset) {
        TABLE_TASK_ALLOTMENT entity = new TABLE_TASK_ALLOTMENT( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // taId
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // taEtId
                cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // taUsrId
                cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // taStartTime
                cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // taEndTime
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // taConfirm
                cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // taStaffId
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // taStaffName
                cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // taStatusId
                cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // timeStamp
                cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11), // taStartDate
                cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12), // taEndDate
                cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // usrName
                cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // usrMobile
                cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // usrImage
                cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16) // usrTypeId
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_TASK_ALLOTMENT entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTaId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTaEtId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setTaUsrId(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setTaStartTime(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setTaEndTime(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setTaConfirm(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTaStaffId(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setTaStaffName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTaStatusId(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setTimeStamp(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setTaStartDate(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
        entity.setTaEndDate(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
        entity.setUsrName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUsrMobile(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setUsrImage(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setTaUsertypeId(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_TASK_ALLOTMENT entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_TASK_ALLOTMENT entity) {
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
