package com.example.sevennine_Delivery.Activity;


import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.view.Gravity;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.Fragment.Verification_Fragment;
import com.example.sevennine_Delivery.R;

import org.json.JSONException;
import org.json.JSONObject;


public class Verification_Activity extends AppCompatActivity {


    Fragment selectedFragment = null;

    public static boolean connectivity_check;
    String toast_internet,toast_nointernet;
    public static JSONObject lngObject;







    private void showSnack(boolean isConnected) {
        String message = null;
        int color=0;
        if (isConnected) {
            if(connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;

                Toast toast = Toast.makeText(Verification_Activity.this,toast_internet, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                toast.show();

//                int duration=1000;
//                Snackbar snackbar = Snackbar.make(linear_layout,toast_internet, duration);
//                View sbView = snackbar.getView();
//                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setBackgroundColor(ContextCompat.getColor(New_Login_Activity2.this,R.color.orange));
//                textView.setTextColor(Color.WHITE);
//                snackbar.show();


                connectivity_check=false;
            }


        } else {
            message = "No Internet Connection";
            color = Color.RED;

            int duration=1000;
            connectivity_check=true;

            Toast toast = Toast.makeText(Verification_Activity.this,toast_nointernet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
            toast.show();


//            Snackbar.make(findViewById(android.R.id.content),toast_nointernet, duration).show();


        }
    }



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
