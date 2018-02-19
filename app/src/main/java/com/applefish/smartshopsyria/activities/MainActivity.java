package com.applefish.smartshopsyria.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.entities.Category;
import com.applefish.smartshopsyria.classes.ConnectChecked;
import com.applefish.smartshopsyria.entities.Item;
import com.applefish.smartshopsyria.entities.Offer;
import com.applefish.smartshopsyria.adapter.PagerAdapter;
import com.applefish.smartshopsyria.entities.Store;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_CITIES;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_CITY;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_COVERURL;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_DATE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ELEMENTS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ID;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_IMAGE_TYPE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ITEM;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ITEMS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_LOCATIONS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_MALL;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_MALLS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NAME;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NUMOFPAGES;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NUM_OF_VIEWS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_PDFURL;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_RESULTS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_SPECIFICATION;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_STORE_IDSTORE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_TITLE;
import static com.applefish.smartshopsyria.classes.URLTAG.CATEGRY_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.CITIES_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.LATEST_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.MALLS_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.STORES_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.VERSION_INFO_URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String storesResult;
    String latestResult;
    String citiesResult;
    String mallsResult;
    String categoryResult;
   // String mostViewedResult;

    private String TAG = MainActivity.class.getSimpleName();

    public static final String TAG_STORE_NAME = "storeName";
    private static final String TAG_ADD ="logoUrl";



    public static ArrayList<Store> storesList;
    public static ArrayList<Offer> latestOffersList;
   // public static ArrayList<Offer> mostViewedList;

    public static ArrayList<ImageButton> storesLogosList;
    public static ArrayList<ImageButton> latestOffersCoversList;
    //public static ArrayList<ImageButton> mostViewedCoversList;

    public static ArrayList<Bitmap> storesBitmapsList;
    public static ArrayList<Bitmap> latestBitmapsList;
 //   public static ArrayList<Bitmap> mostViewedBitmapsList;


    public static ArrayList<String> citiesList;
    public static ArrayList<String> mallsList;


    private static JSONArray storesArray = null;

    public static ArrayList<Category> categoryList;
    private static JSONArray categoryArray = null;

    public static FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static Thread getData;
    private  ProgressBar progressBar;

    static PagerAdapter adapter;

    ImageLoader imageLoader;
    AlertDialog alert;

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

                 fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchagent = new Intent();
                searchagent.setClass(getBaseContext(), SearchAgent.class);
                startActivity(searchagent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        final SearchView searchView = (SearchView) findViewById(R.id.search);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if ( ConnectChecked.isNetworkAvailable( getBaseContext() ) &&
//                        ConnectChecked.isOnline() ) {
//                Intent searchIntent = new Intent();
//                searchIntent.setClass( getBaseContext(), SearchActivity.class );
//                searchIntent.putExtra( SearchManager.QUERY , query);
//                searchIntent.setAction( Intent.ACTION_SEARCH );
//                startActivity(searchIntent);
//                return false;
//                } else {
//                    Snackbar.make(toolbar, "No Internet Connection", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(3);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        storesList = new ArrayList<>();
        storesLogosList = new ArrayList<>();
        storesBitmapsList = new ArrayList<>();

        latestOffersList = new ArrayList<>();
        latestOffersCoversList = new ArrayList<>();
        latestBitmapsList = new ArrayList<>();

//               mostViewedList  = new ArrayList<>();
//        mostViewedCoversList = new ArrayList<>();
//        mostViewedBitmapsList = new ArrayList<>();

      citiesList= new ArrayList<>();
       mallsList= new ArrayList<>();

        categoryList=new ArrayList<>();



        // new pager Adapter and TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Latest"));
        //      tabLayout.addTab(tabLayout.newTab().setText("Most Viewed"));
        tabLayout.addTab(tabLayout.newTab().setText("Mall Offers"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Stores"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        progressBar=(ProgressBar)findViewById(R.id.progressbar_foralltabs);

        runOnUiThread(new Runnable() {
            public void run() {
                displayViewPagerContent( 0 );
            }
        });



        getJSonByThread(true);


        int permissionCheckWriteExternalStorage = ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if ( permissionCheckWriteExternalStorage != PackageManager.PERMISSION_GRANTED  ) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

        }


        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("New Update");
        builder.setMessage("There is a new update available.");
        // builder.setCancelable(false);
        builder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int ii) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });
        builder.setNegativeButton("Leater", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();


            }
        });
        builder.setNeutralButton("Rate", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }});
         alert=builder.create();


        getVersionServer();



    }

    private void getVersionServer() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VERSION_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                                PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
                                int versionnumber=info.versionCode;
                                String versionname=info.versionName;
                                if(Float.parseFloat(obj.getString("version")) > Float.parseFloat(versionname))
                                alert.show();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(SettingActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", token);
//                params.put("token", token);
//                return params;
//            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void getJSonByThread(final boolean ckeck) {
        getData = new Thread() {
            @Override
            public void run() {
                getJSON(ckeck);
            }
        };


        try {
            getData.start();
            getData.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public  void displayViewPagerContent(final int position) {

             try
             {

                final Handler handler = new Handler(Looper.getMainLooper());

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                       //    if( storesResult != null && latestResult != null && mostViewedResult != null ){
                               if( storesResult != null && latestResult != null && citiesResult != null && mallsResult != null   && categoryResult != null ){

                                   progressBar.setVisibility(View.GONE);
                                    viewPager.setAdapter(adapter);
                                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                    tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));

                               viewPager.setCurrentItem(position);
                               tabLayout.getTabAt(position).select();
                                }
                                else
                           {
                               displayViewPagerContent( position );
                           }
                            }
                        }, 1000);
                    }
                });



            } catch (Exception e) {
               // Log.i("ddddddddddddddddddddddd", "onCreate: " +e);
            }

    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager Pager)
    {

        return  new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Pager.setCurrentItem(tab.getPosition());

//                Log.i("OnTabSelectedListener","position="+tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          //  super.onBackPressed();
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to exit?");
           // builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int ii) {

                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File folder = new File(extStorageDirectory, "SmartShopOffers");
                    if(folder.isDirectory())
                    {
                        String[] children = folder.list();
                        for(int i=0;i<children.length;i++)
                        {
                            File file=new File(folder,children[i]);
                            if(file.isFile())
                                file.delete();
                        }
                    }
                   // moveTaskToBack(true);
                    finishAffinity();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();


                }
            });
            builder.setNeutralButton("Rate the app", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    }
                    catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }

                }});
            AlertDialog alert=builder.create();
            alert.show();

        }

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                actionRefresh();
//                return true;
////            case R.id.searchbtn:
////                Intent searchagent = new Intent();
////                searchagent.setClass(getBaseContext(), SearchAgent.class);
////                startActivity(searchagent);
////                return true;
//            default:
                return super.onOptionsItemSelected(item);
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favourites) {

            Intent favorite = new Intent();
            favorite.setClass(getBaseContext(), FavoriteActivity.class);
            startActivity(favorite);

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            shareIntent.setType("text/plain");
            startActivity(shareIntent);

        } else if (id == R.id.nav_help) {

            Intent help = new Intent();
            help.setClass(getBaseContext(), HelpActivity.class);
            startActivity(help);

        } else if (id == R.id.nav_settings) {

            Intent settings = new Intent();
            settings.setClass(getBaseContext(), SettingActivity.class);
            startActivity(settings);

        }
        else if(id==R.id.nav_rate)
        {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            }
            catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getJSON ( boolean check ) {

        storesList = new ArrayList<>();
        storesLogosList = new ArrayList<>();
        storesBitmapsList = new ArrayList<>();

        latestOffersList = new ArrayList<>();
        latestOffersCoversList = new ArrayList<>();
        latestBitmapsList = new ArrayList<>();

//               mostViewedList  = new ArrayList<>();
//        mostViewedCoversList = new ArrayList<>();
//        mostViewedBitmapsList = new ArrayList<>();

        citiesList= new ArrayList<>();
        mallsList= new ArrayList<>();

        categoryList=new ArrayList<>();

        class GetStores extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result;
                BufferedReader bufferedReader;

                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    int status = con.getResponseCode();


//                    Log.i("GetStores", "doInBackground: " +status);

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
                storesResult = result;
                buildStoresList();
            }
        }


        class GetLatest extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result;
                BufferedReader bufferedReader;

                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    int status = con.getResponseCode();

//                    Log.i("GetLatest", "doInBackground: " +status);

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
                latestResult=result;
                buildOffersList(result,"latest");
            }
        }

//        class GetMostViewed extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                String uri = params[0];
//                String result;
//                BufferedReader bufferedReader;
//
//                try {
//
//                    URL url = new URL(uri);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//
//                    int status = con.getResponseCode();
//
////                    Log.i("GetMost", "doInBackground: " +status);
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String json;
//                    while((json = bufferedReader.readLine())!= null){
//                        sb.append(json+"\n");
//                    }
//                    result=sb.toString().trim();
//                    return result;
//
//                }catch(Exception e){
//                    return null;
//                }
//
//            }

//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//                mostViewedResult=result;
//                buildOffersList(result,"mostViewed");
//            }
//        }

        class GetCities extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result;
                BufferedReader bufferedReader;

                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    int status = con.getResponseCode();

//                    Log.i("GetMost", "doInBackground: " +status);

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
                citiesResult=result;
                buildCitiesList(result);
            }
        }

        class GetMalls extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result;
                BufferedReader bufferedReader;

                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    int status = con.getResponseCode();

//                    Log.i("GetMost", "doInBackground: " +status);

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
                mallsResult=result;
                buildMallsList(result);
            }
        }


        class GetCategoryJSON extends AsyncTask<String, Void, String> {
          //  private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

//            @Override
//            protected void onPreExecute() {
//                this.dialog.setMessage("Loading.....");
//                this.dialog.show();
//            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result = "";
                BufferedReader bufferedReader = null;
                try {
                    String UTF8 = "utf8";
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    result = sb.toString().trim();
                    // Log.i("getJSONOffers", "doInBackground: " +result);
                    return result;

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                categoryResult = result;
                buidlCategoriesList();
//                if (dialog.isShowing())
//                    dialog.dismiss();

            }

//            @Override
//            protected void onCancelled() {
//                if (dialog.isShowing())
//                    dialog.dismiss();
//
//            }
        }


        GetStores getStores = new GetStores();
//        GetMostViewed getMostViewed = new GetMostViewed( );
        GetLatest getLatest = new GetLatest();
        GetCities getCities = new GetCities();
        GetMalls  getMalls = new GetMalls();
        GetCategoryJSON  getCategory = new GetCategoryJSON();
            if (check) {
                getStores.execute( STORES_URL );
//                getMostViewed.execute( MALLOFFER_URL );
                getLatest.execute( LATEST_URL );

                getCities.execute( CITIES_URL );

                getMalls.execute( MALLS_URL );

                getCategory.execute(CATEGRY_URL);
            }
            else {

                if( !getStores.isCancelled() )
                    getStores.cancel( true );

               // if( !getMostViewed.isCancelled() )
              //      getMostViewed.cancel( true );

                if( !getLatest.isCancelled() )
                    getLatest.cancel( true );

                if( !getCities.isCancelled() )
                    getCities.cancel( true );

                if( !getMalls.isCancelled() )
                    getMalls.cancel( true );

                if( !getCategory.isCancelled() )
                    getCategory.cancel( true );


            }


    }

    public void buildStoresList() {

        try {

            if ( storesResult != null) {

                JSONObject jsonObj = new JSONObject(storesResult);

                if( !storesResult.toString().equals("{\"result\":\"NoStores\"}") ) {
                storesArray = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < storesArray.length(); i++) {

                        JSONObject c = storesArray.getJSONObject(i);
                        int id = Integer.parseInt( c.getString(TAG_ID) );
                        String name = c.getString(TAG_STORE_NAME);
                        String logoUrl = c.getString(TAG_ADD);

                        Store store = new Store( id, name, logoUrl );
                        storesList.add(store);

                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "NO Stores", Toast.LENGTH_LONG).show();
                }
            } else {
                getJSonByThread(false);
                Log.i("An error Stores","An error occurred Stores");
              //  Toast.makeText(getBaseContext(), "An error occurred Stores", Toast.LENGTH_SHORT).show();
                getJSonByThread(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void buildOffersList( String latestResult, String list ) {

        try {

            if (latestResult != null) {

                JSONObject jsonObj = new JSONObject(latestResult);

                if (!latestResult.toString().equals("{\"result\":\"NoOffers\"}")) {
                    JSONArray offersArray = jsonObj.getJSONArray(TAG_RESULTS);


                    for (int i = 0; i < offersArray.length(); i++) {

                        JSONObject c = offersArray.getJSONObject(i);

                        int id = Integer.parseInt(c.getString(TAG_ID));
                        String title = c.getString(TAG_TITLE);
                        String date = c.getString(TAG_DATE);
                        int numberOfViews = Integer.parseInt(c.getString(TAG_NUM_OF_VIEWS));
                        String PdfUrl = c.getString(TAG_PDFURL);
                        String coverUrl = c.getString(TAG_COVERURL);
                        int numberOfPages = Integer.parseInt(c.getString(TAG_NUMOFPAGES));
                        String specification = c.getString(TAG_SPECIFICATION);
                        String imageType=c.getString(TAG_IMAGE_TYPE);
                        String cities = c.getString(TAG_CITIES);
                        String malls = c.getString(TAG_MALLS);
                        String locations = c.getString(TAG_LOCATIONS);
                        int store_idstore = Integer.parseInt(c.getString(TAG_STORE_IDSTORE));

                        Offer offer = new Offer(id, title, date, numberOfViews, PdfUrl, coverUrl, numberOfPages, specification,imageType,cities,malls,locations, store_idstore);

                            if ( list.equals("latest"))
                                latestOffersList.add(offer);
                           // else if ( list.equals("mostViewed"))
                            //    mostViewedList.add(offer);

                    }
                }
            }
            else
            {
                getJSonByThread(false);
                Log.i("An error Offers","An error occurred Offers");
                //Toast.makeText(getBaseContext(), "An error occurred Offers", Toast.LENGTH_SHORT).show();
                getJSonByThread(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void buildCitiesList( String CitiesResult ) {

        try {

            if (CitiesResult != null) {

                JSONObject jsonObj = new JSONObject(CitiesResult);

                if (!CitiesResult.toString().equals("{\"result\":\"NoCities\"}")) {
                    JSONArray citiesArray = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < citiesArray.length(); i++) {
                        JSONObject c = citiesArray.getJSONObject(i);
                        String city = c.getString(TAG_CITY);

                        citiesList.add(city);
                    }
                }
            }
            else
            {
                getJSonByThread(false);
                Log.i("An error Cities","An error occurred Cities");
              //  Toast.makeText(getBaseContext(), "An error occurred Cities", Toast.LENGTH_SHORT).show();
                getJSonByThread(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void buildMallsList( String mallsResult ) {

        try {

            if (mallsResult != null) {

                JSONObject jsonObj = new JSONObject(mallsResult);

                if (!mallsResult.toString().equals("{\"result\":\"NoMalls\"}")) {
                    JSONArray mallsArray = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < mallsArray.length(); i++) {
                        JSONObject c = mallsArray.getJSONObject(i);
                        String mall = c.getString(TAG_MALL);

                        mallsList.add(mall);
                    }
                }
            }
            else
            {
                getJSonByThread(false);
                Log.i("An error Malls","An error occurred Malls");
               // Toast.makeText(getBaseContext(), "An error occurred Malls", Toast.LENGTH_SHORT).show();
                getJSonByThread(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void buidlCategoriesList() {

        try {

            if ( categoryResult != null) {

                JSONObject jsonObj = new JSONObject(categoryResult);

                if( !categoryResult.toString().equals("{\"result\":\"NoCategory\"}")) {

                    categoryArray = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < categoryArray.length(); i++) {
                        ArrayList<Item> items_valuse=new ArrayList<>();
                        JSONArray items=new JSONArray();
                        JSONObject c = categoryArray.getJSONObject(i);

                        int id = Integer.parseInt(c.getString(TAG_ID));

                         items =c.getJSONArray(TAG_ITEMS);

                        String name = c.getString(TAG_NAME);


                        for (int JJ = 0; JJ < items.length(); JJ++) {

                            JSONArray elements=new JSONArray();
                            ArrayList<String> element_valuse=new ArrayList<>();
                            JSONObject items_content = items.getJSONObject(JJ);

                            String item =items_content.getString(TAG_ITEM);
                            elements=  items_content.getJSONArray(TAG_ELEMENTS);


                            for (int ss = 0; ss < elements.length(); ss++) {
                                String element = elements.get(ss).toString();
                                element_valuse.add(element);
                            }

                            Item item1=new Item(item,element_valuse);
                            items_valuse.add(item1);

                        }

                      //  String []itemsAarray=items.split("-");
                        Category category = new Category(id, name, items_valuse);
                        categoryList.add(category);


                    }

                }
                else {
                    Toast.makeText(getBaseContext(), "NO Category", Toast.LENGTH_SHORT).show();
                }
            } else {

                getJSonByThread(false);
                Log.i("An error Offers","An error occurred Categories");
               // Toast.makeText(getBaseContext(), "An error occurred Categories", Toast.LENGTH_SHORT).show();
                getJSonByThread(true);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    public static void getStoresImages(){
//
//        Thread retrieveLogo ;
//        retrieveLogo = new Thread(){
//
//            @Override
//            public void run() {
//                for( int i=0; i < storesList.size() ; i++ ) {
//                    final Store store = storesList.get(i);
//                    getStoreImage( i, store.getLogoUrl());
//                }
//            }
//        };
//
//        retrieveLogo.start();
//
//    }

//    public static void getOffersImages(final String list ){
//
//
//        Thread retrieveImage ;
//        retrieveImage = new Thread(){
//
//            @Override
//            public void run() {
//                if ( list.equals("latest"))
//                    for( int i=0; i < latestOffersList.size() ; i++ ) {
//                        Offer offer = latestOffersList.get(i);
//                        getLatestImage( i, offer.getCoverURL());
//                    }
//               // else if ( list.equals("mostViewed") )
//               //     for( int i=0; i < mostViewedList.size() ; i++ ) {
//                  //      Offer offer = mostViewedList.get(i);
//                  //      getMostViewedImage( i, offer.getCoverURL());
//                  //  }
//            }
//        };
//
//        retrieveImage.start();
//
//    }

//    private static void getStoreImage(final int index, String urlToImage){
//
//        class GetImage extends AsyncTask<String,Void,Bitmap> {
//
//
//            @Override
//            protected Bitmap doInBackground(String... params) {
//
//                URL url;
//                String urlToImage = params[0];
//                Bitmap image = null;
//
//                    if ( storesBitmapsList.size() == (index+1) )
//                        image = storesBitmapsList.get( index );
//
//                    if ( image == null ) {
//                        try {
//
//                            url = new URL(urlToImage);
//                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
////                            Log.i("getImage", "URL : " + urlToImage);
//
//                            image = BitmapFactory.decodeStream(con.getInputStream());
//                            storesBitmapsList.add(image);
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                return image;
//            }
//
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//
//                super.onPostExecute(bitmap);
//                if(storesList.size()>0)
//                {  Store store = storesList.get( index );
//                store.setLogo(bitmap);
//                MainActivity.setStoreBitmap(index);}
////                Log.i("post Execute", "Call # : "+index );
//
//            }
//        }
//
//        GetImage gi = new GetImage();
//        gi.execute(urlToImage);
//
//    }

//    private static void getLatestImage(final int index, String urlToImage){
//
//        class GetImage extends AsyncTask<String,Void,Bitmap> {
//
//
//            @Override
//            protected Bitmap doInBackground(String... params) {
//
//                URL url;
//                String urlToImage = params[0];
//                Bitmap image = null;
//
//                if ( latestBitmapsList.size() == (index+1) )
//                    image = latestBitmapsList.get( index );
//
//                if ( image == null ) {
//                    try {
//
//                        url = new URL(urlToImage);
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
////                        Log.i("getImage", "URL : " + urlToImage);
//
//                        image = BitmapFactory.decodeStream(con.getInputStream());
//                        latestBitmapsList.add(image);
//
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                return image;
//            }
//
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//
//                super.onPostExecute(bitmap);
//            if(latestOffersList.size()>0)
//            {Offer offer = latestOffersList.get( index );
//                offer.setCover(bitmap);
//                MainActivity.setOffersBitmap(index,"latest");}
////                Log.i("post Execute", "Call # : "+index );
//
//            }
//        }
//
//        GetImage gi = new GetImage();
//        gi.execute(urlToImage);
//
//    }

  /*  private static void getMostViewedImage(final int index, String urlToImage){

        class GetImage extends AsyncTask<String,Void,Bitmap> {


            @Override
            protected Bitmap doInBackground(String... params) {

                URL url;
                String urlToImage = params[0];
                Bitmap image = null;

                if ( mostViewedBitmapsList.size() == (index+1) )
                    image = mostViewedBitmapsList.get( index );

                if ( image == null ) {
                    try {

                        url = new URL(urlToImage);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

//                        Log.i("getImage", "URL : " + urlToImage);

                        image = BitmapFactory.decodeStream(con.getInputStream());
                        mostViewedBitmapsList.add(image);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return image;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {

                super.onPostExecute(bitmap);
                if(mostViewedList.size()>0)
                {  Offer offer = mostViewedList.get( index );
                offer.setCover(bitmap);
                MainActivity.setOffersBitmap(index,"mostViewed");}
//                Log.i("post Execute", "Call # : "+index );

            }
        }

        GetImage gi = new GetImage();
        gi.execute(urlToImage);

    }
*/
    private static void setStoreBitmap( int index ){

        // RelativeLayout  Params  apply on child (imageButton ) when on click
        final RelativeLayout.LayoutParams rlp4 = new RelativeLayout.LayoutParams(
                300,
                300
        );
        rlp4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rlp4.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageButton storeLogo = storesLogosList.get(index);
        storeLogo.setBackgroundResource(0);
        storeLogo.setLayoutParams(rlp4);
        storeLogo.setImageBitmap(storesList.get(index).getLogo());

    }

    private static void setOffersBitmap( int index, String list ){

        // RelativeLayout  Params  apply on child (imageButton ) when on click
        final RelativeLayout.LayoutParams rlp4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rlp4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rlp4.addRule(RelativeLayout.CENTER_VERTICAL);

        if ( list.equals("latest")) {
            ImageButton cover = latestOffersCoversList.get(index);
            cover.setBackgroundResource(0);
            cover.setLayoutParams(rlp4);
            cover.setBackgroundResource(R.drawable.customborder);
            cover.setImageBitmap(latestOffersList.get(index).getCover());

        }
        //else
      //  if ( list.equals("mostViewed") ) {
       //     ImageButton cover = mostViewedCoversList.get(index);
        //  cover.setBackgroundResource(0);
         //   cover.setLayoutParams(rlp4);
         //   cover.setBackgroundResource(R.drawable.customborder);
         //   cover.setImageBitmap(mostViewedList.get(index).getCover());
       // }

    }


    public void actionRefresh (){

        if ( ConnectChecked.isNetworkAvailable( getBaseContext() )
                ) {

            getJSON( false );

            runOnUiThread(new Runnable() {
                public void run() {
//                    Log.i("onRefresh "," Refreshing ......");

                    int position = tabLayout.getSelectedTabPosition();

                    storesList.clear();
                    storesLogosList.clear();
                    storesBitmapsList.clear();

                    latestOffersList.clear();
                    latestOffersCoversList.clear();
                    latestBitmapsList.clear();

//                    mostViewedList.clear();
//                    mostViewedCoversList.clear();
//                    mostViewedBitmapsList.clear();

                    citiesList.clear();
                    mallsList.clear();
                    categoryList.clear();

                    storesResult = null;
                    latestResult = null;
                  //  mostViewedResult = null;
                    citiesResult=null;
                    mallsResult=null;
                    categoryResult=null;

                    Thread refresh = new Thread(){
                        @Override
                        public void run() {
                            getJSON( true );
                        }
                    };

                    try {
                        refresh.start();
                        refresh.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    displayViewPagerContent( position );

                }
            });

        } else {
            Snackbar.make( toolbar , "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

}