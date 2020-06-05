/*
package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;


public class TrackApplicationAdapter extends RecyclerView.Adapter<TrackApplicationAdapter.MyViewHolder>  {
    private List<BankOfferBean> productList;
    Activity activity;
    Fragment selectedFragment;


    public static CardView cardView;
    public TrackApplicationAdapter(Activity activity, List<BankOfferBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bank_name,view_text,loan_amount,interest_rate,tenure_range,in_process;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            bank_name=view.findViewById(R.id.bank_name);
            image=view.findViewById(R.id.image);
            loan_amount=view.findViewById(R.id.loan_amount);
            interest_rate=view.findViewById(R.id.interest_rate);
            tenure_range=view.findViewById(R.id.tenure_range);
            view_text=view.findViewById(R.id.view_text);
            in_process=view.findViewById(R.id.in_process);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_app_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BankOfferBean products1 = productList.get(position);
        holder.bank_name.setText(Html.fromHtml("<b>"+products1.getBank_name()+"</b>"+" "+products1.getLoan_type()));
        holder.loan_amount.setText(products1.getLoan_amount());
        holder.interest_rate.setText(products1.getInterest_rate());
        holder.tenure_range.setText(products1.getTenuree_rang());

        Glide.with(activity).load(R.drawable.bank_of_baroda_logo)

                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.in_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("BankNamee",products1.getBank_name());
                bundle.putString("LoanType",products1.getLoan_type());
                bundle.putString("LoanAmountt",products1.getLoan_amount());
                bundle.putString("Interestt",products1.getInterest_rate());
                bundle.putString("Tenurerangee",products1.getTenuree_rang());
                selectedFragment = CropLoan.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("track");
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}*/
