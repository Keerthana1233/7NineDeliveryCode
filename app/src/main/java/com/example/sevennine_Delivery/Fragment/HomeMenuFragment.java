
package com.example.sevennine_Delivery.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.sevennine_Delivery.Activity.GPSTracker;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class HomeMenuFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
Fragment selectedFragment;
    public static DrawerLayout drawer;
    private ViewPager viewPager;
    SessionManager sessionManager;
    String userid;
    ImageView imageView;
    LinearLayout notification;
    public static ImageView menuimg,notificationimg;
    public static TextView toolbartxt;
    boolean doubleBackToExitPressedOnce = false;
    public static LinearLayout menu,tl;
    TextView home,yourorders,mypayments,rewards,bankaccounts,dutyonoff,myaccounts,ratecard,wallet,sell_on_xohri,help_center,settings,legal;
    public static TextView cart_count_text,user_name_menu;
GPSTracker gpsTracker;
    public static TabLayout tabLayout;
    public static HomeMenuFragment newInstance() {
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
        toolbartxt.setText("7Nine Delhivery");
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
        sessionManager = new SessionManager(getActivity());
        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);

            selectedFragment = OrdersFragments.newInstance();
            FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
            transaction7.replace(R.id.frame_layout, selectedFragment);
            transaction7.commit();
            drawer.closeDrawers();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = NotificationFragment.newInstance();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("nothome");
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
                        FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction7.replace(R.id.frame_layout, selectedFragment);
                        transaction7.commit();
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
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                ratecard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = RateCardFragment.newInstance();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                myaccounts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = AaSettingFragment.newInstance();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();

                    }
                });


            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (doubleBackToExitPressedOnce) {

                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent1);
                        getActivity().finish();                   }
                    // System.exit(0);

                    // home_img.setImageResource(R.drawable.ic_home_green);

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(getActivity(),"Please click back again to exit", Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 3000);

                }

                return true;
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

