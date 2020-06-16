package com.example.sevennine_Delivery.Orders;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.DeliveryOrderAdapter;
import com.example.sevennine_Delivery.Bean.NewOrderBean;
import com.example.sevennine_Delivery.Fragment.FilterFragment;
import com.example.sevennine_Delivery.R;

import java.util.ArrayList;
import java.util.List;

public class DeliverdTab extends Fragment {

    RecyclerView recyclerView;
    private DeliveryOrderAdapter madapter;
    TextView filter;
    Fragment selectedFragment;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deliverd_tab, container, false);
        filter=view.findViewById(R.id.filter);
      /*  sessionManager=new SessionManager(getActivity());
        bmmvendorstoreid=sessionManager.getRegId("selectedvednorstoreid");
        setRepeatingAsyncTask();*/
        recyclerView=view.findViewById(R.id.new_order_recy);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCodezzzzzzzzzq  : " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                    // getFragmentManager().popBackStack("ORDER_FRAGMENT", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().onBackPressed();
                    // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        NewOrderBean bean=new NewOrderBean("#10989876","Hima Bindhu","567, 4th Cross, Banshankari,\n" +
                "560091..","Rs.100","Cash on Delivery","","","","","","");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);

        madapter=new DeliveryOrderAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = FilterFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout1, selectedFragment);
                ft.commit();


            }
        });

        return view;
    }

}
