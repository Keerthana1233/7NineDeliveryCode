package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Bean.StateBean;
import com.example.sevennine_Delivery.Fragment.AddNewAddressFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.HoblisMyViewHolder> {

    List<StateBean> stateBeans;
    public static int villageid;
    Activity activity;



    public VillageAdapter(List<StateBean> stateBeans, Activity activity) {
        this.stateBeans = stateBeans;
        this.activity = activity;
    }



    @NonNull
    @Override
    public HoblisMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview= LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);


        return new HoblisMyViewHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final HoblisMyViewHolder holder, int position) {
        final StateBean stateBean=stateBeans.get(position);
        holder.statename.setText(stateBean.getName());

        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = activity.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
               villageid = stateBean.getId();
               System.out.println("viiiiiii"+villageid);
                AddNewAddressFragment.ed_vill.setText(holder.statename.getText().toString());
                AddNewAddressFragment.drawer.closeDrawers();

              /*  if (LandDetails.land_deta!=null){
                    LandDetails.ed_vill.setText(holder.statename.getText().toString());
                    LandDetails.drawer.closeDrawers();
                } else if (AddNewAddressPreview.page!=null){
                    AddNewAddressPreview.ed_vill.setText(holder.statename.getText().toString());
                    AddNewAddressPreview.drawer.closeDrawers();
                }else {
                    //  DealerAddressFragment.district_name.setText(holder.statename.getText().toString());
                      // DealerAddressFragment.drawer.closeDrawers();
                    //  Add_New_Address_Fragment.grade_dialog.dismiss();
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateBeans.size();
    }

    public class HoblisMyViewHolder extends RecyclerView.ViewHolder{
        TextView statename;
        LinearLayout state_name_layout;
        public HoblisMyViewHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);

        }
    }
}
