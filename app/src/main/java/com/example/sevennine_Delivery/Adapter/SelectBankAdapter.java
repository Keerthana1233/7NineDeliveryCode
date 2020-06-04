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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sevennine_Delivery.Bean.ListBeanHome;
import com.example.sevennine_Delivery.Fragment.RelationshipBankFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;


public class SelectBankAdapter extends RecyclerView.Adapter<SelectBankAdapter.MyViewHolder>  {
    private List<ListBeanHome> productList;
    Activity activity;
    Fragment selectedFragment;
    public static int bankid;


    public static CardView cardView;
    public SelectBankAdapter(Activity activity, List<ListBeanHome> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dealer_text;
        public LinearLayout brand_lay;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
           //dealer_text=view.findViewById(R.id.dealer_text);
            image=view.findViewById(R.id.dealer_img);
            brand_lay=view.findViewById(R.id.brand_lay);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_bank_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ListBeanHome products1 = productList.get(position);


      //  holder.dealer_text.setVisibility(View.GONE);
        Glide.with(activity).load(R.drawable.sbi)

                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.brand_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankid=products1.getId();
                selectedFragment = RelationshipBankFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("bank");
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}