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
 * DAO for table "ALL_EVENTS".
*/
public class AllEventsDao extends AbstractDao<AllEvents, Void> {

    public static final String TABLENAME = "ALL_EVENTS";

    /**
     * Properties of entity AllEvents.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Event_id = new Property(0, String.class, "event_id", false, "EVENT_ID");
        public final static Property Is_student_calender = new Property(1, String.class, "is_student_calender", false, "IS_STUDENT_CALENDER");
        public final static Property Event_title = new Property(2, String.class, "event_title", false, "EVENT_TITLE");
        public final static Property Event_start = new Property(3, String.class, "event_start", false, "EVENT_START");
        public final static Property Event_end = new Property(4, String.class, "event_end", false, "EVENT_END");
        public final static Property Start_time = new Property(5, String.class, "start_time", false, "START_TIME");
        public final static Property End_time = new Property(6, String.class, "end_time", false, "END_TIME");
        public final static Property Description = new Property(7, String.class, "description", false, "DESCRIPTION");
    }


    public AllEventsDao(DaoConfig config) {
        super(config);
    }
    
    public AllEventsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_EVENTS\" (" + //
                "\"EVENT_ID\" TEXT," + // 0: event_id
                "\"IS_STUDENT_CALENDER\" TEXT," + // 1: is_student_calender
                "\"EVENT_TITLE\" TEXT," + // 2: event_title
                "\"EVENT_START\" TEXT," + // 3: event_start
                "\"EVENT_END\" TEXT," + // 4: event_end
                "\"START_TIME\" TEXT," + // 5: start_time
                "\"END_TIME\" TEXT," + // 6: end_time
                "\"DESCRIPTION\" TEXT);"); // 7: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_EVENTS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AllEvents entity) {
        stmt.clearBindings();
 
        String event_id = entity.getEvent_id();
        if (event_id != null) {
            stmt.bindString(1, event_id);
        }
 
        String is_student_calender = entity.getIs_student_calender();
        if (is_student_calender != null) {
            stmt.bindString(2, is_student_calender);
        }
 
        String event_title = entity.getEvent_title();
        if (event_title != null) {
            stmt.bindString(3, event_title);
        }
 
        String event_start = entity.getEvent_start();
        if (event_start != null) {
            stmt.bindString(4, event_start);
        }
 
        String event_end = entity.getEvent_end();
        if (event_end != null) {
            stmt.bindString(5, event_end);
        }
 
        String start_time = entity.getStart_time();
        if (start_time != null) {
            stmt.bindString(6, start_time);
        }
 
        String end_time = entity.getEnd_time();
        if (end_time != null) {
            stmt.bindString(7, end_time);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AllEvents entity) {
        stmt.clearBindings();
 
        String event_id = entity.getEvent_id();
        if (event_id != null) {
            stmt.bindString(1, event_id);
        }
 
        String is_student_calender = entity.getIs_student_calender();
        if (is_student_calender != null) {
            stmt.bindString(2, is_student_calender);
        }
 
        String event_title = entity.getEvent_title();
        if (event_title != null) {
            stmt.bindString(3, event_title);
        }
 
        String event_start = entity.getEvent_start();
        if (event_start != null) {
            stmt.bindString(4, event_start);
        }
 
        String event_end = entity.getEvent_end();
        if (event_end != null) {
            stmt.bindString(5, event_end);
        }
 
        String start_time = entity.getStart_time();
        if (start_time != null) {
            stmt.bindString(6, start_time);
        }
 
        String end_time = entity.getEnd_time();
        if (end_time != null) {
            stmt.bindString(7, end_time);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public AllEvents readEntity(Cursor cursor, int offset) {
        AllEvents entity = new AllEvents( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // event_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // is_student_calender
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // event_title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // event_start
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // event_end
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // start_time
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // end_time
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // description
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AllEvents entity, int offset) {
        entity.setEvent_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIs_student_calender(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEvent_title(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEvent_start(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEvent_end(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStart_time(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEnd_time(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDescription(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(AllEvents entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(AllEvents entity) {
        return null;
    }

    @Override
    public boolean hasKey(AllEvents entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}