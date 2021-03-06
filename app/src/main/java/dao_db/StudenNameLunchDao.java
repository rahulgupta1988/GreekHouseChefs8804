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
 * DAO for table "STUDEN_NAME_LUNCH".
*/
public class StudenNameLunchDao extends AbstractDao<StudenNameLunch, Void> {

    public static final String TABLENAME = "STUDEN_NAME_LUNCH";

    /**
     * Properties of entity StudenNameLunch.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Dayname = new Property(0, String.class, "dayname", false, "DAYNAME");
        public final static Property Studentname = new Property(1, String.class, "studentname", false, "STUDENTNAME");
    }


    public StudenNameLunchDao(DaoConfig config) {
        super(config);
    }
    
    public StudenNameLunchDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STUDEN_NAME_LUNCH\" (" + //
                "\"DAYNAME\" TEXT," + // 0: dayname
                "\"STUDENTNAME\" TEXT);"); // 1: studentname
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STUDEN_NAME_LUNCH\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, StudenNameLunch entity) {
        stmt.clearBindings();
 
        String dayname = entity.getDayname();
        if (dayname != null) {
            stmt.bindString(1, dayname);
        }
 
        String studentname = entity.getStudentname();
        if (studentname != null) {
            stmt.bindString(2, studentname);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, StudenNameLunch entity) {
        stmt.clearBindings();
 
        String dayname = entity.getDayname();
        if (dayname != null) {
            stmt.bindString(1, dayname);
        }
 
        String studentname = entity.getStudentname();
        if (studentname != null) {
            stmt.bindString(2, studentname);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public StudenNameLunch readEntity(Cursor cursor, int offset) {
        StudenNameLunch entity = new StudenNameLunch( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // dayname
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // studentname
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, StudenNameLunch entity, int offset) {
        entity.setDayname(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setStudentname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(StudenNameLunch entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(StudenNameLunch entity) {
        return null;
    }

    @Override
    public boolean hasKey(StudenNameLunch entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
