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

public class TalukAdapter extends RecyclerView.Adapter<TalukAdapter.TalukMyViewHolder> {

    List<StateBean> stateBeanList;
    public static int talukid;
    Activity activity;


    public TalukAdapter(List<StateBean> stateBeanList, Activity activity) {
        this.stateBeanList = stateBeanList;
        this.activity=activity;
    }



    @NonNull
    @Override
    public TalukMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview= LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);
        return new TalukMyViewHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final TalukMyViewHolder holder, int position) {
        final StateBean stateBean=stateBeanList.get(position);

        holder.statename.setText(stateBean.getName());

        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = activity.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                talukid=stateBean.getId();
                System.out.println("checkingggggg"+talukid);
                AddNewAddressFragment.ed_tsl.setText(holder.statename.getText().toString());
                AddNewAddressFragment.drawer.closeDrawers();
                /* if (LandDetails.land_deta!=null){
                    LandDetails.ed_tsl.setText(holder.statename.getText().toString());
                    LandDetails.drawer.closeDrawers();
                } else if (AddNewAddressPreview.page!=null){
                    AddNewAddressPreview.ed_tsl.setText(holder.statename.getText().toString());
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
        return stateBeanList.size();
    }

    public class TalukMyViewHolder extends RecyclerView.ViewHolder {
        TextView statename;
        LinearLayout state_name_layout;

        public TalukMyViewHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);
        }
    }
}
