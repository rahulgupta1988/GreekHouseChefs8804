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
 * DAO for table "ALL_FORM_CATEGORY".
*/
public class AllFormCategoryDao extends AbstractDao<AllFormCategory, String> {

    public static final String TABLENAME = "ALL_FORM_CATEGORY";

    /**
     * Properties of entity AllFormCategory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Forum_category_id = new Property(0, String.class, "Forum_category_id", true, "FORUM_CATEGORY_ID");
        public final static Property Category_name = new Property(1, String.class, "Category_name", false, "CATEGORY_NAME");
        public final static Property Created_on = new Property(2, String.class, "Created_on", false, "CREATED_ON");
        public final static Property Total_topics = new Property(3, String.class, "Total_topics", false, "TOTAL_TOPICS");
        public final static Property Total_comments = new Property(4, String.class, "Total_comments", false, "TOTAL_COMMENTS");
    }


    public AllFormCategoryDao(DaoConfig config) {
        super(config);
    }
    
    public AllFormCategoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_FORM_CATEGORY\" (" + //
                "\"FORUM_CATEGORY_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Forum_category_id
                "\"CATEGORY_NAME\" TEXT," + // 1: Category_name
                "\"CREATED_ON\" TEXT," + // 2: Created_on
                "\"TOTAL_TOPICS\" TEXT," + // 3: Total_topics
                "\"TOTAL_COMMENTS\" TEXT);"); // 4: Total_comments
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_FORM_CATEGORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AllFormCategory entity) {
        stmt.clearBindings();
 
        String Forum_category_id = entity.getForum_category_id();
        if (Forum_category_id != null) {
            stmt.bindString(1, Forum_category_id);
        }
 
        String Category_name = entity.getCategory_name();
        if (Category_name != null) {
            stmt.bindString(2, Category_name);
        }
 
        String Created_on = entity.getCreated_on();
        if (Created_on != null) {
            stmt.bindString(3, Created_on);
        }
 
        String Total_topics = entity.getTotal_topics();
        if (Total_topics != null) {
            stmt.bindString(4, Total_topics);
        }
 
        String Total_comments = entity.getTotal_comments();
        if (Total_comments != null) {
            stmt.bindString(5, Total_comments);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AllFormCategory entity) {
        stmt.clearBindings();
 
        String Forum_category_id = entity.getForum_category_id();
        if (Forum_category_id != null) {
            stmt.bindString(1, Forum_category_id);
        }
 
        String Category_name = entity.getCategory_name();
        if (Category_name != null) {
            stmt.bindString(2, Category_name);
        }
 
        String Created_on = entity.getCreated_on();
        if (Created_on != null) {
            stmt.bindString(3, Created_on);
        }
 
        String Total_topics = entity.getTotal_topics();
        if (Total_topics != null) {
            stmt.bindString(4, Total_topics);
        }
 
        String Total_comments = entity.getTotal_comments();
        if (Total_comments != null) {
            stmt.bindString(5, Total_comments);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public AllFormCategory readEntity(Cursor cursor, int offset) {
        AllFormCategory entity = new AllFormCategory( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Forum_category_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Category_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Created_on
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Total_topics
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // Total_comments
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AllFormCategory entity, int offset) {
        entity.setForum_category_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCategory_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreated_on(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTotal_topics(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTotal_comments(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(AllFormCategory entity, long rowId) {
        return entity.getForum_category_id();
    }
    
    @Override
    public String getKey(AllFormCategory entity) {
        if(entity != null) {
            return entity.getForum_category_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AllFormCategory entity) {
        return entity.getForum_category_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
