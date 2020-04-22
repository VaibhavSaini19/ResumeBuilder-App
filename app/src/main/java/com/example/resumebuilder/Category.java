package com.example.resumebuilder;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private ArrayList<Template> templates;

    public Category() { }

    public Category(String name, ArrayList<Template> templates) {
        this.name = name;
        this.templates = templates;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Template> getTemplates() {
        return templates;
    }
    public void setTemplates(ArrayList<Template> templates) {
        this.templates = templates;
    }

    public static class Template{
        public String imgPath, filePath;
        public Template() {}
        public Template(String imgPath, String filePath){
            this.imgPath= imgPath;
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getImgPath() {
            return imgPath;
        }
        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }
    }
}
