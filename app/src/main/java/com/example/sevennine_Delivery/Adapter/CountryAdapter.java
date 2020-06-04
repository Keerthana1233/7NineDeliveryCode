package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sevennine_Delivery.Bean.StateBean;
import com.example.sevennine_Delivery.Fragment.AddNewAddressFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyStateHolder> {
    List<StateBean> stateBeans;
    Activity activity;
    public static String countryname;
    public static int countryid;



    public CountryAdapter(List<StateBean> stateBeans, Activity activity) {
        this.stateBeans = stateBeans;
        this.activity=activity;
    }


    @NonNull
    @Override
    public MyStateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview= LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);
        return new MyStateHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyStateHolder holder, int position) {
        final StateBean stateBean=stateBeans.get(position);
        holder.statename.setText(stateBean.getName());



        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryid = stateBean.getId();
                countryname = stateBean.getName();

                if (AddNewAddressFragment.address == null) {
                    /*DealerAddressFragment.district_name.setText(holder.statename.getText().toString());
                    DealerAddressFragment.drawer.closeDrawers();*/

                } else {
                    //  DealerAddressFragment.district_name.setText(holder.statename.getText().toString());
                  //  AddNewAddressFragment.country_text.setText(holder.statename.getText().toString());
                    AddNewAddressFragment.drawer.closeDrawers();
                    // DealerAddressFragment.drawer.closeDrawers();
                    //  Add_New_Address_Fragment.grade_dialog.dismiss();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return stateBeans.size();
    }

    public class MyStateHolder extends RecyclerView.ViewHolder{

        TextView statename;
        LinearLayout state_name_layout;

        public MyStateHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);

        }
    }
}
