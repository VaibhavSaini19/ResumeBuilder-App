package com.example.resumebuilder.FormFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.resumebuilder.EditDetailsActivity;
import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.example.resumebuilder.RVFragExpAdapter;
import com.example.resumebuilder.RVFragSkillAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkillsFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    Profile userProfile;
    String ProfileId;

    AlertDialog alertDialog;

    RecyclerView rv_frag_skill_list;

    String skill_lvl;                   // NEEDS TO BE DECLARED AS INSTANCE VARIABLE, since cannot modify it inside anonymous class, if declared final in 'skillFormDiaglog'

    public SkillsFragment() {
        // Required empty public constructor
    }

    public static SkillsFragment newInstance() {
        SkillsFragment fragment = new SkillsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
        ProfileId = editDetailsActivity.getProfileId();
        alertDialog = skillFormDiaglog();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skills, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference.child(ProfileId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Log.d("TAG", dataSnapshot.getValue().toString());
                        userProfile = dataSnapshot.getValue(Profile.class);
                        if (userProfile != null && userProfile.getSkillArrayList() != null) {
                            ArrayList<Profile.Skill> skills = userProfile.getSkillArrayList();

                            rv_frag_skill_list = getView().findViewById(R.id.container_skill_list);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            rv_frag_skill_list.setLayoutManager(layoutManager);

                            RVFragSkillAdapter rvFragSkillAdapter = new RVFragSkillAdapter(getContext(), skills, userProfile);
                            rv_frag_skill_list.setAdapter(rvFragSkillAdapter);

                            rvFragSkillAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

        Button form_skill_btn_add = getView().findViewById(R.id.form_skill_btn_add);
        form_skill_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    public AlertDialog skillFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.form_frag_skill, null);
        final TextInputEditText skill_et = view.findViewById(R.id.form_skill_et_skill);
        final RadioGroup radioGroup = view.findViewById(R.id.form_skill_radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.form_skill_radio_beginner:
                        skill_lvl = "Beginner";
                        break;
                    case R.id.form_skill_radio_intermediate:
                        skill_lvl = "Intermediate";
                        break;
                    case R.id.form_skill_radio_advanced:
                        skill_lvl = "Advanced";
                        break;
                    case R.id.form_skill_radio_expert:
                        skill_lvl = "Expert";
                        break;
                }
            }
        });
        builder.setView(view);

        builder.setTitle("Add skill");
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT);
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String skill = skill_et.getText().toString();
                String lvl = skill_lvl;

                ArrayList<Profile.Skill> skills = userProfile.getSkillArrayList();
                Profile.Skill skillNew = new Profile.Skill(skill, lvl);
                skills.add(skillNew);
                userProfile.setSkillArrayList(skills);

//                Log.d("TAG", userProfile.toString());
                databaseReference.child(ProfileId).setValue(userProfile);
                Toast.makeText(getContext(), "Data added", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
