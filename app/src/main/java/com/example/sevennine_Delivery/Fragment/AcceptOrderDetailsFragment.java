package com.example.sevennine_Delivery.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevennine_Delivery.Activity.GPSTracker;
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
import com.google.android.gms.maps.SupportMapFragment;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.android.volley.VolleyLog.TAG;

public class AcceptOrderDetailsFragment extends Fragment implements LocationListener, OnMapReadyCallback {

    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    String orderid,addr,mode,amount,createddate,mobilestr,pronamestr,proimgstr;
    String latid,langid,custlat,custlong;
    String dellat,dellang,mask;
    SessionManager sessionManager;
    AcceptOrderDetailsAdapter madapter;
    JSONObject lngObject;
    TextView toolbar_title;
    Fragment selectedFragment;
    GoogleMap pubGoogleMap;
    PolylineOptions lineOptions = null;

    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    LocationManager locationManager;
    public double latitude; // latitude
    public  double longitude; // longitude
    int tot_seconds = 5000;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    Location loc;
    TextView orderidtxt,modetxt,amounttxt,addrtxt,nametxt,orderdatetxt,phoneno,itemscosttxt,tamounttxt;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    private LatLng latLng;
    SupportMapFragment mapFrag;
    GPSTracker gpsTracker;
    public static ImageView menuimg,notificationimg;
    public static TextView toolbartxt;
    Date date;
    public static AcceptOrderDetailsFragment newInstance() {
        AcceptOrderDetailsFragment fragment = new AcceptOrderDetailsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details_layout2, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        back_feed=view.findViewById(R.id.back_feed);
        gpsTracker=new GPSTracker(getActivity());
        orderidtxt=view.findViewById(R.id.orderid);
        orderdatetxt=view.findViewById(R.id.orderdate);
        modetxt=view.findViewById(R.id.mode);
        amounttxt=view.findViewById(R.id.amount);
        itemscosttxt=view.findViewById(R.id.itemscost);
        tamounttxt=view.findViewById(R.id.tamount);
        nametxt=view.findViewById(R.id.name);
        addrtxt=view.findViewById(R.id.addr);
        orderdatetxt=view.findViewById(R.id.orderdate);
        phoneno=view.findViewById(R.id.phoneno);
        locationManager = (LocationManager) getActivity().getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest =findUnAskedPermissions(permissions);
        gpsTracker = new GPSTracker(getActivity());

        toolbartxt=view.findViewById(R.id.toolbartxt);
        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }
            // get location
            getLocation();
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orderid = bundle.getString("orderId");
            latid = bundle.getString("latidkey");
            langid = bundle.getString("langidkey");
           custlat= bundle.getString("custlatidkey");
           custlong= bundle.getString("custlangidkey");
            System.out.println("rtyr"+custlat);
            amount = bundle.getString("totalamount");
            createddate = bundle.getString("orderdate");
            addr = bundle.getString("addr");
            mode = bundle.getString("mode");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dtStart = createddate;
            System.out.println("getCreateddate"+dtStart);

            try {
                date = format.parse(dtStart);
                orderdatetxt.setText(date.getDate()+"/"+(1+date.getMonth())+"/"+(1900+date.getYear()+" "+date.getHours()+":"+date.getMinutes()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderidtxt.setText(orderid);
            amounttxt.setText(amount);
            tamounttxt.setText(amount);
            itemscosttxt.setText(amount);
            addrtxt.setText(addr);
            modetxt.setText(mode);
            mobilestr = bundle.getString("mobile");
            try {
                mask = maskString(mobilestr, 4, 10, '*');
            } catch (Exception e) {
                e.printStackTrace();
            }
            // String result = number.substring(number.length() - 4).replace(String.valueOf(number.length()), "*");
            phoneno.setText(mask);
           // phoneno.setText(mobilestr);
        }
        sessionManager = new SessionManager(getActivity());
        dellat = sessionManager.getRegId("latitude");
        dellang = sessionManager.getRegId("longtitude");
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;

            }
        });

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("accept", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("accept", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;

            }
        });
        Trackingmap();
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        pronamestr = bundle.getString("proname");
        proimgstr = bundle.getString("proimg");
        OrderDetailBean bean=new OrderDetailBean(pronamestr,"1",amount,"Rs.2","Rs.2",proimgstr);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        //   newOrderBeansList.add(bean);
        madapter=new AcceptOrderDetailsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
        //    LoanInformation();
        return view;

    }
    public void Trackingmap() {
        Intent serviceIntent = new Intent(getActivity(), TrackerService.class);
        serviceIntent.putExtra("orderId", orderid);
        getActivity().startService(serviceIntent);
        // finally ..... thnx god
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        View mapView = mapFrag.getView();
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
//String key ="&key=AIzaSyBMScKP4py29BqVV_LwXmMf8qANqNxpF0Y";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+key;
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pubGoogleMap = googleMap;
       /* String[] latValues = latid.split(" ");
        float sum = 0;
        for (int i = 0; i < latValues.length; i++) {
            if (!latValues[i].equals("null"))
                sum = sum + Float.valueOf(latValues[i].trim()).floatValue();
        }
        latid = Float.toString(sum / (float) latValues.length);
        String[] longValues = langid.split(" ");
        float sum2 = 0;
        for (int i = 0; i < longValues.length; i++) {
            if (!longValues[i].equals("null"))
                sum2 = sum2 + Float.valueOf(longValues[i].trim()).floatValue();
        }
        langid = Float.toString(sum / (float) latValues.length);*/
        final LatLng storelatlong = new LatLng(Double.parseDouble(latid),
                Double.parseDouble(langid));
        //12.9698° N, 77.7500° E
        final LatLng custlatlong = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
        final LatLng dellatlong = new LatLng(Double.parseDouble(dellat),Double.parseDouble(dellang));
        System.out.println("dellatlong"+dellatlong);
        System.out.println("slatlong"+storelatlong);
        String url = getDirectionsUrl(dellatlong
                , custlatlong, storelatlong);
        FetchUrl FetchUrl = new FetchUrl();
        System.out.println("url ddd " + url);
        Log.d("url data ", url);
        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
        pubGoogleMap.addMarker(new MarkerOptions().position(storelatlong)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_shop)));
        pubGoogleMap.addMarker(new MarkerOptions().position(custlatlong)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location_pin)));
        pubGoogleMap.addMarker(new MarkerOptions().position(dellatlong)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_pin)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(storelatlong);
        builder.include(custlatlong);
        builder.include(dellatlong);
        //Setting the width and height of your screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        pubGoogleMap.moveCamera((CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, padding)));
        pubGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                int permission = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getActivity(), "Please enable location services", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "All done", Toast.LENGTH_SHORT).show();

                        String cust = custlatlong.latitude + "," + custlatlong.longitude;
                        String store = storelatlong.latitude + "," + storelatlong.longitude;


                        // activity.startService(new Intent(activity, TrackerService.class));
                        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + cust + "&waypoints=" + store + "&mode=l");
                        // Uri gmmIntentUri = Uri.parse("google.navigation:q="+store.latitude+","+store.longitude+"&mode=l");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            getActivity().startActivity(mapIntent);
                        } else {
                            Toast.makeText(getActivity(), "Google Maps are already in use", Toast.LENGTH_SHORT).show();
                            //map is used by other apps
                        }
                                       /*             Intent intent = new Intent(activity, TrackerActivity.class);
                                                    intent.putExtra("store",storelatlong);
                                                    intent.putExtra("cust",custlatlong);
                                                    activity.startActivity(intent);*/
                    }
                } else
                    Toast.makeText(getActivity(), "Please allow location permission", Toast.LENGTH_SHORT).show();
            }
        });
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
                lineOptions.color(R.color.colorPrimary);
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
    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        Log.e(TAG,Double.toString(loc.getLatitude()));
        Log.e(TAG,Double.toString(loc.getLongitude()));
        Log.e(TAG, DateFormat.getTimeInstance().format(loc.getTime()));
        sessionManager.saveLatLng(String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
        dellat = sessionManager.getRegId("latitude");
        dellang = sessionManager.getRegId("longtitude");
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(loc);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
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
