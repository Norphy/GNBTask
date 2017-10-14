package com.example.android.gnbtask;


import java.util.ArrayList;

/**
 * Created on 10/13/2017.
 */

public class ExploreSight {

    //Integer private variable that will hold the id of the place item
    private int id;

    //Image object
    private image image;

    //Integer that holds price returned from API of the sight place
    private int price;

    //String that holds the description returned from the API of the sight place
    private String placeDescription;

    public ExploreSight() {  }

    //Getter method to get id
    public int getId() { return id; }

    //Getter method to get url
    public image getImage() { return image; }

    //Getter method to get price int
    public int getPrice() { return price; }

    //Getter method to get description String
    public String getPlaceDescription() { return placeDescription; }

    public void setId(int setId) { id = setId; }

    //Getter method to get id
    public void setImage(image setImage) { image = setImage; }

    public void setPrice (int setPrice) { price = setPrice; }

    public void setPlaceDescription(String description) {placeDescription = description;}
}


