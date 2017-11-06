package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_LOCATION_AREA;
import com.abyz.infotech.warroomapp.model.TABLE_USER;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__LOCATION__AREA.
*/
public class TABLE_LOCATION_AREADao extends AbstractDao<TABLE_LOCATION_AREA, Long> {

    public static final String TABLENAME = "TABLE__LOCATION__AREA";

    /**
     * Properties of entity TABLE_LOCATION_AREA.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Locarea_id = new Property(1, Integer.class, "locarea_id", false, "LOCAREA_ID");
        public final static Property Locarea_user_id = new Property(2, Integer.class, "locarea_user_id", false, "LOCAREA_USER_ID");
        public final static Property Locarea_dist_id = new Property(3, Integer.class, "locarea_dist_id", false, "LOCAREA_DIST_ID");
        public final static Property Locarea_status_id = new Property(4, Integer.class, "locarea_status_id", false, "LOCAREA_STATUS_ID");
        public final static Property Locarea_name = new Property(5, String.class, "locarea_name", false, "LOCAREA_NAME");
    };


    public TABLE_LOCATION_AREADao(DaoConfig config) {
        super(config);
    }
    
    public TABLE_LOCATION_AREADao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__LOCATION__AREA' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'LOCAREA_ID' INTEGER UNIQUE," + // 1: locarea_id
                "'LOCAREA_USER_ID' INTEGER," + // 2: locarea_user_id
                "'LOCAREA_DIST_ID' INTEGER," + // 3: locarea_dist_id
                "'LOCAREA_STATUS_ID' INTEGER," + // 4: locarea_status_id
                "'LOCAREA_NAME' TEXT);"); // 5: locarea_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__LOCATION__AREA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_LOCATION_AREA entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer locarea_id = entity.getLocarea_id();
        if (locarea_id != null) {
            stmt.bindLong(2, locarea_id);
        }
 
        Integer locarea_user_id = entity.getLocarea_user_id();
        if (locarea_user_id != null) {
            stmt.bindLong(3, locarea_user_id);
        }
 
        Integer locarea_dist_id = entity.getLocarea_dist_id();
        if (locarea_dist_id != null) {
            stmt.bindLong(4, locarea_dist_id);
        }
 
        Integer locarea_status_id = entity.getLocarea_status_id();
        if (locarea_status_id != null) {
            stmt.bindLong(5, locarea_status_id);
        }
 
        String locarea_name = entity.getLocarea_name();
        if (locarea_name != null) {
            stmt.bindString(6, locarea_name);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TABLE_LOCATION_AREA readEntity(Cursor cursor, int offset) {
        TABLE_LOCATION_AREA entity = new TABLE_LOCATION_AREA( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // locarea_id
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // locarea_user_id
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // locarea_dist_id
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // locarea_status_id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // locarea_name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_LOCATION_AREA entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLocarea_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setLocarea_user_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setLocarea_dist_id(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setLocarea_status_id(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setLocarea_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_LOCATION_AREA entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_LOCATION_AREA entity) {
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
