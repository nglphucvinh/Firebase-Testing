package com.example.bill.firebasetesting2;

import android.app.Application;

/**
 * This class is for setting a variable globally.
 */

public class GlobalKey extends Application {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
