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
import android.widget.Toast;

import com.example.resumebuilder.EditDetailsActivity;
import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.example.resumebuilder.RVFragEduAdapter;
import com.example.resumebuilder.RVFragExpAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExperienceFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    Profile userProfile;
    String ProfileId;

    AlertDialog alertDialog;

    RecyclerView rv_frag_exp_list;

    public ExperienceFragment() {
        // Required empty public constructor
    }

    public static ExperienceFragment newInstance() {
        ExperienceFragment fragment = new ExperienceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
        ProfileId = editDetailsActivity.getProfileId();
        alertDialog = expFormDiaglog();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_experience, container, false);
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
                        if (userProfile != null && userProfile.getEducationArrayList() != null) {
                            ArrayList<Profile.Experience> experiences = userProfile.getExperienceArrayList();

                            rv_frag_exp_list = getView().findViewById(R.id.container_exp_list);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            rv_frag_exp_list.setLayoutManager(layoutManager);

                            RVFragExpAdapter rvFragExpAdapter = new RVFragExpAdapter(getContext(), experiences, userProfile);
                            rv_frag_exp_list.setAdapter(rvFragExpAdapter);

                            rvFragExpAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

        Button form_exp_btn_add = getView().findViewById(R.id.form_exp_btn_add);
        form_exp_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    private AlertDialog expFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.form_frag_exp, null);
        final TextInputEditText company_et = view.findViewById(R.id.form_exp_et_company);
        final TextInputEditText job_et = view.findViewById(R.id.form_exp_et_job);
        final TextInputEditText sdate_et = view.findViewById(R.id.form_exp_et_sdate);
        final TextInputEditText edate_et = view.findViewById(R.id.form_exp_et_edate);
        final TextInputEditText desc_et = view.findViewById(R.id.form_exp_et_desc);
        builder.setView(view);

        builder.setTitle("Add experience");
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT);
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String company = company_et.getText().toString();
                String job = job_et.getText().toString();
                String sdate = sdate_et.getText().toString();
                String edate = edate_et.getText().toString();
                String desc = desc_et.getText().toString();

                ArrayList<Profile.Experience> experiences = userProfile.getExperienceArrayList();
                Profile.Experience experienceNew = new Profile.Experience(company, job, sdate, edate, desc);
                experiences.add(experienceNew);
                userProfile.setExperienceArrayList(experiences);

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
