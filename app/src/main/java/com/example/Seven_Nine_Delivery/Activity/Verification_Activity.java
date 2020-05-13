package com.example.Seven_Nine_Delivery.Activity;


import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.view.Gravity;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.Seven_Nine_Delivery.Fragment.Verification_Fragment;
import com.example.Seven_Nine_Delivery.R;

import org.json.JSONException;
import org.json.JSONObject;


public class Verification_Activity extends AppCompatActivity {


    Fragment selectedFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firm);




        selectedFragment = Verification_Fragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        transaction.commit();
    }


}
