package com.example.bill.firebasetesting2;

public class Param {

    private String paramID;
    private String paramLocation;
    private String paramName;

    public Param(){

    }

    public Param(String paramID, String paramLocation, String paramName) {
        this.paramID = paramID;
        this.paramLocation = paramLocation;
        this.paramName = paramName;
    }

    public String getParamID() {
        return paramID;
    }

    public String getParamLocation() {
        return paramLocation;
    }

    public String getParamName() {
        return paramName;
    }
}
