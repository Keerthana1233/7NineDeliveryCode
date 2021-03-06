package com.example.sevennine_Delivery.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.Address_Adapter;
import com.example.sevennine_Delivery.Bean.Add_New_Address_Bean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewAddressDetails_Fragment extends Fragment {

    public static ArrayList<Add_New_Address_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static Address_Adapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title,Continue_txt;
    public static String address_nav_stat;
    LinearLayout back_feed,linearLayout,Continue,main_layout;
    JSONArray get_categorylist_array;
    Bundle bundle;
    public static String address;
    JSONObject lngObject;

    SessionManager sessionManager;
    public static NewAddressDetails_Fragment newInstance() {
        NewAddressDetails_Fragment fragment = new NewAddressDetails_Fragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.soil_details_recy_layout, container, false);
      //  Status_bar_change_singleton.getInstance().color_change(getActivity());
      //  HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
        recyclerView=view.findViewById(R.id.recycler_what_looking);
      /*  toolbar_title=view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Addresses");*/
      back_feed=view.findViewById(R.id.back_feed);

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("sett", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        Continue = view.findViewById(R.id.continuebtn);
        Continue_txt = view.findViewById(R.id.apply_loan);
        main_layout = view.findViewById(R.id.main_layout);
        Continue_txt.setText("ADD NEW ADDRESS");
        sessionManager=new SessionManager(getActivity());
       // NewAddressDetails_Fragment.address_nav_stat=null;
        setupUI(main_layout);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*bundle=new Bundle();
                bundle.putString("profile_addr","address");*/
                address_nav_stat="profile";
                System.out.println("address_nav_statpprooo"+address_nav_stat);

                selectedFragment = AddNewAddressFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("newaddressfragment");
               // setArguments(bundle);
                transaction.commit();
            }
        });
       /* back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("sett", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });*/
        System.out.println("adreess_list_size"+newOrderBeansList.size());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("sett", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));

            toolbar_title.setText(lngObject.getString("MyAddresses").replace("\n",""));
            Continue_txt.setText(lngObject.getString("ADDNEWADDRESS").replace("\n",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        addressList();

       /* Add_New_Address_Bean img1=new Add_New_Address_Bean("Jagdish","102","RR Nagar","","581106","","","Karnataka","Haveri","Byadgi","","","1",
                "");
        newOrderBeansList.add(img1);

        livestock_types_adapter=new Address_Adapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(livestock_types_adapter);
*/

        return view;
    }

    private void addressList() {
        newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
           // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetUserAddress, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("UserAddressList");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String Name=jsonObject1.getString("FullName");
                            String MobileNo=jsonObject1.getString("MobileNo");
                            String StreeAddress=jsonObject1.getString("Address");
                            String LandMark=jsonObject1.getString("LandMark");
                            String State=jsonObject1.getString("State");
                            String Taluk=jsonObject1.getString("BlockName");
                            String District=jsonObject1.getString("District");
                            String Village=jsonObject1.getString("Village");
                            String StateId=jsonObject1.getString("StateId");
                            String DistrictId=jsonObject1.getString("DistrictId");
                            String BlockId=jsonObject1.getString("BlockId");
                            String VillageId=jsonObject1.getString("VillageId");
                            String Pincode=jsonObject1.getString("Pincode");
                            boolean AddressType=jsonObject1.getBoolean("AddressType");
                            String UserAddressId=jsonObject1.getString("UserAddressId");
                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            Add_New_Address_Bean img1=new Add_New_Address_Bean(Name,StreeAddress,LandMark,Taluk,Pincode,MobileNo,AddressType,State,
                                    District,Taluk,"",Village,UserAddressId, true,StateId,DistrictId,BlockId,VillageId);
                            newOrderBeansList.add(img1);

                            System.out.println("adreess_list_size"+newOrderBeansList.size());
                            if (newOrderBeansList.size()==0){
                                address="No_address_data";
                                selectedFragment = EmptyFragmentAddr.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.no_item_frame1, selectedFragment);
                               // transaction.addToBackStack("emmpty2");
                                transaction.commit();
                            }else {
                                livestock_types_adapter = new Address_Adapter(getActivity(), newOrderBeansList);
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
