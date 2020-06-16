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

import com.example.sevennine_Delivery.Bean.NewOrderBean;
import com.example.sevennine_Delivery.Fragment.DeliveryOrderDetailsFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;


public class DeliveryOrderAdapter extends RecyclerView.Adapter<DeliveryOrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;

    public LinearLayout linearLayout;

    public static CardView cardView;
    public DeliveryOrderAdapter(Activity activity, List<NewOrderBean> productList) {
        this.productList = productList;
        this.activity=activity;


    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,cod,addr,username,vieworder;



        public MyViewHolder(View view) {
            super(view);
            prod_name=view.findViewById(R.id.prod_name);
            prod_price=view.findViewById(R.id.amount);
            cod=view.findViewById(R.id.cod);
            addr=view.findViewById(R.id.addr);
            username=view.findViewById(R.id.username);
            image=view.findViewById(R.id.image);
            vieworder=view.findViewById(R.id.vieworderdetails);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.neworder_detail_item3, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewOrderBean products = productList.get(position);
      holder.vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = DeliveryOrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("del");
                transaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}