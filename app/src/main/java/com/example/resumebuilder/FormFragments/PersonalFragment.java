package com.example.resumebuilder.FormFragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.resumebuilder.EditDetailsActivity;
import com.example.resumebuilder.Profile;
import com.example.resumebuilder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

public class PersonalFragment extends Fragment {

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int RESULT_OK = -1;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("users/"+user.getUid());

    Profile userProfile;
    String ProfileId;

    private TextInputEditText form_personal_et_name, form_personal_et_email, form_personal_et_address, form_personal_et_contact;
    private ImageView form_personal_iv_img;
    private Uri img_uri, downloadUri;
    private String img_uri_name;

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
        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
        ProfileId = editDetailsActivity.getProfileId();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        form_personal_iv_img = view.findViewById(R.id.form_personal_iv_img);
        form_personal_et_name = view.findViewById(R.id.form_personal_et_name);
        form_personal_et_email = view.findViewById(R.id.form_personal_et_email);
        form_personal_et_address = view.findViewById(R.id.form_personal_et_address);
        form_personal_et_contact = view.findViewById(R.id.form_personal_et_contact);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference.child(ProfileId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userProfile = dataSnapshot.getValue(Profile.class);
                        if(userProfile != null && userProfile.getProfilePic() != null && !userProfile.getProfilePic().isEmpty()){
                            Glide.with(getContext())
                                    .load(storageReference.child(userProfile.getProfilePic()))
                                    .into(form_personal_iv_img);
                        }
                        if(userProfile != null && userProfile.getName() != null && !userProfile.getName().isEmpty()){
                            form_personal_et_name.setText(userProfile.getName().toString());
                        }
                        if(userProfile != null && userProfile.getAddress() != null && !userProfile.getAddress().isEmpty()){
                            form_personal_et_address.setText(userProfile.getAddress().toString());
                        }
                        if(userProfile != null && userProfile.getContact() != null && !userProfile.getContact().isEmpty()){
                            form_personal_et_contact.setText(userProfile.getContact().toString());
                        }
                        if(userProfile != null && userProfile.getEmail() != null && !userProfile.getEmail().isEmpty()){
                            form_personal_et_email.setText(userProfile.getEmail().toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });


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

    public void submitData(){
        String name = form_personal_et_name.getText().toString().trim();
        String address = form_personal_et_address.getText().toString().trim();
        String contact = form_personal_et_contact.getText().toString().trim();
        String email = form_personal_et_email.getText().toString().trim();

        databaseReference.child(ProfileId).child("name").setValue(name);
        databaseReference.child(ProfileId).child("address").setValue(address);
        databaseReference.child(ProfileId).child("contact").setValue(contact);
        databaseReference.child(ProfileId).child("email").setValue(email);

        if (img_uri != null) {
            if(userProfile != null && userProfile.getProfilePic() != null && !userProfile.getProfilePic().isEmpty()){
                storageReference.child(userProfile.getProfilePic()).delete();
            }
            try {
                img_uri_name = URLEncoder.encode(new File(img_uri.getPath()).getName(), "UTF-8");
            } catch (Exception e){
                e.printStackTrace();
            }
            Toast.makeText(getContext(), img_uri_name, Toast.LENGTH_SHORT).show();
            storageReference.child(img_uri_name).putFile(img_uri)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    databaseReference.child(ProfileId).child("profilePic").setValue(img_uri_name);
                }
            });
        }
        Toast.makeText(getContext(), "Data saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) getView().findViewById(R.id.form_personal_iv_img);
            img_uri = data.getData();
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
