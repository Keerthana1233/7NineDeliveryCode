package com.example.sevennine_Delivery.Orders;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sevennine_Delivery.Activity.GPSTracker;
import com.example.sevennine_Delivery.Adapter.OrderAdapter;
import com.example.sevennine_Delivery.Bean.NewOrderBean;
import com.example.sevennine_Delivery.Fragment.FilterFragment;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;
import com.google.android.gms.location.LocationListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
//Our class extending fragment
public class NewOrderTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener   {
    RecyclerView recyclerView;
    public static OrderAdapter madapter;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    Fragment selectedFragment=null;
    ArrayList<Integer> timers;
    int i1;
    SessionManager sessionManager;
    TextView filter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    JSONArray jsonArray;
    NewOrderBean bean;
    GPSTracker gpsTracker;
    public static NewOrderTab newInstance() {
        NewOrderTab itemOnFragment = new NewOrderTab();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_order_tab, container, false);
        filter=view.findViewById(R.id.filter);
       // setRepeatingAsyncTask();
        sessionManager = new SessionManager(getActivity());
        recyclerView=view.findViewById(R.id.new_order_recy);
        sessionManager=new SessionManager(getActivity());
        mSwipeRefreshLayout = view.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCodezzzzzzzzzq  : " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                   // getFragmentManager().popBackStack("ORDER_FRAGMENT", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().onBackPressed();
                    // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        madapter=new OrderAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
        Newdata();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = FilterFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout1, selectedFragment);
                ft.commit();
            }
        });
        return view;
        
    }
public  void  Newdata(){
    mSwipeRefreshLayout.setRefreshing(true);
    gpsTracker = new GPSTracker(getActivity());
    try{
        newOrderBeansList.clear();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserId",sessionManager.getRegId("userId"));
        System.out.println("GetOrderslist"+jsonObject);
        Crop_Post.crop_posting(getActivity(), Urls.GetOrderslist, jsonObject, new VoleyJsonObjectCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                System.out.println("iGetOrderslistdetails"+result);
                try{
                    jsonArray = result.getJSONArray("orderfromcart");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        bean=new NewOrderBean(jsonObject1.getString("AcceptOrdersId"),jsonObject1.getString("CustAddress"),jsonObject1.getString("CustAddress"),jsonObject1.getString("Amount"),jsonObject1.getString("mode"),jsonObject1.getString("SellingListIcon"),jsonObject1.getString("Latitude"),jsonObject1.getString("Longitude"),jsonObject1.getString("CreatedOn"),jsonObject1.getString("CustLongitude"),jsonObject1.getString("CustLatitude"));
                        newOrderBeansList.add(bean);


                    }
                    madapter=new OrderAdapter(getActivity(),newOrderBeansList);
                    recyclerView.setAdapter(madapter);
                    madapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }catch (Exception e){
        e.printStackTrace();
    }
    mSwipeRefreshLayout.setRefreshing(false);
}
    @Override
    public void onRefresh(){
        Newdata();
    }


}
