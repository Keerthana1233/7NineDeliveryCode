package com.example.sevennine_Delivery.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sevennine_Delivery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;




public class Selfie_Preview_Fragment extends Fragment {



    public static RecyclerView recyclerView;
    LinearLayout back_feed,main_layout,upload_img,whatsapp,twitter,facebook,instagram;
    Fragment selectedFragment;
    String packageName;
    EditText farm_name,description,cont_person_name,mobile_no,email_id;
    public static String farm_name_string,cont_name,mob_no,email_id_strg;
    TextView toolbar_title,take_photo,upload_imge,tips_selfie,selfie_tips_1,selfie_tips_2,selfie_tips_3,selfie_tips_4;
    ImageView b_arrow;
    public static JSONObject lngObject;
    public static String FACEBOOK_URL = "https://www.facebook.com/FarmPe-698463080607409/";
    public static String FACEBOOK_PAGE_ID = "FarmPe-698463080607409";
    public static String imageUri;
    ImageView imageView,correct_icon,dismiss_icon;
    String status,message;

    LinearLayout tips_selfie_layout;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;


    public static Selfie_Preview_Fragment newInstance() {
        Selfie_Preview_Fragment fragment = new Selfie_Preview_Fragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_camera_preview, container, false);



        back_feed=view.findViewById(R.id.back_feed);

        // continue_4= view.findViewById(R.id.continue_4);
        // farm_name=view.findViewById(R.id.farm_name);
        // description=view.findViewById(R.id.desce);
        imageView=view.findViewById(R.id.capt_img);
        upload_img=view.findViewById(R.id.upload_img);
        take_photo=view.findViewById(R.id.take_photo);
        tips_selfie_layout=view.findViewById(R.id.tips_selfie_layout);

        tips_selfie_layout.setVisibility(View.VISIBLE);



        upload_imge=view.findViewById(R.id.upload_imge);

        tips_selfie=view.findViewById(R.id.tips_selfie);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        selfie_tips_1=view.findViewById(R.id.selfie_tips_1);
        selfie_tips_2=view.findViewById(R.id.selfie_tips_2);
        selfie_tips_3=view.findViewById(R.id.selfie_tips_3);
        selfie_tips_4=view.findViewById(R.id.selfie_tips_4);











        String id= (getArguments().getString("name"));
        imageUri = id;



        Glide.with(getActivity()).load("file://" + id)
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.avatarmale))
                .into(imageView);



//
//        Glide.with(getActivity()).load("file://" + id)
//                .thumbnail(0.5f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageView);


//
//        Glide.with(getActivity()).load("file://" + id)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
//                        .skipMemoryCache(true))
//                .into(imageView);


//        Glide.with(getActivity()).load("file://" + id)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true))
//                .into(imageView);



//        farm_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(farm_name.hasFocus()){
//                    //et1.setCursorVisible(true);
////                    street_add.setActivated(true);
////                    street_add.setPressed(true);
//                    farm_name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30) });
//
//                }
//            }
//        });

        // mobile_no=view.findViewById(R.id.mobile_no);


        main_layout=view.findViewById(R.id.linearLayout);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // setupUI(main_layout);


        // cont_person_name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30) });


        Resources resources = getResources();
        PackageManager pm = getActivity().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        packageName = pm.queryIntentActivities(sendIntent, 0).toString();


//
//        mBottomSheetDialog = new BottomSheetDialog(getActivity());
//
//        sheetView = this.getLayoutInflater().inflate(R.layout.click_a_selfie, null);
//        ImageView cancel = sheetView.findViewById(R.id.cancel);
//        final TextView tips = sheetView.findViewById(R.id.tips);
//        final TextView take_photo = sheetView.findViewById(R.id.take_photo);
//        final LinearLayout tips_layout = sheetView.findViewById(R.id.tips_layout);
//
//
//
//        cancel.setVisibility(View.GONE);
//
//        tips.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tips_layout.setVisibility(View.VISIBLE);
//                take_photo.setVisibility(View.VISIBLE);
//                tips.setTextColor(Color.parseColor("#000000"));
//            }
//        });
//

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("Selfie_Edit","verify_selfie");
                selectedFragment = Selfie_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.commit();


            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    selectedFragment = Selfie_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();



                    return true;
                }
                return false;
            }
        });


        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedFragment = Selfie_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();

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

//        mBottomSheetDialog.setContentView(sheetView);
//        mBottomSheetDialog.show();






//
//        back_feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                selectedFragment = Shop_Camera_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();
//
//            }
//        });
//


        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //       uploadImage(getResizedBitmap(Selfie_Fragment.selectedImage, 100, 100));



                // uploadImage(getResizedBitmap(VoterId_Photo_Fragment.selectedImage, 100, 100));

                selectedFragment = Verification_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();


            }
        });


        return view;

    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }






    public Bitmap getResizedBitmap(Bitmap bm1, int newWidth, int newHeight) {

        if (bm1==null){

            return null;

        }else {
            System.out.println("llllllllllllllllllllllllllllll"+newHeight);
            int width = bm1.getWidth();
            int height = bm1.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            // "RECREATE" THE NEW BITMAP

            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm1, 0, 0, width, height, matrix, false);
            //  bm1.recycle();
            return resizedBitmap;
        }
    }



    public void setupUI(View view) {


        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }


        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try{
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }catch(AssertionError e){
                e.printStackTrace();
            }
        }
    }




    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return FACEBOOK_URL;
            } else { //older versions of fb app
                return FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };


    public static InputFilter EMOJI_FILTER1 = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }

                String filtered = "";
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isWhitespace(character)) {
                        filtered += character;
                    }
                }
                return filtered;

            }
            if (keepOriginal)
                return null;

            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };






}
