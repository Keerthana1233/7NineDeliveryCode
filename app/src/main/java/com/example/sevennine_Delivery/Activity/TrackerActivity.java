package com.example.sevennine_Delivery.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.TrackerService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;


public class TrackerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST = 1;

    public static GoogleMap mMap;

    public static LatLng store,cust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_track);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button = findViewById(R.id.track);
        Button googlemap =  findViewById(R.id.gmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store = (LatLng) getIntent().getExtras().get("store");
                cust = (LatLng) getIntent().getExtras().get("cust");
                startService(new Intent(getBaseContext(), TrackerService.class));
            }
        });

        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            //finish();
        }


        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String custloc = cust.latitude+","+cust.longitude;
                String storeloc = store.latitude+","+store.longitude;
                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+custloc+"&waypoints="+storeloc+"&mode=l");
               // Uri gmmIntentUri = Uri.parse("google.navigation:q="+store.latitude+","+store.longitude+"&mode=l");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Google Maps are already in use", Toast.LENGTH_SHORT).show();
                    //map is used by other apps
                }
            }
        });





/*        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }*/
    }

    private void startTrackerService() {
        startService(new Intent(this, TrackerService.class));
        //commented to open the app
      //  finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
           // startTrackerService();
        } else {
        //    finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;


/*
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.addMarker(new MarkerOptions().position(store)
                .icon(bitmapDescriptorFromVector(this,R.drawable.ic_shop)));

        mMap.addMarker(new MarkerOptions().position(cust)
                .icon(bitmapDescriptorFromVector(this,R.drawable.ic_location_pin)));


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(store);
        builder.include(cust);
     //   builder.include(dellatlong);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));*/

       /* mMap.addMarker(new MarkerOptions().position(dellatlong)
                .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pin)));*/
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
