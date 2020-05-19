package com.example.sevennine_Delivery.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.Activity.NewSignUpActivity;
import com.example.sevennine_Delivery.Activity.Status_bar_change_singleton;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirmShopDetailsFragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout Continue,linearLayout;
    EditText shopname,gst,addressline1,addressline2,mobile_no,email;
    SessionManager sessionManager;
    String status,message,shop_name_toast,shop_ads_toast;
    boolean doubleBackToExitPressedOnce = false;
    JSONArray get_cimage_array,get_loctn_array;
    JSONObject lngObject;
    TextView continue_text,toolbar_title,shopname_txt,shop_ads_txt,line1_txt,line2_txt,gst_txt;

    public static FirmShopDetailsFragment newInstance() {
        FirmShopDetailsFragment fragment = new FirmShopDetailsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_firm_details_layout, container, false);

       Status_bar_change_singleton.getInstance().color_change(getActivity());

        Continue = view.findViewById(R.id.continuebtn);
        shopname = view.findViewById(R.id.shopname);
        gst = view.findViewById(R.id.gst);
        addressline1 = view.findViewById(R.id.address1);
        addressline2 = view.findViewById(R.id.address2);
        linearLayout = view.findViewById(R.id.main_layout);
        continue_text = view.findViewById(R.id.apply_loan);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        shopname_txt = view.findViewById(R.id.fsname);
        shop_ads_txt = view.findViewById(R.id.fsaddress);
        line1_txt = view.findViewById(R.id.line1);
        line2_txt = view.findViewById(R.id.line2);
        gst_txt = view.findViewById(R.id.vhnum);
        mobile_no = view.findViewById(R.id.mnumbr);
        email = view.findViewById(R.id.emailid);

        sessionManager = new SessionManager(getActivity());


        setupUI(linearLayout);


        shopname.setFilters(new InputFilter[] { EMOJI_FILTER,new InputFilter.LengthFilter(50)});
        addressline1.setFilters(new InputFilter[] { EMOJI_FILTER,new InputFilter.LengthFilter(50)});
        addressline2.setFilters(new InputFilter[] { EMOJI_FILTER,new InputFilter.LengthFilter(50)});


        /*try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            continue_text.setText(lngObject.getString("PROCEED").replace("\n",""));
            toolbar_title.setText(lngObject.getString("FirmShopDetails"));
            shopname_txt.setText(lngObject.getString("FirmShopName"));
            shop_ads_txt.setText(lngObject.getString("FirmShopAddress"));
            line1_txt.setText(lngObject.getString("Line1"));
            line2_txt.setText(lngObject.getString("Line2"));
            gst_txt.setText(lngObject.getString("GSTOptional"));
            shop_name_toast= (lngObject.getString("PleaseenterFirmShopname"));
            shop_ads_toast = (lngObject.getString("PleaseenterAddressLine1"));


        } catch (JSONException e) {
            e.printStackTrace();
        }*/




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        // this.finishAffinity();

                        if (doubleBackToExitPressedOnce) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            getActivity().finish();
                            System.exit(0);
                        }

                        doubleBackToExitPressedOnce = true;
                        // Toast.makeText(getActivity().getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                  /*      int duration = 1000;
                        Snackbar snackbar = Snackbar
                                .make(linearLayout,"Please Click Back To Exit", duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER);
                        }
                        snackbar.show();*/
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 3000);
                    }

                    return true;
                }
                return false;

            }
        });



        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopname.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getActivity(),"Enter your name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                    toast.show();
                    /*Toast toast = Toast.makeText(getActivity(), shop_name_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();
*/
                 /*   Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please enter Firm/Shop Name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 150);
                    toast.show();*/

                } else if(addressline1.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getActivity(),"Enter Address line 1", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                    toast.show();

                   /* Toast toast = Toast.makeText(getActivity(), shop_ads_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();
*/
                  /*  Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please enter Address Line1", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 150);
                    toast.show();*/

                }else if(gst.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getActivity(),"Enter Vehicle number", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                    toast.show();

                   /* Toast toast = Toast.makeText(getActivity(), shop_ads_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();
*/
                  /*  Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please enter Address Line1", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 150);
                    toast.show();*/

                }else{


                  //  shoplocation();
                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("PId","1");
                        jsonObject.put("Name",shopname.getText().toString());
                        jsonObject.put("Mnumber",mobile_no.getText().toString());
                        jsonObject.put("Address",addressline1.getText().toString());
                        jsonObject.put("EmailId",email.getText().toString());
                        jsonObject.put("Vehicalnumber",gst.getText().toString());
                        jsonObject.put("UserId",sessionManager.getRegId("userId"));
                        jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));

                        Crop_Post.crop_posting(getActivity(), Urls.PERSONALDETAILS, jsonObject, new VoleyJsonObjectCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject result) {

                                try {
                                    status= result.getString("Status");
                                    message = result.getString("Message");

                                    if(status.equals("1")) {

                                       // shoplocation();

                                        selectedFragment = Verification_Fragment.newInstance();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame_layout1, selectedFragment);
                                        transaction.addToBackStack("verify");
                                        transaction.commit();
                                        }else {

                                        Toast toast = Toast.makeText(getActivity(),"Personal Details not added", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                        toast.show();

                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }

            }
        });


        shopname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int after)    {

            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if((shopname.getText().toString().length()>0)&&(addressline1.getText().toString().length()>0)&&(gst.getText().toString().length()>0)){

                    Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);
                }else{

                    Continue.setBackgroundResource(R.drawable.grey_curved_border);
                }
            }
        });



        addressline1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int after)    {

            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if((addressline1.getText().toString().length()>0)&&(shopname.getText().toString().length()>0)&&(gst.getText().toString().length()>0)){
                    Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);

                }else{
                    Continue.setBackgroundResource(R.drawable.grey_curved_border);
                }
            }
        });


        gst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int after)    {

            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if((gst.getText().toString().length()>0)&&(shopname.getText().toString().length()>0)&&(addressline1.getText().toString().length()>0)){
                    Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);

                }else{
                    Continue.setBackgroundResource(R.drawable.grey_curved_border);
                }
            }
        });

        ///////////capture image




////////////// get Location details







        return view;
    }

    private void shoplocation() {




/*
        if((get_loctn_array.length()>0)&&(get_cimage_array.length()>0)){

            Bundle bundle = new Bundle();
            bundle.putString("status","firmdetails");
            selectedFragment = Shop_LocationEdit_Fragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout1, selectedFragment);
            selectedFragment.setArguments(bundle);
            transaction.addToBackStack("firmdetails1");
            transaction.commit();

        }else{
            selectedFragment = Shop_Location_Fragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout1, selectedFragment);
            transaction.addToBackStack("firm");
            transaction.commit();

        }


*/


    }


   /* public void linear_layout_selection(EditText selectdl1, EditText l2, EditText l3, EditText l4){
        selectdl1.getResources().getColor(R.color.green);
        l2.getResources().getColor(R.color.light_gray);
        l3.getResources().getColor(R.color.light_gray);
        l4.getResources().getColor(R.color.light_gray);

    }*/


    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        /*InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);*/

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


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            String specialChars = ".1/*!@#$%^&*()\"{}_[]|\\?/<>,.:-'';§£¥₹...%&+=€π|";
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL||type== Character.MATH_SYMBOL||specialChars.contains("" + source)) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }else if(Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };


}

