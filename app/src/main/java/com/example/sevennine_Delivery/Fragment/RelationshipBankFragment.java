package com.example.sevennine_Delivery.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.RelationshipBankAdapter;
import com.example.sevennine_Delivery.Bean.SelectLanguageBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RelationshipBankFragment extends Fragment {
    private List<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    LinearLayout back_feed,yes_lin_lay,main_layout;
    public static TextView back_text,next,select;
    Fragment selectedFragment;
    RecyclerView recyclerView;
   TextView rela_sbi,type;
    TextView account_no,ifsc_code;
    int selectedId;
    String status;
     String relationstatus;
    RelationshipBankAdapter mAdapter;
    SessionManager sessionManager;
    String account_validate,ifsc_validate;
    public static RelationshipBankFragment newInstance() {
        RelationshipBankFragment fragment = new RelationshipBankFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.relationship_bank, container, false);
        //back_text=view.findViewById(R.id.back_text);
        back_feed=view.findViewById(R.id.back_feed);
        next=view.findViewById(R.id.next);
        select=view.findViewById(R.id.type);
        main_layout=view.findViewById(R.id.main_layout);
        type=view.findViewById(R.id.type);
        rela_sbi=view.findViewById(R.id.rela_sbi);
        ifsc_code=view.findViewById(R.id.ifsc_code);
        account_no=view.findViewById(R.id.account_no);

        account_validate="[0-9]{9,18}";
        ifsc_validate="^[A-Za-z]{4}0[A-Z0-9a-z]{6}$";
      //  System.out.println("rrerrererrerere"+relation_bank.getTag().toString());
sessionManager=new SessionManager(getActivity());

//System.out.println("currenttttlatttlongg"+YourAddressLocationFragment.lat+" "+YourAddressLocationFragment.longi);
        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

       // ifsc_code.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30)});

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("pref_branch", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), type);
                popup.getMenu().add("Saving Account");
                popup.getMenu().add("Current Account");
                popup.getMenu().add("Kisan Credit Card");
                popup.getMenu().add("Tractor Loan");
                popup.getMenu().add("Deposits");
                popup.getMenu().add("Other");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        System.out.println("teeexxtt"+item.getTitle());
                        type.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
        rela_sbi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), rela_sbi);
                popup.getMenu().add("Yes");
                popup.getMenu().add("No");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        System.out.println("teeexxtt"+item.getTitle());
                        rela_sbi.setText(item.getTitle());
                        if (rela_sbi.getText().toString().equals("No")){
                            type.setVisibility(View.GONE);
                            account_no.setVisibility(View.GONE);
                            ifsc_code.setVisibility(View.GONE);
                        }else{
                            type.setVisibility(View.VISIBLE);
                            account_no.setVisibility(View.VISIBLE);
                            ifsc_code.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rela_sbi.getText().toString().equals("")){
                    if (select.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Do you have an existing relationship with SBI?", Toast.LENGTH_SHORT).show();
                       /* int duration=1000;
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Do you have an existing relationship with SBI?",duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();*/
                    } else if (rela_sbi.getText().toString().equals("Yes")){
                        if (type.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Please select type of relationship.", Toast.LENGTH_SHORT).show();

                          /*  int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Please select type of relationship.",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();*/
                        } else if (account_no.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Enter Account Number.", Toast.LENGTH_SHORT).show();

                           /* int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Enter Account Number.",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();*/
                        }  else if (!(account_no.getText().toString().matches(account_validate))){
                            Toast.makeText(getActivity(),  "Enter Valid Account Number.", Toast.LENGTH_SHORT).show();

                            /*int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Enter Valid Account Number.",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();*/
                        }else if (ifsc_code.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Enter IFSC Code.", Toast.LENGTH_SHORT).show();

                          /*  int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Enter IFSC Code.",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();*/
                        }  else if (!(ifsc_code.getText().toString().matches(ifsc_validate))){
                            Toast.makeText(getActivity(), "Enter Valid IFSC Code.", Toast.LENGTH_SHORT).show();

                          /*  int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Enter Valid IFSC Code.",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();*/
                        }else {
                            //apply_loan();

                        }
                    }else {
                       // apply_loan();
                    }
                }else {
                   // apply_loan();

                }
               /* Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);*/
            }
        });



        return view;
    }

   /* private void apply_loan() {
        try{
System.out.println("loannnnnnnnnnn");
            JSONObject jsonObject = new JSONObject();
            if (LandingPageActivity.loan_type==null){
                System.out.println("entering to farmer Finance "+AddressListAdapter.addressid);
                jsonObject.put("ApplicationId", LandingPageActivity.applicationId);
                if (AddressListAdapter.addressid==null){
                    System.out.println("addddddnotttherrer");

                    jsonObject.put("AddressId", "");
                }else{
                    System.out.println("adddddiissss");

                    jsonObject.put("AddressId", AddressListAdapter.addressid);

                }
                System.out.println("2");

                jsonObject.put("BankOfferId",1);
                jsonObject.put("BankBranchId", SelectBankAdapter.bankid);
                jsonObject.put("IsRelationShipWithBank",rela_sbi.getText().toString());
                jsonObject.put("MobileNo",sessionManager.getRegId("phone"));
                jsonObject.put("TypeOfRelation",select.getText().toString());
                jsonObject.put("AccountNumber", account_no.getText().toString());
                jsonObject.put("IFSCCode", ifsc_code.getText().toString());
                *//*if (Map_Current_Loc_Fragment.addr_lat!=null){
                    System.out.println("mappppppphhhhhh");

                    jsonObject.put("latitude", Map_Current_Loc_Fragment.latitude);
                    jsonObject.put("longitude", Map_Current_Loc_Fragment.longitude);
                }else {
                    System.out.println("nooooomappppp");

                    jsonObject.put("latitude", "");
                    jsonObject.put("longitude", "");
                }*//*
                jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
            }
            else{
                jsonObject.put("ApplicationId", LandingPageActivity.applicationId);
                jsonObject.put("MakerId",1);
                jsonObject.put("ModelId", ModelAdapter.model_id);
                if (AddressListAdapter.addressid.equals("")){
                    jsonObject.put("AddressId", "");
                }else{
                    jsonObject.put("AddressId", AddressListAdapter.addressid);

                }                jsonObject.put("BankOfferId",1);
                jsonObject.put("BankBranchId", SelectBankAdapter.bankid);
                jsonObject.put("IsRelationShipWithBank",relationstatus);
                jsonObject.put("MobileNo",sessionManager.getRegId("phone"));
                jsonObject.put("TypeOfRelation",select.getText().toString());
                jsonObject.put("AccountNumber", account_no.getText().toString());
                jsonObject.put("IFSCCode", ifsc_code.getText().toString());

              //////  jsonObject.put("latitude", Map_Current_Loc_Fragment.latitude);
              /////  jsonObject.put("longitude", Map_Current_Loc_Fragment.longitude);
               *//* if (YourAddressLocationFragment.latitude=null){
                    jsonObject.put("latitude", YourAddressLocationFragment.lat);
                    jsonObject.put("longitude", YourAddressLocationFragment.longi);
                }else {
                    jsonObject.put("latitude", "");
                    jsonObject.put("longitude", "");
                }*//*
                jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
            }

            System.out.println("Add_New_AddresssssssssssssssssjsonObject"+jsonObject);


System.out.println("appppplllyy"+Urls.ApplyAllLoans+Urls.ApplyLoan);
            Crop_Post.crop_posting(getActivity(), Urls.ApplyAllLoans+Urls.ApplyLoan, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    Bundle bundle=new Bundle();

                    System.out.println("Add_New_Addressssssssssssssssslllllllllllllllllllllll"+result);
                    try{

                        status= result.getString("Status");
                        // message = result.getString("Message");

                        bundle.putString("add_id",status);

                        bundle.putString("streetname",  DistrictAdapter.district_name);


                        if(!(status.equals("0"))) {
                            LandingPageActivity.applicationId=null;
                            LandingPageActivity.loan_type=null;
                            selectedFragment = LoanDetailsSuccessful.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            // transaction.addToBackStack("relation");
                            transaction.commit();
                        }else{

                            int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Something went wrong.Please apply again",duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();



                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        int duration=1000;
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Something went wrong.",duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();

                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();

        }
    }*/
}
