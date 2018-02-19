package com.applefish.smartshopsyria.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applefish.smartshopsyria.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Amro on 2/07/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
   Activity activity;
    String []images;
    ImageLoader imageLoader;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Activity activity, String[] images, ImageLoader imageLoader) {
        this.activity = activity;
        this.images = images;
        this.imageLoader = imageLoader;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview=layoutInflater.inflate(R.layout.viewpager_item,container,false);
        PhotoView imageView=(PhotoView)itemview.findViewById(R.id.imageview);
        ///
        final View prog=itemview.findViewById(R.id.progressbar);



        final View imageView1= (ImageView) itemview.findViewById(R.id.imageView1);
        final View imageView2= (ImageView) itemview.findViewById(R.id.imageView2);
        final View imageView3= (ImageView) itemview.findViewById(R.id.imageView3);
        //
       // View itemview2=layoutInflater.inflate(R.layout.activity_main,container,false);
      // final View pagenumber=activity.findViewById(R.id.pagenumber);
        final View pagenumber=itemview.findViewById(R.id.pagenumber);
        //

        ((TextView)pagenumber).setText(images.length+"/"+(position+1));

        //configration image loader
        File cacheDir = StorageUtils.getCacheDirectory(activity);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
                ///
        imageLoader.displayImage(images[position],imageView,options,new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                prog.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                prog.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                prog.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
            }
        });
            container.addView(itemview);
                return itemview;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPagerFixed)container).removeView((View)object);
    }

}
