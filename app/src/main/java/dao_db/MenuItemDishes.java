package dao_db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "MENU_ITEM_DISHES".
 */
@Entity
public class MenuItemDishes {
    private String menu_id;
    private String menu_title;
    private String description;

    @Generated
    public MenuItemDishes() {
    }

    @Generated
    public MenuItemDishes(String menu_id, String menu_title, String description) {
        this.menu_id = menu_id;
        this.menu_title = menu_title;
        this.description = description;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_title() {
        return menu_title;
    }

    public void setMenu_title(String menu_title) {
        this.menu_title = menu_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
