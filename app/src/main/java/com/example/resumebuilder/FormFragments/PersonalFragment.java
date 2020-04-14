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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class PersonalFragment extends Fragment {

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int RESULT_OK = -1;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"profiles");

    Profile userProfile;

    private EditText form_personal_et_name, form_personal_et_email, form_personal_et_address, form_personal_et_contact;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        form_personal_et_name = view.findViewById(R.id.form_personal_et_name);
        form_personal_et_email = view.findViewById(R.id.form_personal_et_email);
        form_personal_et_address = view.findViewById(R.id.form_personal_et_address);
        form_personal_et_contact = view.findViewById(R.id.form_personal_et_contact);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference.child("RandomProfileId")
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

        if(userProfile.getName() != null && !userProfile.getName().isEmpty()){
            form_personal_et_name.setText(userProfile.getName().toString());
        }
        if(userProfile.getAddress() != null && !userProfile.getAddress().isEmpty()){
            form_personal_et_address.setText(userProfile.getAddress().toString());
        }
        if(userProfile.getContact() != null && !userProfile.getContact().isEmpty()){
            form_personal_et_contact.setText(userProfile.getContact().toString());
        }
        if(userProfile.getEmail() != null && !userProfile.getEmail().isEmpty()){
            form_personal_et_email.setText(userProfile.getEmail().toString());
        }


        Button chooseBtn = getView().findViewById(R.id.form_personal_btn_choose);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose an Image");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        Button submitBtn = getView().findViewById(R.id.form_personal_btn_save);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

//    TODO: Implement submitData() to get details from views and save to DB?

    public void submitData(){
        String name = form_personal_et_name.getText().toString().trim();
        String address = form_personal_et_address.getText().toString().trim();
        String contact = form_personal_et_contact.getText().toString().trim();
        String email = form_personal_et_email.getText().toString().trim();

        databaseReference.child("RandomProfileId").child("name").setValue(name);
        databaseReference.child("RandomProfileId").child("address").setValue(address);
        databaseReference.child("RandomProfileId").child("contact").setValue(contact);
        databaseReference.child("RandomProfileId").child("email").setValue(email);

        Toast.makeText(getContext(), "Data saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) getView().findViewById(R.id.form_personal_iv_img);
            Uri img_uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(img_uri));
                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
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
