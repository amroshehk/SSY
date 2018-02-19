package com.applefish.smartshopsyria.entities;

import java.util.ArrayList;

/**
 * Created by Amro on 16/02/2017.
 */

public class Category {
    private int id;
    private String name;
    private ArrayList<Item> items;

    public Category(int id, String name, ArrayList items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
