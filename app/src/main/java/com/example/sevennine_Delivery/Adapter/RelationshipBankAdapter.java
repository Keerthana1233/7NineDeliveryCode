package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;

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

import com.example.sevennine_Delivery.Bean.SelectLanguageBean;
import com.example.sevennine_Delivery.Fragment.RelationshipBankFragment;
import com.example.sevennine_Delivery.R;

import java.util.List;


public class RelationshipBankAdapter extends RecyclerView.Adapter<RelationshipBankAdapter.MyViewHolder>  {
    private List<SelectLanguageBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static int selected_position=0;
    public static String type_relation;


    public static CardView cardView;
    public RelationshipBankAdapter(Activity activity, List<SelectLanguageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lang_text;
        public LinearLayout lang_lay;
        public ImageView lang_img;
        public RadioButton lang_radio;




        public MyViewHolder(View view) {
            super(view);
            lang_text=view.findViewById(R.id.lang_text);
            lang_lay=view.findViewById(R.id.lang_lay);
            lang_radio=view.findViewById(R.id.lang_radio);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_relnship_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SelectLanguageBean products1 = productList.get(position);


        holder.lang_text.setText(products1.getVendor());

        if (position==selected_position){
            holder.lang_radio.setChecked(true);
            type_relation=products1.getVendor();
            RelationshipBankFragment.select.setText(type_relation);
           // holder.lang_text.setTypeface(holder.lang_text.getTypeface(), Typeface.BOLD);

        }else{
            holder.lang_radio.setChecked(false);
           // holder.lang_text.setTypeface(holder.lang_text.getTypeface(), Typeface.NORMAL);

        }

        /*if (sessionManager.getRegId("language_name").equals(products1.getVendor())){
            // holder.right_img.setImageResource(R.drawable.ic_verified_filled_grey_white);
            //  holder.lng_rad_but.setBackgroundColor(Color.GREEN);
            holder.lang_img.setVisibility(View.VISIBLE);
            holder.lang_text.setTypeface(holder.lang_text.getTypeface(), Typeface.BOLD);

        }else {

            holder.lang_img.setVisibility(View.GONE);

        }
*/

        /*Glide.with(activity).load(R.drawable.success)

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.lang_img);*/

        System.out.print("iiidddddd" + products1.getLanguageid());

        holder.lang_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                System.out.println("llllliidhhd"+products1.getVendor());
                //getLang(products1.getLanguageid());
               // holder.lang_text.setTypeface(holder.lang_text.getTypeface(), Typeface.BOLD);


               /* Intent intent = new Intent(activity, LoginActivity_new.class);
                activity.startActivity(intent);*/


            }
        });
        holder.lang_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                System.out.println("llllliidhhd"+products1.getVendor());
                //getLang(products1.getLanguageid());
              //  holder.lang_text.setTypeface(holder.lang_text.getTypeface(), Typeface.BOLD);


               /* Intent intent = new Intent(activity, LoginActivity_new.class);
                activity.startActivity(intent);*/


            }
        });
    }








    @Override
    public int getItemCount() {
        return productList.size();
    }
}