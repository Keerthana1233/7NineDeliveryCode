package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.OrderDetailsAdapter;
import com.example.sevennine_Delivery.Bean.OrderDetailBean;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailsFragmentaddr extends Fragment implements OnMapReadyCallback {

    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    OrderDetailsAdapter madapter;
    JSONObject lngObject;
    TextView toolbar_title;
    Fragment selectedFragment;
    private GoogleMap mMap;
    MapView mMapView;
    public static OrderDetailsFragmentaddr newInstance() {
        OrderDetailsFragmentaddr fragment = new OrderDetailsFragmentaddr();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details_layoutaddr, container, false);
        mMapView = (MapView) view.findViewById(R.id.activity_fragment_mapview);
        HomeMenuFragment.toolbartxt.setText("Order Details");
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        initializeMapView();
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);
        double lat1 = Double.parseDouble("12.9716");
        double lng1 = Double.parseDouble("77.5946");
        LatLng bord = new LatLng(lat1, lng1);
        mMap.addMarker(new MarkerOptions().position(bord).title("7Nine Delhivery"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bord, 13.0f));
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(12.9716, 77.5946)));
        polyline1.setTag("7Nine Delhivery");
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