package com.applefish.smartshopsyria.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.activities.MainActivity;
import com.applefish.smartshopsyria.activities.OffersActivity;
import com.applefish.smartshopsyria.entities.Store;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by Amro on 11/08/2017.
 */

public class RecyclerStoreCardAdapter extends RecyclerView.Adapter<RecyclerStoreCardAdapter.ViewHolder> {

    public  ArrayList<Store> stores;
    Context context;
    private static int lastPosition=-1;


    ImageLoaderConfiguration config;
    public static final ImageLoader imageLoader = ImageLoader.getInstance();

    public RecyclerStoreCardAdapter(ArrayList<Store> stores, Context context) {
        this.stores = stores;
        this.context=context;
        config= new ImageLoaderConfiguration.Builder(context)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
        public RecyclerStoreCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_store_layout,parent,false);
            ViewHolder viewHolder=new ViewHolder(v);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.storeName.setText(stores.get(position).getStoreName());
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            imageLoader.displayImage(stores.get(position).getLogoUrl(),holder.storeLogo,options);

            // Here you apply the animation when the view is bound
            setAnimation(holder.itemView, position);
        }
    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

        @Override
        public int getItemCount() {
            return stores.size();
        }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

        class ViewHolder extends  RecyclerView.ViewHolder{

            public TextView storeName;
            public ImageView storeLogo;

            public  ViewHolder(final View itemView)
            {
                super(itemView);


                storeName=(TextView)itemView.findViewById(R.id.storeName);
                storeLogo=(ImageView)itemView.findViewById(R.id.storeLogo);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=getAdapterPosition();

                        Intent offers = new Intent();
                        Bundle bundle = new Bundle();

                        offers.setClass(context, OffersActivity.class);
                        bundle.putParcelable( MainActivity.TAG_STORE_NAME, MainActivity.storesList.get(position));
                        bundle.putString("ACTIVITY_NAME", "MAIN");
                        offers.putExtras(bundle);
                        context.startActivity(offers);

//                        Snackbar.make(v,"Cliecked detedcted item on "+position,Snackbar.LENGTH_SHORT).
//                                setAction("Action",null).show();
                    }
                });


            }



        }
    }
