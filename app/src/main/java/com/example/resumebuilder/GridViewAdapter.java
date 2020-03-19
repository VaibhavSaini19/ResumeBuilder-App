package com.example.resumebuilder;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class GridViewAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<Uri> imagesUri;

    private class ViewHolder {
        GridView template_gv;
    }

    public GridViewAdapter(Context context, ArrayList<Uri> imagesUri){
        super();
        this.mContext = context;
        this.imagesUri = imagesUri;
    }

    @Override
    public int getCount() {
        return imagesUri.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        try {
            imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imagesUri.get(position)));
        }catch (IOException e){
            e.printStackTrace();
        }
        return imageView;
    }

}


//https://stackoverflow.com/questions/33843671/adding-array-of-uris-into-a-gridview
//https://stackoverflow.com/questions/51272723/list-of-uri-to-a-gridview