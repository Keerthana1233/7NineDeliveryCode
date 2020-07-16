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
import com.example.sevennine_Delivery.Fragment.CancelOrderDetailsFragment;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AcceptOrderAdapter extends RecyclerView.Adapter<AcceptOrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
String orderid,latid,langid,custlatid,custlangid,modestr,phone;
    public LinearLayout linearLayout;
    Date date;
    public static CardView cardView;
    public AcceptOrderAdapter(Activity activity, List<NewOrderBean> productList) {
        this.productList = productList;
        this.activity=activity;

        sessionManager = new SessionManager(activity);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,cod,addr,username,vieworder,orderdate;



        public MyViewHolder(View view) {
            super(view);
            prod_name=view.findViewById(R.id.prod_name);
            orderdate=view.findViewById(R.id.dispatched);
            //cod=view.findViewById(R.id.cod);
           // addr=view.findViewById(R.id.addr);
           // username=view.findViewById(R.id.username);
            image=view.findViewById(R.id.image);
          //  vieworder=view.findViewById(R.id.vieworder);

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
        holder.prod_name.setText(products.getProductname()+","+"1" +" Kg"+","+"₹"+Double.parseDouble(products.getProd_price()));
   //     holder.prod_price.setText(products.getProd_price());

    /*  holder.username.setText(products.getUsername());
        holder.addr.setText(products.getAddr());*/
        orderid=products.getProd_name();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dtStart = products.getCreateddate();
        System.out.println("getCreateddate"+dtStart);

        try {
            date = format.parse(dtStart);
            //holder.orderdate.setText("Ordered On "+date.getYear()+"-"+(1+date.getMonth())+"-"+(date.getDate()+" "+date.getHours()+":"+date.getMinutes()));
            holder.orderdate.setText("Ordered On "+date.getDate()+"-"+(1+date.getMonth())+"-"+(1900+date.getYear()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(products.getCod().equalsIgnoreCase("")){
            modestr = "COD";
            //+ "," + "76.48490166";
        }else{
            modestr=products.getCod();
        }
      //  holder.cod.setText(modestr);
        if(products.getPhone().equalsIgnoreCase("")){
            phone = "9999999999";
        }else{
            phone=products.getPhone();
        }
        if(products.getLatitude().equalsIgnoreCase("null")){
            latid = "14.5555555";
            //+ "," + "76.48490166";
        }else{
            latid=products.getLatitude();
        }
        if(products.getLongitude().equalsIgnoreCase("null")){
            langid = "76.48490166";
            //+ "," + "76.48490166";
        }else{
            langid=products.getLongitude();
        }
        if(products.getCustlat().equalsIgnoreCase("")){
            custlatid = "14.665805680534437";
            //+ "," + "76.48490166";
        }else{
            custlatid=products.getCustlat();
        }
        if(products.getCustlong().equalsIgnoreCase("")){
            custlangid = "75.48590049147606";
            //+ "," + "76.48490166";
        }else{
            custlangid=products.getCustlong();
        }

        Glide.with(activity).load(products.getProducticon()).placeholder(R.drawable.ic_gallery__default).dontAnimate().into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("proimg", products.getProducticon());
                bundle.putString("proname",products.getProductname());
                bundle.putString("orderId", orderid);
                bundle.putString("latidkey",latid);
                bundle.putString("langidkey",langid);
                bundle.putString("custlatidkey",custlatid);
                bundle.putString("custlangidkey",custlangid);
                bundle.putString("orderdate",products.getCreateddate());
                bundle.putString("totalamount",products.getProd_price());
                bundle.putString("addr",products.getAddr());
                bundle.putString("mode",modestr);
                bundle.putString("mobile",phone);
                selectedFragment = AcceptOrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("acceptmap");
                selectedFragment.setArguments(bundle);
                System.out.println("bundlev"+bundle);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}