package com.example.resumebuilder.FormFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.resumebuilder.CategoryRVAdapter;
import com.example.resumebuilder.EditDetailsActivity;
import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.example.resumebuilder.RVFragEduAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class EducationFragment extends Fragment {


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    Profile userProfile;
    String ProfileId;

    AlertDialog alertDialog;

    RecyclerView rv_frag_edu_list;

    public EducationFragment() {
        // Required empty public constructor
    }

    public static EducationFragment newInstance() {
        EducationFragment fragment = new EducationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
        ProfileId = editDetailsActivity.getProfileId();
        alertDialog = eduFormDiaglog();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);
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
                            ArrayList<Profile.Education> educations = userProfile.getEducationArrayList();
//                            Log.d("TAG", Arrays.toString(educations.toArray()));
                            rv_frag_edu_list = getView().findViewById(R.id.container_edu_list);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            rv_frag_edu_list.setLayoutManager(layoutManager);

                            RVFragEduAdapter rvFragEduAdapter = new RVFragEduAdapter(getContext(), educations, userProfile);
                            rv_frag_edu_list.setAdapter(rvFragEduAdapter);

                            rvFragEduAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

        Button form_edu_btn_add = getView().findViewById(R.id.form_edu_btn_add);
        form_edu_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });


    }

    private AlertDialog eduFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.form_frag_edu, null);
        final TextInputEditText degree_et = view.findViewById(R.id.form_edu_et_degree);
        final TextInputEditText university_et = view.findViewById(R.id.form_edu_et_university);
        final TextInputEditText grade_et = view.findViewById(R.id.form_edu_et_grade);
        final TextInputEditText year_et = view.findViewById(R.id.form_edu_et_year);
        builder.setView(view);

        builder.setTitle("Add education");
        builder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT);
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String degree = degree_et.getText().toString();
                String university = university_et.getText().toString();
                String grade = grade_et.getText().toString();
                String year = year_et.getText().toString();

                ArrayList<Profile.Education> educations = userProfile.getEducationArrayList();
                Profile.Education educationNew = new Profile.Education(degree, university, grade, year);
                educations.add(educationNew);
                userProfile.setEducationArrayList(educations);

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
