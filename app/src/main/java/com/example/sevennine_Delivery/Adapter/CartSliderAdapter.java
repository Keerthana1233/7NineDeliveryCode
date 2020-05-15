package com.example.sevennine_Delivery.Adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sevennine_Delivery.Bean.ListBean2;
import com.example.sevennine_Delivery.R;

import java.util.List;

public class CartSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<ListBean2> productList;
    Activity activity;
    Fragment selectedFragment;

    public static CardView cardView;
    public CartSliderAdapter(Activity activity, List<ListBean2> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView loan_text;
        private LinearLayout loan_lay;
        private ImageView loan_img;
        public MyViewHolder(View view) {
            super(view);

            loan_text=view.findViewById(R.id.loan_text);
            loan_img=view.findViewById(R.id.loan_img);
            loan_lay=view.findViewById(R.id.loan_lay);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            System.out.println("itemmmmmmm");
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sell_item, parent, false);


            float height = (float) parent.getMeasuredHeight() /4;//(Width/Height)
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.height = Math.round(height);
            itemView.setLayoutParams(params);
            return new MyViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            MyViewHolder viewHolder0 = (MyViewHolder)holder;
            final ListBean2 products1 = productList.get(position);
            viewHolder0.loan_text.setText(products1.getName());



     /*   Glide.with(activity).load(products1.getImage())

                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder0.loan_img);
        */

     /*   Glide.with(activity)
                .load(products1.getImage())
                .error(R.drawable.avatarmale)
                .placeholder(R.drawable.ic_gallery__default)
                .into(viewHolder0.loan_img);*/


        Glide.with(activity).load(products1.getImage())
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.avatarmale))
                .placeholder(R.drawable.avatarmale)
                .into(viewHolder0.loan_img);



        System.out.println("imageeeeesliderrrrr"+products1.getImage());

    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        System.out.println("jhdjsk"+position+" "+SliderPagerAdapter.morecount);
        if (position==11 & SliderPagerAdapter.morecount!=0) {
            System.out.println("my pos "+SliderPagerAdapter.morecount);
            return 1;
        }
        else return 0;
        //  return position % 2 * 2;
    }

    @Override
    public int getItemCount() {
        if(SliderPagerAdapter.morecount!=0)
            return productList.size()+1;
        else
            return productList.size();
    }
}