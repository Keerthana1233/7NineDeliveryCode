package com.example.sevennine_Delivery.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.BankAccount_Adapter;
import com.example.sevennine_Delivery.Adapter.DistrictAdapter;
import com.example.sevennine_Delivery.Adapter.StateApdater;
import com.example.sevennine_Delivery.Bean.StateBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class Add_NewBankDetails_Fragment extends Fragment {
    RecyclerView recycler_brand;
    LinearLayout back_feed,loan_lay,main_layout,continuebtn;
    TextView toolbar_title,next,verify_ifsc,apply_loan;
    Fragment selectedFragment;
    EditText bankname,bank_branchname,saving_ac_nu,confirm_saving_ac,enterifsc;
    Calendar myCalendar;
    public static TextView state,district,do_u_have_ifsc;
    public static EditText holder_name,search;
    public static DrawerLayout drawer;
    public static String search_status="status";
    public static String page;
    StateApdater stateApdater;
    DistrictAdapter districtAdapter;
    StateBean stateBean;
    JSONArray state_array,jsonArray;
    public static Dialog grade_dialog;
    RecyclerView recyclerView;
    SessionManager sessionManager;
    int ifscid;
    String account_validate,ifsc_validate,status;
    EditText account_no,ifsc_code;
    JSONObject lngObject;

    static List<StateBean> stateBeanList = new ArrayList<>();
    static List<StateBean> districtBeanList = new ArrayList<>();
    private List<StateBean> searchresultAraaylist = new ArrayList<>();

    public static Add_NewBankDetails_Fragment newInstance() {
        Add_NewBankDetails_Fragment fragment = new Add_NewBankDetails_Fragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_your_region_layout1, container, false);
        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Window window = getActivity().getWindow();
         window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        holder_name=view.findViewById(R.id.holder_name);
          state=view.findViewById(R.id.state_bank);
          district=view.findViewById(R.id.relativename);
          bankname=view.findViewById(R.id.age);
          bank_branchname=view.findViewById(R.id.castecategory);
          saving_ac_nu=view.findViewById(R.id.farmertype);
          confirm_saving_ac=view.findViewById(R.id.farmercategory);
          continuebtn=view.findViewById(R.id.continuebtn);
          recyclerView=view.findViewById(R.id.recycler_view);
          main_layout=view.findViewById(R.id.main_layout);
         search = view.findViewById(R.id.search);
        enterifsc = view.findViewById(R.id.enterifsc);
        back_feed=view.findViewById(R.id.back_feed);

        toolbar_title = view.findViewById(R.id.toolbar_title);
        apply_loan = view.findViewById(R.id.apply_loan);

        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);
        setupUI(main_layout);
        account_validate="[0-9]{9,18}";
        sessionManager=new SessionManager(getActivity());
        System.out.println("userrrridd"+sessionManager.getRegId("userId"));
      back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));

            toolbar_title.setText(lngObject.getString("AddNewBankDetails").replace("\n",""));
            apply_loan.setText(lngObject.getString("PROCEED").replace("\n",""));
      //      do_u_have_ifsc.setHint(lngObject.getString("DoyouhaveIFSC").replace("\n",""));
            enterifsc.setHint(lngObject.getString("EnterIFSC").replace("\n",""));
            state.setHint(lngObject.getString("State").replace("\n",""));
//            verify_ifsc.setText(lngObject.getString("EnterIFSC").replace("\n",""));
            district.setHint(lngObject.getString("District").replace("\n",""));
            bankname.setHint(lngObject.getString("BankName").replace("\n",""));
            bank_branchname.setHint(lngObject.getString("BankBranchName").replace("\n",""));
            saving_ac_nu.setHint(lngObject.getString("SavingsBankACNumber").replace("\n",""));
            confirm_saving_ac.setHint(lngObject.getString("ConfirmSavingsBankACNumber").replace("\n",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        System.out.println("bankacciddd"+ BankAccount_Adapter.bank_id_ed);

        if (BankAccount_Adapter.edit_addr!=null){
           /* BankAccount_Adapter.edit_addr=null;
            if (getArguments().getString("IFSCExist").equals("true")){
                do_u_have_ifsc.setText("Yes");
            }else{
                do_u_have_ifsc.setText("No");

            }*/
            saving_ac_nu.setText(getArguments().getString("AccountNumber"));
            state.setText(getArguments().getString("State"));
            district.setText(getArguments().getString("City"));
            bankname.setText(getArguments().getString("BankNmae"));
            bank_branchname.setText(getArguments().getString("Branch"));
            enterifsc.setText(getArguments().getString("IfscNumber"));
            /*if ((getArguments().getString("IfscNumber").equals("0"))){
                enterifsc.setVisibility(View.GONE);
                verify_ifsc.setVisibility(View.GONE);
                state.setVisibility(View.VISIBLE);
                district.setVisibility(View.VISIBLE);
                bankname.setVisibility(View.VISIBLE);
                bank_branchname.setVisibility(View.VISIBLE);

            }else{
                enterifsc.setVisibility(View.VISIBLE);
                verify_ifsc.setVisibility(View.VISIBLE);
                state.setVisibility(View.GONE);
                district.setVisibility(View.GONE);
               // bankname.setVisibility(View.GONE);
               // bank_branchname.setVisibility(View.GONE);

            }*/
        }/*else if (CropLoanDetailsForm.bank_st!=null){
            saving_ac_nu.setText(getArguments().getString("BankSavingsNo"));
            state.setText(getArguments().getString("BankState"));
            district.setText(getArguments().getString("BankDistrict"));
            bankname.setText(getArguments().getString("BankNameD"));
            bank_branchname.setText(getArguments().getString("BranchNameD"));
            enterifsc.setText(getArguments().getString("IFSCCODE"));
        }*/
       /* do_u_have_ifsc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), do_u_have_ifsc);
                popup.getMenu().add("Yes");
                popup.getMenu().add("No");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("teeexxtt"+item.getTitle());
                        do_u_have_ifsc.setText(item.getTitle());
                        if (do_u_have_ifsc.getText().toString().equals("Yes")){
                            ifscid=1;
                            state.setVisibility(View.INVISIBLE);
                            district.setVisibility(View.GONE);
                           // bankname.setVisibility(View.GONE);
                           // bank_branchname.setVisibility(View.GONE);
                            verify_ifsc.setVisibility(View.VISIBLE);
                            enterifsc.setVisibility(View.VISIBLE);
                        }else{
                            ifscid=0;
                            state.setVisibility(View.VISIBLE);
                            district.setVisibility(View.VISIBLE);
                            bankname.setVisibility(View.VISIBLE);
                            bank_branchname.setVisibility(View.VISIBLE);
                            verify_ifsc.setVisibility(View.GONE);
                            enterifsc.setVisibility(View.GONE);

                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });*/
       /* verify_ifsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Pattern.compile("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$").matcher(enterifsc.getText().toString()).matches())){
                    int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Valid IFSC Code.", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                }else{
                    verify_ifsc.setText("Verified");
                }
            }
        });*/
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("conti_entering..");
                if (holder_name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Enter Holder Nmae", Toast.LENGTH_SHORT).show();
                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Holder Nmae", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }else if (bankname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Enter bank name", Toast.LENGTH_SHORT).show();

          /*          int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter bank name", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }else if (bank_branchname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter bank branch name", Toast.LENGTH_SHORT).show();
                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter bank branch name", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                } else if (enterifsc.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter IFSC and verify", Toast.LENGTH_SHORT).show();
                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter IFSC and verify", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/
                }
                else if (!(Pattern.compile("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$").matcher(enterifsc.getText().toString()).matches())){
                    Toast.makeText(getActivity(),  "Enter Valid IFSC Code.", Toast.LENGTH_SHORT).show();
                    /*int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Valid IFSC Code.", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/
                }else if (state.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),  "Select State", Toast.LENGTH_SHORT).show();
                /*    int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Select State", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }else if (district.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Select District", Toast.LENGTH_SHORT).show();
                    /*int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Select District", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }
                else if (saving_ac_nu.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter savings bank A/C number", Toast.LENGTH_SHORT).show();
                    /*int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter savings bank A/C number", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                } else if (!(saving_ac_nu.getText().toString().matches(account_validate))) {
                    Toast.makeText(getActivity(), "Enter Valid Account Number.", Toast.LENGTH_SHORT).show();
                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Valid Account Number.", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/
                } else if (confirm_saving_ac.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter confirm savings bank A/C number", Toast.LENGTH_SHORT).show();

                    /*int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter confirm savings bank A/C number", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }else if (!(confirm_saving_ac.getText().toString().equals(saving_ac_nu.getText().toString()))){
                    Toast.makeText(getActivity(), "Savings A/C number is not matching", Toast.LENGTH_SHORT).show();

                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Savings A/C number is not matching", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/

                }else{
                    AddBankDetails();
                }


            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search_status="state";
                page="Bank_Add";
                search.setText("");


                stateBeanList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                /*StateBean bean=new StateBean("Karnataka","1");
                stateBeanList.add(bean);
                stateBeanList.add(bean);
                stateBeanList.add(bean);
                stateBeanList.add(bean);
                stateBeanList.add(bean);
                stateBeanList.add(bean);
                stateBeanList.add(bean);*/



                prepareStateData();


            }
        });

        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(GravityCompat.END);

                search_status="district";
                search.setText("");
                districtBeanList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                   /* StateBean bean=new StateBean("Karnataka","1");
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
                   districtBeanList.add(bean);
*/
                districtAdapter= new DistrictAdapter( districtBeanList,getActivity());
                recyclerView.setAdapter(districtAdapter);
                prepareDistricData();

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    return true;
                }

                return false;
            }
        });

        bankname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(bankname,saving_ac_nu,enterifsc,bank_branchname,confirm_saving_ac,district,state,do_u_have_ifsc);
                return false;
            }
        });

        saving_ac_nu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(saving_ac_nu,bankname,enterifsc,bank_branchname,confirm_saving_ac,district,state,do_u_have_ifsc);
                return false;
            }
        });

        bank_branchname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(bank_branchname,saving_ac_nu,enterifsc,bankname,confirm_saving_ac,district,state,do_u_have_ifsc);
                return false;
            }
        });
        enterifsc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(enterifsc,saving_ac_nu,bank_branchname,bankname,confirm_saving_ac,district,state,do_u_have_ifsc);
                return false;
            }
        });
        confirm_saving_ac.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(confirm_saving_ac,enterifsc,saving_ac_nu,bank_branchname,bankname,district,state,do_u_have_ifsc);
                return false;
            }
        });
       /* do_u_have_ifsc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection1(do_u_have_ifsc,saving_ac_nu,enterifsc,bank_branchname,bankname,district,state,confirm_saving_ac);
                return false;
            }
        });*/
        state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection1(state,saving_ac_nu,bank_branchname,enterifsc,bankname,district,do_u_have_ifsc,confirm_saving_ac);
                return false;
            }
        });
        district.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection1(district,saving_ac_nu,bank_branchname,enterifsc,bankname,do_u_have_ifsc,state,confirm_saving_ac);
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        return view;
    }
  public void linear_layout_selection(EditText selectdl1, EditText l10, EditText l2, EditText l3, EditText l4, TextView l5 , TextView l6 , TextView l7){
      selectdl1.setBackgroundResource(R.drawable.border_green_empty);
      l2.setBackgroundResource(R.drawable.border_grey);
      l3.setBackgroundResource(R.drawable.border_grey);
      l4.setBackgroundResource(R.drawable.border_grey);
      l5.setBackgroundResource(R.drawable.border_grey);
      l6.setBackgroundResource(R.drawable.border_grey);
//      l7.setBackgroundResource(R.drawable.border_grey);
      l10.setBackgroundResource(R.drawable.border_grey);

  }
    public void linear_layout_selection1(TextView selectdl1, EditText l10, EditText l2, EditText l3, EditText l4, TextView l5 , TextView l6 , EditText l7){
        selectdl1.setBackgroundResource(R.drawable.border_green_empty);
        l2.setBackgroundResource(R.drawable.border_grey);
        l3.setBackgroundResource(R.drawable.border_grey);
        l4.setBackgroundResource(R.drawable.border_grey);
//        l5.setBackgroundResource(R.drawable.border_grey);
    //    l6.setBackgroundResource(R.drawable.border_grey);
        l7.setBackgroundResource(R.drawable.border_grey);
        l10.setBackgroundResource(R.drawable.border_grey);

    }
    private void prepareStateData() {
        stateBeanList.clear();
        AddNewAddressPreview.page=null;


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("CountryId","91");
            post_jsonobject.put("Stateobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.State, post_jsonobject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("11111ssss" + result);


                    try{
                        stateBeanList.clear();
                        state_array = result.getJSONArray("StateList");
                        for(int i =0;i<state_array.length();i++){
                            JSONObject jsonObject1 = state_array.getJSONObject(i);

                            stateBean = new StateBean(jsonObject1.getString("State"),jsonObject1.getInt("StateId"));
                            stateBeanList.add(stateBean);
                        }
                        sorting(stateBeanList);
                        stateApdater = new StateApdater(stateBeanList,getActivity());

                        recyclerView.setAdapter(stateApdater);
                        stateApdater.notifyDataSetChanged();
                        //grade_dialog.show();



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void prepareDistricData() {

        districtBeanList.clear();


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("StateId",stateApdater.stateid);
            post_jsonobject.put("Districtobj",jsonObject);

            System.out.println("podistriiiii"+post_jsonobject);

            Crop_Post.crop_posting(getActivity(), Urls.Districts, post_jsonobject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dddddddddddd11111" + result);
                    try{
                        districtBeanList.clear();
                        jsonArray = result.getJSONArray("DistrictList");
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            stateBean = new StateBean(jsonObject1.getString("District"),jsonObject1.getInt("DistrictId"));
                            districtBeanList.add(stateBean);
                        }

                        sorting(districtBeanList);


                        districtAdapter.notifyDataSetChanged();
                        grade_dialog.show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sorting(List<StateBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question

                return ((StateBean)state_name1).getName()
                        .compareTo(((StateBean)state_name2).getName());
            }
        });
    }


    public  void sorting(String filter_text) {

        final String text = filter_text.toLowerCase();


        if (search_status.equals("state")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < stateBeanList.size(); i++) {

                if (stateBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(stateBeanList.get(i));

                }
            }
            stateApdater = new StateApdater(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(stateApdater);
        } else if (search_status.equals("district")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < districtBeanList.size(); i++) {

                if (districtBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(districtBeanList.get(i));

                }
            }
            districtAdapter = new DistrictAdapter(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(districtAdapter);

        }
    }

    private void AddBankDetails() {
        try {

            JSONObject jsonObject = new JSONObject();

            if (BankAccount_Adapter.bank_id_ed!=null){
                System.out.println("bankkkkiiidd"+BankAccount_Adapter.bank_id_ed);

                jsonObject.put("BankDetailsId", BankAccount_Adapter.bank_id_ed);
            }/*else if (CropLoanDetailsForm.bank_st!=null){
                jsonObject.put("BankDetailsId", getArguments().getString("BankDetailsId"));

            }*/
            jsonObject.put("UserId", sessionManager.getRegId("userId"));
           // jsonObject.put("UserId", "1");
            jsonObject.put("BankName", bankname.getText().toString());
            jsonObject.put("SavingsBankAccountNumber", saving_ac_nu.getText().toString());
            jsonObject.put("IsIFSCCodeExist","1");
            jsonObject.put("BankBranchName", bank_branchname.getText().toString());
            jsonObject.put("CreatedBy", sessionManager.getRegId("userId"));
            System.out.println("stateiddddd"+StateApdater.stateid);
            jsonObject.put("IFSCCode", enterifsc.getText().toString());
            if (StateApdater.stateid!=0){
                jsonObject.put("StateId", StateApdater.stateid);
                jsonObject.put("DistrictId", DistrictAdapter.districtid);
            }else {
                jsonObject.put("StateId", getArguments().getString("StateId"));
                jsonObject.put("DistrictId", getArguments().getString("DistrictId"));
               // jsonObject.put("IFSCCode", enterifsc.getText().toString());
                //jsonObject.put("BankDetailsId", BankAccount_Adapter.bank_id_ed);
            }
            System.out.println("poooooossttiing_parameters"+jsonObject);
            Login_post.login_posting(getActivity(), Urls.AddBankAccounts, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("111111useruuuu" + result);
                    try {
                        status = result.getString("Status");

                        if (!(status.equals("0"))) {
                            BankAccount_Adapter.bank_id_ed=null;
                            /*selectedFragment = BankDetails.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.addToBackStack("bank_ddddee");
                            //setArguments(bundle);
                            transaction.commit();*//*
                            if (CropLoanDetailsForm.bank_st!=null){
                                selectedFragment = CropLoanDetailsForm.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                               // transaction.addToBackStack("bank_frag");
                                transaction.commit();
                            }else {*/
                                selectedFragment = BankAccount_Fragment.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.addToBackStack("bank_frag");
                                transaction.commit();
                           // }
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
