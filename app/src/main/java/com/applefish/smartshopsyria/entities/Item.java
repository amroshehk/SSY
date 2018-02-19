package com.applefish.smartshopsyria.entities;

import java.util.ArrayList;

/**
 * Created by Amro on 05/08/2017.
 */

public class Item {
    String itemName;
    private ArrayList<String> elements;

    public Item(String itemName, ArrayList elements) {
        this.itemName = itemName;
        this.elements = elements;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
    }
}
