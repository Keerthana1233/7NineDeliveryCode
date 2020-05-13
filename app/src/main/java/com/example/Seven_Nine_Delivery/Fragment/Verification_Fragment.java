package com.example.Seven_Nine_Delivery.Fragment;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Seven_Nine_Delivery.R;
import org.json.JSONException;
import org.json.JSONObject;



public class Verification_Fragment extends Fragment {



    Fragment selectedFragment;
    LinearLayout back_feed;
    public static JSONObject lngObject;
    TextView setting_tittle,voter_id_back,voter_id_front,select_location,selfie_verify,face_verify_selfy_text,sel_loc_text,voter_front_text,voter_back_text,continue_btn;
    String status_1,status_2;




    public static Verification_Fragment newInstance() {
        Verification_Fragment fragment = new Verification_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verification_layout, container, false);

        //  Status_bar_change_singleton.getInstance().color_change(getActivity());

        voter_id_back = view.findViewById(R.id.voter_id_back);
        voter_id_front = view.findViewById(R.id.voter_id_front);
        select_location = view.findViewById(R.id.select_loc);
        selfie_verify = view.findViewById(R.id.selfie_verify);
        face_verify_selfy_text = view.findViewById(R.id.face_verify_selfy_text);
        sel_loc_text = view.findViewById(R.id.sel_loc_text);
        voter_back_text = view.findViewById(R.id.voter_back_text);
        voter_front_text = view.findViewById(R.id.voter_front_text);
        continue_btn = view.findViewById(R.id.continue_btn);
        back_feed = view.findViewById(R.id.back_feed);
        setting_tittle = view.findViewById(R.id.setting_tittle);








        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("verify", FragmentManager.POP_BACK_STACK_INCLUSIVE);


//
//                selectedFragment = Verification_Aadhar_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();

            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("verify", FragmentManager.POP_BACK_STACK_INCLUSIVE);




                    return true;
                }
                return false;
            }
        });


        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                selectedFragment = Shop_Current_Location_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();

            }
        });


        voter_id_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedFragment = Voter_Id_Front_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("verify_voter");
                transaction.commit();


            }
        });


        voter_id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedFragment = Voter_Id_Back_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();


            }
        });

        selfie_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = Selfie_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("verify_selfie");
                transaction.commit();

            }
        });


        return view;
    }
}

