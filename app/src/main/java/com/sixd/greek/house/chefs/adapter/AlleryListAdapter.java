package com.sixd.greek.house.chefs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.AllergyListManager;
import com.sixd.greek.house.chefs.model.AllergyListModel;

/**
 * Created by Praveen on 09-Aug-17.
 */

public class AlleryListAdapter extends RecyclerView.Adapter<AlleryListAdapter.AllergyHolder> {


    Context mContext;
    public interface AllergyCall{
        void allergyID(String id);
        void removeAllergy(String id);
    }
    AllergyCall allergyCall;

    public AlleryListAdapter(Context mContext,AllergyCall allergyCall){
        this.mContext=mContext;
        this.allergyCall=allergyCall;
    }

    @Override
    public AlleryListAdapter.AllergyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.allergylist_item, parent, false);
        return new AlleryListAdapter.AllergyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlleryListAdapter.AllergyHolder holder, final int position) {


        AllergyListModel allergyListModel=AllergyListManager.allergyListModels.get(position);
        String allergy_id=allergyListModel.getAllergy_id();
        String allergy_name=allergyListModel.getAllergy_name();
        String user_check=allergyListModel.getUser_check();

        holder.allergy_txt.setText(""+allergy_name);


        if(user_check.equals("YES"))
        {
            holder.ch_ic.setSelected(true);
            allergyCall.allergyID(allergy_id);
        }
        else holder.ch_ic.setSelected(false);

        holder.ch_ic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(holder.ch_ic.isSelected()){
                    holder.ch_ic.setSelected(false);
                    String allergy_id=AllergyListManager.allergyListModels.get(position).getAllergy_id();
                    allergyCall.removeAllergy(allergy_id);
                }
                else{
                    holder.ch_ic.setSelected(true);
                    String allergy_id=AllergyListManager.allergyListModels.get(position).getAllergy_id();
                    allergyCall.allergyID(allergy_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllergyListManager.allergyListModels.size();
    }

    public class AllergyHolder extends RecyclerView.ViewHolder {

        ImageView ch_ic;
        TextView allergy_txt;
        public AllergyHolder(View convertView) {
            super(convertView);
            ch_ic=(ImageView)convertView.findViewById(R.id.ch_ic);
            allergy_txt=(TextView)convertView.findViewById(R.id.allergy_txt);
        }

    }
}
