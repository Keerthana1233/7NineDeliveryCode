package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Activity.ActivitySelectLang;
import com.example.sevennine_Delivery.Adapter.AdapterSelectLanguage;
import com.example.sevennine_Delivery.Adapter.SelectLanguageAdapter;
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

public class ChangeLanguageFragment extends Fragment {
    private List<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SelectLanguageAdapter mAdapter;
   public static TextView continue_lang,lang_title;
    LinearLayout back_feed;
    JSONObject lngObject;
    String langimg;
SessionManager sessionManager;
    public static ChangeLanguageFragment newInstance() {
        ChangeLanguageFragment fragment = new ChangeLanguageFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_language_layout, container, false);
        recyclerView =view.findViewById(R.id.recycler_view_lang);
        continue_lang =view.findViewById(R.id.continue_lang);
        //lang_title =view.findViewById(R.id.lang_title);

        sessionManager=new SessionManager(getActivity());
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Langauges();
        newOrderBeansList.clear();
        mAdapter = new SelectLanguageAdapter(getActivity(), newOrderBeansList);
        recyclerView.setAdapter(mAdapter);
       /* SelectLanguageBean bean=new SelectLanguageBean("English",1,"");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);*/
        Window window = getActivity().getWindow();
        back_feed=view.findViewById(R.id.back_feed);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        Langauges();
        continue_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("change_lang", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("change_lang", FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });
        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));

            //lang_title.setText(lngObject.getString("MyLanguage").replace("\n",""));
            continue_lang.setText(lngObject.getString("PROCEED").replace("\n",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    private void Langauges() {
        try {

            JSONObject postjsonObject = new JSONObject();

            Login_post.login_posting(getActivity(), Urls.Languages,postjsonObject,new VoleyJsonObjectCallback() {
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
                        mAdapter = new SelectLanguageAdapter(getActivity(), newOrderBeansList);
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
}

