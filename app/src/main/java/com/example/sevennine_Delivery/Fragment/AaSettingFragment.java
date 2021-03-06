package com.example.sevennine_Delivery.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.android.volley.AuthFailureError;
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
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;
import com.example.sevennine_Delivery.volleypost.VolleyMultipartRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

import static com.android.volley.VolleyLog.TAG;
import static com.example.sevennine_Delivery.Fragment.AddNewAddressFragment.EMOJI_FILTER;

public class AaSettingFragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout backfeed,acc_info_lay,not_lay,lang_lay,help_lay,invi_lay,main_layout,change_pass_lay,kyc_lay,addr_lay,log_lay,loans_layout;
    public static TextView phone_no,name,logout;
    JSONObject lngObject;
    CircleImageView image_acc;
    boolean newState=false;
    SessionManager sessionManager;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    TextView change_pro,setting_tittle,acc_info1,addre,reque,notifi,pass_change,help,invite_frnd,logout1,loans_lay;
    String packageName;
    String update;
    public static Bitmap bitmap;
    ImageView cam_img,edit_pencil;
    String name_str,phone_str;
    LinearLayout back_feed;
    JSONArray get_location_array, imagelist_array;
    String selfie_image_id;
    public static AaSettingFragment newInstance() {
        AaSettingFragment fragment = new AaSettingFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.a_s_setting_layout1, container, false);
        invi_lay=view.findViewById(R.id.invi_lay);
      //  change_pass_lay=view.findViewById(R.id.change_pass_lay);
        lang_lay=view.findViewById(R.id.lang_lay);
        not_lay=view.findViewById(R.id.not_lay);
       // kyc_lay=view.findViewById(R.id.kyc_lay);
        addr_lay=view.findViewById(R.id.addr_lay);
        acc_info_lay=view.findViewById(R.id.acc_info_lay);
        phone_no=view.findViewById(R.id.phone_noo);
        name=view.findViewById(R.id.name_set);
        image_acc=view.findViewById(R.id.image_acc1);
        logout=view.findViewById(R.id.logout);
        cam_img=view.findViewById(R.id.cam_img);
        main_layout=view.findViewById(R.id.main_layout);
      //  edit_pencil=view.findViewById(R.id.edit_pencil);
        log_lay=view.findViewById(R.id.log_lay);
        change_pro=view.findViewById(R.id.change_pro);
        help_lay=view.findViewById(R.id.help_lay);
     //   setting_tittle=view.findViewById(R.id.setting_tittle);
        acc_info1=view.findViewById(R.id.acc_info1);
        addre=view.findViewById(R.id.addre);
        //reque=view.findViewById(R.id.reque);
        notifi=view.findViewById(R.id.notifi);
        pass_change=view.findViewById(R.id.pass_change);
        help=view.findViewById(R.id.help);
        invite_frnd=view.findViewById(R.id.invite_frnd);
        logout1=view.findViewById(R.id.logout);
        loans_layout=view.findViewById(R.id.loans_layout);
        loans_lay=view.findViewById(R.id.loans_lay);
        sessionManager=new SessionManager(getActivity());
        Window window = getActivity().getWindow();
       // back_feed=view.findViewById(R.id.back_feed);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
       /* back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("set", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });*/
        HomeMenuFragment.toolbartxt.setText("My Accounts");
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction7 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction7.replace(R.id.frame_layout, selectedFragment);
                    transaction7.commit();
                    return true;
                }
                return false;
            }
        });

        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));


          //  setting_tittle.setText(lngObject.getString("Profile").replace("\n",""));
            change_pro.setText(lngObject.getString("Change").replace("\n",""));
            acc_info1.setText(lngObject.getString("BankAccounts").replace("\n",""));
            addre.setText(lngObject.getString("MyAddresses").replace("\n",""));
        //    reque.setText(lngObject.getString("VerifyKYC").replace("\n",""));
            pass_change.setText(lngObject.getString("ChangeLanguage").replace("\n",""));
            help.setText(lngObject.getString("Policies").replace("\n",""));
            invite_frnd.setText(lngObject.getString("InviteaFriend").replace("\n",""));
            logout1.setText(lngObject.getString("Logout").replace("\n",""));
            notifi.setText(lngObject.getString("Notifications").replace("\n",""));
            System.out.println("myloanssss"+lngObject.getString("ChangeLanguage"));
            loans_lay.setText(lngObject.getString("MyLoans").replace("\n",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        loans_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   selectedFragment = AppliedLoanDetails.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("appliiii");
                transaction.commit();*/
            }
        });
        lang_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ChangeLanguageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("change_lang");
                transaction.commit();
            }
        });
        not_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = AaNotificationSetting.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("notifi");
                transaction.commit();
            }
        });

       /* kyc_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = VerifyKYC.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("kyc");
                transaction.commit();
            }
        });*/
        addr_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = NewAddressDetails_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("sett");
                transaction.commit();
            }
        });
        acc_info_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = BankAccount_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("bbbbb");
                transaction.commit();
            }
        });
        help_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = PrivacyPolicyFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("Policy");
                transaction.commit();
            }
        });

        log_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog = new BottomSheetDialog(getActivity());

                sheetView = getActivity().getLayoutInflater().inflate(R.layout.farm_view, null);
                TextView cancel = sheetView.findViewById(R.id.cancel);
                TextView save = sheetView.findViewById(R.id.save);
                TextView logout = sheetView.findViewById(R.id.logout);
                TextView enter_name = sheetView.findViewById(R.id.enter_name);
                TextView enter_name_text = sheetView.findViewById(R.id.enter_name_text);
                enter_name_text.setText("Logout");
                enter_name.setVisibility(View.GONE);
                enter_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                enter_name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});
                logout.setVisibility(View.VISIBLE);
                logout.setText("Are you sure you want to Exit?");
                cancel.setText("No");
                save.setText("Yes");
                try {
                    lngObject = new JSONObject(sessionManager.getRegId("language"));
                    // popuptxt.setText(lngObject.getString("SelectanAddressType"));
                   // save.setText(lngObject.getString("Yes"));
                   // cancel.setText(lngObject.getString("No"));
                   // deleted=lngObject.getString("Addressdeletedsuccessfully");
                    logout.setText(lngObject.getString("AreyousureyouwanttoLogout")+"?");
                    enter_name_text.setText(lngObject.getString("Logout"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManager.logoutUser();
                        getActivity().finish();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        mBottomSheetDialog.dismiss();
                    }
                });

                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }

        });

        invi_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog = new BottomSheetDialog(getActivity());

                sheetView = getActivity().getLayoutInflater().inflate(R.layout.invite_a_frnd_view, null);

                TextView cancel = sheetView.findViewById(R.id.cancel_invite);
                LinearLayout twitter=sheetView.findViewById(R.id.twitter);
                LinearLayout whatsapp=sheetView.findViewById(R.id.whatsapp);
                LinearLayout facebook=sheetView.findViewById(R.id.facebook);
                LinearLayout ista=sheetView.findViewById(R.id.ista);
                LinearLayout messenger=sheetView.findViewById(R.id.messenger);
                LinearLayout message=sheetView.findViewById(R.id.message);

                PackageManager pm = getActivity().getPackageManager();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                packageName = pm.queryIntentActivities(sendIntent, 0).toString();


                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        mBottomSheetDialog.dismiss();
                    }
                });


                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (packageName.contains("com.twitter.android")) {
                            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                            // whatsappIntent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                            whatsappIntent.setType("text/plain");
                            whatsappIntent.setPackage("com.twitter.android");
                            //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app ");
                            try {
                                startActivity(whatsappIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Twitter is not installed in this device.", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getActivity(),"Twitter is not installed in this device.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (packageName.contains("com.whatsapp")) {
                            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                            whatsappIntent.setType("text/plain");
                            whatsappIntent.setPackage("com.whatsapp");
                            //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app ");
                            try {
                                startActivity(whatsappIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Whatsapp is not installed in this device.", Toast.LENGTH_SHORT);
                            }

                        }else {
                            Toast.makeText(getActivity(), "Whatsapp is not installed in this device.", Toast.LENGTH_SHORT);

                        }
                    }
                });


                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (packageName.contains("com.facebook.katana")) {
                            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                            whatsappIntent.setType("text/plain");
                            whatsappIntent.setPackage("com.facebook.katana");
                            //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app ");
                            try {
                                startActivity(whatsappIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Facebook is not installed in this device.", Toast.LENGTH_SHORT);
                            }

                        }else {
                            Toast.makeText(getActivity(), "Facebook is not installed in this device.", Toast.LENGTH_SHORT);

                        }
                    }
                });


                ista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (packageName.contains("com.instagram")) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app");

                            sendIntent.setType("text/plain");
                            sendIntent.setPackage("com.instagram.android");
                            try {
                                startActivity(sendIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Instagram is not installed in this device.", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Instagram is not installed in this device.", Toast.LENGTH_LONG).show();

                        }
                    }
                });


                messenger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (packageName.contains("com.facebook.orca")) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app.");

                            sendIntent.setType("text/plain");
                            sendIntent.setPackage("com.facebook.orca");
                            try {
                                startActivity(sendIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Messenger is not installed in this device.", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Messenger is not installed in this device.", Toast.LENGTH_LONG).show();

                        }
                    }
                });


                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (packageName.contains("com.android.mms")) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "FarmPe Loans : Confluence of agriculture and allied loans. Select Farm loan , preferred bank at your location with loan offerings and apply online. https://play.google.com/store/apps/details?id=com.FarmPe.Finance to download the app");

                            sendIntent.setType("text/plain");
                            sendIntent.setPackage("com.android.mms");
                            try {
                                startActivity(sendIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), "Message is not installed in this device.", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Message is not installed in this device.", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });

        cam_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update="image_update";
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                startActivityForResult(i, 100); // on activity method will execute


            }
        });
        change_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update="image_update";
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                startActivityForResult(i, 100); // on activity method will execute


            }
        });
       /* edit_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*selectedFragment = EditNamePhoneNum.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("phoooonnee");
                transaction.commit();*//*
            }
        });*/
///phonenumber

        try{
            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details1, post_object, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("ggpgpgpg" + result);

                            try {

                                JSONObject jsonObject1 = result.getJSONObject("user");
                                String ProfileName1 = jsonObject1.getString("UserName");
                                System.out.println("11111" + jsonObject1.getString("FullName"));
                                String ProfilePhone = jsonObject1.getString("PhoneNo");
                                String ProfileEmail = jsonObject1.getString("EmailId");
                                String ProfileImage = jsonObject1.getString("ProfilePic");
                                System.out.println("11111" + ProfileName1);


                                //  name.setText(ProfileName1);
                                phone_no.setText(ProfilePhone);
                                name.setText(ProfileName1);

                                name.setFilters(new InputFilter[]{EMOJI_FILTER});
                                phone_no.setFilters(new InputFilter[]{EMOJI_FILTER});
                                //profile_mail.setFilters(new InputFilter[]{EMOJI_FILTER});
                                Glide.with(getActivity()).load(ProfileImage)
                                        .thumbnail(0.5f)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .error(R.drawable.ic_gallery__default)
                                        .into(image_acc);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
  //get Image

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dhfjfjd" + result);


                    try {

                        imagelist_array = result.getJSONArray("captureImagelist");

                        for (int i = 0; i < imagelist_array.length(); i++) {


                            JSONObject jsonObject1 = imagelist_array.getJSONObject(i);
                            selfie_image_id = jsonObject1.getString("CImageId");
                            String image_view = jsonObject1.getString("Image1");


                            Glide.with(getActivity()).load(image_view)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_gallery__default)
                                    .into(image_acc);

                        }




                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


                            return view;

                        }
/*

        try{
            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details1, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggpgpgpg" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("UserName");
                        System.out.println("11111" + jsonObject1.getString("FullName"));
                        String ProfilePhone = jsonObject1.getString("PhoneNo");
                        String ProfileEmail = jsonObject1.getString("EmailId");
                        String ProfileImage = jsonObject1.getString("ProfilePic");
                        System.out.println("11111" + ProfileName1);



                      //  name.setText(ProfileName1);
                        phone_no.setText(ProfilePhone);
                        name.setText(ProfileName1);

                        name.setFilters(new InputFilter[]{EMOJI_FILTER});
                        phone_no.setFilters(new InputFilter[]{EMOJI_FILTER});
                        //profile_mail.setFilters(new InputFilter[]{EMOJI_FILTER});


                        if (!(ProfileImage.equals(""))){
                            Glide.with(getActivity()).load(ProfileImage)
                                    .thumbnail(0.5f)
                                    // .crossFade()
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                            .error(R.drawable.ic_gallery__default))
                                    .into(cam_img);
                        }else{
                            try {

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("UserId", sessionManager.getRegId("userId"));


                                Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
                                    @Override
                                    public void onSuccessResponse(JSONObject result) {
                                        System.out.println("dhfjfjd" + result);


                                        try {

                                            JSONArray imagelist_array = result.getJSONArray("captureImagelist");

                                            for (int i = 0; i < imagelist_array.length(); i++) {


                                                JSONObject jsonObject1 = imagelist_array.getJSONObject(i);
                                                String image_view = jsonObject1.getString("Image1");



                                                Glide.with(getActivity()).load(image_view)
                                                        .thumbnail(0.5f)
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .error(R.drawable.avatarmale)
                                                        .into(image_acc);
                                            }




                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                       *//* Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                //  .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img);*//*


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }*/


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                System.out.println("biiiiiiiiiiiiiitttt"+bitmap);
                image_acc.setImageBitmap(bitmap);

                Snackbar snackbar = Snackbar
                        .make(main_layout, "You Changed Your Profile Photo", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                tv.setTextColor(Color.WHITE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                snackbar.show();
                //  Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
           uploadImage(getResizedBitmap(bitmap,100,100));


        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //   g_vision_controller = G_Vision_Controller.getInstance( );
//getting the image Uri

                final InputStream imageStream;

                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                //   g_vision_controller.callCloudVision(bitmap,getActivity(),"profile");
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                image_acc.setImageBitmap(bitmap);
                uploadImage(getResizedBitmap(bitmap,100,100));
             /*   int duration = 1000;
                Snackbar snackbar = Snackbar
                        .make(main_layout, "You Changed Your Profile Photo", duration);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                tv.setTextColor(Color.WHITE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                snackbar.show();*/
                //  Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    /* private void uploadImage(final Bitmap bitmap){
     *//*final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");*//*

        // progressDialog.show();
System.out.println("ennnnnnnterrrrr");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        Log.e(TAG,"afaeftagsbillvalue"+response.toString());
                        //  progressDialog.dismiss();

                        System.out.println("hfhfhfhfh"+response);

                       // mBottomSheetDialog.dismiss();
                       // prof_name.setText(enter_name.getText().toString());

                      *//*  Snackbar snackbar = Snackbar
                                .make(main_layout, "Your name has updated successfully", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();*//*

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        // progressDialog.dismiss();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",sessionManager.getRegId("userId"));

                    params.put("FullName",name.getText().toString());
                    params.put("PhoneNo",phone_no.getText().toString());
                    params.put("EmailId","abcd@gmail.com");
                    System.out.println("parametrsss"+params);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
               // params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                if (bitmap==null) {
                    params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    Log.e(TAG,"Im here " + params);
                }
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }*/



    private void uploadImage(final Bitmap bitmap){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //  String resultResponse = new String(response.data);
                        Log.e(TAG,"afaeftagsbillvaluegf"+response);
                        /*if (update!=null){
                          //  mBottomSheetDialog.dismiss();
                            name.setText(name_str.length()+"7");
                            System.out.println("pppphhhoonnee_numbrr"+name_str+phone_str);
                            phone_no.setText(phone_str.length()+"7");
                        }*/
                        //  sessionManager.save_name(name.getText().toString(),phone_no.getText().toString(),"");



                        //Toast.makeText(getActivity(),"uploaded", Toast.LENGTH_SHORT).show();
                       /* selectedFragment = SettingFragment.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout,selectedFragment);
                        ft.commit();*/

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));

                if (update != null) {

                    params.put("PhoneNo", phone_str);

                    System.out.println("parametrsss1111" + params);
                } else {

                    params.put("PhoneNo", phone_no.getText().toString());

                    System.out.println("parametrsss" + params);
                    // return params;
                }
                return params;
            }
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                // params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.e(TAG,"Imhereafaeftagsparams Imhereafaeftagsparams "+bitmap);

                if (bitmap==null){

                }else {
                    params.put("ProfilePic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }
                Log.e(TAG,"Imhereafaeftagsparams "+params);
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        if (bm==null){

            return null;
        }else {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
          //  bm.recycle();
            return resizedBitmap;
        }

    }

}
// Unexpected response code 500 for http://52.66.200.98:8282/api/Auth/UpdateRegProfile