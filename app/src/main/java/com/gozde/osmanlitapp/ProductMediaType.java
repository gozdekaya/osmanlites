package com.gozde.osmanlitapp;

public enum ProductMediaType {

    VIDEO("video",0),
    IMAGE("image",1);

    private String stringValue;
    private int intValue;

    ProductMediaType(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
