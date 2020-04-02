package com.example.resumebuilder.FormFragments;

import android.content.Context;
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

import com.example.resumebuilder.R;

public class ExperienceFragment extends Fragment {

    private EditText form_exp_et_company, form_exp_et_job, form_exp_et_sdate, form_exp_et_edate, form_exp_et_desc;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_experience, container, false);
        form_exp_et_company = view.findViewById(R.id.form_exp_et_company);
        form_exp_et_job = view.findViewById(R.id.form_exp_et_job);
        form_exp_et_sdate = view.findViewById(R.id.form_exp_et_sdate);
        form_exp_et_edate = view.findViewById(R.id.form_exp_et_edate);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button submitBtn = getView().findViewById(R.id.form_exp_btn_save);
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
