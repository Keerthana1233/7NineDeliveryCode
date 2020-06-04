
package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class HomeMenuFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
Fragment selectedFragment;
    public static DrawerLayout drawer;
   public static ImageView menuimg,notificationimg;
   public static LinearLayout menu,tl;
    SessionManager sessionManager;
    String userid;
    ImageView imageView;
    LinearLayout notification;
    public static TextView toolbartxt;
    TextView home,yourorders,mypayments,rewards,bankaccounts,dutyonoff,myaccounts,ratecard,wallet,sell_on_xohri,help_center,settings,legal;
    public static TextView cart_count_text,user_name_menu;
static boolean fragloaded;


    public static HomeMenuFragment newInstance() {
        fragloaded =true;
        HomeMenuFragment fragment = new HomeMenuFragment();
        return fragment;
    }

    public static HomeMenuFragment firstLoaded(Fragment abc) {

        HomeMenuFragment fragment = new HomeMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_navigation_menu_home, container, false);
        imageView=view.findViewById(R.id.imageview);
        tl=view.findViewById(R.id.linearlayouttoolbar);
        menu=view.findViewById(R.id.menu);
        menuimg=view.findViewById(R.id.menuimg);
        toolbartxt=view.findViewById(R.id.toolbartxt);
        notificationimg=view.findViewById(R.id.notificationimg);
        home=view.findViewById(R.id.home);
        yourorders=view.findViewById(R.id.shop_cat);
        mypayments=view.findViewById(R.id.todaydeal);
        rewards=view.findViewById(R.id.my_orders);
        bankaccounts=view.findViewById(R.id.wish_list);
        dutyonoff=view.findViewById(R.id.wallet);
        myaccounts=view.findViewById(R.id.lang);
        ratecard=view.findViewById(R.id.sell_on_xohri);
     notification=view.findViewById(R.id.notification);

        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        selectedFragment = OrdersFragments.newInstance();
        FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
        transaction7.replace(R.id.frame_layout, selectedFragment);
        transaction7.commit();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                drawer.closeDrawers();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
             home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      selectedFragment = OrdersFragments.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                yourorders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       selectedFragment = YourOrdersFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                mypayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = PaymentsTabLayout.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                rewards.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                bankaccounts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                dutyonoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = Dutyonofffragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                ratecard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = RateCardFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                myaccounts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment =AaSettingFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });


            }
        });
        return view;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

}

