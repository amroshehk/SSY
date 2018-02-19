package com.applefish.smartshopsyria.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.activities.ImagesViewerActivity;
import com.applefish.smartshopsyria.classes.staticMethods;
import com.applefish.smartshopsyria.entities.Offer;

import java.util.ArrayList;

/**
 * Created by Amro on 11/08/2017.
 */

public class RecyclerOfferAdapter  extends RecyclerView.Adapter<RecyclerOfferAdapter.ViewHolder> {

    public  ArrayList<Offer> OffersList;
    Context context;
    private static int lastPosition=-1;
    final static String Key = "com.applefish.smartshop.PdfViewer";
    final static String Key2 = "com.applefish.smartshop.IDOffer";
    final static String Key3= "com.applefish.smartshop.NUMOFPAGE";
    final static String Key4= "com.applefish.smartshop.ImageType";

    public RecyclerOfferAdapter(ArrayList<Offer> offersList,Context context) {
        OffersList = offersList;
        this.context=context;
    }

    @Override
        public RecyclerOfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_news,parent,false);
            ViewHolder viewHolder=new ViewHolder(v);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(OffersList.get(position).getTitle());
            holder.date.setText(OffersList.get(position).getDate());

            holder.Locations.setText(OffersList.get(position).getLocations());
           staticMethods.makeTextViewResizable( holder.Locations, 3, context.getResources().getString(R.string.view_more), true);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


        @Override
        public int getItemCount() {
            return OffersList.size();
        }


        class ViewHolder extends  RecyclerView.ViewHolder{
            public TextView title;
            public TextView date;
            public TextView Locations;
            public Button showBTN;

            public  ViewHolder(final View itemView)
            {
                super(itemView);

                title=(TextView)itemView.findViewById(R.id.offerTitle);
                date=(TextView)itemView.findViewById(R.id.offerdate);
                Locations=(TextView)itemView.findViewById(R.id.offerlocation);
                showBTN=(Button)itemView.findViewById(R.id.showBTN);

//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int position=getAdapterPosition();
//
//
//                                        Intent pdfViewer = new Intent( );
//                                        int tableRowId = v.getId();
//                                        String pdfUrl = OffersList.get(position).getPDF_URL();
//                                        int idoffer = OffersList.get(position).getId();
//                                        int numofpage=OffersList.get(position).getNumberOfPages();
//
//                                        String imagetype=OffersList.get(position).getImageType();
//                                        // Toast.makeText(getBaseContext(),pdfUrl,Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(itemView.getContext(),"Please,wait.....",Toast.LENGTH_SHORT).show();
////                                            Log.i("getAllImages", "setOnClickListener: " +pdfUrl);
//                                        pdfViewer.putExtra(Key,pdfUrl);
//                                        pdfViewer.putExtra(Key2,idoffer);
//                                        pdfViewer.putExtra(Key3,numofpage);
//                                        pdfViewer.putExtra(Key4,imagetype);
//                                        pdfViewer.setClass( itemView.getContext(), ImagesViewerActivity.class );
//                                        // pdfViewer.setClass( getBaseContext(), PdfViewerActivity.class );
//                                        itemView.getContext().startActivity( pdfViewer);
//
//
////                        Snackbar.make(v,"Cliecked detedcted item on "+position,Snackbar.LENGTH_SHORT).
////                                setAction("Action",null).show();
//                    }
//                });

                showBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=getAdapterPosition();


                        Intent pdfViewer = new Intent( );
                        int tableRowId = v.getId();
                        String pdfUrl = OffersList.get(position).getPDF_URL();
                        int idoffer = OffersList.get(position).getId();
                        int numofpage=OffersList.get(position).getNumberOfPages();

                        String imagetype=OffersList.get(position).getImageType();
                        // Toast.makeText(getBaseContext(),pdfUrl,Toast.LENGTH_SHORT).show();
                        Toast.makeText(itemView.getContext(),"Please,wait.....",Toast.LENGTH_SHORT).show();
//                                            Log.i("getAllImages", "setOnClickListener: " +pdfUrl);
                        pdfViewer.putExtra(Key,pdfUrl);
                        pdfViewer.putExtra(Key2,idoffer);
                        pdfViewer.putExtra(Key3,numofpage);
                        pdfViewer.putExtra(Key4,imagetype);
                        pdfViewer.setClass( itemView.getContext(), ImagesViewerActivity.class );
                        // pdfViewer.setClass( getBaseContext(), PdfViewerActivity.class );
                        itemView.getContext().startActivity( pdfViewer);


//                        Snackbar.make(v,"Cliecked detedcted item on "+position,Snackbar.LENGTH_SHORT).
//                                setAction("Action",null).show();
                    }
                });


            }



        }
    }
