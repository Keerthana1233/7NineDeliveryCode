package com.example.sevennine_Delivery.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.volleypost.VolleyMultipartRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class VoterId_Back_Preview_Details_Fragment extends Fragment {



    public static RecyclerView recyclerView;
    LinearLayout back_feed,main_layout,upload_img,tips_voter_layout,facebook,instagram;
    Fragment selectedFragment;
    String packageName;
    EditText farm_name,description,cont_person_name,mobile_no,email_id;
    public static String farm_name_string,cont_name,mob_no,email_id_strg;
    TextView toolbar_title,take_voter_photo,preview_tips,tips_1,tips_2,tips_3,tips_4,upload_imge;
    ImageView b_arrow;
    public static String FACEBOOK_URL = "https://www.facebook.com/FarmPe-698463080607409/";
    public static String FACEBOOK_PAGE_ID = "FarmPe-698463080607409";
    public static String imageUri,imageUri1;
    ImageView imageView,correct_icon,dismiss_icon;
    String status,message;
    public static JSONObject lngObject;
    SessionManager sessionManager;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;


    public static VoterId_Back_Preview_Details_Fragment newInstance() {
        VoterId_Back_Preview_Details_Fragment fragment = new VoterId_Back_Preview_Details_Fragment();

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
        tips_voter_layout=view.findViewById(R.id.tips_voter_layout);
        take_voter_photo=view.findViewById(R.id.take_voter_photo);

        tips_voter_layout.setVisibility(View.VISIBLE);


        upload_imge=view.findViewById(R.id.upload_imge);
        preview_tips=view.findViewById(R.id.preview_tips);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        tips_1=view.findViewById(R.id.tips_1);
        tips_2=view.findViewById(R.id.tips_2);
        tips_3=view.findViewById(R.id.tips_3);
        tips_4=view.findViewById(R.id.tips_4);



        sessionManager = new SessionManager(getActivity());


        String back_id = (getArguments().getString("name1"));

        imageUri1 = back_id;



//            Glide.with(getActivity()).load("file://" + back_id)
//                    .thumbnail(0.5f)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageView);
//

        Glide.with(getActivity()).load("file://" + back_id)
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.avatarmale))
                .into(imageView);








        back_feed.setOnClickListener(new View.OnClickListener() {
          @Override
             public void onClick(View view) {

                   Bundle bundle = new Bundle();
                   bundle.putString("VoterBack_Fragment","voter_back");
                   selectedFragment = Voter_Id_Back_Fragment.newInstance();
                   FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.frame_layout1, selectedFragment);
                   transaction.commit();

                 }
                 });



        take_voter_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("VoterBack_Fragment","voter_back");
                selectedFragment = Voter_Id_Back_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();

            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Bundle bundle = new Bundle();
                    bundle.putString("VoterBack_Fragment",getArguments().getString("VoterBack_Fragment"));
                    selectedFragment = Voter_Id_Back_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });





   //     b_arrow=view.findViewById(R.id.b_arrow);

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
//
//                tips_layout.setVisibility(View.VISIBLE);
//                take_photo.setVisibility(View.VISIBLE);
//                tips.setTextColor(Color.parseColor("#000000"));
//            }
//        });





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


                uploadImage(getResizedBitmap(Voter_Id_Back_Fragment.selectedImage, 100, 100));



                // uploadImage(getResizedBitmap(VoterId_Photo_Fragment.selectedImage, 100, 100));

//                selectedFragment = Shop_Camera_Fragment_Edit.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();


            }
        });


        return view;

    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void uploadImage(final Bitmap bitmap){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");
        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Add_Back_Voter_ID_Details,
                new Response.Listener<NetworkResponse>(){

                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();  int duration=1000;


                        selectedFragment = Edit_Verification_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();

//                        Toast.makeText(getActivity(),"Profile Details Updated Successfully", Toast.LENGTH_SHORT).show();
//                        selectedFragment = SettingFragment.newInstance();
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.frame_layout,selectedFragment);
//                        ft.commit();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {


            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));

                    Toast.makeText(getActivity(),volleyError.getMessage(), Toast.LENGTH_SHORT).show();

//                    int duration=1000;
//                    Snackbar snackbar = Snackbar
//                            .make(main_layout, volleyError.getMessage(),duration);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
//                    tv.setTextColor(Color.WHITE);
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//
//                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//                    } else {
//
//                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                    }
//
//                    snackbar.show();


                    volleyError = error;
                }

                return volleyError;
            }




            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.e(TAG,"Im here " + params);

                if (bitmap!=null) {

                    params.put("DLicenseBackImage", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }

                Log.e(TAG,"Im here " + params);
                return params;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));

                params.put("DBackId","0");

                //  System.out.println("Latitude11111111"+String.valueOf(Farms_MapView_Fragment.a));
                //  params.put("FarmDescription", description.getText().toString());
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
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
