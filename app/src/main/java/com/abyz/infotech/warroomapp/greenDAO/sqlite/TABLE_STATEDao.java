package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_STATE;
import com.abyz.infotech.warroomapp.model.TABLE_USER;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__STATE.
*/
public class TABLE_STATEDao extends AbstractDao<TABLE_STATE, Long> {

    public static final String TABLENAME = "TABLE__STATE";

    /**
     * Properties of entity TABLE_STATE.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property St_id = new Property(1, Integer.class, "st_id", false, "ST_ID");
        public final static Property St_status_id = new Property(2, Integer.class, "st_status_id", false, "ST_STATUS_ID");
        public final static Property St_name = new Property(3, String.class, "st_name", false, "ST_NAME");
    };


    public TABLE_STATEDao(DaoConfig config) {
        super(config);
    }
    
    public TABLE_STATEDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__STATE' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'ST_ID' INTEGER UNIQUE," + // 1: st_id
                "'ST_STATUS_ID' INTEGER," + // 2: st_status_id
                "'ST_NAME' TEXT);"); // 3: st_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__STATE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_STATE entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer st_id = entity.getSt_id();
        if (st_id != null) {
            stmt.bindLong(2, st_id);
        }
 
        Integer st_status_id = entity.getSt_status_id();
        if (st_status_id != null) {
            stmt.bindLong(3, st_status_id);
        }
 
        String st_name = entity.getSt_name();
        if (st_name != null) {
            stmt.bindString(4, st_name);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TABLE_STATE readEntity(Cursor cursor, int offset) {
        TABLE_STATE entity = new TABLE_STATE( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // st_id
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // st_status_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // st_name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_STATE entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSt_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setSt_status_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setSt_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_STATE entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_STATE entity) {
        if(entity != null) {
            return entity.getId();
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
