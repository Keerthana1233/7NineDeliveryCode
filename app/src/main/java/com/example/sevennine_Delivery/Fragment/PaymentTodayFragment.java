package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.TodayPaymentAdapter;
import com.example.sevennine_Delivery.Bean.TodayPaymentBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PaymentTodayFragment extends Fragment {

    public static List<TodayPaymentBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    TodayPaymentAdapter madapter;
    JSONObject lngObject;
    TextView toolbar_title;

    public static PaymentTodayFragment newInstance() {
        PaymentTodayFragment fragment = new PaymentTodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_order_recy, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);


        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));


        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TodayPaymentBean bean=new TodayPaymentBean("ABC XYZ","404-8614299-2321138","","HDFC Bank","09-Mar-2020","9898670456");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);


        madapter=new TodayPaymentAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);


    //    LoanInformation();


        return view;
    }


}
