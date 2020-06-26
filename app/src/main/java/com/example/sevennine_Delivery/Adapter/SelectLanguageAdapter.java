package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sevennine_Delivery.Bean.SelectLanguageBean;
import com.example.sevennine_Delivery.Fragment.ChangeLanguageFragment;
import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.example.sevennine_Delivery.Urls;
import com.example.sevennine_Delivery.Volly_class.Crop_Post;
import com.example.sevennine_Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.MyViewHolder>  {
    private List<SelectLanguageBean> productList;
    SelectLanguageBean selectLanguageBean;
    Activity activity;
    JSONArray lng_array;
    Fragment selectedFragment;
    Date o_date;
    SessionManager sessionManager;
    int selected_position=0;
    String lang_str;

    public static CardView cardView;
    public SelectLanguageAdapter(Activity activity, List<SelectLanguageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
        sessionManager=new SessionManager(activity);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView language_name;
        LinearLayout submit_langu;
        public RadioButton radioButton;
        ImageView right_img,image_view;

        public MyViewHolder(View view) {
            super(view);
            language_name=view.findViewById(R.id.lang_text);
            submit_langu=view.findViewById(R.id.lang_lay);
            right_img=view.findViewById(R.id.right_img);
            image_view=view.findViewById(R.id.image_view);
            //radioButton=view.findViewById(R.id.lang_radio);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_lang_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SelectLanguageBean products = productList.get(position);
        lang_str = products.getVendor();

        /*if (position==selected_position){
            holder.radioButton.setChecked(true);
        }else{
            holder.radioButton.setChecked(false);
        }*/

        if (lang_str.equals(sessionManager.getRegId("language_name"))) {

            holder.right_img.setImageResource(R.drawable.ic_verified_green);
            holder.language_name.setTypeface(null, Typeface.BOLD);


        } else {

            System.out.println("sfdsdfsdxvvvv" + sessionManager.getRegId("language_name"));


            if((sessionManager.getRegId("language_name").equals(""))){

                if(position == 0){

                    holder.right_img.setImageResource(R.drawable.ic_verified_green);
                    holder.language_name.setTypeface(null, Typeface.BOLD);

                }



            }else{

                holder.right_img.setImageResource(R.drawable.ic_verified_grey);
                holder.language_name.setTypeface(null, Typeface.NORMAL);
            }

        }


       /* if (selected_position==position){
            holder.right_img.setImageResource(R.drawable.ic_verified_green);
            //  holder.lng_rad_but.setBackgroundColor(Color.GREEN);
            holder.language_name.setTypeface(null, Typeface.BOLD);

            getLang(products.getLanguageid());

        }else {

            holder.right_img.setImageResource(R.drawable.ic_verified_grey);
            holder.language_name.setTypeface(null, Typeface.BOLD);


        }*/
        holder.language_name.setText(products.getVendor());

        holder.submit_langu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sessionManager.saveLanguage_name(products.getVendor());
                getLang(products.getLanguageid());
                lang_str = products.getVendor();
                //  sessionManager.saveLanguage(lng_list);
                selected_position = position;
                notifyDataSetChanged();



               /* System.out.println("selecteddddd"+products.getVendor());
                sessionManager.saveLanguage_name(products.getVendor());
                getLang(products.getLanguageid());
                lang_str = products.getVendor();
                selected_position = position;
                notifyDataSetChanged();*/

            }
        });

        Glide.with(activity).load(products.getImageicon())
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(holder.image_view);


       /* holder.right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                sessionManager.saveLanguage_name(products.getVendor());
                getLang(products.getLanguageid());
               // AaSettingFragment.sub_lang.setText(sessionManager.getRegId("language_name"));

            }
        });*/

    }

    private void getLang(int id) {

        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id",id);


            System.out.print("iiidddddd"+ id);

            Crop_Post.crop_posting(activity, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try{

                        sessionManager.saveLanguage(result.toString());

                        String lang_title1 = result.getString("MyLanguage");
                        String select_title = result.getString("SelectYourLanguage");
                        String continue_btnn = result.getString("PROCEED").replace("\n","");
                       // ChangeLanguageFragment.lang_title.setText(lang_title1);

                       // SelectLanguage.lang_text_main.setText(select_title.replace("\n",""));
                      //  SelectLanguage.proceed.setText(continue_btnn.replace("\n",""));
                        ChangeLanguageFragment.lang_title.setText(lang_title1.replace("\n",""));
                        ChangeLanguageFragment.continue_lang.setText(continue_btnn.replace("\n",""));


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
    public int getItemCount() {
        return productList.size();
    }
}