package com.applefish.smartshopsyria.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.entities.Category;
import com.applefish.smartshopsyria.classes.ConnectChecked;
import com.applefish.smartshopsyria.entities.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ELEMENTS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ID;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ITEM;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_ITEMS;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_NAME;
import static com.applefish.smartshopsyria.classes.JsonTAG.TAG_RESULTS;
import static com.applefish.smartshopsyria.classes.URLTAG.CATEGRY_URL;

public class SearchAgent extends AppCompatActivity {

    private String jsonResult;



    private static ArrayList<Category> categoryList;
    private static JSONArray categoryArray = null;

    private RadioButton wordsearch;
    private RadioButton catsearch;
    private SearchView searchView;
    private Spinner cat_spinner;
    private Spinner item_spinner;
    private Spinner element_spinner;
    private Button button;
    Toolbar toolbar;

    private LinearLayout linearLayout_Gategory;
    private LinearLayout tv_element;

    private static ArrayList<ArrayAdapter> categoryArrayAdapte;
    private ArrayAdapter<String> All_cat_adapter;
//    private ArrayAdapter<CharSequence> cat1_adapter;
//    private ArrayAdapter<CharSequence> cat2_adapter;
//
//    private ArrayAdapter<CharSequence> cat3_adapter;
//    private ArrayAdapter<CharSequence> cat4_adapter;
//    private ArrayAdapter<CharSequence> cat5_adapter;
//    private ArrayAdapter<CharSequence> cat6_adapter;
//    private ArrayAdapter<CharSequence> cat7_adapter;
//    private ArrayAdapter<CharSequence> cat8_adapter;
//    private ArrayAdapter<CharSequence> cat9_adapter;
//    private ArrayAdapter<CharSequence> cat10_adapter;
//    private ArrayAdapter<CharSequence> cat11_adapter;
//    private ArrayAdapter<CharSequence> cat12_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_agent);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wordsearch= (RadioButton) findViewById(R.id.radioButton);
        catsearch= (RadioButton) findViewById(R.id.radioButton2);
        searchView= (android.support.v7.widget.SearchView) findViewById(R.id.search);
        cat_spinner= (Spinner) findViewById(R.id.spinner);
        item_spinner= (Spinner) findViewById(R.id.spinner2);
        element_spinner= (Spinner) findViewById(R.id.spinner3);
        button= (Button) findViewById(R.id.button2);

        linearLayout_Gategory=(LinearLayout)findViewById(R.id.line2);
        tv_element=(LinearLayout)findViewById(R.id.line_element);

        categoryList=new ArrayList<>();
        categoryArrayAdapte=new ArrayList<>();
        //get json
        if (ConnectChecked.isNetworkAvailable(getBaseContext())
               ) {
//            Thread getData = new Thread(){
//                @Override
//                public void run() {
//                    super.run();
                    getJSON(CATEGRY_URL,true);
//        }
//            };

//            getData.start();
        } else {
            Snackbar.make(toolbar, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



//        cat1_adapter=ArrayAdapter.createFromResource(this,R.array.item1,android.R.layout.simple_spinner_item);
//        cat1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        cat2_adapter=ArrayAdapter.createFromResource(this,R.array.item2,android.R.layout.simple_spinner_item);
//        cat2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat3_adapter=ArrayAdapter.createFromResource(this,R.array.item3,android.R.layout.simple_spinner_item);
//        cat3_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat4_adapter=ArrayAdapter.createFromResource(this,R.array.item4,android.R.layout.simple_spinner_item);
//        cat4_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat5_adapter=ArrayAdapter.createFromResource(this,R.array.item5,android.R.layout.simple_spinner_item);
//        cat5_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat6_adapter=ArrayAdapter.createFromResource(this,R.array.item6,android.R.layout.simple_spinner_item);
//        cat6_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat7_adapter=ArrayAdapter.createFromResource(this,R.array.item7,android.R.layout.simple_spinner_item);
//        cat7_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat8_adapter=ArrayAdapter.createFromResource(this,R.array.item8,android.R.layout.simple_spinner_item);
//        cat8_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat9_adapter=ArrayAdapter.createFromResource(this,R.array.item9,android.R.layout.simple_spinner_item);
//        cat9_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat10_adapter=ArrayAdapter.createFromResource(this,R.array.item10,android.R.layout.simple_spinner_item);
//        cat10_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat11_adapter=ArrayAdapter.createFromResource(this,R.array.item11,android.R.layout.simple_spinner_item);
//        cat11_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        cat12_adapter=ArrayAdapter.createFromResource(this,R.array.item12,android.R.layout.simple_spinner_item);
//        cat12_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                if(i==0)
//                    item_spinner.setAdapter(cat1_adapter);
//                if(i==1)
//                    item_spinner.setAdapter(cat2_adapter);
//                if(i==2)
//                    item_spinner.setAdapter(cat3_adapter);
//                if(i==3)
//                    item_spinner.setAdapter(cat4_adapter);
//                if(i==4)
//                    item_spinner.setAdapter(cat5_adapter);
//                if(i==5)
//                    item_spinner.setAdapter(cat6_adapter);
//                if(i==6)
//                    item_spinner.setAdapter(cat7_adapter);
//                if(i==7)
//                    item_spinner.setAdapter(cat8_adapter);
//                if(i==8)
//                    item_spinner.setAdapter(cat9_adapter);
//                if(i==9)
//                    item_spinner.setAdapter(cat10_adapter);
//                if(i==10)
//                    item_spinner.setAdapter(cat11_adapter);
//                if(i==11)
//                    item_spinner.setAdapter(cat12_adapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if ( ConnectChecked.isNetworkAvailable( getBaseContext() ) ) {
                    Intent searchIntent = new Intent();
                    searchIntent.setClass( getBaseContext(), SearchActivity.class );
                    searchIntent.putExtra( SearchManager.QUERY , query);
                    searchIntent.setAction( Intent.ACTION_SEARCH );
                    startActivity(searchIntent);
                    return false;
                } else {
                    Snackbar.make(toolbar, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ConnectChecked.isNetworkAvailable( getBaseContext() ) ) {
                    Intent searchIntent = new Intent();
                    searchIntent.setClass( getBaseContext(), SearchActivity.class );
                    String item=item_spinner.getSelectedItem().toString();
                    String categry=cat_spinner.getSelectedItem().toString();
                    String element="";
                    if(element_spinner.getSelectedItem() != null)
                        element=element_spinner.getSelectedItem().toString();

                    String query;
                    if(item.toLowerCase().equals("all"))
                        query=categry+"_"+item;
                    else
                        query=categry+"_"+item;

                    if(element !="")
                        query+="_"+element;

                    searchIntent.putExtra( SearchManager.QUERY , query);
                    searchIntent.setAction( Intent.ACTION_SEARCH );
                    startActivity(searchIntent);

                } else {
                    Snackbar.make(toolbar, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

    }

    public void SearchWay(View view)
    {
        int id=view.getId();
        if(id==R.id.radioButton)
        {
            wordsearch.setChecked(true);
            searchView.setVisibility(View.VISIBLE);

            catsearch.setChecked(false);
            linearLayout_Gategory.setVisibility(View.GONE);
        }
        else
        {

            wordsearch.setChecked(false);
            searchView.setVisibility(View.GONE);
            catsearch.setChecked(true);
            linearLayout_Gategory.setVisibility(View.VISIBLE);
           // tv_element.setVisibility(View.VISIBLE);

        }

    }


    private void getJSON(String url,boolean check) {


        class GetJSON extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog=new ProgressDialog(SearchAgent.this);

            @Override
            protected void onPreExecute()
            {
                this.dialog.setMessage("Loading.....");
                this.dialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result ="";
                BufferedReader bufferedReader = null;
                try {
                    String UTF8 = "utf8";
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    result=sb.toString().trim();
                    // Log.i("getJSONOffers", "doInBackground: " +result);
                    return result;

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                jsonResult = result;
                buidlTasksList();
                if(dialog.isShowing())
                    dialog.dismiss();

            }
            @Override
            protected void onCancelled() {
                if(dialog.isShowing())
                    dialog.dismiss();

            }
        }
        GetJSON gj = new GetJSON();

        if(check)
            gj.execute(url);
        else
        {
            if(!gj.isCancelled())
                gj.cancel(true);
        }

    }

    public void buidlTasksList() {

        try {

            if ( jsonResult != null) {

                JSONObject jsonObj = new JSONObject(jsonResult);

                if( !jsonResult.toString().equals("{\"result\":\"NoCategory\"}")) {

                    categoryArray = jsonObj.getJSONArray(TAG_RESULTS);

                    ArrayList<String> cat_adapter_array = new ArrayList<>();
                    ArrayAdapter<String> cat=null;

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

                    String [] arraycat=new String[categoryList.size()];
                    for (int i = 0; i < categoryList.size(); i++)
                        arraycat[i]= categoryList.get(i).getName();

                    ArrayAdapter<String>   All_cat_adapter= new ArrayAdapter<String>(getBaseContext(),android.R.layout.preference_category, arraycat);
                    All_cat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cat_spinner.setAdapter(All_cat_adapter);

                    cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            String [] arrayitem=new String[categoryList.get(i).getItems().size()];
                            for (int jj = 0; jj < categoryList.get(i).getItems().size(); jj++) {
                                arrayitem[jj] = categoryList.get(i).getItems().get(jj).getItemName();

                            }
                            ArrayAdapter<String>   All_item_adapter= new ArrayAdapter<String>(getBaseContext(),android.R.layout.preference_category, arrayitem);
                            All_item_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            item_spinner.setAdapter(All_item_adapter);

                        }
                          @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                            String [] arrayelement=null;

                            for (int j = 0; j < categoryList.size(); j++)
                                for (int v = 0; v < categoryList.get(j).getItems().size(); v++)
                                    if(categoryList.get(j).getName()==cat_spinner.getSelectedItem().toString()
                                            && categoryList.get(j).getItems().get(v).getItemName()== item_spinner.getSelectedItem().toString())
                                    {
                                        arrayelement= new String[categoryList.get(j).getItems().get(v).getElements().size()];
                                        for (int xx = 0; xx < categoryList.get(j).getItems().get(v).getElements().size(); xx++)
                                            arrayelement[xx] = categoryList.get(j).getItems().get(v).getElements().get(xx);

                                        break;

                                    }

                            if(arrayelement != null) {
                                ArrayAdapter<String> All_element_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.preference_category, arrayelement);
                                All_element_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                element_spinner.setAdapter(All_element_adapter);
                                if(arrayelement.length !=0)
                                    tv_element.setVisibility(View.VISIBLE);
                                else
                                    tv_element.setVisibility(View.GONE);

                            }
                            else
                            {

                                tv_element.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                else {
                    Toast.makeText(getBaseContext(), "NO Category", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getBaseContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
