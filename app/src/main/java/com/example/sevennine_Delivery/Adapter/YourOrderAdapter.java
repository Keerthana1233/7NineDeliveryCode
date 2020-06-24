package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Bean.YourOrderBean;
import com.example.sevennine_Delivery.Fragment.OrderDetailsFragmentaddr;
import com.example.sevennine_Delivery.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.MyViewHolder> implements OnMapReadyCallback {
    private List<YourOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
    private GoogleMap mMap;
    public LinearLayout linearLayout;
    MapView mMapView;
    private Projection projection;
    public static CardView cardView;
    public YourOrderAdapter(Activity activity, List<YourOrderBean> productList) {
        this.productList = productList;
        this.activity=activity;

       // mMapView.getMapAsync(this);

    }
 @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);
        double lat1 = Double.parseDouble("12.9716");
        double lng1 = Double.parseDouble("77.5946");
        LatLng bord = new LatLng(lat1, lng1);
        mMap.addMarker(new MarkerOptions().position(bord).title("Marker on board"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bord, 13.0f));
     Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
             .clickable(true)
             .add(new LatLng(12.9716, 77.5946)));
     polyline1.setTag("7Nine Delhivery");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prod_price,orderid,date;
        public RatingBar rate;

        public MyViewHolder(View view) {
            super(view);
            prod_price=view.findViewById(R.id.amount);
            orderid=view.findViewById(R.id.orderid);
            date=view.findViewById(R.id.pricetxt);
            rate=view.findViewById(R.id.rate_us);
            mMapView = (MapView) view.findViewById(R.id.activity_fragment_mapview);
            initializeMapView();
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yourorder_detail_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final YourOrderBean products = productList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = OrderDetailsFragmentaddr.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }
    public void initializeMapView() {
        if (mMapView != null) {
            // Initialise the MapView
            mMapView.onCreate(null);
            // Set the map ready callback to receive the GoogleMap object
            mMapView.getMapAsync(this);
        }
    }
}