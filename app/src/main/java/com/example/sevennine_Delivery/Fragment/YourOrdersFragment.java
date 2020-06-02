package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
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

import com.example.sevennine_Delivery.Adapter.YourOrderAdapter;
import com.example.sevennine_Delivery.Bean.YourOrderBean;
import com.example.sevennine_Delivery.R;

import java.util.ArrayList;
import java.util.List;

//pushed totol code today
public class YourOrdersFragment extends Fragment {

    TextView webView,textView;
    RecyclerView recyclerView;
    private YourOrderAdapter madapter;
    TextView filter;
    Fragment selectedFragment;
    public static List<YourOrderBean> newOrderBeansList = new ArrayList<>();

    public static YourOrdersFragment newInstance() {
        YourOrdersFragment fragment = new YourOrdersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yourorders, container, false);
        filter=view.findViewById(R.id.filter);
        HomeMenuFragment.toolbartxt.setText("Your Orders");
        recyclerView=view.findViewById(R.id.new_order_recy);
           view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction7.replace(R.id.frame_layout1, selectedFragment);
                    transaction7.commit();
                    return true;
                }
                return false;
            }
        });
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        YourOrderBean bean=new YourOrderBean("20-Mar-2020, 12:53","Rs.100","#10989876");
        newOrderBeansList.add(bean);
      newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        madapter=new YourOrderAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = FilterFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout, selectedFragment);
                ft.commit();
            }
        });
        return view;
    }

}
