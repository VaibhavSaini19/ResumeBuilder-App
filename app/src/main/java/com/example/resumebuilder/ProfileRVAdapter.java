package com.example.resumebuilder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Profile> profileList;
    private Context mContext;
    private RecyclerView rv_template_list;

    // Constructor for the Class
    public ProfileRVAdapter(Context context, ArrayList<Profile> profileList) {
        this.mContext = context;
        this.profileList = profileList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_profile_list, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return profileList == null? 0: profileList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final Profile profile = profileList.get(position);

        // Set the data to the views here
        holder.setProfileName(profile.getName());
        holder.setProfileCategory(profile.getCategory());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView pName, pCategory;

        public ContactHolder(View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.profile_name);
            pCategory = itemView.findViewById(R.id.profile_category);
        }

        public void setProfileName(String name) {
            pName.setText(name);
        }

        public void setProfileCategory(String category) { pCategory.setText(category); }
    }
}
