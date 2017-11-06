package com.abyz.infotech.warroomapp.greenDAO.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.abyz.infotech.warroomapp.model.TABLE_DISTRICT;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TABLE__DISTRICT.
*/
public class TABLE_DISTRICTDao extends AbstractDao<TABLE_DISTRICT, Long> {

    public static final String TABLENAME = "TABLE__DISTRICT";

    /**
     * Properties of entity TABLE_DISTRICT.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Dist_id = new Property(1, Integer.class, "dist_id", false, "DIST_ID");
        public final static Property Dist_st_id = new Property(2, Integer.class, "dist_st_id", false, "DIST_ST_ID");
        public final static Property Dist_status_id = new Property(3, Integer.class, "dist_status_id", false, "DIST_STATUS_ID");
        public final static Property Dist_name = new Property(4, String.class, "dist_name", false, "DIST_NAME");
    };


    public TABLE_DISTRICTDao(DaoConfig config) {
        super(config);
    }
    
    public TABLE_DISTRICTDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE__DISTRICT' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'DIST_ID' INTEGER UNIQUE," + // 1: dist_id
                "'DIST_ST_ID' INTEGER," + // 2: dist_st_id
                "'DIST_STATUS_ID' INTEGER," + // 3: dist_status_id
                "'DIST_NAME' TEXT);"); // 4: dist_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE__DISTRICT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TABLE_DISTRICT entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer dist_id = entity.getDist_id();
        if (dist_id != null) {
            stmt.bindLong(2, dist_id);
        }
 
        Integer dist_st_id = entity.getDist_st_id();
        if (dist_st_id != null) {
            stmt.bindLong(3, dist_st_id);
        }
 
        Integer dist_status_id = entity.getDist_status_id();
        if (dist_status_id != null) {
            stmt.bindLong(4, dist_status_id);
        }
 
        String dist_name = entity.getDist_name();
        if (dist_name != null) {
            stmt.bindString(5, dist_name);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TABLE_DISTRICT readEntity(Cursor cursor, int offset) {
        TABLE_DISTRICT entity = new TABLE_DISTRICT( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // dist_id
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // dist_st_id
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // dist_status_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // dist_name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TABLE_DISTRICT entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDist_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setDist_st_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setDist_status_id(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDist_name(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TABLE_DISTRICT entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TABLE_DISTRICT entity) {
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
