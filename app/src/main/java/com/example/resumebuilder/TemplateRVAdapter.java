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
    private ArrayList<String> templateUriStrings;
    private Context mContext;

    // Constructor for the Class
    public TemplateRVAdapter(Context context, ArrayList<String> templateUriStrings) {
        this.mContext = context;
        this.templateUriStrings = templateUriStrings;
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
        return templateUriStrings == null? 0: templateUriStrings.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final String templateUriString = templateUriStrings.get(position);

        // Set the data to the views here
        holder.setTemplateUri(templateUriString);

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

        // TODO: Use Uri to fetch template image from Firebase storage
        public void setTemplateUri(String uriString) {
            Uri imgUri = Uri.parse(uriString);
            templateImg.setImageURI(null);
            templateImg.setImageURI(imgUri);
        }
    }
}