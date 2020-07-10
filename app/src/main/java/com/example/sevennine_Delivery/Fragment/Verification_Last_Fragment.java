package com.example.sevennine_Delivery.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.sevennine_Delivery.Activity.FirmShopDetailsActivity;
import com.example.sevennine_Delivery.Activity.MainActivity;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;


public class Verification_Last_Fragment extends Fragment {


    Boolean user_uploaded;
    Fragment selectedFragment;
    public static LinearLayout linear_layout,cont_btn;
    boolean doubleBackToExitPressedOnce = false;
    JSONObject verify_status;
    TextView toolbar_title,mobile_no,proceed_txt,in_progress_details,success_details;
    public static TextView user_status,ph_no,user_status_text;
    ImageView in_progress_image,success_image;
    public static JSONObject lngObject;
    SessionManager sessionManager;
    public String status;



    public static Verification_Last_Fragment newInstance() {
        Verification_Last_Fragment fragment = new Verification_Last_Fragment();
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_last_layout, container, false);

        //   Status_bar_change_singleton.getInstance().color_change(getActivity());

        linear_layout = view.findViewById(R.id.linear_layout);
        user_status = view.findViewById(R.id.user_status);
        ph_no = view.findViewById(R.id.ph_no);
        cont_btn = view.findViewById(R.id.cont_btn);
        toolbar_title = view.findViewById(R.id.setting_tittle);
        mobile_no = view.findViewById(R.id.mobile_no);
        user_status_text = view.findViewById(R.id.user_status_text);
        proceed_txt = view.findViewById(R.id.proceed_txt);
        in_progress_details = view.findViewById(R.id.in_progress_details);
        success_details = view.findViewById(R.id.success_details);
        in_progress_image = view.findViewById(R.id.in_progress_image);
        success_image = view.findViewById(R.id.success_image);

        sessionManager = new SessionManager(getActivity());

        ph_no.setText(sessionManager.getRegId("phone"));


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
                     Toast.makeText(getActivity().getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//                       int duration = 1000;
//
//                       Snackbar snackbar = Snackbar
//                               .make(linear_layout,"Please Click Back To Exit", duration);
//                       View snackbarView = snackbar.getView();
//                       TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                       tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                       tv.setTextColor(Color.WHITE);
//
//
//                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                           tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                       } else {
//                           tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                       }
//                       snackbar.show();

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






        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("deyuiirwe" + sessionManager.getRegId("userId"));



            Crop_Post.crop_posting(getActivity(), Urls.Get_Verification_Status, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("ghdgfd" + result);

                    try{


                        verify_status = result.getJSONObject("VerificationStatus");

                        user_uploaded = verify_status.getBoolean("IsUserUploaded");

                        if(user_uploaded.equals(false)){

                            user_status.setText("Pending");
                            in_progress_details.setVisibility(View.VISIBLE);
                            in_progress_image.setVisibility(View.VISIBLE);


                            // Toast.makeText(getActivity(), "we will get back to you once verification is  done", Toast.LENGTH_SHORT).show();

                            //cont_btn.setVisibility(View.GONE);
                            cont_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent i = new Intent(getActivity(), FirmShopDetailsActivity.class);
                                    startActivity(i);



                                }
                            });


                        }else{

                            user_status.setText("In Progress");
                            // success_details.setVisibility(View.VISIBLE);
                            success_image.setVisibility(View.VISIBLE);

                            cont_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);



                                }
                            });




                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }









        return view;
    }
}

