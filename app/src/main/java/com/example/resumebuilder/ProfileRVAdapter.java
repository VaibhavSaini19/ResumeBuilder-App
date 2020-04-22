package com.example.resumebuilder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;
import java.util.ArrayList;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ContactHolder> {

    // List to store all the contact details
    private String name, templateImgPath, templateFilePath;
    private ArrayList<Profile> profileList;
    private Context mContext;
    private RecyclerView rv_template_list;

    private AlertDialog alertDialog;

    // Constructor for the Class
    public ProfileRVAdapter(Context context, ArrayList<Profile> profileList, String name, String templateImgPath, String templateFilePath) {
        this.mContext = context;
        this.name = name;
        this.templateImgPath = templateImgPath;
        this.templateFilePath = templateFilePath;
        this.profileList = profileList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_profile_list, parent, false);
//        alertDialog = confirmDeleteDialog();
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
        holder.setProfileCount(position);
        holder.setProfileName(profile.getName());
        holder.setProfileCategory(profile.getCategory());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView pName, pCategory, pCount;
        private Button editBtn, deleteBtn;
        private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        public ContactHolder(View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.profile_name);
            pCategory = itemView.findViewById(R.id.profile_category);
            pCount = itemView.findViewById(R.id.profile_count);

            editBtn = itemView.findViewById(R.id.btn_edit);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), EditDetailsActivity.class);
                    intent.putExtra("CategoryName", name);
                    intent.putExtra("TemplateImgPath", templateImgPath);
                    intent.putExtra("TemplateFilePath", templateFilePath);
                    intent.putExtra("ProfileId", profileList.get(pos).getProfileId());
                    view.getContext().startActivity(intent);
                }
            });
            deleteBtn = itemView.findViewById(R.id.btn_del);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    databaseReference.child("users/"+user.getUid()+"/profiles")
                            .child(profileList.get(pos).getProfileId()).removeValue();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: View CV on click
//                    int pos = getAdapterPosition();
//                    Intent intent = new Intent(view.getContext(), DisplayTemplate.class);
//                    intent.putExtra("CategoryName", name);
//                    intent.putExtra("TemplateImgPath", templateImgPath);
//                    intent.putExtra("TemplateFilePath", templateFilePath);
//                    intent.putExtra("ProfileId", profileList.get(pos).getProfileId());
//                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setProfileCount(int pos){
            pCount.setText(String.valueOf(pos+1));
        }

        public void setProfileName(String name) {
            pName.setText(name);
        }

        public void setProfileCategory(String category) { pCategory.setText(category); }
    }
}
