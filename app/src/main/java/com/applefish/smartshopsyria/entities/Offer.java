package com.applefish.smartshopsyria.entities;

import android.graphics.Bitmap;

/**
 * Created by Ghiath on 12/22/2016.
 */

public class Offer {

    private int id;
    private String title;
    private String date;
    private int numberOfViews;
    private String PDF_URL;// image_url
    private String coverURL;
    private int numberOfPages;
    private String specification;

    private String imageType;
    private String cities;
    private String malls;
    private String locations;

    private int storeID;
    private Bitmap cover;


    public Offer() {

    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getMalls() {
        return malls;
    }

    public void setMalls(String malls) {
        this.malls = malls;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public Offer(int id, String title, String date, int numberOfViews, String PDF_URL, String coverURL, int numberOfPages, String specification, String imageType, int storeID) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.numberOfViews = numberOfViews;
        this.PDF_URL = PDF_URL;
        this.coverURL = coverURL;
        this.numberOfPages = numberOfPages;
        this.specification=specification;
        this.storeID = storeID;
        this.imageType=imageType;
    }

    public Offer(int id, String title, String date, int numberOfViews, String PDF_URL, String coverURL, int numberOfPages, String specification, String imageType, String cities, String malls, String locations, int storeID) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.numberOfViews = numberOfViews;
        this.PDF_URL = PDF_URL;
        this.coverURL = coverURL;
        this.numberOfPages = numberOfPages;
        this.specification = specification;
        this.imageType = imageType;
        this.cities = cities;
        this.malls = malls;
        this.locations = locations;
        this.storeID = storeID;

    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPDF_URL() {
        return PDF_URL;
    }

    public void setPDF_URL(String PDF_URL) {
        this.PDF_URL = PDF_URL;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
}

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }
}
