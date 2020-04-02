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
import android.widget.RadioButton;

import com.example.resumebuilder.R;

public class SkillsFragment extends Fragment {

    private EditText form_skill_et_skill;
    private RadioButton form_skill_radio;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skills, container, false);
        form_skill_et_skill = view.findViewById(R.id.form_skill_et_skill);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button submitBtn = getView().findViewById(R.id.form_skill_btn_save);
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

//    TODO: Save the value of selected radio button to instance variable?
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.form_skill_radio_beginner:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.form_skill_radio_intermediate:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.form_skill_radio_advanced:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.form_skill_radio_expert:
                if (checked)
                    // Ninjas rule
                    break;
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
