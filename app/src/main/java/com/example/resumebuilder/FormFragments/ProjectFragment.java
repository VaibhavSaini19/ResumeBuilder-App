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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.resumebuilder.EditDetailsActivity;
import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.example.resumebuilder.RVFragProAdapter;
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

public class ProjectFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    Profile userProfile;
    String ProfileId;

    AlertDialog alertDialog;

    RecyclerView rv_frag_project_list;


    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
        ProfileId = editDetailsActivity.getProfileId();
        alertDialog = projectFormDiaglog();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
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
                        if(userProfile != null && userProfile.getProjectArrayList() != null) {
                            ArrayList<Profile.Project> projects = userProfile.getProjectArrayList();

                            rv_frag_project_list = getView().findViewById(R.id.container_project_list);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            rv_frag_project_list.setLayoutManager(layoutManager);

                            RVFragProAdapter rvFragProAdapter = new RVFragProAdapter(getContext(), projects, userProfile);
                            rv_frag_project_list.setAdapter(rvFragProAdapter);

                            rvFragProAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

        Button submitBtn = getView().findViewById(R.id.form_pro_btn_add);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    public AlertDialog projectFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.form_frag_pro, null);
        final TextInputEditText title_et = view.findViewById(R.id.form_pro_et_title);
        final TextInputEditText desc_et = view.findViewById(R.id.form_pro_et_desc);
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
                String title = title_et.getText().toString().trim();
                String desc = desc_et.getText().toString().trim();

                ArrayList<Profile.Project> projects = userProfile.getProjectArrayList();
                Profile.Project projectNew = new Profile.Project(title, desc);
                projects.add(projectNew);
                userProfile.setProjectArrayList(projects);

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
