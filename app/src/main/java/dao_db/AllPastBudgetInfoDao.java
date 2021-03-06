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
 * DAO for table "ALL_PAST_BUDGET_INFO".
*/
public class AllPastBudgetInfoDao extends AbstractDao<AllPastBudgetInfo, Void> {

    public static final String TABLENAME = "ALL_PAST_BUDGET_INFO";

    /**
     * Properties of entity AllPastBudgetInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Interval = new Property(0, String.class, "interval", false, "INTERVAL");
        public final static Property Chef_name = new Property(1, String.class, "chef_name", false, "CHEF_NAME");
    }


    public AllPastBudgetInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AllPastBudgetInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_PAST_BUDGET_INFO\" (" + //
                "\"INTERVAL\" TEXT," + // 0: interval
                "\"CHEF_NAME\" TEXT);"); // 1: chef_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_PAST_BUDGET_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AllPastBudgetInfo entity) {
        stmt.clearBindings();
 
        String interval = entity.getInterval();
        if (interval != null) {
            stmt.bindString(1, interval);
        }
 
        String chef_name = entity.getChef_name();
        if (chef_name != null) {
            stmt.bindString(2, chef_name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AllPastBudgetInfo entity) {
        stmt.clearBindings();
 
        String interval = entity.getInterval();
        if (interval != null) {
            stmt.bindString(1, interval);
        }
 
        String chef_name = entity.getChef_name();
        if (chef_name != null) {
            stmt.bindString(2, chef_name);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public AllPastBudgetInfo readEntity(Cursor cursor, int offset) {
        AllPastBudgetInfo entity = new AllPastBudgetInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // interval
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // chef_name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AllPastBudgetInfo entity, int offset) {
        entity.setInterval(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setChef_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(AllPastBudgetInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(AllPastBudgetInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(AllPastBudgetInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
