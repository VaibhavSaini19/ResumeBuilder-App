package com.example.resumebuilder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVFragEduAdapter extends RecyclerView.Adapter<RVFragEduAdapter.educationHolder> {

    // List to store all the contact details
    private ArrayList<Profile.Education> educationArrayList;
    private Context mContext;
    private Profile userProfile;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    // Constructor for the Class
    public RVFragEduAdapter(Context context, ArrayList<Profile.Education> educationArrayList, Profile userProfile) {
        this.mContext = context;
        this.educationArrayList = educationArrayList;
        this.userProfile = userProfile;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public RVFragEduAdapter.educationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_frag_edu, parent, false);
        return new RVFragEduAdapter.educationHolder(view);
    }

    @Override
    public int getItemCount() {
        return educationArrayList == null? 0: educationArrayList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull RVFragEduAdapter.educationHolder holder, final int position) {
        final Profile.Education education = educationArrayList.get(position);

        // Set the data to the views here
        holder.setEducation(education);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class educationHolder extends RecyclerView.ViewHolder {

        EditText degree_et, university_tv, grade_tv, year_tv;
        Button btn_remove_edu;

        public educationHolder(View itemView) {
            super(itemView);
            degree_et = itemView.findViewById(R.id.form_edu_et_degree);
            university_tv = itemView.findViewById(R.id.form_edu_et_university);
            grade_tv = itemView.findViewById(R.id.form_edu_et_grade);
            year_tv = itemView.findViewById(R.id.form_edu_et_year);

            btn_remove_edu = itemView.findViewById(R.id.btn_remove_edu);
            btn_remove_edu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    educationArrayList.remove(pos);
                    userProfile.setEducationArrayList(educationArrayList);
                    databaseReference.child(userProfile.getProfileId()).setValue(userProfile);
                    notifyItemRemoved(pos);
                }
            });
        }

        public void setEducation(Profile.Education education) {
            degree_et.setText(education.getDegree());
            university_tv.setText(education.getUniversity());
            grade_tv.setText(education.getGrade());
            year_tv.setText(education.getYear());
        }
    }
}