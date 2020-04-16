package com.example.resumebuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RVFragProAdapter extends RecyclerView.Adapter<RVFragProAdapter.proHolder> {

    // List to store all the contact details
    private ArrayList<Profile.Project> projectArrayList;
    private Context mContext;

    // Constructor for the Class
    public RVFragProAdapter(Context context, ArrayList<Profile.Project> projectArrayList) {
        this.mContext = context;
        this.projectArrayList = projectArrayList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public RVFragProAdapter.proHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_frag_project, parent, false);
        return new RVFragProAdapter.proHolder(view);
    }

    @Override
    public int getItemCount() {
        return projectArrayList == null? 0: projectArrayList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull RVFragProAdapter.proHolder holder, final int position) {
        final Profile.Project project = projectArrayList.get(position);

        // Set the data to the views here
        holder.setProject(project);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class proHolder extends RecyclerView.ViewHolder {

        TextInputEditText title_et, desc_et;
        public proHolder(View itemView) {
            super(itemView);
            title_et = itemView.findViewById(R.id.form_pro_et_title);
            desc_et = itemView.findViewById(R.id.form_pro_et_desc);
        }

        public void setProject(Profile.Project project) {
            title_et.setText(project.getTitle());
            desc_et.setText(project.getDescription());
        }
    }
}