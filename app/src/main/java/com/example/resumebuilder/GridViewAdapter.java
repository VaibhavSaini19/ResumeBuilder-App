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

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class GridViewAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<Category.Template> templates;
    private StorageReference storageReference;
    private LayoutInflater inflater;

    private class ViewHolder {
        ImageView templateIV;
    }

    public GridViewAdapter(Context context, ArrayList<Category.Template> templates){
        super();
        this.mContext = context;
        this.templates = templates;
    }

    @Override
    public int getCount() {
        return templates.size();
    }

    @Override
    public Object getItem(int position) {
        return templates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.rv_template_list, parent, false);
            holder = new ViewHolder();
            holder.templateIV = convertView.findViewById(R.id.template_img);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        storageReference = FirebaseStorage.getInstance().getReference(templates.get(position).getImgPath());
        Glide.with(mContext)
                .load(storageReference)
                .into(holder.templateIV);
        return convertView;
    }

}

//https://stackoverflow.com/questions/32076806/android-grid-view-with-custom-base-adapter
//https://stackoverflow.com/questions/33843671/adding-array-of-uris-into-a-gridview
//https://stackoverflow.com/questions/51272723/list-of-uri-to-a-gridview