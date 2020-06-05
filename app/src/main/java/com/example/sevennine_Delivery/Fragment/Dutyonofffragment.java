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

import com.example.sevennine_Delivery.R;


public class Dutyonofffragment extends Fragment {

    TextView webView;

    Fragment selectedFragment;

    public static Dutyonofffragment newInstance() {
        Dutyonofffragment fragment = new Dutyonofffragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dutyonoff, container, false);
        HomeMenuFragment.menuimg.setImageResource(R.drawable.ic_go_back_left_arrow_);
      HomeMenuFragment.toolbartxt.setText("Duty On/OFF");
       HomeMenuFragment.notificationimg.setVisibility(View.GONE);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        HomeMenuFragment.menuimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction7.replace(R.id.frame_layout1, selectedFragment);
                transaction7.commit();

            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction7.replace(R.id.frame_layout1, selectedFragment);
                    transaction7.commit();


                    return true;
                }
                return false;

            }
        });

        return view;
    }
}
