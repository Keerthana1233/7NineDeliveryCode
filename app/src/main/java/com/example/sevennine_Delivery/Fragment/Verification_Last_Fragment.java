package com.example.sevennine_Delivery.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.sevennine_Delivery.Activity.FirmShopDetailsActivity;
import com.example.sevennine_Delivery.R;

import org.json.JSONException;
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





        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(getActivity(), FirmShopDetailsActivity.class);
                    startActivity(intent);




            }
        });

        return view;
    }
}

