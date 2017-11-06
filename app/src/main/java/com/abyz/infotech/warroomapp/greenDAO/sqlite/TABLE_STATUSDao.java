package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_STATUS;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__STATUS.
*/
public class TABLE_STATUSDao extends AbstractDao<TABLE_STATUS, Long> {

    public static final String TABLENAME = "TABLE__STATUS";

    /**
     * Properties of entity TABLE_STATUS.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_ID");
        public final static Property StatusId = new Property(1, Integer.class, "statusId", false, "STATUS_ID");
        public final static Property StatusName = new Property(2, String.class, "statusName", false, "STATUS_NAME");
    };


    public TABLE_STATUSDao(DaoConfig config) {
        super(config);
    }
    
    public TABLE_STATUSDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__STATUS' (" + //
                "'_ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "'STATUS_ID' INTEGER UNIQUE ," + // 1: statusId
                "'STATUS_NAME' TEXT);"); // 2: statusName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__STATUS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_STATUS entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        Integer statusId = entity.getStatusId();
        if (statusId != null) {
            stmt.bindLong(2, statusId);
        }
 
        String statusName = entity.getStatusName();
        if (statusName != null) {
            stmt.bindString(3, statusName);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TABLE_STATUS readEntity(Cursor cursor, int offset) {
        TABLE_STATUS entity = new TABLE_STATUS( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // statusId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // statusName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_STATUS entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStatusId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setStatusName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_STATUS entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_STATUS entity) {
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