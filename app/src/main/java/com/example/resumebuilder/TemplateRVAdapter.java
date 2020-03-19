package com.example.resumebuilder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TemplateRVAdapter extends RecyclerView.Adapter<TemplateRVAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Uri> templateUriList;
    private Context mContext;

    // Constructor for the Class
    public TemplateRVAdapter(Context context, ArrayList<Uri> templateUriList) {
        this.mContext = context;
        this.templateUriList = templateUriList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_template_list, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return templateUriList == null? 0: templateUriList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final Uri templateUri = templateUriList.get(position);

        // Set the data to the views here
        holder.setTemplateUri(templateUri);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private ImageView templateImg;

        public ContactHolder(View itemView) {
            super(itemView);
            templateImg = itemView.findViewById(R.id.template_img);
        }

        public void setTemplateUri(Uri uri) {
            templateImg.setImageURI(uri);
        }
    }
}