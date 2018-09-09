package com.sixd.greek.house.chefs.CheffAdapter;

    import android.content.Context;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;

    import com.sixd.greek.house.chefs.R;
    import com.sixd.greek.house.chefs.utils.Constant;

    import java.util.List;


    public class AddMenuAdapterLunch extends BaseAdapter {
        Context mcontext;
        List<String> ElementsArrayListProduct;
        ListView listview;
        String value_check;

        public AddMenuAdapterLunch(Context context, List<String> ListElementsProduct, ListView listView, String value_check)
        {
            this.mcontext = context;
            this.listview = listView;
            this.value_check=value_check;
            this.ElementsArrayListProduct = ListElementsProduct;
            Log.i("size 134234", "" + ElementsArrayListProduct.size());
        }


        @Override
        public int getCount() {
            return ElementsArrayListProduct.size();
        }

        @Override
        public Object getItem(int i) {
            return ElementsArrayListProduct.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final int pos = i;
            TextView menu_name;
            ImageView img_delete;

            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);

//        convertView = inflater.inflate(R.layout.coupon_adapter, null);
            convertView = inflater.inflate(R.layout.add_menu_list, null);
            menu_name = (TextView) convertView.findViewById(R.id.menu_name);
            img_delete = (ImageView) convertView.findViewById(R.id.img_delete);
            menu_name.setText(ElementsArrayListProduct.get(pos));



            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Log.i("respo_value_check", ""+value_check);
                    if (value_check.equalsIgnoreCase("ed"))
                    {
                        Log.i("respo_ed_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed2"))
                    {
                        Log.i("respo_ed2_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed3"))
                    {
                        Log.i("respo_ed3_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed4"))
                    {
                        Log.i("respo_ed4_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed5"))
                    {
                        Log.i("respo_ed5_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed6"))
                    {
                        Log.i("respo_ed6_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed7"))
                    {
                        Log.i("respo_ed7_size", ""+Constant.ListElementsFinal_temp_lunch.size());
                        try {
                            if (Constant.ListElementsFinal_temp_lunch.size() != 0)
                            {
                                Constant.ListElementsFinal_temp_lunch.remove(ElementsArrayListProduct.get(pos));
                                listview.setAdapter(new AddMenuAdapterLunch(mcontext, Constant.ListElementsFinal_temp_lunch,listview,value_check));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            });


            return convertView;


        }
    }