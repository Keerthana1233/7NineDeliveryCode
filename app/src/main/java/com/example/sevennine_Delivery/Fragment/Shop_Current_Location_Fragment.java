package com.example.sevennine_Delivery.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class Shop_Current_Location_Fragment extends Fragment implements

        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    Fragment selectedFragment;
    private LocationRequest mLocationRequest;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LinearLayout main_layout,back_feed;
    Button capture_loc;
    Marker mCurrLocationMarker;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String state,statelatlongi,location_captured;
    int mDimension;
    SessionManager sessionManager;
    String curr_latitude,curr_longitude;
    LatLng latLag,latLng;
    JSONArray get_location_array;
    String location_id;
    public static JSONObject lngObject;



    public static Shop_Current_Location_Fragment newInstance() {
        Shop_Current_Location_Fragment fragment = new Shop_Current_Location_Fragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_current_location_layout, container, false);
        displayLocationSettingsRequest(getActivity());
        //  resutText = (TextView) view.findViewById(R.id.curr_address);


        back_feed = view.findViewById(R.id.back_feed);
        //    confirm_loc = view.findViewById(R.id.confirm_loc);
        capture_loc = view.findViewById(R.id.capture_loc);
        // currentaddress = view.findViewById(R.id.curr_address);
        main_layout = view.findViewById(R.id.main_layout);
        //getSupportActionBar().setTitle("Map Location Activity");
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);



        View mapView = mapFrag.getView();
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }


        sessionManager = new SessionManager(getActivity());









        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {




                    if(getArguments().getString("Edit_Fragment").equals("shop_location")){

                        selectedFragment = Verification_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();



                    }else if (getArguments().getString("Edit_Fragment").equals("curr_loc_edit")){

                        selectedFragment = Edit_Verification_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                    }



                }
                return false;
            }
        });



        back_feed.setOnClickListener(this);
        capture_loc.setOnClickListener(this);

        return view;
    }


    public void  shop_current_location(){
        System.out.println("hrrrjjkjlklohnklholk"+getArguments().getString("Edit_Fragment"));

        try{
//add
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Latitude",curr_latitude);
            jsonObject.put("Longitude",curr_longitude);
            jsonObject.put("CapturedLocation",state);
            jsonObject.put("CImageId","1");
            jsonObject.put("UserId",sessionManager.getRegId("userId"));

//edit
            if(getArguments().getString("Edit_Fragment").equals("curr_loc_edit")) {


                jsonObject.put("Latitude", curr_latitude);
                jsonObject.put("Longitude", curr_longitude);
                jsonObject.put("CapturedLocation", statelatlongi);
                jsonObject.put("CLocationId", location_id);
                jsonObject.put("CImageId", "1");
                jsonObject.put("UserId", sessionManager.getRegId("userId"));
                System.out.println("hfdsfhhhhhhhhh" + jsonObject);


            }

            Crop_Post.crop_posting(getActivity(), Urls.Add_CurrentLocation, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("hfdsf" + result);

                    try{

                        String status = result.getString("Status");
                        String message = result.getString("Message");


                        if(status.equals("1")){

                            //    Toast.makeText(getActivity(), "welcome", Toast.LENGTH_SHORT).show();

                            selectedFragment = Edit_Verification_Fragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout1, selectedFragment);
                            transaction.addToBackStack("map_loctn");
                            transaction.commit();

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mapFrag.getMapAsync(this);
            configureCameraIdle();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        captured_location();
        configureCameraIdle();
        mGoogleMap.setOnCameraIdleListener(onCameraIdleListener);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),

                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }

        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }



    }


    private void captured_location() {



        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls. Get_Shop_Location, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dhfjfjd" + result);


                    try{
                        get_location_array = result.getJSONArray("clocationList");

                        for(int i = 0;i<get_location_array.length();i++){

                            JSONObject jsonObject1 = get_location_array.getJSONObject(i);
                            double lat,lng;

                            // mClusterManager.addItem(new Person(jsonObject1.getDouble("Latitude"), jsonObject1.getDouble("Longitude"), jsonObject1.getString("CapturedLocation"), jsonObject1.getString("CLocationId")));

                            location_id = jsonObject1.getString("CLocationId");
                            System.out.println("khhcmnc"+location_id);
                            curr_latitude = jsonObject1.getString("Latitude");
                            curr_longitude = jsonObject1.getString("Longitude");
                           location_captured = jsonObject1.getString("CapturedLocation");




//                                    Glide.with(getActivity()).load(image_view)
//                                           .thumbnail(0.5f)
//                                           //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
//                                                   .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)
//                                                   .error(R.drawable.avatarmale))
//                                           .into(capture_image);
//

                        }
                        /*Setting Marker*/
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.valueOf(curr_latitude), Double.valueOf(curr_longitude)));
                        markerOptions.title(location_captured);
                        mGoogleMap.addMarker(markerOptions);


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
//

        }catch(Exception e){
            e.printStackTrace();
        }


    }





    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }



    private void displayLocationSettingsRequest(Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        //  locationRequest.setFastestInterval(10000 / 2);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());




        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {

                final Status status = result.getStatus();

                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");

                        break;


                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }


                        break;


                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");

                        break;
                }
            }
        });

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    @Override
    public void onLocationChanged(Location location)

    {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();

        }

        latLng = new LatLng(location.getLatitude(), location.getLongitude());


        Geocoder geocoder = new Geocoder(getActivity());
        try {

            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {

                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();

                // Toast.makeText(getActivity(), state, Toast.LENGTH_LONG).show();
                if (!locality.isEmpty() && !country.isEmpty())

                    //  currentaddress.setText(locality + "  " + country);
                    curr_latitude = String.valueOf(addressList.get(0).getLatitude());
                curr_longitude = String.valueOf(addressList.get(0).getLongitude());
                state = addressList.get(0).getLocality()+","+addressList.get(0).getAdminArea();
               // statelatlongi=addressList.get(0).getLongitude()+","+addressList.get(0).getLatitude();

                System.out.println("movelocation2"+ statelatlongi);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("djhgfhfdhfddjksdh" + curr_latitude);


        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

        mGoogleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(100)
                .fillColor(getResources().getColor(R.color.lite_blue))
                .strokeWidth(0));

        //   mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })

                        .create()
                        .show();


            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }


    private void configureCameraIdle() {

        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mGoogleMap.getCameraPosition().target;
                System.out.println("movelocation2"+ statelatlongi);


                Geocoder geocoder = new Geocoder(getActivity());
                try {

                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {

                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();

                        // Toast.makeText(getActivity(), state, Toast.LENGTH_LONG).show();
                        if (!locality.isEmpty() && !country.isEmpty())

                            //  currentaddress.setText(locality + "  " + country);
                            curr_latitude = String.valueOf(addressList.get(0).getLatitude());
                        curr_longitude = String.valueOf(addressList.get(0).getLongitude());
                        statelatlongi = addressList.get(0).getLocality()+","+addressList.get(0).getAdminArea();
                        //statelatlongi=addressList.get(0).getLongitude()+","+addressList.get(0).getLatitude();

                        System.out.println("movelocation2"+ statelatlongi);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }

                return;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.back_feed: /** Start a new Activity MyCards.java */
                if(getArguments().getString("Edit_Fragment").equals("shop_location")){

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("shop_locatn", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                }else if (getArguments().getString("Edit_Fragment").equals("curr_loc_edit")){

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("map_locatn", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                break;

            case R.id.capture_loc:
                shop_current_location();
                break;
        }
    }









        /*    mIconGenerator1.setBackground(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
           // mIconGenerator1.setTextAppearance(R.style.MineCustomTabText);
            final Bitmap icon = mIconGenerator1.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));*/
/*

            mImageView.setImageResource(R.drawable.ic_psychoanalysis);
            Bitmap icon = mIconGenerator.makeIcon();
            mIconGenerator.setBackground(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_profile));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
*/



}





