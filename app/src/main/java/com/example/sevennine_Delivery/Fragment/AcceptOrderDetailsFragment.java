package com.example.sevennine_Delivery.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Adapter.AcceptOrderDetailsAdapter;
import com.example.sevennine_Delivery.Bean.OrderDetailBean;
import com.example.sevennine_Delivery.DataParser;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.TrackerService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class AcceptOrderDetailsFragment extends Fragment {

    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    String orderid;
    SessionManager sessionManager;
    AcceptOrderDetailsAdapter madapter;
    JSONObject lngObject;
    TextView toolbar_title,mapview;
    Fragment selectedFragment;
    GoogleMap pubGoogleMap;
    PolylineOptions lineOptions = null;
    public static AcceptOrderDetailsFragment newInstance() {
        AcceptOrderDetailsFragment fragment = new AcceptOrderDetailsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details_layout2, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orderid = bundle.getString("orderId");
        }
        HomeMenuFragment.menuimg.setImageResource(R.drawable.ic_go_back_left_arrow_);
        HomeMenuFragment.toolbartxt.setText("Order Details");
        HomeMenuFragment.notificationimg.setVisibility(View.GONE);

   mapview=view.findViewById(R.id.mapview);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        HomeMenuFragment.menuimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction7.replace(R.id.frame_layout1, selectedFragment);
                transaction7.commit();

            }
        });
       mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent serviceIntent = new Intent(getActivity(), TrackerService.class);
                serviceIntent.putExtra("orderId", orderid);
                getActivity().startService(serviceIntent);
                AlertDialog.Builder   alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View convertView = (View) inflater.inflate(R.layout.confirm_order, null);
                alertDialog.setView(convertView);
                alertDialog.show();
                //12.8441° N, 77.6794° E
                final LatLng storelatlong = new LatLng(Double.parseDouble("12.8441")
                        ,Double.parseDouble("77.6794"));
                //12.9698° N, 77.7500° E
                final LatLng custlatlong = new LatLng(Double.parseDouble("12.9698"),
                        Double.parseDouble("77.7500"));
                final LatLng dellatlong = new LatLng(Double.parseDouble("12.9242199")
                        ,Double.parseDouble(" 77.51911949999999"));
                final MapView mapView = convertView.findViewById(R.id.map);
                // finally ..... thnx god
                mapView.onCreate(new Bundle());
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        pubGoogleMap=googleMap;
                        String url = getDirectionsUrl(dellatlong
                                , custlatlong,storelatlong);
                        FetchUrl FetchUrl = new FetchUrl();
                        System.out.println("url ddd "+url);
                        Log.d("url data ", url);
                        // Start downloading json data from Google Directions API
                        FetchUrl.execute(url);
                        pubGoogleMap.addMarker(new MarkerOptions().position(storelatlong)
                                .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_shop)));
                        pubGoogleMap.addMarker(new MarkerOptions().position(custlatlong)
                                .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_location_pin)));
                        pubGoogleMap.addMarker(new MarkerOptions().position(dellatlong)
                                .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_pin)));
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(storelatlong);
                        builder.include(custlatlong);
                        builder.include(dellatlong);
                        pubGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));
                        pubGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                int permission = ContextCompat.checkSelfPermission(getActivity(),
                                        Manifest.permission.ACCESS_FINE_LOCATION);
                                if (permission == PackageManager.PERMISSION_GRANTED) {
                                    LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                                    if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                        Toast.makeText(getActivity(), "Please enable location services", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "All done", Toast.LENGTH_SHORT).show();

                                        String cust = custlatlong.latitude+","+custlatlong.longitude;
                                        String store = storelatlong.latitude+","+storelatlong.longitude;



                                        // activity.startService(new Intent(activity, TrackerService.class));
                                        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+cust+"&waypoints="+store+"&mode=l");
                                        // Uri gmmIntentUri = Uri.parse("google.navigation:q="+store.latitude+","+store.longitude+"&mode=l");
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            getActivity().startActivity(mapIntent);
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(),"Google Maps are already in use",Toast.LENGTH_SHORT).show();
                                            //map is used by other apps
                                        }
                                       /*             Intent intent = new Intent(activity, TrackerActivity.class);
                                                    intent.putExtra("store",storelatlong);
                                                    intent.putExtra("cust",custlatlong);
                                                    activity.startActivity(intent);*/
                                    }
                                }
                                else
                                    Toast.makeText(getActivity(),"Please allow location permission",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
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

        OrderDetailBean bean=new OrderDetailBean("Parle-G Gold Milk Glucose..","1","Rs.100","Rs.2","Rs.2","");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        //   newOrderBeansList.add(bean);

        madapter=new AcceptOrderDetailsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);


        //    LoanInformation();


        return view;
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private String getDirectionsUrl(LatLng origin, LatLng dest,LatLng intermediate) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String str_intermediate ="&waypoints="+ intermediate.latitude + "," + intermediate.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest +str_intermediate+ "&" + sensor + "&" + mode;
        // Output format
        String output = "json";
        String key ="&key=AIzaSyDgQSmB4zuUBFUv4rzBhY_e-ZRygBRVT4U";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+key;
        return url;
    }
    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            downloadUrl:
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl", data);
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0]);
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());
                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());
            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            lineOptions = new PolylineOptions();
            System.out.println("sss  = "+result.size()+" no "+result);
            // Traversing through all the routes
            // made it 1 as its was giving out all paths making a loop
            for (int i = 0; i < 1; i++) {
                points = new ArrayList<>();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                Log.d("onPostExecute","onPostExecute lineoptions decoded");
            }
            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                pubGoogleMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

}
