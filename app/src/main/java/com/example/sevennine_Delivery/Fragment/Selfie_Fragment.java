package com.example.sevennine_Delivery.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Selfie_Fragment extends Fragment implements SurfaceHolder.Callback {

    public static Camera camera;
    int camBackId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    ImageView imageView,backfeed;
    CircleImageView gallery;
    ConstraintLayout constraintLayout;
    Camera.PictureCallback jpegCallback;
    int currentCameraId = camBackId;
    public static SurfaceHolder surfaceHolder;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    SurfaceView surfaceView;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    public static JSONObject lngObject;
    private static final int REQUEST_PERMISSIONS = 100;
    public  static  Bitmap selectedImage;
    Fragment selectedFragment;




    public static Selfie_Fragment newInstance() {
        Selfie_Fragment fragment = new Selfie_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_camera, container, false);
        //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //  HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);

        //  System.out.println("sdfjhsdfgjsgfjsd"+ HomePage_With_Bottom_Navigation.farm_latitude);


        imageView=view.findViewById(R.id.image);
        backfeed=view.findViewById(R.id.backfeed);

        //  selfie=view.findViewById(R.id.selfie);


        constraintLayout=view.findViewById(R.id.const_lyt);



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    if (FirmShopDetailsFragment.status.equals("Verify_Page")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("verification_status","Verify_Page");
                        selectedFragment = Verification_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        selectedFragment.setArguments(bundle);
                        transaction.commit();


                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("verification_status","Edit_Page");
                        selectedFragment = Edit_Verification_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();

                    }


                    return true;

                }
                return false;
            }
        });




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                camera.takePicture(null, null, jpegCallback);

            }

        });

//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
//                startActivityForResult(i, 100);
//            }
//        });



        mBottomSheetDialog = new BottomSheetDialog(getActivity());

        sheetView = this.getLayoutInflater().inflate(R.layout.click_a_selfie, null);
        ImageView cancel = sheetView.findViewById(R.id.cancel);
        final TextView tips = sheetView.findViewById(R.id.tips);
        final LinearLayout tips_layout = sheetView.findViewById(R.id.tips_layout);
        final TextView title = sheetView.findViewById(R.id.title);



        final TextView title_details_front = sheetView.findViewById(R.id.selfie_details);

        final TextView  front_tips1 = sheetView.findViewById(R.id.selfie_tips1);
        final TextView front_tips2 = sheetView.findViewById(R.id.selfie_tips2);
        final TextView front_tips3 = sheetView.findViewById(R.id.selfie_tips3);
        final TextView front_tips4 = sheetView.findViewById(R.id.selfie_tips4);





        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tips_layout.setVisibility(View.VISIBLE);
                tips.setTextColor(Color.parseColor("#000000"));
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tips_layout.setVisibility(View.GONE);
                tips.setTextColor(Color.parseColor("#090BCD"));

                // mBottomSheetDialog.dismiss();
            }
        });

//        title.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
////                Intent intent=new Intent(VerificationSelfie.this, ReviewPhoto.class);
////                intent.putExtra("EXTRA_SELFIE", "SELFIE");
////                startActivity(intent);
//            }
//        });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();



//        selfie.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {

        //

//        camera.takePicture(null, null, jpegCallback);
//                camera.stopPreview();
//
////NB: if you don't release the current camera before switching, you app will crash
//                camera.release();

//swap the id of the camera to be used
//swap the id of the camera to be used
//                if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {

        currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;

//                } else {
//
//                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
//                }

        camera = Camera.open(currentCameraId);

        camera.setDisplayOrientation(90);
        Camera.Parameters param;
        param = camera.getParameters();

        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            // modify parameter
            // param.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            param.setPreviewSize(352, 288);
            param.setPictureSize(1200, 900);
            //camera.setParameters(param);

            camera.getParameters().getSupportedPreviewSizes();
            camera.startPreview();
            camera.setPreviewDisplay(surfaceHolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();

        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //  gv_folder = (GridView)findViewById(R.id.gv_folder);




        jpegCallback = new Camera.PictureCallback() {

            public void onPictureTaken(byte[] data, Camera camera) {

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(destination);
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + destination);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();


                } finally {
                }

                selectedImage = BitmapFactory.decodeFile(String.valueOf(destination));


                //refreshCamera();

                Bundle bundle = new Bundle();
                bundle.putString("name", destination.getPath() );
                selectedFragment = Selfie_Preview_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                selectedFragment.setArguments(bundle);
                transaction.commit();


                /*Intent intent = new Intent(this, UploadCamera_Activity.class);
                intent.putExtra("name", destination.getPath() );
                System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq"+destination.getPath());

                startActivity(intent);


                Fragment fragment = new Gallery_Fragment() ;
                fragment.setArguments(bundle);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
                System.out.println("abc1111hhhhhhhhh"+destination.getPath());*/


                refreshCamera();
            }
        };


        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                System.out.println("abc1111hhhhhhhhh"+"granted");


            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            // fn_imagespath();
        }

        if (!isDeviceSupportCamera()) {
            //Toast.makeText(this).getApplicationContext(),
            //  "Sorry! Your device doesn't support camera",
            //   Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera

        }




        return view;
    }


    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("gggggggggggggggggggggggggggggggggg");

        try {
            // open the camera
            camera = Camera.open(camBackId);
            camera.setDisplayOrientation(90);
            Camera.Parameters p = camera.getParameters();
            p.set("jpeg-quality", 100);
            p.set("orientation", "landscape");
            p.setRotation(90);
            p.setPictureFormat(PixelFormat.JPEG);
            // p.setPreviewSize(h, w);
            camera.setParameters(p);

        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            // modify parameter
            // param.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            param.setPreviewSize(352, 288);
            param.setPictureSize(1200, 900);
            camera.setParameters(param);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();

    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // stop preview and release camera
        camera.stopPreview();
        camera.release();
        camera = null;
    }



    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            System.out.println("abcccccamerA");

            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();

            System.out.println("abcccc");
        } catch (Exception e) {
            System.out.println("abcccc11122");

            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();


        } catch (Exception e) {

        }
    }

    private boolean isDeviceSupportCamera() {
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("aaaaaaaaaaaaaaaaaaaaa"+requestCode+"gfjhygj"+resultCode);

        if (requestCode == 100&& resultCode == RESULT_OK && data != null) {

            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getActivity().getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                if (!(selectedImage==null)){

                    /*Intent intent = new Intent(getActivity().getApplicationContext(), UploadCamera_Activity.class);
                    intent.putExtra("name",getPath(imageUri));*//*
                    System.out.println("ccccccccccccccccccccccccccc"+imageUri);
                    startActivity(intent);*/


                    Bundle bundle = new Bundle();
                    bundle.putString("name",getPath(imageUri) );


                    selectedFragment = Selfie_Preview_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    selectedFragment.setArguments(bundle);
                    transaction.commit();

                }else {

                    Toast.makeText(getActivity().getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();

                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {

        }

    }


}
