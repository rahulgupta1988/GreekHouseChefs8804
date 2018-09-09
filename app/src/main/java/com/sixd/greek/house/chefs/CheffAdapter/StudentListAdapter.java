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


    public class StudentListAdapter extends BaseAdapter {
        Context mcontext;
        List<String> ListElementsStudent;


        public StudentListAdapter(Context context, List<String> ListElementsStudent)
        {
            this.mcontext = context;
            this.ListElementsStudent = ListElementsStudent;
            Log.i("size 134234", "" + ListElementsStudent.size());
        }


        @Override
        public int getCount() {
            return ListElementsStudent.size();
        }

        @Override
        public Object getItem(int i) {
            return ListElementsStudent.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final int pos = i;
            TextView menu_name;

            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);

//        convertView = inflater.inflate(R.layout.coupon_adapter, null);
            convertView = inflater.inflate(R.layout.student_list_adapter, null);
            menu_name = (TextView) convertView.findViewById(R.id.menu_name);
            menu_name.setText(ListElementsStudent.get(pos).toString().trim());


            return convertView;


        }
    }