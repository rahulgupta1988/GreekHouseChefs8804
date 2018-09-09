package dao_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CALENDER_DATA".
*/
public class CalenderDataDao extends AbstractDao<CalenderData, Void> {

    public static final String TABLENAME = "CALENDER_DATA";

    /**
     * Properties of entity CalenderData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Week_number = new Property(0, String.class, "week_number", false, "WEEK_NUMBER");
        public final static Property Week_year = new Property(1, String.class, "week_year", false, "WEEK_YEAR");
    }


    public CalenderDataDao(DaoConfig config) {
        super(config);
    }
    
    public CalenderDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CALENDER_DATA\" (" + //
                "\"WEEK_NUMBER\" TEXT," + // 0: week_number
                "\"WEEK_YEAR\" TEXT);"); // 1: week_year
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CALENDER_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CalenderData entity) {
        stmt.clearBindings();
 
        String week_number = entity.getWeek_number();
        if (week_number != null) {
            stmt.bindString(1, week_number);
        }
 
        String week_year = entity.getWeek_year();
        if (week_year != null) {
            stmt.bindString(2, week_year);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CalenderData entity) {
        stmt.clearBindings();
 
        String week_number = entity.getWeek_number();
        if (week_number != null) {
            stmt.bindString(1, week_number);
        }
 
        String week_year = entity.getWeek_year();
        if (week_year != null) {
            stmt.bindString(2, week_year);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public CalenderData readEntity(Cursor cursor, int offset) {
        CalenderData entity = new CalenderData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // week_number
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // week_year
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CalenderData entity, int offset) {
        entity.setWeek_number(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setWeek_year(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(CalenderData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(CalenderData entity) {
        return null;
    }

    @Override
    public boolean hasKey(CalenderData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}