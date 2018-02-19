package com.applefish.smartshopsyria.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.activities.MainActivity;
import com.applefish.smartshopsyria.activities.SearchActivity;
import com.applefish.smartshopsyria.classes.ConnectChecked;

import static com.applefish.smartshopsyria.activities.MainActivity.categoryList;


public class SearchFragment extends Fragment {

    private RadioButton wordsearch;
    private RadioButton catsearch;
    private SearchView searchView;
    private Spinner cat_spinner;
    private Spinner item_spinner;
    private Spinner element_spinner;
    private Button button;
    private LinearLayout linearLayout_Gategory;
    private LinearLayout tv_element;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate( R.layout.content_search_agent, container, false );
        wordsearch= (RadioButton) rootView.findViewById(R.id.radioButton);
        catsearch= (RadioButton) rootView.findViewById(R.id.radioButton2);
        searchView= (android.support.v7.widget.SearchView) rootView.findViewById(R.id.search);
        cat_spinner= (Spinner) rootView.findViewById(R.id.spinner);
        item_spinner= (Spinner) rootView.findViewById(R.id.spinner2);
        element_spinner=(Spinner) rootView.findViewById(R.id.spinner3);
        button= (Button) rootView.findViewById(R.id.button2);
        linearLayout_Gategory=(LinearLayout)rootView.findViewById(R.id.line2);
        tv_element=(LinearLayout)rootView.findViewById(R.id.line_element);

        MainActivity.fab.setVisibility(View.GONE);

        String [] arraycat=new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++)
            arraycat[i]= categoryList.get(i).getName();

        ArrayAdapter<String>   All_cat_adapter= new ArrayAdapter<String>(getContext(),android.R.layout.preference_category, arraycat);
        All_cat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat_spinner.setAdapter(All_cat_adapter);

        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String [] arrayitem=new String[categoryList.get(i).getItems().size()];
                for (int jj = 0; jj < categoryList.get(i).getItems().size(); jj++) {
                    arrayitem[jj] = categoryList.get(i).getItems().get(jj).getItemName();

                }
                ArrayAdapter<String>   All_item_adapter= new ArrayAdapter<String>(getContext(),android.R.layout.preference_category, arrayitem);
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
                    ArrayAdapter<String> All_element_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.preference_category, arrayelement);
                    All_element_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    element_spinner.setAdapter(All_element_adapter);
                    if(arrayelement.length !=0)
                        tv_element.setVisibility(View.VISIBLE);
                    else
                        tv_element.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        wordsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchWay(view);
            }
        });
        catsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchWay(view);
            }
        });


        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if ( ConnectChecked.isNetworkAvailable( getContext() )
                        ) {
                    Intent searchIntent = new Intent();
                    searchIntent.setClass( getContext(), SearchActivity.class );
                    searchIntent.putExtra( SearchManager.QUERY , query);
                    searchIntent.setAction( Intent.ACTION_SEARCH );
                    startActivity(searchIntent);
                    return false;
                } else {
                    Snackbar.make(searchView, "No Internet Connection", Snackbar.LENGTH_LONG)
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

                if ( ConnectChecked.isNetworkAvailable( getContext() )  ) {
                    Intent searchIntent = new Intent();
                    searchIntent.setClass( getContext(), SearchActivity.class );
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
                    Snackbar.make(searchView, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });



        return rootView;


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


}
