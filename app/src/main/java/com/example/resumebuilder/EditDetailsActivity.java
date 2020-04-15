package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.resumebuilder.FormFragments.EducationFragment;
import com.example.resumebuilder.FormFragments.ExperienceFragment;
import com.example.resumebuilder.FormFragments.ObjectiveFragment;
import com.example.resumebuilder.FormFragments.PersonalFragment;
import com.example.resumebuilder.FormFragments.ProjectFragment;
import com.example.resumebuilder.FormFragments.SkillsFragment;

public class EditDetailsActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    private Button btn_per, btn_edu, btn_exp, btn_skill, btn_obj, btn_pro;

    private String ProfileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        Intent intent = getIntent();
        ProfileId = intent.getStringExtra("ProfileId");

        btn_per = findViewById(R.id.btn_personal_detail);
        btn_edu = findViewById(R.id.btn_educational_detail);
        btn_exp = findViewById(R.id.btn_experience_detail);
        btn_skill = findViewById(R.id.btn_skill_detail);
        btn_obj = findViewById(R.id.btn_objective_detail);
        btn_pro = findViewById(R.id.btn_project_detail);

        btn_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_per");
            }
        });
        btn_edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_edu");
            }
        });
        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_exp");
            }
        });
        btn_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_skill");
            }
        });
        btn_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_obj");
            }
        });
        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFormFragment("frag_pro");
            }
        });


    }

    public String getProfileId() {
        return ProfileId;
    }

    protected void addFormFragment(String fragName){
        FragmentTransaction transaction = fm.beginTransaction();
        switch (fragName){
            case "frag_per": transaction.replace(R.id.container_main, PersonalFragment.newInstance()); break;
            case "frag_edu": transaction.replace(R.id.container_main, EducationFragment.newInstance()); break;
            case "frag_exp":  transaction.replace(R.id.container_main, ExperienceFragment.newInstance()); break;
            case "frag_skill":  transaction.replace(R.id.container_main, SkillsFragment.newInstance()); break;
            case "frag_obj": transaction.replace(R.id.container_main, ObjectiveFragment.newInstance()); break;
            case "frag_pro":  transaction.replace(R.id.container_main, ProjectFragment.newInstance()); break;
            default:
                Toast.makeText(getApplicationContext(), "addFormFragment() not Working!", Toast.LENGTH_SHORT);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
