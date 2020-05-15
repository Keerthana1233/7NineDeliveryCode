package com.example.sevennine_Delivery.Activity;



import android.os.Bundle;

import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.Fragment.FirmShopDetailsFragment;
import com.example.sevennine_Delivery.R;


public class FirmShopDetailsActivity extends AppCompatActivity {

    Fragment selectedFragment = null;
   public static LinearLayout bottom_linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firm);



        selectedFragment = FirmShopDetailsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        transaction.commit();

    }


}
