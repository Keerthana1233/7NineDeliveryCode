package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.sevennine_Delivery.Bean.NewOrderBean;
import com.example.sevennine_Delivery.Fragment.AcceptOrderDetailsFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;


public class AcceptOrderAdapter extends RecyclerView.Adapter<AcceptOrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
String orderid,latid,langid;
    public LinearLayout linearLayout;

    public static CardView cardView;
    public AcceptOrderAdapter(Activity activity, List<NewOrderBean> productList) {
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
            vieworder=view.findViewById(R.id.vieworder);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.neworder_detail_item2, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewOrderBean products = productList.get(position);
        holder.prod_name.setText(products.getProd_name());
        holder.prod_price.setText(products.getProd_price());
        holder.cod.setText(products.getCod());
        holder.username.setText(products.getAddr());
        holder.addr.setText(products.getAddr());
        orderid=products.getProd_name();
        latid=products.getLatitude();
        langid=products.getLongitude();
        Glide.with(activity).load(products.getImage()).placeholder(R.drawable.ic_gallery__default).dontAnimate().into(holder.image);

        holder.vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderid);
                bundle.putString("latidkey",latid);
                bundle.putString("langidkey",langid);
                bundle.putString("orderdate",products.getCreateddate());
                bundle.putString("totalamount",products.getProd_price());
                bundle.putString("addr",products.getAddr());
                bundle.putString("mode",products.getCod());
                selectedFragment = AcceptOrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
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