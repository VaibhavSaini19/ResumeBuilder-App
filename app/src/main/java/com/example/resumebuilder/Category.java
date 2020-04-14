package com.example.resumebuilder;

import android.net.Uri;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Uri> templatesUri;

    public Category(String name, ArrayList<Uri> templatesUri) {
        this.name = name;
        this.templatesUri = templatesUri;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Uri> getTemplatesUri() {
        return templatesUri;
    }
    public void setTemplatesUri(ArrayList<Uri> templatesUri) {
        this.templatesUri = templatesUri;
    }
}
