package com.example.resumebuilder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.PrivateKey;
import java.util.ArrayList;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ProfileHolder> {

    // List to store all the contact details
    private String name, templateImgPath, templateFilePath;
    private ArrayList<Profile> profileList;
    private Context mContext;
    private RecyclerView rv_template_list;

    private AlertDialog alertDialog;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("users/"+user.getUid());

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
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_profile_list, parent, false);
//        alertDialog = confirmDeleteDialog();
        return new ProfileHolder(view);
    }

    @Override
    public int getItemCount() {
        return profileList == null? 0: profileList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, final int position) {
        final Profile profile = profileList.get(position);

        // Set the data to the views here
        holder.setProfileCount(position);
        holder.setProfileImage(profile.getProfilePic());
        holder.setProfileName(profile.getName());
        holder.setProfileCategory(profile.getCategory());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ProfileHolder extends RecyclerView.ViewHolder {

        private ImageView pImage;
        private TextView pName, pCategory, pCount;
        private Button editBtn, deleteBtn;
        private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        public ProfileHolder(View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.profile_img);
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
                    final int pos = getAdapterPosition();
                    databaseReference.child("users/"+user.getUid()+"/profiles")
                            .child(profileList.get(pos).getProfileId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            profileList.remove(pos);
                            notifyItemRemoved(pos);
                        }
                    });
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), ViewCVActivity.class);
                    intent.putExtra("CategoryName", name);
                    intent.putExtra("TemplateImgPath", templateImgPath);
                    intent.putExtra("TemplateFilePath", templateFilePath);
                    intent.putExtra("ProfileId", profileList.get(pos).getProfileId());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setProfileCount(int pos){
            pCount.setText(String.valueOf(pos+1));
        }

        public void setProfileImage(String profileImage){
            if(profileImage != null) {
                Glide.with(mContext)
                        .load(storageReference.child(profileImage))
                        .into(pImage);
            }
        }

        public void setProfileName(String name) {
            if(name != null) {
                pName.setText(name);
            }
        }

        public void setProfileCategory(String category) {
            if (category != null) {
                pCategory.setText(category);
            }
        }
    }
}
