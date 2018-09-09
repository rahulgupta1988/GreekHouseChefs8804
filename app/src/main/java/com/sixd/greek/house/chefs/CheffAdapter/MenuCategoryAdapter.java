package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.sixd.greek.house.chefs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import dao_db.MenuCategory;


public class MenuCategoryAdapter extends BaseAdapter {
    Context mcontext;
    List<MenuCategory> temp_ll_MenuCategory;

    public MenuCategoryAdapter(Context context,  List<MenuCategory> temp_ll_MenuCategory)
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
        TextView menu_txt;
        ImageView menu_dev_category_img;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.menu_development_item, null);


        menu_dev_category_img=(ImageView)
                convertView.findViewById(R.id.menu_dev_category_img);
        menu_txt=(TextView)convertView.findViewById(R.id.menu_txt);

        String category_id=temp_ll_MenuCategory.get(pos).getMenu_category_id();
        String category_name=temp_ll_MenuCategory.get(pos).getCategory_name();


        //menu_txt.setText("" + category_name);
        if (temp_ll_MenuCategory.get(pos).getCategory_name() != null
                && !temp_ll_MenuCategory.get(pos).getCategory_name().equalsIgnoreCase("")
                && !temp_ll_MenuCategory.get(pos).getCategory_name().equalsIgnoreCase("null"))
        {
            String Category_name=temp_ll_MenuCategory.get(pos).getCategory_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Category_name);
            menu_txt.setText(result.toString().trim());
        }
        else
        {
            menu_txt.setText("");
        }



      /*  if (image_url != null
                && !image_url.equalsIgnoreCase("")
                && !image_url.equalsIgnoreCase("null"))
        {
            Picasso.with(mcontext)
                    .load(image_url)
                    .resize(400, 200)
                    .centerInside()
                    .placeholder(R.drawable.sides_default)
                    .error(R.drawable.sides_default)
                    .into(menu_dev_category_img);
        }
        else
        {
            menu_txt.setText("");
        }*/


        try {
            String image_url=temp_ll_MenuCategory.get(pos).getImage_url();
            Log.i("respo_picfinal",""+image_url);
            if (image_url != null
                    && !image_url.equalsIgnoreCase("")
                    && !image_url.equalsIgnoreCase("null") )
            {
               // Log.i("respo_pic_if","if");
                Picasso.with(mcontext)
                        .load(image_url)
                        .resize(400, 200)
                        .centerInside()
                        .error(R.drawable.sides_default)
                        .placeholder(R.drawable.sides_default)
                        .into(menu_dev_category_img );
            }
            else
            {
               // Log.i("respo_pic_else","else");
                menu_dev_category_img.setBackgroundResource(R.drawable.sides_default);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




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