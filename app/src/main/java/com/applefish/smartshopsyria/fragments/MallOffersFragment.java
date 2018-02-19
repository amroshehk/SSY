package com.applefish.smartshopsyria.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.activities.MainActivity;
import com.applefish.smartshopsyria.adapter.RecyclerOfferAdapter;
import com.applefish.smartshopsyria.classes.ConnectChecked;
import com.applefish.smartshopsyria.entities.Offer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_CITIES;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_COVERURL;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_DATE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ID;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_IMAGE_TYPE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_LOCATIONS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_MALLS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NUMOFPAGES;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NUM_OF_VIEWS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_PDFURL;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_RESULTS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_SPECIFICATION;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_STORE_IDSTORE;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_TITLE;

import static com.applefish.smartshopsyria.classes.URLTAG.BY_CITy_URL;
import static com.applefish.smartshopsyria.classes.URLTAG.BY_MALL_URL;


public class MallOffersFragment extends Fragment {
    //component
    private Spinner byCitiesSpinner;
    private Button byCitiesButton;

    private Spinner byMallspinner;
    private Button byMallButton;

    private RadioButton bycite;
    private RadioButton bymall;

    private ArrayAdapter<String> All_cities;
    private ArrayAdapter<String> All_Malls;

    private LinearLayout linebycite;
    private LinearLayout linebymall;
    private View rootView;
    private View rootView2;
    ViewGroup containermain;
    ProgressBar progressBar;
    //Json
    public static ArrayList<Offer> OffersList;
    String jsonResult;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    NestedScrollView nestedScrollView;

//    private  NestedScrollView nestedScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate( R.layout.mall_offers, container, false );
        rootView2=inflater.inflate( R.layout.offer_news, container, false );
         containermain=container;
        byCitiesSpinner = (Spinner) rootView.findViewById(R.id.byCitiesSpinner);
        byCitiesButton = (Button) rootView.findViewById(R.id.bycityeBTN);

        bycite = (RadioButton) rootView.findViewById(R.id.bycityeBTN_tab1);
        bymall = (RadioButton) rootView.findViewById(R.id.bymallBTN_tab1);

        byMallspinner = (Spinner) rootView.findViewById(R.id.byMallSpinner);
        byMallButton = (Button) rootView.findViewById(R.id.bymallBTN);

        linebycite = (LinearLayout) rootView.findViewById(R.id.linebycity);
        linebymall = (LinearLayout) rootView.findViewById(R.id.linebymall);

        progressBar=(ProgressBar)rootView.findViewById(R.id.progressbaroffer);

        MainActivity.fab.setVisibility(View.VISIBLE);

//        nestedScrollView=(NestedScrollView)rootView.findViewById(R.id.mall_offer_scroll);
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY>oldScrollY)
//                    MainActivity.fab.hide();
//                else
//                    MainActivity.fab.show();
//            }
//        });
        OffersList = new ArrayList<>();

        All_cities = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, MainActivity.citiesList);
        byCitiesSpinner.setAdapter(All_cities);


        All_Malls = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, MainActivity.mallsList);
        byMallspinner.setAdapter(All_Malls);

        bycite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linebycite.setVisibility(View.VISIBLE);
                linebymall.setVisibility(View.GONE);
                OffersList.clear();

            }
        });
        nestedScrollView=(NestedScrollView)rootView.findViewById(R.id.Scrolloffer) ;
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY<scrollY)
                    if (MainActivity.fab.isShown()) {
                        MainActivity.fab.hide();
                    }
                  if(oldScrollY>scrollY)
                    if (!MainActivity.fab.isShown()) {
                        MainActivity.fab.show();
                    }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        layoutManager=new LinearLayoutManager(rootView.getContext());


        bymall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linebycite.setVisibility(View.GONE);
                linebymall.setVisibility(View.VISIBLE);
                OffersList.clear();

            }
        });

        byCitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectChecked.isNetworkAvailable(getContext())) {
                    Thread getData = new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }, 20);

                            OffersList.clear();
//                            if(recyclerView.getChildCount() >0)
//                            recyclerView.removeAllViews();
                            getJSON( BY_CITy_URL ,true,"city");            }
                    };

                    getData.start();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(byCitiesButton, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        byMallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectChecked.isNetworkAvailable(getContext())) {
                    Thread getData = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }, 20);
                            OffersList.clear();
//                            if(recyclerView.getChildCount() >0)
//                                recyclerView.
//                            ();
                            getJSON( BY_MALL_URL ,true,"mall");          }
                    };

                    getData.start();
                } else {
                    Snackbar.make(byMallButton, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return rootView;
    }

    //get Json

    private void getJSON(String url, boolean ckeck, final String cityormall) {



        class GetJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result ="";
                BufferedReader bufferedReader = null;
                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    result=sb.toString().trim();
//                    Log.i("getJSONOffers", "doInBackground: " +result);
                    return result;

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                jsonResult = result;
                buildOffersList(cityormall);

            }

            public void canceltask(GetJSON getJSON)
            {getJSON.cancel(true);}
        }
        GetJSON gj = new GetJSON();
        if(ckeck)
        {
//            if(cityormall.equals("city") && MainActivity.citiesList.get(byCitiesSpinner.getSelectedIndex()).toString() !=null)
//            gj.execute(url+"/?city="+MainActivity.citiesList.get(byCitiesSpinner.getSelectedIndex()).toString());
//            else if(cityormall.equals("mall") && MainActivity.mallsList.get( byMallspinner.getSelectedIndex()).toString() !=null)
//            gj.execute(url+"/?mall="+ MainActivity.mallsList.get( byMallspinner.getSelectedIndex()).toString());
            try {
            if(cityormall.equals("city") &&  byCitiesSpinner.getSelectedItem().toString() !=null)

                    gj.execute(url+"/?city="+ URLEncoder.encode(byCitiesSpinner.getSelectedItem().toString(), "UTF-8"));

            else if(cityormall.equals("mall") && byMallspinner.getSelectedItem().toString() !=null)
            gj.execute(url+"/?mall="+  URLEncoder.encode(byMallspinner.getSelectedItem().toString(), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
               // e.printStackTrace();
                Snackbar.make(byMallButton, "An Error Occurred", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
            catch (NullPointerException e)
            {
                Log.i("eeeeee","ava.lang.String java.lang.Object.toString()");
            }
            catch (Exception e)
            {
                Log.i("eeeeee","Exception");
            }
        }
        else
        {
            if(!gj.isCancelled())
                gj.cancel(true);
        }

    }


    public void buildOffersList(String cityormall ) {

        try {

            if (jsonResult != null) {

                JSONObject jsonObj = new JSONObject(jsonResult);

                if (!jsonResult.toString().equals("{\"result\":\"NoOffers\"}")) {
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


                         OffersList.add(offer);



                    }
                    if(OffersList.size()>0) {
                        progressBar.setVisibility(View.GONE);


                        recyclerView.setLayoutManager(layoutManager);
                        adapter=new RecyclerOfferAdapter(OffersList,rootView.getContext());
                        recyclerView.setAdapter(adapter);


                    }



            }
            else {
                    Toast.makeText(rootView.getContext(), "NO Mall offers for this "+cityormall, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    OffersList.clear();
               // Toast.makeText(rootView.getContext(), "NO Mall offers", Toast.LENGTH_SHORT).show();
            }
        }
        else {
           // Toast.makeText(rootView.getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
