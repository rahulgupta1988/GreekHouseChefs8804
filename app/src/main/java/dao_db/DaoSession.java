package dao_db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import dao_db.UserLoginInfo;
import dao_db.UserType;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;
import dao_db.AllEvents;
import dao_db.AllWeekIntervalList;
import dao_db.AllLatePlateChefList;
import dao_db.StudenNameLunch;
import dao_db.StudenNameDinner;
import dao_db.AllLunchAllergyList;
import dao_db.AllDinnerAllergyList;
import dao_db.CalenderData;
import dao_db.CurrentWeekLunch;
import dao_db.CurrentWeekDinner;
import dao_db.NextWeekLunch;
import dao_db.NextWeekDinner;
import dao_db.SubmitBudget;
import dao_db.AllPastBudgetInfo;
import dao_db.AllFormCategory;
import dao_db.AllCategoryTopics;
import dao_db.TopicDetail;
import dao_db.TopicComment;

import dao_db.UserLoginInfoDao;
import dao_db.UserTypeDao;
import dao_db.MenuCategoryDao;
import dao_db.MenuItemDishesDao;
import dao_db.AllEventsDao;
import dao_db.AllWeekIntervalListDao;
import dao_db.AllLatePlateChefListDao;
import dao_db.StudenNameLunchDao;
import dao_db.StudenNameDinnerDao;
import dao_db.AllLunchAllergyListDao;
import dao_db.AllDinnerAllergyListDao;
import dao_db.CalenderDataDao;
import dao_db.CurrentWeekLunchDao;
import dao_db.CurrentWeekDinnerDao;
import dao_db.NextWeekLunchDao;
import dao_db.NextWeekDinnerDao;
import dao_db.SubmitBudgetDao;
import dao_db.AllPastBudgetInfoDao;
import dao_db.AllFormCategoryDao;
import dao_db.AllCategoryTopicsDao;
import dao_db.TopicDetailDao;
import dao_db.TopicCommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userLoginInfoDaoConfig;
    private final DaoConfig userTypeDaoConfig;
    private final DaoConfig menuCategoryDaoConfig;
    private final DaoConfig menuItemDishesDaoConfig;
    private final DaoConfig allEventsDaoConfig;
    private final DaoConfig allWeekIntervalListDaoConfig;
    private final DaoConfig allLatePlateChefListDaoConfig;
    private final DaoConfig studenNameLunchDaoConfig;
    private final DaoConfig studenNameDinnerDaoConfig;
    private final DaoConfig allLunchAllergyListDaoConfig;
    private final DaoConfig allDinnerAllergyListDaoConfig;
    private final DaoConfig calenderDataDaoConfig;
    private final DaoConfig currentWeekLunchDaoConfig;
    private final DaoConfig currentWeekDinnerDaoConfig;
    private final DaoConfig nextWeekLunchDaoConfig;
    private final DaoConfig nextWeekDinnerDaoConfig;
    private final DaoConfig submitBudgetDaoConfig;
    private final DaoConfig allPastBudgetInfoDaoConfig;
    private final DaoConfig allFormCategoryDaoConfig;
    private final DaoConfig allCategoryTopicsDaoConfig;
    private final DaoConfig topicDetailDaoConfig;
    private final DaoConfig topicCommentDaoConfig;

    private final UserLoginInfoDao userLoginInfoDao;
    private final UserTypeDao userTypeDao;
    private final MenuCategoryDao menuCategoryDao;
    private final MenuItemDishesDao menuItemDishesDao;
    private final AllEventsDao allEventsDao;
    private final AllWeekIntervalListDao allWeekIntervalListDao;
    private final AllLatePlateChefListDao allLatePlateChefListDao;
    private final StudenNameLunchDao studenNameLunchDao;
    private final StudenNameDinnerDao studenNameDinnerDao;
    private final AllLunchAllergyListDao allLunchAllergyListDao;
    private final AllDinnerAllergyListDao allDinnerAllergyListDao;
    private final CalenderDataDao calenderDataDao;
    private final CurrentWeekLunchDao currentWeekLunchDao;
    private final CurrentWeekDinnerDao currentWeekDinnerDao;
    private final NextWeekLunchDao nextWeekLunchDao;
    private final NextWeekDinnerDao nextWeekDinnerDao;
    private final SubmitBudgetDao submitBudgetDao;
    private final AllPastBudgetInfoDao allPastBudgetInfoDao;
    private final AllFormCategoryDao allFormCategoryDao;
    private final AllCategoryTopicsDao allCategoryTopicsDao;
    private final TopicDetailDao topicDetailDao;
    private final TopicCommentDao topicCommentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userLoginInfoDaoConfig = daoConfigMap.get(UserLoginInfoDao.class).clone();
        userLoginInfoDaoConfig.initIdentityScope(type);

        userTypeDaoConfig = daoConfigMap.get(UserTypeDao.class).clone();
        userTypeDaoConfig.initIdentityScope(type);

        menuCategoryDaoConfig = daoConfigMap.get(MenuCategoryDao.class).clone();
        menuCategoryDaoConfig.initIdentityScope(type);

        menuItemDishesDaoConfig = daoConfigMap.get(MenuItemDishesDao.class).clone();
        menuItemDishesDaoConfig.initIdentityScope(type);

        allEventsDaoConfig = daoConfigMap.get(AllEventsDao.class).clone();
        allEventsDaoConfig.initIdentityScope(type);

        allWeekIntervalListDaoConfig = daoConfigMap.get(AllWeekIntervalListDao.class).clone();
        allWeekIntervalListDaoConfig.initIdentityScope(type);

        allLatePlateChefListDaoConfig = daoConfigMap.get(AllLatePlateChefListDao.class).clone();
        allLatePlateChefListDaoConfig.initIdentityScope(type);

        studenNameLunchDaoConfig = daoConfigMap.get(StudenNameLunchDao.class).clone();
        studenNameLunchDaoConfig.initIdentityScope(type);

        studenNameDinnerDaoConfig = daoConfigMap.get(StudenNameDinnerDao.class).clone();
        studenNameDinnerDaoConfig.initIdentityScope(type);

        allLunchAllergyListDaoConfig = daoConfigMap.get(AllLunchAllergyListDao.class).clone();
        allLunchAllergyListDaoConfig.initIdentityScope(type);

        allDinnerAllergyListDaoConfig = daoConfigMap.get(AllDinnerAllergyListDao.class).clone();
        allDinnerAllergyListDaoConfig.initIdentityScope(type);

        calenderDataDaoConfig = daoConfigMap.get(CalenderDataDao.class).clone();
        calenderDataDaoConfig.initIdentityScope(type);

        currentWeekLunchDaoConfig = daoConfigMap.get(CurrentWeekLunchDao.class).clone();
        currentWeekLunchDaoConfig.initIdentityScope(type);

        currentWeekDinnerDaoConfig = daoConfigMap.get(CurrentWeekDinnerDao.class).clone();
        currentWeekDinnerDaoConfig.initIdentityScope(type);

        nextWeekLunchDaoConfig = daoConfigMap.get(NextWeekLunchDao.class).clone();
        nextWeekLunchDaoConfig.initIdentityScope(type);

        nextWeekDinnerDaoConfig = daoConfigMap.get(NextWeekDinnerDao.class).clone();
        nextWeekDinnerDaoConfig.initIdentityScope(type);

        submitBudgetDaoConfig = daoConfigMap.get(SubmitBudgetDao.class).clone();
        submitBudgetDaoConfig.initIdentityScope(type);

        allPastBudgetInfoDaoConfig = daoConfigMap.get(AllPastBudgetInfoDao.class).clone();
        allPastBudgetInfoDaoConfig.initIdentityScope(type);

        allFormCategoryDaoConfig = daoConfigMap.get(AllFormCategoryDao.class).clone();
        allFormCategoryDaoConfig.initIdentityScope(type);

        allCategoryTopicsDaoConfig = daoConfigMap.get(AllCategoryTopicsDao.class).clone();
        allCategoryTopicsDaoConfig.initIdentityScope(type);

        topicDetailDaoConfig = daoConfigMap.get(TopicDetailDao.class).clone();
        topicDetailDaoConfig.initIdentityScope(type);

        topicCommentDaoConfig = daoConfigMap.get(TopicCommentDao.class).clone();
        topicCommentDaoConfig.initIdentityScope(type);

        userLoginInfoDao = new UserLoginInfoDao(userLoginInfoDaoConfig, this);
        userTypeDao = new UserTypeDao(userTypeDaoConfig, this);
        menuCategoryDao = new MenuCategoryDao(menuCategoryDaoConfig, this);
        menuItemDishesDao = new MenuItemDishesDao(menuItemDishesDaoConfig, this);
        allEventsDao = new AllEventsDao(allEventsDaoConfig, this);
        allWeekIntervalListDao = new AllWeekIntervalListDao(allWeekIntervalListDaoConfig, this);
        allLatePlateChefListDao = new AllLatePlateChefListDao(allLatePlateChefListDaoConfig, this);
        studenNameLunchDao = new StudenNameLunchDao(studenNameLunchDaoConfig, this);
        studenNameDinnerDao = new StudenNameDinnerDao(studenNameDinnerDaoConfig, this);
        allLunchAllergyListDao = new AllLunchAllergyListDao(allLunchAllergyListDaoConfig, this);
        allDinnerAllergyListDao = new AllDinnerAllergyListDao(allDinnerAllergyListDaoConfig, this);
        calenderDataDao = new CalenderDataDao(calenderDataDaoConfig, this);
        currentWeekLunchDao = new CurrentWeekLunchDao(currentWeekLunchDaoConfig, this);
        currentWeekDinnerDao = new CurrentWeekDinnerDao(currentWeekDinnerDaoConfig, this);
        nextWeekLunchDao = new NextWeekLunchDao(nextWeekLunchDaoConfig, this);
        nextWeekDinnerDao = new NextWeekDinnerDao(nextWeekDinnerDaoConfig, this);
        submitBudgetDao = new SubmitBudgetDao(submitBudgetDaoConfig, this);
        allPastBudgetInfoDao = new AllPastBudgetInfoDao(allPastBudgetInfoDaoConfig, this);
        allFormCategoryDao = new AllFormCategoryDao(allFormCategoryDaoConfig, this);
        allCategoryTopicsDao = new AllCategoryTopicsDao(allCategoryTopicsDaoConfig, this);
        topicDetailDao = new TopicDetailDao(topicDetailDaoConfig, this);
        topicCommentDao = new TopicCommentDao(topicCommentDaoConfig, this);

        registerDao(UserLoginInfo.class, userLoginInfoDao);
        registerDao(UserType.class, userTypeDao);
        registerDao(MenuCategory.class, menuCategoryDao);
        registerDao(MenuItemDishes.class, menuItemDishesDao);
        registerDao(AllEvents.class, allEventsDao);
        registerDao(AllWeekIntervalList.class, allWeekIntervalListDao);
        registerDao(AllLatePlateChefList.class, allLatePlateChefListDao);
        registerDao(StudenNameLunch.class, studenNameLunchDao);
        registerDao(StudenNameDinner.class, studenNameDinnerDao);
        registerDao(AllLunchAllergyList.class, allLunchAllergyListDao);
        registerDao(AllDinnerAllergyList.class, allDinnerAllergyListDao);
        registerDao(CalenderData.class, calenderDataDao);
        registerDao(CurrentWeekLunch.class, currentWeekLunchDao);
        registerDao(CurrentWeekDinner.class, currentWeekDinnerDao);
        registerDao(NextWeekLunch.class, nextWeekLunchDao);
        registerDao(NextWeekDinner.class, nextWeekDinnerDao);
        registerDao(SubmitBudget.class, submitBudgetDao);
        registerDao(AllPastBudgetInfo.class, allPastBudgetInfoDao);
        registerDao(AllFormCategory.class, allFormCategoryDao);
        registerDao(AllCategoryTopics.class, allCategoryTopicsDao);
        registerDao(TopicDetail.class, topicDetailDao);
        registerDao(TopicComment.class, topicCommentDao);
    }
    
    public void clear() {
        userLoginInfoDaoConfig.clearIdentityScope();
        userTypeDaoConfig.clearIdentityScope();
        menuCategoryDaoConfig.clearIdentityScope();
        menuItemDishesDaoConfig.clearIdentityScope();
        allEventsDaoConfig.clearIdentityScope();
        allWeekIntervalListDaoConfig.clearIdentityScope();
        allLatePlateChefListDaoConfig.clearIdentityScope();
        studenNameLunchDaoConfig.clearIdentityScope();
        studenNameDinnerDaoConfig.clearIdentityScope();
        allLunchAllergyListDaoConfig.clearIdentityScope();
        allDinnerAllergyListDaoConfig.clearIdentityScope();
        calenderDataDaoConfig.clearIdentityScope();
        currentWeekLunchDaoConfig.clearIdentityScope();
        currentWeekDinnerDaoConfig.clearIdentityScope();
        nextWeekLunchDaoConfig.clearIdentityScope();
        nextWeekDinnerDaoConfig.clearIdentityScope();
        submitBudgetDaoConfig.clearIdentityScope();
        allPastBudgetInfoDaoConfig.clearIdentityScope();
        allFormCategoryDaoConfig.clearIdentityScope();
        allCategoryTopicsDaoConfig.clearIdentityScope();
        topicDetailDaoConfig.clearIdentityScope();
        topicCommentDaoConfig.clearIdentityScope();
    }

    public UserLoginInfoDao getUserLoginInfoDao() {
        return userLoginInfoDao;
    }

    public UserTypeDao getUserTypeDao() {
        return userTypeDao;
    }

    public MenuCategoryDao getMenuCategoryDao() {
        return menuCategoryDao;
    }

    public MenuItemDishesDao getMenuItemDishesDao() {
        return menuItemDishesDao;
    }

    public AllEventsDao getAllEventsDao() {
        return allEventsDao;
    }

    public AllWeekIntervalListDao getAllWeekIntervalListDao() {
        return allWeekIntervalListDao;
    }

    public AllLatePlateChefListDao getAllLatePlateChefListDao() {
        return allLatePlateChefListDao;
    }

    public StudenNameLunchDao getStudenNameLunchDao() {
        return studenNameLunchDao;
    }

    public StudenNameDinnerDao getStudenNameDinnerDao() {
        return studenNameDinnerDao;
    }

    public AllLunchAllergyListDao getAllLunchAllergyListDao() {
        return allLunchAllergyListDao;
    }

    public AllDinnerAllergyListDao getAllDinnerAllergyListDao() {
        return allDinnerAllergyListDao;
    }

    public CalenderDataDao getCalenderDataDao() {
        return calenderDataDao;
    }

    public CurrentWeekLunchDao getCurrentWeekLunchDao() {
        return currentWeekLunchDao;
    }

    public CurrentWeekDinnerDao getCurrentWeekDinnerDao() {
        return currentWeekDinnerDao;
    }

    public NextWeekLunchDao getNextWeekLunchDao() {
        return nextWeekLunchDao;
    }

    public NextWeekDinnerDao getNextWeekDinnerDao() {
        return nextWeekDinnerDao;
    }

    public SubmitBudgetDao getSubmitBudgetDao() {
        return submitBudgetDao;
    }

    public AllPastBudgetInfoDao getAllPastBudgetInfoDao() {
        return allPastBudgetInfoDao;
    }

    public AllFormCategoryDao getAllFormCategoryDao() {
        return allFormCategoryDao;
    }

    public AllCategoryTopicsDao getAllCategoryTopicsDao() {
        return allCategoryTopicsDao;
    }

    public TopicDetailDao getTopicDetailDao() {
        return topicDetailDao;
    }

    public TopicCommentDao getTopicCommentDao() {
        return topicCommentDao;
    }

}