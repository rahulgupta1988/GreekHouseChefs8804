package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
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

import dao_db.MenuCategory;



public class MenuItemAdapter extends BaseAdapter {
    Context mcontext;
    List<MenuCategory> temp_ll_MenuCategory;

    public MenuItemAdapter(Context context, List<MenuCategory> temp_ll_MenuCategory)
    {
        this.mcontext = context;
        this.temp_ll_MenuCategory = temp_ll_MenuCategory;
    }

    @Override
    public int getCount() {
        return temp_ll_MenuCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return temp_ll_MenuCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView sub_category_item_name_txt,item_dishes_count_txt;
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.subcategory, null);


        image_view=(ImageView)
                convertView.findViewById(R.id.image_view);
        sub_category_item_name_txt=(TextView)convertView.findViewById(R.id.sub_category_item_name_txt);
        item_dishes_count_txt=(TextView)convertView.findViewById(R.id.item_dishes_count_txt);

        String Sub_category_id=temp_ll_MenuCategory.get(pos).getMenu_category_id();
        String Sub_category_item_name=temp_ll_MenuCategory.get(pos).getCategory_name();
        String Item_dishes_count=temp_ll_MenuCategory.get(pos).getTotal_menu_items();


        if (temp_ll_MenuCategory.get(pos).getCategory_name() != null
                && !temp_ll_MenuCategory.get(pos).getCategory_name().equalsIgnoreCase("")
                && !temp_ll_MenuCategory.get(pos).getCategory_name().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=temp_ll_MenuCategory.get(pos).getCategory_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            sub_category_item_name_txt.setText(result.toString().trim());
        }
        else
        {
            sub_category_item_name_txt.setText("");
        }

        if (temp_ll_MenuCategory.get(pos).getTotal_menu_items() != null
                && !temp_ll_MenuCategory.get(pos).getTotal_menu_items().equalsIgnoreCase("")
                && !temp_ll_MenuCategory.get(pos).getTotal_menu_items().equalsIgnoreCase("null"))
        {
            item_dishes_count_txt.setText(temp_ll_MenuCategory.get(pos).getTotal_menu_items() +" dishes");
        }
        else
        {
            item_dishes_count_txt.setText("0 dishes");
        }


        //get first letter of each String item

        String firstLetter = String.valueOf(temp_ll_MenuCategory.get(pos).getCategory_name().toString().trim().toUpperCase().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(temp_ll_MenuCategory.get(pos));
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