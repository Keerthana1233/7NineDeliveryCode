package com.example.sevennine_Delivery;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.sevennine_Delivery.Activity.TrackerActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

public class TrackerService extends Service {
    PolylineOptions lineOptions = null;
    private static final String TAG = TrackerService.class.getSimpleName();

    SessionManager   sessionManager;
    String orderId;
    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("llllllllllllllllllllllllllllllllllllllllllllllll");

        createNotificationChannel();
        buildNotification();
        loginToFirebase();
        sessionManager=new SessionManager(getApplicationContext());



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orderId=intent.getStringExtra("orderId");
        System.out.println("llllllllllllllllllllllllllllllllllllllllllllllll"+orderId);

        return super.onStartCommand(intent, flags, startId);
    }

    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"12345")
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Tracking Initiated...")
                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.ic_pin)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(1, builder.build());
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "hello";
            String description = "osm";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("12345", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "location update " + "closed");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };
    private void loginToFirebase() {
        // Authenticate with Firebase, and request location updates
        String email = "farmpe.renewin@gmail.com";
        String password = "Ogh@0211";
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");
                    requestLocationUpdates();
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }
    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        //hope setFastestInterval not making any changes
        // but if setInterval is 10s update time is 20s for 5 its 10s which increase battery consumption rate
        //doesnt go below 5000
        request.setInterval(5000);
        //hope its like limit which controls no no changes actually
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = "locations" + "/" + orderId;
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                        ref.setValue(location);
/*                        LatLng currentlatlng = new LatLng(
                                location.getLatitude(),location.getLongitude());

                      TrackerActivity.mMap.clear();
                        TrackerActivity.mMap.addMarker(new MarkerOptions().position(TrackerActivity.store)
                                .icon(bitmapDescriptorFromVector(getBaseContext(),R.drawable.ic_shop)));

                        TrackerActivity.mMap.addMarker(new MarkerOptions().position(TrackerActivity.cust)
                                .icon(bitmapDescriptorFromVector(getBaseContext(),R.drawable.ic_location_pin)));

                        TrackerActivity.mMap.addMarker(new MarkerOptions().position(currentlatlng)
                                .icon(bitmapDescriptorFromVector(getBaseContext(),R.drawable.ic_pin)));

                        TrackerActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlatlng));

                        TrackerActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatlng, 18.5f));*/
/*
                        float[] results = new float[1];
                        Location.distanceBetween(TrackerActivity.store.latitude, TrackerActivity.store.longitude,
                                currentlatlng.latitude, currentlatlng.longitude, results);
                        float distanceInMeters = results[0];

                        System.out.println("xxxxxxxxx  = "+distanceInMeters);
                        boolean isWithinrange = distanceInMeters < 100;

                        if(isWithinrange)
                        {
                            System.out.println("you have arrived to shop");

                            callPopUp();
                        }*/
/*if(lineOptions == null) {
    String url = getDirectionsUrl(currentlatlng
            , TrackerActivity.cust, TrackerActivity.store);
    FetchUrl FetchUrl = new FetchUrl();
    System.out.println("url ddd "+url);
    Log.d("url data ", url);
    // Start downloading json data from Google Directions API
    FetchUrl.execute(url);

}
else
{
   TrackerActivity.mMap.addPolyline(lineOptions);
}*/
                    }
                }
            }, null);
        }
    }
  /*  public void callPopUp()
    {
        WindowManager windowManager2 = (WindowManager)getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  View view=layoutInflater.inflate(R.layout.map_popup, null);
        WindowManager.LayoutParams  params=new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity= Gravity.CENTER| Gravity.CENTER;
        params.x=0;
        params.y=0;
        windowManager2.addView(view, params);
    }*/
    private String getDirectionsUrl(LatLng origin, LatLng dest, LatLng intermediate) {
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
        //String key ="&key=AIzaSyDgQSmB4zuUBFUv4rzBhY_e-ZRygBRVT4U";
        String key ="&key=AIzaSyAXsifFPLFyNsK0YxpmcMld89LDRDC2SGI";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+key;
        return url;
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
            for (int i = 0; i < result.size(); i++) {
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
                lineOptions.width(15);
                lineOptions.color(Color.BLUE);
                Log.d("onPostExecute","onPostExecute lineoptions decoded");
            }
            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                TrackerActivity.mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }
}
