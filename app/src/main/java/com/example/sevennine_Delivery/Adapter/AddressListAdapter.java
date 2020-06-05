package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Bean.Add_New_Address_Bean;
import com.example.sevennine_Delivery.Fragment.SelectBankFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder>  {
    private List<Add_New_Address_Bean> productList;
    Activity activity;
    Fragment selectedFragment;
    public LinearLayout linearLayout;
    public static String addressid;


    public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public AddressListAdapter(Activity activity, List<Add_New_Address_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bank_name,address,state;
        public LinearLayout branch_lay;
        public ImageView bank_img;


        public MyViewHolder(View view) {
            super(view);
            //agri_text=view.findViewById(R.id.store_agri);
            branch_lay=view.findViewById(R.id.branch_lay);
            bank_name=view.findViewById(R.id.bank_name);
            address=view.findViewById(R.id.address);
            state=view.findViewById(R.id.state);
          //  bank_img=view.findViewById(R.id.bank_img);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.you_addr_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Add_New_Address_Bean products = productList.get(position);
        //holder.agri_text.setText(products.getAgri_text());
        holder.bank_name.setText(products.getAdd_name());
        holder.address.setText(products.getAdd_street()+"\n"+products.getAdd_landmark());
        holder.state.setText(products.getAdd_taluk()+", "+products.getAdd_state()+" "+products.getAdd_pincode());

        holder.branch_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fm = ((FragmentActivity)activity).getSupportFragmentManager();
                fm.popBackStack ("current", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
               addressid=products.getAdd_id();
                selectedFragment = SelectBankFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });

        /*Glide.with(activity).load(R.drawable.sbibranch)

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.bank_img);*/

    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}