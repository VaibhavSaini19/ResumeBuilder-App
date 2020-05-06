package com.example.resumebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.resumebuilder.FormFragments.EducationFragment;
import com.example.resumebuilder.FormFragments.ExperienceFragment;
import com.example.resumebuilder.FormFragments.ObjectiveFragment;
import com.example.resumebuilder.FormFragments.PersonalFragment;
import com.example.resumebuilder.FormFragments.ProjectFragment;
import com.example.resumebuilder.FormFragments.SkillsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDetailsActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    private Button btn_per, btn_edu, btn_exp, btn_skill, btn_obj, btn_pro, btn_view_cv;
    private String ProfileId, categoryName, templateImgPath, templateFilePath;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private Profile tempProfile;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        intent = getIntent();
        ProfileId = intent.getStringExtra("ProfileId");
        categoryName = intent.getStringExtra("CategoryName");
        templateImgPath = intent.getStringExtra("TemplateImgPath");
        templateFilePath = intent.getStringExtra("TemplateFilePath");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles/");
        databaseReference.child(ProfileId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tempProfile = dataSnapshot.getValue(Profile.class);
                        if(tempProfile == null){
                            tempProfile = new Profile();
                            tempProfile.setProfileId(ProfileId);
                            tempProfile.setCategory(categoryName);
                            databaseReference.child(ProfileId).setValue(tempProfile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });


        btn_per = findViewById(R.id.btn_personal_detail);
        btn_edu = findViewById(R.id.btn_educational_detail);
        btn_exp = findViewById(R.id.btn_experience_detail);
        btn_skill = findViewById(R.id.btn_skill_detail);
        btn_obj = findViewById(R.id.btn_objective_detail);
        btn_pro = findViewById(R.id.btn_project_detail);
//        btn_per.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_personal, 0, 0, 0);
//        btn_edu.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_education, 0, 0, 0);
//        btn_exp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_experience, 0, 0, 0);
//        btn_skill.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_skill, 0, 0, 0);
//        btn_obj.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_objective, 0, 0, 0);
//        btn_pro.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_project, 0, 0, 0);

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

        btn_view_cv = findViewById(R.id.view_cv_btn);
        btn_view_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startViewCVActivity();
            }
        });

    }

    public String getProfileId() {
        return ProfileId;
    }

    protected void startViewCVActivity(){
        Intent intent2 = new Intent(getApplicationContext(), ViewCVActivity.class);
        intent2.putExtra("ProfileId", ProfileId);
        intent2.putExtra("CategoryName", categoryName);
        intent2.putExtra("TemplateFilePath", templateFilePath);
        intent2.putExtra("TemplateImgPath", templateImgPath);
        startActivity(intent2);
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
                Toast.makeText(getApplicationContext(), "addFormFragment() not Working!", Toast.LENGTH_SHORT).show();
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_logout:
                startLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startLogout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
