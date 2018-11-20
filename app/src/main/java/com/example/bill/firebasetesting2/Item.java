package com.example.bill.firebasetesting2;

public class Item {
    private String itemID;
    private String itemName;
    private int itemRating;

    public Item(){

    }

    public Item(String itemID, String itemName, int itemRating) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemRating = itemRating;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemRating() {
        return itemRating;
    }
}
