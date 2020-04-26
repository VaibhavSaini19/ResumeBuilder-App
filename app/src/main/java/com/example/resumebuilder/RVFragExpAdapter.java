package com.example.resumebuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVFragExpAdapter extends RecyclerView.Adapter<RVFragExpAdapter.experienceHolder> {

    // List to store all the contact details
    private ArrayList<Profile.Experience> experienceArrayList;
    private Context mContext;
    private Profile userProfile;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    // Constructor for the Class
    public RVFragExpAdapter(Context context, ArrayList<Profile.Experience> experienceArrayList, Profile userProfile) {
        this.mContext = context;
        this.experienceArrayList = experienceArrayList;
        this.userProfile = userProfile;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public RVFragExpAdapter.experienceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            // Inflate the layout view you have created for the list rows here
            View view = layoutInflater.inflate(R.layout.rv_frag_exp, parent, false);
            return new RVFragExpAdapter.experienceHolder(view);
            }

    @Override
    public int getItemCount() {
            return experienceArrayList == null? 0: experienceArrayList.size();
            }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull RVFragExpAdapter.experienceHolder holder, final int position) {
    final Profile.Experience experience = experienceArrayList.get(position);

            // Set the data to the views here
            holder.setExperience(experience);

            // You can set click listners to indvidual items in the viewholder here
            // make sure you pass down the listner or make the Data members of the viewHolder public

            }

    // This is your ViewHolder class that helps to populate data to the view
    public class experienceHolder extends RecyclerView.ViewHolder {

        EditText company_et, job_tv, sdate_tv, edate_tv, desc_tv;
        Button btn_remove_exp;
        public experienceHolder(View itemView) {
            super(itemView);
            company_et = itemView.findViewById(R.id.form_exp_et_company);
            job_tv = itemView.findViewById(R.id.form_exp_et_job);
            sdate_tv = itemView.findViewById(R.id.form_exp_et_sdate);
            edate_tv = itemView.findViewById(R.id.form_exp_et_edate);
            desc_tv = itemView.findViewById(R.id.form_exp_et_desc);

            btn_remove_exp = itemView.findViewById(R.id.btn_remove_exp);
            btn_remove_exp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    experienceArrayList.remove(pos);
                    userProfile.setExperienceArrayList(experienceArrayList);
                    databaseReference.child(userProfile.getProfileId()).setValue(userProfile);
                    notifyItemRemoved(pos);
                }
            });
        }

        public void setExperience(Profile.Experience experience) {
            company_et.setText(experience.getCompany());
            job_tv.setText(experience.getJob());
            sdate_tv.setText(experience.getStart());
            edate_tv.setText(experience.getEnd());
            desc_tv.setText(experience.getDescription());
        }
    }
}