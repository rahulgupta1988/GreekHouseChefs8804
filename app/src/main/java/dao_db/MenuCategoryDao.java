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
 * DAO for table "MENU_CATEGORY".
*/
public class MenuCategoryDao extends AbstractDao<MenuCategory, Void> {

    public static final String TABLENAME = "MENU_CATEGORY";

    /**
     * Properties of entity MenuCategory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Menu_category_id = new Property(0, String.class, "menu_category_id", false, "MENU_CATEGORY_ID");
        public final static Property Image_url = new Property(1, String.class, "image_url", false, "IMAGE_URL");
        public final static Property Category_name = new Property(2, String.class, "category_name", false, "CATEGORY_NAME");
        public final static Property Parent_id = new Property(3, String.class, "parent_id", false, "PARENT_ID");
        public final static Property Total_menu_items = new Property(4, String.class, "total_menu_items", false, "TOTAL_MENU_ITEMS");
    }


    public MenuCategoryDao(DaoConfig config) {
        super(config);
    }
    
    public MenuCategoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MENU_CATEGORY\" (" + //
                "\"MENU_CATEGORY_ID\" TEXT," + // 0: menu_category_id
                "\"IMAGE_URL\" TEXT," + // 1: image_url
                "\"CATEGORY_NAME\" TEXT," + // 2: category_name
                "\"PARENT_ID\" TEXT," + // 3: parent_id
                "\"TOTAL_MENU_ITEMS\" TEXT);"); // 4: total_menu_items
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MENU_CATEGORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MenuCategory entity) {
        stmt.clearBindings();
 
        String menu_category_id = entity.getMenu_category_id();
        if (menu_category_id != null) {
            stmt.bindString(1, menu_category_id);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(2, image_url);
        }
 
        String category_name = entity.getCategory_name();
        if (category_name != null) {
            stmt.bindString(3, category_name);
        }
 
        String parent_id = entity.getParent_id();
        if (parent_id != null) {
            stmt.bindString(4, parent_id);
        }
 
        String total_menu_items = entity.getTotal_menu_items();
        if (total_menu_items != null) {
            stmt.bindString(5, total_menu_items);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MenuCategory entity) {
        stmt.clearBindings();
 
        String menu_category_id = entity.getMenu_category_id();
        if (menu_category_id != null) {
            stmt.bindString(1, menu_category_id);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(2, image_url);
        }
 
        String category_name = entity.getCategory_name();
        if (category_name != null) {
            stmt.bindString(3, category_name);
        }
 
        String parent_id = entity.getParent_id();
        if (parent_id != null) {
            stmt.bindString(4, parent_id);
        }
 
        String total_menu_items = entity.getTotal_menu_items();
        if (total_menu_items != null) {
            stmt.bindString(5, total_menu_items);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public MenuCategory readEntity(Cursor cursor, int offset) {
        MenuCategory entity = new MenuCategory( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // menu_category_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // image_url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // category_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // parent_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // total_menu_items
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MenuCategory entity, int offset) {
        entity.setMenu_category_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setImage_url(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategory_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setParent_id(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTotal_menu_items(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(MenuCategory entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(MenuCategory entity) {
        return null;
    }

    @Override
    public boolean hasKey(MenuCategory entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
