package com.example.resumebuilder;

import android.net.Uri;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> templatesUriStrings;

    public Category() { }

    public Category(String name, ArrayList<String> templatesUriStrings) {
        this.name = name;
        this.templatesUriStrings = templatesUriStrings;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTemplatesUriStrings() {
        return templatesUriStrings;
    }
    public void setTemplatesUriStrings(ArrayList<String> templatesUriStrings) {
        this.templatesUriStrings = templatesUriStrings;
    }
}
