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
public class AcceptOrderDetailsFragmentview extends Fragment {
    //commit code eod
    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    OrderDetailsAdapter madapter;
    JSONObject lngObject;
    TextView acceptbtn;
    TextView codtxt,sf,tbt,tax,toolbar_title,orderidtxt,modetxt,amounttxt,addrtxt,nametxt,orderdatetxt,phoneno,acceptbtn1,phoneno1,itemscosttxt,tamounttxt;
    Fragment selectedFragment;
    Date date;
    String mask;
    String orderid,addr,mode,amount,createddate,lat,longi,mobilestr,pronamestr,proimgstr,payuidstr,cuslat,cuslongi;
    public static AcceptOrderDetailsFragmentview newInstance() {
        AcceptOrderDetailsFragmentview fragment = new AcceptOrderDetailsFragmentview();
        return fragment;
    }
    //git push -u https://github.com/Keerthana1233/7NineDeliveryCode.git master
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details_layoutview, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        orderidtxt=view.findViewById(R.id.orderid);
        orderdatetxt=view.findViewById(R.id.orderdate);
        modetxt=view.findViewById(R.id.mode);
        amounttxt=view.findViewById(R.id.amount);
        itemscosttxt=view.findViewById(R.id.itemscost);
        tamounttxt=view.findViewById(R.id.tamount);
        sf=view.findViewById(R.id.sf);
        tbt=view.findViewById(R.id.tbt);
        tax=view.findViewById(R.id.tax);
        nametxt=view.findViewById(R.id.name);
        addrtxt=view.findViewById(R.id.addr);
        orderdatetxt=view.findViewById(R.id.orderdate);
        acceptbtn=view.findViewById(R.id.acceptbtn);
        phoneno=view.findViewById(R.id.phoneno);
        acceptbtn1=view.findViewById(R.id.acceptbtn1);
        phoneno1=view.findViewById(R.id.phoneno1);
        sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        back_feed=view.findViewById(R.id.back_feed);
        codtxt=view.findViewById(R.id.cod);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("acceptview", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("acceptview", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;

            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orderid = bundle.getString("orderId");
          /*  latid = bundle.getString("latidkey");
            langid = bundle.getString("langidkey");
            custlat= bundle.getString("custlatidkey");
            custlong= bundle.getString("custlangidkey");
            System.out.println("rtyr"+custlat);*/
            amount = bundle.getString("totalamount");
            createddate = bundle.getString("orderdate");
            addr = bundle.getString("addr");
            mode = bundle.getString("mode");
            mobilestr = bundle.getString("mobile");
            pronamestr = bundle.getString("proname");
            proimgstr = bundle.getString("proimg");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dtStart = createddate;
            System.out.println("getCreateddate"+dtStart);

            try {
                date = format.parse(dtStart);
             orderdatetxt.setText(date.getDate()+"-"+(1+date.getMonth())+"-"+(1900+date.getYear()+" "+date.getHours()+":"+date.getMinutes()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        orderidtxt.setText(orderid);
            amounttxt.setText("₹"+Double.parseDouble(amount));
            tamounttxt.setText("₹"+Double.parseDouble(amount));
            itemscosttxt.setText("₹"+Double.parseDouble(amount));
            sf.setText("₹"+Double.parseDouble("0"));
            tbt.setText("₹"+Double.parseDouble("0"));
            tax.setText("₹"+Double.parseDouble("0"));
          String s = addr;
            s = s.replace(",",",\n");
            addrtxt.setText(" "+s);
            //addrtxt.setText(addr);
            modetxt.setText("Mode: "+mode);
            codtxt.setText(mode);
           /* try {
                mask = maskString(mobilestr, 4, 10, '*');
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            // String result = number.substring(number.length() - 4).replace(String.valueOf(number.length()), "*");
          //  phoneno.setText(mask);
            // phoneno.setText(mobilestr);
        }
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        pronamestr = bundle.getString("proname");
        proimgstr = bundle.getString("proimg");
        payuidstr = bundle.getString("payuid");
        OrderDetailBean bean=new OrderDetailBean(pronamestr,"1",amount,"2","0",proimgstr);
        newOrderBeansList.add(bean);
        //newOrderBeansList.add(bean);
        //   newOrderBeansList.add(bean);

        madapter=new OrderDetailsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);

       /* acceptbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject params = new JSONObject();
                try {
                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("AcceptOrdersId",orderid);  // amount
                    params.put("Amount",amount);  // amount
                    params.put("PayUTransactionId",payuidstr);  //transaction fees
                    params.put("ProductInfo",addr);
                    params.put("SellingListName","flower");
                    params.put("CategoryName","testingfruit");
                    params.put("SelectedQuantity","1"); //using status
                    params.put("UnitOfPrice","ampers");
                    params.put("SellingListIcon","");
                    params.put("Latitude",lat);  //tarnsaction id
                    params.put("Longitude",longi);
                    params.put("CustomerName","test");
                    params.put("CustLatitude",cuslat);
                    params.put("CustLongitude",cuslongi);
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
        //    LoanInformation();
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject params = new JSONObject();
                try {
                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("AcceptOrdersId",orderid);  // amount
                    params.put("Amount",amount);  // amount
                    params.put("PayUTransactionId",payuidstr);  //transaction fees
                    params.put("ProductInfo",addr);
                    params.put("SellingListName","flower");
                    params.put("CategoryName","testingfruit");
                    params.put("SelectedQuantity","1"); //using status
                    params.put("UnitOfPrice","ampers");
                    params.put("SellingListIcon","");
                    params.put("Latitude",lat);  //tarnsaction id
                    params.put("Longitude",longi);
                    params.put("CustomerName","test");
                    params.put("CustLatitude",cuslat);
                    params.put("CustLongitude",cuslongi);
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
        });*/
        return view;
    }

    private static String maskString(String strText, int start, int end, char maskChar)
            throws Exception{

        if(strText == null || strText.equals(""))
            return "";

        if(start < 0)
            start = 0;

        if( end > strText.length() )
            end = strText.length();

        if(start > end)
            throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if(maskLength == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for(int i = 0; i < maskLength; i++){
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLength);
    }
}
