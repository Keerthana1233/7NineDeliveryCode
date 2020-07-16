package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.sevennine_Delivery.Fragment.Verification_Last_Fragment;
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
    String phonestr,modestr,acceptorderid,amount,payuid,addrid,image,storelat,storelang,custlat,custlang,quantity;
    public static CardView cardView;
    String dtStart;
    Date date;
    public OrderAdapter(Activity activity, List<NewOrderBean> productList) {
        this.productList = productList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,cod,addr,username,vieworder,acceptorder,orderdate;



        public MyViewHolder(View view) {
            super(view);
            prod_name=view.findViewById(R.id.prod_name);
            orderdate=view.findViewById(R.id.dispatched);
            image=view.findViewById(R.id.image);
            vieworder=view.findViewById(R.id.vieworder);
            acceptorder=view.findViewById(R.id.accept);
          /*  cod=view.findViewById(R.id.cod);
            addr=view.findViewById(R.id.addr);
            username=view.findViewById(R.id.username);

           */
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
    acceptorderid=products.getProd_name();
        amount=products.getProd_price();
        payuid=products.getPayuid();
        addrid=products.getAddr();
        image=products.getImage();
        storelat=products.getLatitude();
        storelang=products.getLongitude();
        custlat=products.getCustlat();
        custlang=products.getCustlong();
        if(products.getCod().equalsIgnoreCase("")){
            modestr = "COD";
            //+ "," + "76.48490166";
        }else{
            modestr=products.getCod();
        }
     //   holder.prod_price.setText(products.getProd_price());

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
     // holder.username.setText(products.getUsername());
      //  holder.addr.setText(products.getAddr());

        /*if(products.getQuantity().equals("")){
            quantity = "1";
            //+ "," + "76.48490166";
        }else{
            quantity=products.getQuantity();
        }*/
        holder.prod_name.setText(products.getProductname()+","+"1" +" Kg"+","+"₹"+Double.parseDouble(products.getProd_price()));

        // holder.cod.setText(modestr);
        if(products.getPhone().equalsIgnoreCase("")){
            phonestr = "9999999999";
        }else{
            phonestr=products.getPhone();
        }
        Glide.with(activity).load(products.getProducticon()).placeholder(R.drawable.ic_gallery__default).dontAnimate().into(holder.image);
        holder.vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("proimg", products.getProducticon());
                bundle.putString("proname",products.getProductname());
                bundle.putString("orderId", products.getProd_name());
                bundle.putString("orderdate",products.getCreateddate());
                bundle.putString("totalamount",products.getProd_price());
                bundle.putString("addr",products.getAddr());
                bundle.putString("mode",modestr);
                bundle.putString("lat", products.getLatitude());
                bundle.putString("long",products.getLongitude());
                bundle.putString("cuslat", products.getCustlat());
                bundle.putString("cuslong",products.getCustlong());
                bundle.putString("mobile", phonestr);
                bundle.putString("payuid", products.getPayuid());
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
                AlertMessage();
            }
        });
    }
    private void AlertMessage() { // alert dialog box
        final TextView ok_btn,cancel_btn,text_desc;
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.acceptorderpopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);



        ok_btn =  dialog.findViewById(R.id.ok_btn);
        cancel_btn =  dialog.findViewById(R.id.cancel_btn);
        text_desc =  dialog.findViewById(R.id.text_desc);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject params = new JSONObject();
                try {
                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("AcceptOrdersId",acceptorderid);  // amount
                    params.put("Amount",amount);  // amount
                    params.put("PayUTransactionId",payuid);  //transaction fees
                    params.put("ProductInfo",addrid);
                    params.put("SellingListName","flower");
                    params.put("CategoryName","testingfruit");
                    params.put("SelectedQuantity","1"); //using status
                    params.put("UnitOfPrice","ampers");
                    params.put("SellingListIcon",image);
                    params.put("Latitude",storelat);  //tarnsaction id
                    params.put("Longitude",storelang);
                    params.put("CustomerName","test");
                    params.put("CustLatitude",custlat);
                    params.put("CustLongitude",custlang);
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
                                    dialog.dismiss();
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


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


//
//
//CInventory_Adapter.MyViewHolder viewHolder1 =(CInventory_Adapter.MyViewHolder) CInventory_Fragment.recyclerView.findViewHolderForAdapterPosition(CInventory_Adapter.selected_position);

//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
//        alertDialogBuilder.setMessage("Do you want to submit the details for verification?");
//        //alertDialogBuilder.setMessage(Html.fromHtml("<font size = '18dp'>Do You want to submit the details for verification?</font>"));
//        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                selectedFragment = Verification_Last_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();
//
//            }
//        });
//
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.show();


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