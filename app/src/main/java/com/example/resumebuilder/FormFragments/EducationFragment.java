package com.example.resumebuilder.FormFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.util.Map;

public class EducationFragment extends Fragment {

    private EditText form_edu_et_degree, form_edu_et_university, form_edu_et_grade, form_edu_et_year;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid());

    Profile userProfile;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        form_edu_et_degree = view.findViewById(R.id.form_edu_et_degree);
        form_edu_et_university = view.findViewById(R.id.form_edu_et_university);
        form_edu_et_grade = view.findViewById(R.id.form_edu_et_grade);
        form_edu_et_year = view.findViewById(R.id.form_edu_et_year);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference.child("profiles").child("RandomProfileId")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userProfile = dataSnapshot.getValue(Profile.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

        // TODO: How tf iterate all arraylist object and display them? -_-


        Button submitBtn = getView().findViewById(R.id.form_edu_btn_save);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

//    TODO: Implement submitData() to get details from views and save to DB?
    public void submitData(){
        String degree = form_edu_et_degree.getText().toString().trim();
        String university = form_edu_et_university.getText().toString().trim();
        String grade = form_edu_et_grade.getText().toString().trim();
        String year = form_edu_et_year.getText().toString().trim();

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
