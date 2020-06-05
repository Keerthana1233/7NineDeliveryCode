package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.sevennine_Delivery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

public class EmptyFragmentAddr extends Fragment {
    Fragment selectedFragment;
    LinearLayout backfeed,feedback_lay,main_layout,about_lay;
    TextView no_data;
    ImageView no_data_image;
    JSONObject lngObject;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    String description,status,message,fd_sucess,fd_failure,feeddesc;
    public static EmptyFragmentAddr newInstance() {
        EmptyFragmentAddr fragment = new EmptyFragmentAddr();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_layout, container, false);
        no_data_image=view.findViewById(R.id.no_dat_img);
        no_data=view.findViewById(R.id.no_data);
        if (NewAddressDetails_Fragment.address==null){
            no_data_image.setVisibility(View.GONE);
            no_data.setText("No address is added");
        }else if (BankAccount_Fragment.bank_details==null){
            no_data_image.setVisibility(View.GONE);
            no_data.setText("No Bank detail is added");
        }

        return view;
    }


    }
