package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sevennine_Delivery.Activity.GPSTracker;
import com.example.sevennine_Delivery.Bean.NewOrderBean;
import com.example.sevennine_Delivery.Fragment.AcceptOrderDetailsFragment;
import com.example.sevennine_Delivery.Fragment.CancelOrderDetailsFragment;
import com.example.sevennine_Delivery.Fragment.OrderDetailsFragment;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    public LinearLayout linearLayout;
GPSTracker gpsTracker;
    public static CardView cardView;
    Date date;
    public OrderAdapter(Activity activity, List<NewOrderBean> productList) {
        this.productList = productList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,cod,addr,username,vieworder,acceptorder;



        public MyViewHolder(View view) {
            super(view);
            prod_name=view.findViewById(R.id.prod_name);
            prod_price=view.findViewById(R.id.amount);
            cod=view.findViewById(R.id.cod);
            addr=view.findViewById(R.id.addr);
            username=view.findViewById(R.id.username);
            image=view.findViewById(R.id.image);
            vieworder=view.findViewById(R.id.vieworder);
            acceptorder=view.findViewById(R.id.accept);
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.neworder_detail_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    final NewOrderBean products = productList.get(position);
      holder.prod_name.setText(products.getProd_name());
        holder.prod_price.setText(products.getProd_price());
        holder.cod.setText(products.getCod());
      //  holder.username.setText(products.getAddr());
        holder.addr.setText(products.getAddr());


        Glide.with(activity).load(products.getImage()).placeholder(R.drawable.ic_gallery__default).dontAnimate().into(holder.image);
        holder.vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", products.getProd_name());
                bundle.putString("orderdate",products.getCreateddate());
                bundle.putString("totalamount",products.getProd_price());
                bundle.putString("addr",products.getAddr());
                bundle.putString("mode",products.getCod());
                bundle.putString("lat", products.getLatitude());
                bundle.putString("long",products.getLongitude());
                selectedFragment = OrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("new");
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });

        holder.acceptorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject params = new JSONObject();
                try {
                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("AcceptOrdersId",products.getProd_name());  // amount
                    params.put("Amount",products.getProd_price());  // amount
                    params.put("PayUTransactionId","1");  //transaction fees
                    params.put("ProductInfo",products.getAddr());
                    params.put("SellingListName","flower");
                    params.put("CategoryName","testingfruit");
                    params.put("SelectedQuantity","1"); //using status
                    params.put("UnitOfPrice","ampers");
                    params.put("SellingListIcon",products.getImage());
                    params.put("Latitude",products.getLatitude());  //tarnsaction id
                    params.put("Longitude",products.getLongitude());
                    params.put("CustomerName","test");
                    params.put("CustLatitude",products.getCustlat());
                    params.put("CustLongitude",products.getCustlong());
                    params.put("CreatedBy",sessionManager.getRegId("userId"));
                    System.out.println("RESPMsgdsfadf"+params);
                    Login_post.login_posting(activity, Urls.AddAccept, params, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("llllllllllllllllllllllllllll"+result);
                            try {
                                System.out.println("nnnnnmnm" + result.toString());
                                String status=result.getString("Status");
                                if(status.equals("1")){

                                    Toast toast = Toast.makeText(activity,"Order details Accepted for Delhivery Successfully", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                    toast.show();
                                }
                                else {
                                    Toast toast = Toast.makeText(activity,"Order details  Not Accepted", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                    toast.show();
                                    //  Toast.makeText(getActivity(),"Transaction Incomplete",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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