package com.sixd.greek.house.chefs.model;

/**
 * Created by Praveen on 09-Aug-17.
 */

public class MenuDevelopmentModel {

    String menu_category_id;
    String image_url;
    String category_name;

    public String getMenu_category_id() {
        return menu_category_id;
    }

    public void setMenu_category_id(String menu_category_id) {
        this.menu_category_id = menu_category_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
