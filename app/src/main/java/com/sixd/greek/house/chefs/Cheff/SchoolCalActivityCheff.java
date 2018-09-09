package com.sixd.greek.house.chefs.Cheff;

    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v7.widget.DefaultItemAnimator;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.widget.LinearLayout;
    import android.widget.ListView;
    import android.widget.Toast;

    import com.afollestad.materialdialogs.MaterialDialog;
    import com.sixd.greek.house.chefs.Cheff.HomeActivityCheff;
    import com.sixd.greek.house.chefs.CheffAdapter.AllEventsAdapter;
    import com.sixd.greek.house.chefs.CheffAdapter.MenuItemDishAdapter;
    import com.sixd.greek.house.chefs.R;
    import com.sixd.greek.house.chefs.manager.CallBackManager;
    import com.sixd.greek.house.chefs.manager.EventManager;
    import com.sixd.greek.house.chefs.utils.Constant;
    import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

    import java.util.ArrayList;
    import java.util.List;

    import dao_db.AllEvents;

    /**
     * Created by Praveen on 21-Jul-17.
     */

    public class SchoolCalActivityCheff extends HeaderActivtyCheff {

        Context mContext;
        ListView event_list;
        MaterialDialog materialDialog=null;
        List<AllEvents> allEventses;
        List<AllEvents> temp_ll_AllEvents;
        LinearLayout linear;

        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mContext=this;
            materialDialog=new MaterialDialog.Builder(this)
                    .content("Please Wait...")
                    .progress(true, 0)
                    .cancelable(false).build();

            setHeaderTitele("School Calendar");
            initViews();
        }


        public void initViews(){

            linear=(LinearLayout)findViewById(R.id.linear);

            event_list=(ListView)findViewById(R.id.event_list);
            allEventses= new EventManager(SchoolCalActivityCheff.this).getAllEvents();
            temp_ll_AllEvents=new ArrayList<AllEvents>();
            temp_ll_AllEvents.clear();
            for(int x=0;x<allEventses.size();x++)
            {
                if (allEventses.get(x).getIs_student_calender().equalsIgnoreCase("1"))
                {
                    temp_ll_AllEvents.add(allEventses.get(x));
                }
            }

            event_list.setDivider(null);
            event_list.setAdapter(new AllEventsAdapter(SchoolCalActivityCheff.this, temp_ll_AllEvents));


        }

        @Override
        public void onBackPressed() {
            temp_ll_AllEvents.clear();
            Intent intent=new Intent(SchoolCalActivityCheff.this,HomeActivityCheff.class);
            startActivity(intent);
            finish();
        }
    }
