package com.example.sevennine_Delivery.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Activity.Status_bar_change_singleton;
import com.example.sevennine_Delivery.Adapter.BankAccount_Adapter;
import com.example.sevennine_Delivery.Bean.BankBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BankAccount_Fragment extends Fragment {

    public static ArrayList<BankBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static BankAccount_Adapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title,apply_loan;
    public static String bank_details;
    LinearLayout back_feed,linearLayout,Continue,main_layout;
    SessionManager sessionManager;
    JSONArray get_categorylist_array;
    JSONObject lngObject;

    public static BankAccount_Fragment newInstance() {
        BankAccount_Fragment fragment = new BankAccount_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.soil_details_recy_layout, container, false);
      Status_bar_change_singleton.getInstance().color_change(getActivity());

        //HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
        recyclerView=view.findViewById(R.id.recycler_what_looking);
      toolbar_title=view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Bank Accounts");
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.linearLayout);
        Continue = view.findViewById(R.id.continuebtn);
        main_layout = view.findViewById(R.id.main_layout);
        apply_loan = view.findViewById(R.id.apply_loan);

        sessionManager=new SessionManager(getActivity());
        setupUI(main_layout);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           selectedFragment = Add_NewBankDetails_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("bankaccount");
                transaction.commit();

            }
        });
     back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("bbbbb", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("bbbbb", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                return false;
            }
        });

        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));

            toolbar_title.setText(lngObject.getString("BankAccounts").replace("\n",""));
            apply_loan.setText(lngObject.getString("ADDNEWBANKDETAILS").replace("\n",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bankList();
      /*  BankBean img1=new BankBean("State Bank of India","Jagdish Kumar","30253918938","SBI0009876","Raja Rajeshwari Nagar","Bangaluru","");
        newOrderBeansList.add(img1);

        BankBean img2=new BankBean("Bank of Baroda","Jagdish Kumar","5325391836","BOB0000987","Raja Rajeshwari Nagar","Bangaluru","");
        newOrderBeansList.add(img2);
*/


/*
        livestock_types_adapter=new BankAccount_Adapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(livestock_types_adapter);*/


        return view;
    }

    private void bankList() {
        //newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
         //   userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetBankList, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss111555555" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {
                        jsonArray = result.getJSONArray("BankDetails");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String BankName=jsonObject1.getString("BankName");
                            String SavingsBankAccountNumber=jsonObject1.getString("SavingsBankAccountNumber");
                            String BankBranchName=jsonObject1.getString("BankBranchName");
                            String State=jsonObject1.getString("State");
                            String StateId=jsonObject1.getString("StateId");
                            String DistrictId=jsonObject1.getString("DistrictId");
                            String BankDetailsId=jsonObject1.getString("BankDetailsId");
                            String District=jsonObject1.getString("District");
                            String IsIFSCCodeExist=jsonObject1.getString("IsIFSCCodeExist");
                            String IFSCCode=jsonObject1.getString("IFSCCode");

                            BankBean img1=new BankBean(BankName,"Jagdish Kumar",SavingsBankAccountNumber,IFSCCode,BankBranchName,District,State,BankDetailsId,IsIFSCCodeExist,StateId,DistrictId);
                            newOrderBeansList.add(img1);
                            System.out.println("uiuuuuuussseeettttiiinnnngg111"+newOrderBeansList.size());

                            if (newOrderBeansList.size()==0){
                                bank_details="No_bank_details";
                                selectedFragment = EmptyFragment.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.no_item_frame1, selectedFragment);
                                transaction.addToBackStack("emmpty");
                                transaction.commit();
                            }else {
                                livestock_types_adapter = new BankAccount_Adapter(getActivity(), newOrderBeansList);
                                recyclerView.setAdapter(livestock_types_adapter);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setupUI(View view) {


        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }


        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try{
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }catch(AssertionError e){
                e.printStackTrace();
            }
        }
    }
}
