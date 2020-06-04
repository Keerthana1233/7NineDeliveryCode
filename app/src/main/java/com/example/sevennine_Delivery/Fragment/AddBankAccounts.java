package com.example.sevennine_Delivery.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import com.example.sevennine_Delivery.Adapter.BankAdapter;
import com.example.sevennine_Delivery.Bean.StateBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class AddBankAccounts extends Fragment {
    RecyclerView recycler_brand;
    LinearLayout back_feed,loan_lay,main_layout;
    TextView dob,next,toolbar_title;
    Fragment selectedFragment;
    EditText search,emi_paid;
    BankAdapter bank_adapter;
    public static String status;
    public static TextView state_txt;
    public static DrawerLayout drawer;
    static List<StateBean> stateBeanList = new ArrayList<>();
    RecyclerView recyclerView;
    View view_dob;
    SessionManager sessionManager;
    EditText account_no,ifsc_code,acc_holder_name;
    String account_validate,ifsc_validate;

    public static AddBankAccounts newInstance() {
        AddBankAccounts fragment = new AddBankAccounts();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_loan_acc_drawer, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        next=view.findViewById(R.id.apply_loan);
        loan_lay=view.findViewById(R.id.loan_lay);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);
        search = view.findViewById(R.id.search);
        state_txt = view.findViewById(R.id.state_txt);
        recyclerView = view.findViewById(R.id.recycler_view);
        emi_paid = view.findViewById(R.id.emi_paid);
        dob = view.findViewById(R.id.dob);
        view_dob = view.findViewById(R.id.view_dob);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        account_no = view.findViewById(R.id.acc_no);
        ifsc_code = view.findViewById(R.id.ifsc_code);
        acc_holder_name = view.findViewById(R.id.acc_holder_name);
        main_layout = view.findViewById(R.id.main_layout);

        account_validate="[0-9]{9,18}";
        ifsc_validate="^[A-Za-z]{4}0[A-Z0-9a-z]{6}$";
        toolbar_title.setText("Aadd Bank Account");
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));

        emi_paid.setVisibility(View.GONE);
        dob.setVisibility(View.GONE);
        view_dob.setVisibility(View.GONE);
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        sessionManager=new SessionManager(getActivity());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account_no.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Account Number.", Toast.LENGTH_SHORT).show();
                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Account Number.", duration);
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
                } else if (!(account_no.getText().toString().matches(account_validate))) {
                    Toast.makeText(getActivity(), "Enter Valid Account Number.", Toast.LENGTH_SHORT).show();
/*
                    int duration = 1000;
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
                } else if (ifsc_code.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter IFSC Code..", Toast.LENGTH_SHORT).show();

                    /*int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter IFSC Code.", duration);
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
                } else if (!(Pattern.compile("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$").matcher(ifsc_code.getText().toString()).matches())) {
                    Toast.makeText(getActivity(), "Enter Valid IFSC Code..", Toast.LENGTH_SHORT).show();

                 /*   int duration = 1000;
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
                } else if (acc_holder_name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Account holder name.", Toast.LENGTH_SHORT).show();

                   /* int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter Account holder name.", duration);
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
                } else {
                   /* selectedFragment = BankDetails.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("bank_detail");
                    transaction.commit();*/

                    try {

                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("Id", sessionManager.getRegId("userId"));
                        jsonObject.put("AccountHolderName", acc_holder_name.getText().toString());
                        jsonObject.put("BankName", state_txt.getText().toString());
                        jsonObject.put("AccountNumber", account_no.getText().toString());
                        jsonObject.put("IFSCCode", ifsc_code.getText().toString());
                        jsonObject.put("CreatedBy", sessionManager.getRegId("userId"));

                        System.out.println("poooooossttiing_parameters"+jsonObject);
                        Login_post.login_posting(getActivity(), Urls.AddBankAccounts, jsonObject, new VoleyJsonObjectCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject result) {
                                System.out.println("111111user" + result);
                                try {
                                    status = result.getString("Status");

                                    if (!(status.equals("0"))){
                                        selectedFragment = BankDetails.newInstance();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame_layout, selectedFragment);
                                        transaction.addToBackStack("bank_ddddee");
                                        //setArguments(bundle);
                                        transaction.commit();
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
            }
        });

        state_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="bank";
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search.setText("");


                stateBeanList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                StateBean bean=new StateBean("Allahabad Bank",1);
                stateBeanList.add(bean);
                StateBean bean1=new StateBean("Andhra Bank",1);
                stateBeanList.add(bean1);
                StateBean bean2=new StateBean("Bank of Baroda",1);
                stateBeanList.add(bean2);
                StateBean bean3=new StateBean("Bank of India",1);
                stateBeanList.add(bean3);
                StateBean bean4=new StateBean("Bank of Maharashtra",1);
                stateBeanList.add(bean4);
                StateBean bean5=new StateBean("Canara Bank",1);
                stateBeanList.add(bean5);
                StateBean bean6=new StateBean("Central Bank of India",1);
                stateBeanList.add(bean6);
                StateBean bean7=new StateBean("Corporation Bank",1);
                stateBeanList.add(bean7);
                StateBean bean8=new StateBean("Dena Bank",1);
                stateBeanList.add(bean8);
                StateBean bean9=new StateBean("Indian Bank",1);
                stateBeanList.add(bean9);
                StateBean bean10=new StateBean("Indian Overseas Bank",1);
                stateBeanList.add(bean10);
                StateBean bean11=new StateBean("Oriental Bank of Commerce",1);
                stateBeanList.add(bean11);
                StateBean bean12=new StateBean("Panjab National Bank",1);
                stateBeanList.add(bean12);

                bank_adapter = new BankAdapter(stateBeanList,getActivity());

                recyclerView.setAdapter(bank_adapter);

            }
        });
        account_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(account_no,state_txt,ifsc_code,acc_holder_name);
                return false;
            }
        });

        state_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection1(account_no,state_txt,ifsc_code,acc_holder_name);
                return false;
            }
        });

        ifsc_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(ifsc_code,state_txt,account_no,acc_holder_name);
                return false;
            }
        });

        acc_holder_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(acc_holder_name,state_txt,account_no,ifsc_code);
                return false;
            }
        });




        return view;
    }

    public void linear_layout_selection(EditText selectdl1, TextView l2, EditText l3, EditText l4 ){

        selectdl1.setBackgroundResource(R.drawable.border_green_empty);
        l2.setBackgroundResource(R.drawable.border_grey);
        l3.setBackgroundResource(R.drawable.border_grey);
        l4.setBackgroundResource(R.drawable.border_grey);

    }
    public void linear_layout_selection1(EditText selectdl1, TextView l2, EditText l3, EditText l4){

        l2.setBackgroundResource(R.drawable.border_green_empty);
        l3.setBackgroundResource(R.drawable.border_grey);
        l4.setBackgroundResource(R.drawable.border_grey);
        selectdl1.setBackgroundResource(R.drawable.border_grey);

    }

}
