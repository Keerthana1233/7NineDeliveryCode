package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.OrderDetailsAdapter;
import com.example.sevennine_Delivery.Bean.OrderDetailBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Login_post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderDetailsFragment extends Fragment {
//commit code eod
    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    OrderDetailsAdapter madapter;
    JSONObject lngObject;
    TextView acceptbtn;
    TextView toolbar_title,orderidtxt,modetxt,amounttxt,addrtxt,nametxt,orderdatetxt;
    Fragment selectedFragment;
    Date date;
    String orderid,addr,mode,amount,createddate,lat,longi;
    public static OrderDetailsFragment newInstance() {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        return fragment;
    }
    //git push -u https://github.com/Keerthana1233/7NineDeliveryCode.git master
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details_layout, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        acceptbtn=view.findViewById(R.id.acceptbtn);
        orderidtxt=view.findViewById(R.id.orderid);
        orderdatetxt=view.findViewById(R.id.orderdate);
        modetxt=view.findViewById(R.id.mode);
        amounttxt=view.findViewById(R.id.amount);
        nametxt=view.findViewById(R.id.name);
        addrtxt=view.findViewById(R.id.addr);
        orderdatetxt=view.findViewById(R.id.orderdate);
        sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        back_feed=view.findViewById(R.id.back_feed);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("new", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("new", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;

            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orderid = bundle.getString("orderId");
            amount = bundle.getString("totalamount");
            createddate = bundle.getString("orderdate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            String dtStart = createddate;
            System.out.println("getCreateddate"+dtStart);

            try {
                date = format.parse(dtStart);
                orderdatetxt.setText(date.getDate()+"/"+(1+date.getMonth())+"/"+(1900+date.getYear()+" "+date.getHours()+":"+date.getMinutes()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            addr = bundle.getString("addr");
            mode = bundle.getString("mode");
            lat = bundle.getString("lat");
            longi = bundle.getString("long");
            //orderdatetxt.setText(createddate);
            orderidtxt.setText(orderid);
            amounttxt.setText(amount);
            addrtxt.setText(addr);
            modetxt.setText(mode);
        }
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        OrderDetailBean bean=new OrderDetailBean("Parle-G Gold Milk Glucose..","1","Rs.100","Rs.2","Rs.2","");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
     //   newOrderBeansList.add(bean);

        madapter=new OrderDetailsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);


    //    LoanInformation();
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject params = new JSONObject();
                try {
                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("AcceptOrdersId",orderid);  // amount
                    params.put("Amount",amount);  // amount
                    params.put("PayUTransactionId","1");  //transaction fees
                    params.put("ProductInfo",addr);
                    params.put("SellingListName","flower");
                    params.put("CategoryName","testingfruit");
                    params.put("SelectedQuantity","1"); //using status
                    params.put("UnitOfPrice","ampers");
                    params.put("SellingListIcon", "");
                    params.put("Latitude",lat);  //tarnsaction id
                    params.put("Longitude",longi);
                    params.put("CustomerName","test");
                    params.put("CreatedBy",sessionManager.getRegId("userId"));
                    System.out.println("RESPMsgdsfadf"+params);
                    Login_post.login_posting(getActivity(), Urls.AddAccept, params, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("llllllllllllllllllllllllllll"+result);
                            try {
                                System.out.println("nnnnnmnm" + result.toString());
                                String status=result.getString("Status");
                                if(status.equals("1")){
                                    Toast toast = Toast.makeText(getActivity(),"Order details Accepted for Delhivery Successfully", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                    toast.show();
                                }
                                else {
                                    Toast toast = Toast.makeText(getActivity(),"Order details  Not Accepted", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                    toast.show();
                                    //  Toast.makeText(getActivity(),"Transaction Incomplete",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
