package com.applefish.smartshopsyria.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;


import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.classes.ConnectChecked;
import com.applefish.smartshopsyria.adapter.ViewPagerAdapter;
import com.applefish.smartshopsyria.adapter.ViewPagerFixed;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.applefish.smartshopsyria.classes.URLTAG.AddView_URL;

public class ImagesViewerActivity extends AppCompatActivity {

    final static String Key="com.applefish.smartshop.PdfViewer";
    final static String Key2 = "com.applefish.smartshop.IDOffer";
    final static String Key3= "com.applefish.smartshop.NUMOFPAGE";
    final static String Key4= "com.applefish.smartshop.ImageType";
    private String pdfurl;
    private String PDF_Name;
    private int numofpage;
    private int IDOffer;
    private String imagetype;

    ViewPagerFixed viewPager;
    ViewPagerAdapter pagerAdapter;



    private CheckBox starBTN;
    private  String [] images;
//    private  String [] images={
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/1.jpg",
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/2.jpg",
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/3.jpg",
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/4.jpg",
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/5.jpg",
//            "http://smartshop-uae.org/smartshop_v2/pdf/carrefour/Test2/6.jpg"
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //relativeLayout=(RelativeLayout)findViewById(R.id.rel2);

        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.fab);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text));
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                Snackbar.make(view, "Share Offer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });




        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        Intent intent=getIntent();
        pdfurl=intent.getStringExtra(Key);
        IDOffer=intent.getIntExtra(Key2,0);
        numofpage=intent.getIntExtra(Key3,0);
        imagetype=intent.getStringExtra(Key4);
        images=new String[numofpage];

        if (ConnectChecked.isNetworkAvailable(getBaseContext())) {
            Thread getData = new Thread(){
                @Override
                public void run() {
                    super.run();
                    increaseNumOfView(AddView_URL + "/?idoffer=" + IDOffer);
                }
            };

            getData.start();
        } else {
            Snackbar.make(toolbar, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



        for(int i=0;i<numofpage;i++)
        {
            images[i]=pdfurl+"/"+(i+1)+"."+imagetype;
        }


        viewPager=(ViewPagerFixed)findViewById(R.id.viewpager);
        pagerAdapter=new ViewPagerAdapter(ImagesViewerActivity.this,images,ImageLoader.getInstance());
        viewPager.setAdapter(pagerAdapter);



        starBTN=(CheckBox)findViewById(R.id.btn_star);
        starBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favoriteOffer=readSharedPreference();
                String [] offersId=favoriteOffer.split(",");
                String newFavoritoffers="";
                if(starBTN.isChecked())
                {
                    if(offersId.length==1 && offersId[0].equals(""))
                    {
                        newFavoritoffers=""+IDOffer;
                    }
                    else if(offersId.length>=1 &&  !offersId[0].equals(""))
                    {
                        newFavoritoffers=favoriteOffer+","+IDOffer;
                    }
                    writeSharedPreference(newFavoritoffers);
                    Toast.makeText(getBaseContext(), "Add To Favorite", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //
                    if(offersId.length==1 && !offersId[0].equals(""))
                    {
                        newFavoritoffers="";
                    }
                    else if(offersId.length>=1 && !offersId[0].equals(""))
                    {
                        for (int i=0;i<offersId.length;i++)
                        {
                            if(!offersId[i].equals(IDOffer+""))
                            {
                                if(i==(offersId.length-1))
                                {
                                    newFavoritoffers=newFavoritoffers+offersId[i];
                                }
                                else
                                {
                                    newFavoritoffers=newFavoritoffers+offersId[i]+",";
                                }
                            }
                        }
                    }
                    writeSharedPreference(newFavoritoffers);
                    Toast.makeText(getBaseContext(), "Delete From Favorite", Toast.LENGTH_SHORT).show();
                    //


                }


            }


        });
        String favoriteOffer=readSharedPreference();
        String [] offersId=favoriteOffer.split(",");
        Boolean find=false;
        if(offersId.length==1 && offersId[0].equals(""))
        {

            starBTN.setChecked(false);
        }
        else if(offersId.length>=1 && !offersId[0].equals(""))
        {
            for (int i=0;i<offersId.length;i++)
            {

                if(offersId[i].equals((IDOffer+"")))
                {
                    starBTN.setChecked(true);
                    find=true;
                    break;
                }
            }
            if(!find)
                starBTN.setChecked(false);
        }
    }
    public String readSharedPreference()
    {
        SharedPreferences sharedPref =getBaseContext().getSharedPreferences("com.applefish.smartshop.FAVORITE_KEY",MODE_PRIVATE);
        //0 is default_value if no vaule
        String savedFavoriteOffer = sharedPref .getString(getString(R.string.saved_favorite), "");

        return savedFavoriteOffer;
    }
    public  void  writeSharedPreference(String savedFavoriteOffer)
    {
        SharedPreferences sharedPref =getBaseContext().getSharedPreferences("com.applefish.smartshop.FAVORITE_KEY",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_favorite), savedFavoriteOffer);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        ImageLoader.getInstance().stop();
        super.onBackPressed();
    }



    private void increaseNumOfView(String url) {


        class IncreaseNumOfView extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result;
                BufferedReader bufferedReader ;
                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    int status = con.getResponseCode();


//                    Log.i("increaseNumOfView", "doInBackground: " +status);

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    result=sb.toString().trim();
                    return result;

                }catch(Exception e){
                    return null;
                }

            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }
        }

        IncreaseNumOfView gj = new IncreaseNumOfView();
        gj.execute(url);

    }
}
