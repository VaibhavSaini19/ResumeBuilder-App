package com.example.resumebuilder;

import android.net.Uri;

import java.util.ArrayList;

public class Profile {
    private String name, category, dob, address;

    public Profile(String name, String category, String dob, String address) {
        this.name = name;
        this.category = category;
        this.dob = dob;
        this.address = address;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}
