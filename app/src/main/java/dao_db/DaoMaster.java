package dao_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 41): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 41;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        UserLoginInfoDao.createTable(db, ifNotExists);
        UserTypeDao.createTable(db, ifNotExists);
        MenuCategoryDao.createTable(db, ifNotExists);
        MenuItemDishesDao.createTable(db, ifNotExists);
        AllEventsDao.createTable(db, ifNotExists);
        AllWeekIntervalListDao.createTable(db, ifNotExists);
        AllLatePlateChefListDao.createTable(db, ifNotExists);
        StudenNameLunchDao.createTable(db, ifNotExists);
        StudenNameDinnerDao.createTable(db, ifNotExists);
        AllLunchAllergyListDao.createTable(db, ifNotExists);
        AllDinnerAllergyListDao.createTable(db, ifNotExists);
        CalenderDataDao.createTable(db, ifNotExists);
        CurrentWeekLunchDao.createTable(db, ifNotExists);
        CurrentWeekDinnerDao.createTable(db, ifNotExists);
        NextWeekLunchDao.createTable(db, ifNotExists);
        NextWeekDinnerDao.createTable(db, ifNotExists);
        SubmitBudgetDao.createTable(db, ifNotExists);
        AllPastBudgetInfoDao.createTable(db, ifNotExists);
        AllFormCategoryDao.createTable(db, ifNotExists);
        AllCategoryTopicsDao.createTable(db, ifNotExists);
        TopicDetailDao.createTable(db, ifNotExists);
        TopicCommentDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        UserLoginInfoDao.dropTable(db, ifExists);
        UserTypeDao.dropTable(db, ifExists);
        MenuCategoryDao.dropTable(db, ifExists);
        MenuItemDishesDao.dropTable(db, ifExists);
        AllEventsDao.dropTable(db, ifExists);
        AllWeekIntervalListDao.dropTable(db, ifExists);
        AllLatePlateChefListDao.dropTable(db, ifExists);
        StudenNameLunchDao.dropTable(db, ifExists);
        StudenNameDinnerDao.dropTable(db, ifExists);
        AllLunchAllergyListDao.dropTable(db, ifExists);
        AllDinnerAllergyListDao.dropTable(db, ifExists);
        CalenderDataDao.dropTable(db, ifExists);
        CurrentWeekLunchDao.dropTable(db, ifExists);
        CurrentWeekDinnerDao.dropTable(db, ifExists);
        NextWeekLunchDao.dropTable(db, ifExists);
        NextWeekDinnerDao.dropTable(db, ifExists);
        SubmitBudgetDao.dropTable(db, ifExists);
        AllPastBudgetInfoDao.dropTable(db, ifExists);
        AllFormCategoryDao.dropTable(db, ifExists);
        AllCategoryTopicsDao.dropTable(db, ifExists);
        TopicDetailDao.dropTable(db, ifExists);
        TopicCommentDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserLoginInfoDao.class);
        registerDaoClass(UserTypeDao.class);
        registerDaoClass(MenuCategoryDao.class);
        registerDaoClass(MenuItemDishesDao.class);
        registerDaoClass(AllEventsDao.class);
        registerDaoClass(AllWeekIntervalListDao.class);
        registerDaoClass(AllLatePlateChefListDao.class);
        registerDaoClass(StudenNameLunchDao.class);
        registerDaoClass(StudenNameDinnerDao.class);
        registerDaoClass(AllLunchAllergyListDao.class);
        registerDaoClass(AllDinnerAllergyListDao.class);
        registerDaoClass(CalenderDataDao.class);
        registerDaoClass(CurrentWeekLunchDao.class);
        registerDaoClass(CurrentWeekDinnerDao.class);
        registerDaoClass(NextWeekLunchDao.class);
        registerDaoClass(NextWeekDinnerDao.class);
        registerDaoClass(SubmitBudgetDao.class);
        registerDaoClass(AllPastBudgetInfoDao.class);
        registerDaoClass(AllFormCategoryDao.class);
        registerDaoClass(AllCategoryTopicsDao.class);
        registerDaoClass(TopicDetailDao.class);
        registerDaoClass(TopicCommentDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
