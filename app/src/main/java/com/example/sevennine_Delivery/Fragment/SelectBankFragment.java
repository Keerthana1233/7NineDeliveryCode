package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.SelectBankAdapter;
import com.example.sevennine_Delivery.Bean.ListBeanHome;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SelectBankFragment extends Fragment {
    RecyclerView recycler_brand;
    public static List<ListBeanHome> newOrderBeansList = new ArrayList<>();
    SelectBankAdapter madapter;
    LinearLayout back_feed;
    TextView toolbar_title;

    public static SelectBankFragment newInstance() {
        SelectBankFragment fragment = new SelectBankFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_recy, container, false);
        recycler_brand=view.findViewById(R.id.recycler_brand);
        back_feed=view.findViewById(R.id.back_feed);
        toolbar_title=view.findViewById(R.id.toolbar_title);

       toolbar_title.setText("Select Bank");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("add_addre", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager3 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recycler_brand.setLayoutManager(mLayoutManager3);
        recycler_brand.setItemAnimator(new DefaultItemAnimator());
        BankList();
      /*  ListBean bean=new ListBean("",1,R.drawable.sbi,1);
        newOrderBeansList.add(bean);

        ListBean bean1=new ListBean("",1,R.drawable.bank_of_baroda_logo,1);
        newOrderBeansList.add(bean1);

        ListBean bean2=new ListBean("",1,R.drawable.pnb_logo,1);
        newOrderBeansList.add(bean2);

        ListBean bean3=new ListBean("",1,R.drawable.sbi,1);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean3);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        madapter=new SelectBankAdapter(getActivity(),newOrderBeansList);
        recycler_brand.setAdapter(madapter);
*/
        return view;
    }

    private void BankList() {

        try {
            JSONObject userRequestjsonObject = new JSONObject();

            Login_post.login_posting(getActivity(), Urls.BankList, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("ObjBankList");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            int bankId=jsonObject1.getInt("BankId");
                            String bankName=jsonObject1.getString("BankName");
                            String bankIcon=jsonObject1.getString("BankIcon");
                           // int brandId=jsonObject1.getInt("BrandId");
                            ListBeanHome bean=new ListBeanHome(bankName,bankId,bankIcon);
                            newOrderBeansList.add(bean);
                        }
                        madapter=new SelectBankAdapter(getActivity(),newOrderBeansList);
                        recycler_brand.setAdapter(madapter);



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
