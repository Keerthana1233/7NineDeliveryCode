package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.NotificationAdapter;
import com.example.sevennine_Delivery.Bean.Notification_Home_Bean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    public static List<Notification_Home_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static NotificationAdapter farmadapter;
    TextView toolbar_title;
    LinearLayout back_feed,no_notifitn_added;
    Notification_Home_Bean notification_home_bean;
    Fragment selectedFragment;
    SessionManager sessionManager;
    JSONObject lngObject;
    JSONArray notifn_array;
    String location;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_recy, container, false);

        recyclerView=view.findViewById(R.id.recycler_noti);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        no_notifitn_added=view.findViewById(R.id.no_notifitn_added);
        HomeMenuFragment.toolbartxt.setText("Notifications");
        sessionManager = new SessionManager(getActivity());
     /*   HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
        HomePage_With_Bottom_Navigation.view.setVisibility(View.GONE);
*/

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction7.replace(R.id.frame_layout1, selectedFragment);
                    transaction7.commit();
                    return true;
                }
                return false;
            }
        });



        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Notification_Home_Bean bean=new Notification_Home_Bean("FarmPe Cart launch at Krishi mela in Mysore ","");
        newOrderBeansList.add(bean);

        farmadapter=new NotificationAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);



       /* try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        try{

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("ToUserId",sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.Notification_HomePage, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("sdffdffds" + result);

                    try{

                        notifn_array = result.getJSONArray("NotificationList");

                            for(int i=0;i<notifn_array.length();i++){
                            JSONObject jsonObject1 = notifn_array.getJSONObject(i);
                            notification_home_bean = new Notification_Home_Bean(jsonObject1.getString("NotificationText"),jsonObject1.getString("Id"));
                            newOrderBeansList.add(notification_home_bean);

                        }
                        if(notifn_array.length()==0){

                            no_notifitn_added.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                        }else{

                            no_notifitn_added.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }



                            farmadapter.notifyDataSetChanged();



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }

*/






        return view;
    }



}
