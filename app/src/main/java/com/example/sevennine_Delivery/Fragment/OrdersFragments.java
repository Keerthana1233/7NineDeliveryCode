package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragments extends Fragment implements TabLayout.OnTabSelectedListener{
    private ViewPager viewPager;
    public static TabLayout tabLayout;
    Fragment selectedFragment=null;
    private boolean isBackPressed = false;
    private boolean isBackPressedOnce = false;
    private List<Integer> tabsInBack = new ArrayList<>();
    SessionManager sessionManager;
    private static final int HANDLER_DELAY = 1000;
    protected OnBackPressedListner onBackPress;
    public static OrdersFragments newInstance() {
        OrdersFragments fragment = new OrdersFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        tabLayout = view. findViewById(R.id.simpleTabLayout1);
        HomeMenuFragment.toolbartxt.setText("7Nine Delhivery");
        sessionManager = new SessionManager(getActivity());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCode1234567890-=: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                   //  getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        tabLayout.addTab(tabLayout.newTab().setText("  New"));
        tabLayout.addTab(tabLayout.newTab().setText("  Accepted"));
        tabLayout.addTab(tabLayout.newTab().setText("  Delivered"));
        tabLayout.addTab(tabLayout.newTab().setText("  Cancelled"));

      //  tabLayout.setOnTabSelectedListener(this);

      //  tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.simpleViewPager);
        //Creating our pager adapter
         Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(!isBackPressed){
                    tabsInBack.add(tab.getPosition());}
                else {
                    isBackPressed = false;}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
    public void onBackPressed() {
        isBackPressed = true;
        if (tabsInBack != null && tabsInBack.size() > 0) {
            if (tabLayout.getSelectedTabPosition() == tabsInBack.get(tabsInBack.size() - 1)) {
                tabsInBack.remove(tabsInBack.size() - 1);

            }
            if (tabsInBack != null && tabsInBack.size() > 0) {
                tabLayout.getTabAt(tabsInBack.get(tabsInBack.size() - 1)).select();
                tabsInBack.remove(tabsInBack.size() - 1);
                if (isBackPressedOnce) {
                    super.getActivity().onBackPressed();
                } else {
                    isBackPressedOnce = true;
                }
            } else {
                super.getActivity().onBackPressed();
            }
        } else {
            super.getActivity().onBackPressed();
        }
    }
    public interface OnBackPressedListner{
        boolean onBackPressed();
    }
@Override
    public void onTabSelected(TabLayout.Tab tab)  {
        viewPager.setCurrentItem(tab.getPosition());
    if (viewPager.getCurrentItem() == 0) {

        super.getActivity().onBackPressed();
    }else {

        //If any other tab is open, then switch to first tab
        viewPager.setCurrentItem(0);
    }

    if(!isBackPressed){
        tabsInBack.add(tab.getPosition());}
    else {
        isBackPressed = false;}
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

}
