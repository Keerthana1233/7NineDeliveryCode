package com.example.sevennine_Delivery.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sevennine_Delivery.Adapter.AdapterSelectLanguage;
import com.example.sevennine_Delivery.Bean.SelectLanguageBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectLang extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener  {
    private List<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterSelectLanguage mAdapter;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout linearLayout;
    public  static  TextView continue_lng,select_ur_language;
    SessionManager sessionManager;
    public static   JSONObject lngObject;

    public static String toast_internet,toast_nointernet;

    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("loginonStart");
         //sessionManager.checkLogin();
        sessionManager.checkRegister();

    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }
    ////
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private void showSnack(boolean isConnected) {
        String message = null;
        int color=0;
        if (isConnected) {
            if(connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;

                Toast toast = Toast.makeText(ActivitySelectLang.this,toast_internet, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                toast.show();


                connectivity_check=false;
            }

        } else {
            message = "No Internet Connection";
            color = Color.RED;

            int duration=1000;
            connectivity_check=true;

            Toast toast = Toast.makeText(ActivitySelectLang.this,toast_nointernet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
            toast.show();


        }
    }


    @Override
    public void onResume() {
        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        //MyApplication.getInstance().setConnectivityListener(this);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_a_selectlang);
        //  checkConnection();
        recyclerView =findViewById(R.id.recycler_view_lang);
        linearLayout= findViewById(R.id.main_layout);
        continue_lng= findViewById(R.id.continue_lang);
        select_ur_language= findViewById(R.id.selct_ur_lng);


        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(ActivitySelectLang.this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       /* SelectLanguageBean stateBean=new SelectLanguageBean("English",1,"E");
        newOrderBeansList.add(stateBean);
        SelectLanguageBean stateBean1=new SelectLanguageBean("Kannada",2,"K");
        newOrderBeansList.add(stateBean1);
        SelectLanguageBean stateBean2=new SelectLanguageBean("Hindi",3,"H");
        newOrderBeansList.add(stateBean2);*/
       Langauges();
        mAdapter = new AdapterSelectLanguage(ActivitySelectLang.this, newOrderBeansList);
        recyclerView.setAdapter(mAdapter);


        sessionManager = new SessionManager(this); //check

        continue_lng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivitySelectLang.this, SelectStoreLocation.class);
                startActivity(intent);

            }
        });

       /* try {
            if ((sessionManager.getRegId("language")).equals("")) {
                getLang(1);

            } else {

                lngObject = new JSONObject(sessionManager.getRegId("language"));

                select_ur_language.setText(lngObject.getString("SelectYourLanguage").replace("\n",""));
                continue_lng.setText(lngObject.getString("PROCEED").replace("\n",""));
               toast_nointernet =(lngObject.getString("NoInternetConnection"));
               toast_internet =(lngObject.getString("GoodConnectedtoInternet"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }


    private void getLang(int id) {

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id",id);


            System.out.print("iiidddddd"+ id);

           /* Crop_Post.crop_posting(ActivitySelectLang.this, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try{
                        sessionManager.saveLanguage(result.toString());
                        String lang_title1 = result.getString("PROCEED").replace("\n","");
                        String select = result.getString("SelectYourLanguage").replace("\n","");
                        toast_nointernet =(lngObject.getString("NoInternetConnection"));
                        toast_internet =(lngObject.getString("GoodConnectedtoInternet"));


                        continue_lng.setText(lang_title1);
                        select_ur_language.setText(select);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Langauges() {
        try {

            JSONObject postjsonObject = new JSONObject();

           Login_post.login_posting(ActivitySelectLang.this, Urls.Languages,postjsonObject,new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss"+result);
                    JSONObject jsonObject;
                    try {
                        JSONArray jsonArray=result.getJSONArray("LanguagesList");
                        newOrderBeansList.clear();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String language=jsonObject1.getString("Language");
                            int langCode=jsonObject1.getInt("Id");
                            String langimg=jsonObject1.getString("ImageIcon");
                            SelectLanguageBean stateBean=new SelectLanguageBean(jsonObject1.getString("Language"),jsonObject1.getInt("Id"),jsonObject1.getString("ImageIcon"));
                            newOrderBeansList.add(stateBean);
                        }
                        mAdapter = new AdapterSelectLanguage(ActivitySelectLang.this, newOrderBeansList);
                        recyclerView.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {



        if (doubleBackToExitPressedOnce) {

            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.addCategory(Intent.CATEGORY_HOME);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent1);
            finish();                   }


        doubleBackToExitPressedOnce = true;

      /*  int duration=1000;
        Snackbar snackbar = Snackbar
                .make(linearLayout,"Please click Back again to Exit", duration);
        View snackbarView = snackbar.getView();
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setBackgroundColor(ContextCompat.getColor(ActivitySelectLang.this,R.color.orange));
        tv.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        snackbar.show();
*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
