package com.example.sevennine_Delivery.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.Fragment.HomeMenuFragment;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean connectivity_check;
    private LinearLayout coordinatorLayout;
    private static final String TAG = "Something";
    Fragment selectedFragment = null;
     static SessionManager session;
    public static String sSelected="Home";


public static String CurrentLocationFullAddress="";
    public  static Activity activity;
    Menu menu;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout1);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    @SuppressLint("MissingPermission")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity= this;
      coordinatorLayout = (LinearLayout) findViewById(R.id
                .coordinatorLayout);
        session = new SessionManager(getApplicationContext());
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        selectedFragment = HomeMenuFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        transaction.commit();

     /*   String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
*/


    }



    @Override
    public void onBackPressed() {
        selectedFragment = HomeMenuFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        transaction.commit();
    }



        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            return false;
        }


}