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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sevennine_Delivery.Bean.RepaymentBean;
import com.example.sevennine_Delivery.R;

import java.util.List;

public class BankDetailAdapter extends RecyclerView.Adapter<BankDetailAdapter.MyViewHolder>  {
    private List<RepaymentBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public LinearLayout linearLayout;


    public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public BankDetailAdapter(Activity activity, List<RepaymentBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,bank_name,acc_no,ifsc_code,emi;
        public LinearLayout branch_lay;
        public ImageView bank_img;


        public MyViewHolder(View view) {
            super(view);
            emi=view.findViewById(R.id.emi);
            name=view.findViewById(R.id.name);
            bank_name=view.findViewById(R.id.bank_name);
            acc_no=view.findViewById(R.id.acc_no);
            ifsc_code=view.findViewById(R.id.ifsc_code);
            bank_img=view.findViewById(R.id.image);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_detail_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RepaymentBean products = productList.get(position);
        holder.name.setText(products.getName());
        holder.bank_name.setText(products.getBank_name());
        holder.emi.setText("EMI : "+products.getEmi());
        holder.acc_no.setText("Account Number : "+products.getAcc_no());
        holder.ifsc_code.setText("IFSC Code : "+products.getIfsc_code());

       /* holder.branch_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = LoanDetailsSuccessful.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("pref_branch");
                transaction.commit();
            }
        });*/

        Glide.with(activity).load(R.drawable.bank_of_baroda_logo)

                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.bank_img);

    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}