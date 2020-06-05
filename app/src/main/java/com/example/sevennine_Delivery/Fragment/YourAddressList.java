package com.example.sevennine_Delivery.Fragment;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.AddressListAdapter;
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
import java.util.List;

public class YourAddressList extends Fragment {

    public static List<Add_New_Address_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddressListAdapter farmadapter;
    TextView add_addr;
    LinearLayout back_feed;
    Fragment selectedFragment;
    JSONObject lngObject;
    SessionManager sessionManager;
    public static String address;

    public static YourAddressList newInstance() {
        YourAddressList fragment = new YourAddressList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_addr_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_prefered);
        back_feed=view.findViewById(R.id.back_feed);
        add_addr=view.findViewById(R.id.add_addr);

        sessionManager=new SessionManager(getActivity());
       // Map_Current_Loc_Fragment.addr_lat=null;
        add_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = AddNewAddressFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                 transaction.addToBackStack("addr_list1");
                transaction.commit();
            }
        });

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("current", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                       /* FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack ("current", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

                    return true;
                }
                return false;
            }
        });



        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        addressList();

       /* newOrderBeansList.add(img6);
        newOrderBeansList.add(img6);*/
       /* PreferedBranchBean bean=new PreferedBranchBean("State Bank of India","Yadav Colony,Sambal, NH-93, Babrala Hahjoi Road, Bahjoi,","Uttar Pradesh","244410","");
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);
         newOrderBeansList.add(bean);*/

      /*  farmadapter=new AddressListAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);
*/

        return view;
    }

    private void addressList() {
        newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetUserAddress, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("UserAddressList");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String Name=jsonObject1.getString("FullName");
                                String StreeAddress=jsonObject1.getString("Address");
                                String LandMark=jsonObject1.getString("LandMark");
                                String State=jsonObject1.getString("State");
                                String Taluk=jsonObject1.getString("Taluk");
                                String District=jsonObject1.getString("District");

                            String Pincode=jsonObject1.getString("Pincode");
                                String UserAddressId=jsonObject1.getString("UserAddressId");
                                boolean AddressType=jsonObject1.getBoolean("AddressType");
                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            Add_New_Address_Bean img1=new Add_New_Address_Bean(Name,StreeAddress,LandMark,Taluk,Pincode,"",AddressType,State,
                                    District,Taluk,"","",UserAddressId, true,"","","","");
                            newOrderBeansList.add(img1);

                            if (newOrderBeansList.size()==0){
                                address="No_address_data";
                                selectedFragment = EmptyFragment.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.no_item_frame, selectedFragment);
                                transaction.addToBackStack("emmpty");
                                transaction.commit();
                            }else {
                                farmadapter = new AddressListAdapter(getActivity(), newOrderBeansList);
                                recyclerView.setAdapter(farmadapter);
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


}
