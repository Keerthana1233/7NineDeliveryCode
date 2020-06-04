package com.example.sevennine_Delivery.Fragment;

import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.BankDetailAdapter;
import com.example.sevennine_Delivery.Bean.RepaymentBean;
import com.example.sevennine_Delivery.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankDetails extends Fragment {

    public static List<RepaymentBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static BankDetailAdapter farmadapter;
    TextView toolbar_title;
    LinearLayout back_feed;
    Fragment selectedFragment;
    JSONObject lngObject;

    public static BankDetails newInstance() {
        BankDetails fragment = new BankDetails();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_searches_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_searches);
        back_feed=view.findViewById(R.id.back_feed);
        toolbar_title=view.findViewById(R.id.toolbar_title);

        toolbar_title.setText("Bank Details");
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("bank_detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();

            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                        /*FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack ("bank_detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();

                    return true;
                }
                return false;
            }
        });



        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


       /* newOrderBeansList.add(img6);
        newOrderBeansList.add(img6);*/
        RepaymentBean bean=new RepaymentBean("Jagdish Kumar","Bank of Baroda","85480012112","BOB00987120","3,000","");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);

        farmadapter=new BankDetailAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);


        return view;
    }



}
