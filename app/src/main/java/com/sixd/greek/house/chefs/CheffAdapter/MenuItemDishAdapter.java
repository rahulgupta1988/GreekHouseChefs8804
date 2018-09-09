package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sixd.greek.house.chefs.R;

import java.util.List;

import dao_db.MenuItemDishes;


public class MenuItemDishAdapter extends BaseAdapter {
    Context mcontext;
    List<MenuItemDishes> getMenuItemDishesList;

    public MenuItemDishAdapter(Context context, List<MenuItemDishes> getMenuItemDishesList)
    {
        this.mcontext = context;
        this.getMenuItemDishesList = getMenuItemDishesList;
    }

    @Override
    public int getCount() {
        return getMenuItemDishesList.size();
    }

    @Override
    public Object getItem(int i) {
        return getMenuItemDishesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView sub_category_item_dish_txt,description_item;
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.subcategory_item_dish, null);


        image_view=(ImageView)
                convertView.findViewById(R.id.image_view);
        sub_category_item_dish_txt=(TextView)convertView.findViewById(R.id.sub_category_item_dish_txt);
        description_item=(TextView)convertView.findViewById(R.id.description_item);

        String Menu_id=getMenuItemDishesList.get(pos).getMenu_id();
        String Menu_title=getMenuItemDishesList.get(pos).getMenu_title();
        String Menu_desc=getMenuItemDishesList.get(pos).getDescription();


        if (getMenuItemDishesList.get(pos).getMenu_title() != null
                && !getMenuItemDishesList.get(pos).getMenu_title().equalsIgnoreCase("")
                && !getMenuItemDishesList.get(pos).getMenu_title().equalsIgnoreCase("null"))
        {
            String Menu_titl=getMenuItemDishesList.get(pos).getMenu_title().toString().trim().toLowerCase();
            String result = upperCaseFirst(Menu_titl);
            sub_category_item_dish_txt.setText(result.toString().trim());
        }
        else
        {
            sub_category_item_dish_txt.setText("");
        }


        if (getMenuItemDishesList.get(pos).getDescription().toString().trim() != null
                && !getMenuItemDishesList.get(pos).getDescription().toString().trim() .equalsIgnoreCase("")
                && !getMenuItemDishesList.get(pos).getDescription().toString().trim() .equalsIgnoreCase("null"))
        {
            description_item.setText(getMenuItemDishesList.get(pos).getDescription().toString().trim());
        }
        else
        {
            description_item.setText("");
        }


        //get first letter of each String item

        String firstLetter = String.valueOf(getMenuItemDishesList.get(pos).getMenu_title().toString().trim().toUpperCase().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getMenuItemDishesList.get(pos));
        //int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        image_view.setImageDrawable(drawable);


        return convertView;

    }


    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }
}