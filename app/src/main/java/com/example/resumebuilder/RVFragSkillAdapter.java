package com.example.resumebuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVFragSkillAdapter extends RecyclerView.Adapter<RVFragSkillAdapter.skillHolder> {

    // List to store all the contact details
    private ArrayList<Profile.Skill> skillArrayList;
    private Context mContext;
    private Profile userProfile;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    // Constructor for the Class
    public RVFragSkillAdapter(Context context, ArrayList<Profile.Skill> skillArrayList, Profile userProfile) {
        this.mContext = context;
        this.skillArrayList = skillArrayList;
        this.userProfile = userProfile;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public RVFragSkillAdapter.skillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_frag_skill, parent, false);
        return new RVFragSkillAdapter.skillHolder(view);
    }

    @Override
    public int getItemCount() {
        return skillArrayList == null? 0: skillArrayList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull RVFragSkillAdapter.skillHolder holder, final int position) {
        final Profile.Skill skill = skillArrayList.get(position);

        // Set the data to the views here
        holder.setSkill(skill);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class skillHolder extends RecyclerView.ViewHolder {

        TextInputEditText skill_et;
        RadioButton rbBeginner, rbIntermediate, rbAdvanced, rbExpert;
        String skill_lvl;
        Button btn_remove_skill;

        public skillHolder(View itemView) {
            super(itemView);
            skill_et = itemView.findViewById(R.id.form_skill_et_skill);
            rbBeginner = itemView.findViewById(R.id.form_skill_radio_beginner);
            rbBeginner.setEnabled(false);
            rbIntermediate = itemView.findViewById(R.id.form_skill_radio_intermediate);
            rbIntermediate.setEnabled(false);
            rbAdvanced = itemView.findViewById(R.id.form_skill_radio_advanced);
            rbAdvanced.setEnabled(false);
            rbExpert = itemView.findViewById(R.id.form_skill_radio_expert);
            rbExpert.setEnabled(false);

            btn_remove_skill = itemView.findViewById(R.id.btn_remove_skill);
            btn_remove_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    skillArrayList.remove(pos);
                    userProfile.setSkillArrayList(skillArrayList);
                    databaseReference.child(userProfile.getProfileId()).setValue(userProfile);
                    notifyItemRemoved(pos);
                }
            });
        }

        public void setSkill(Profile.Skill skill) {
            skill_et.setText(skill.getName());
            rbBeginner.setChecked(skill.getLevel().equals("Beginner"));
            rbIntermediate.setChecked(skill.getLevel().equals("Intermediate"));
            rbAdvanced.setChecked(skill.getLevel().equals("Advanced"));
            rbExpert.setChecked(skill.getLevel().equals("Expert"));
        }
    }
}