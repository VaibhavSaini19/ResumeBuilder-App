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

import com.example.resumebuilder.R;

import java.io.FileNotFoundException;

public class PersonalFragment extends Fragment {

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int RESULT_OK = -1;

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
