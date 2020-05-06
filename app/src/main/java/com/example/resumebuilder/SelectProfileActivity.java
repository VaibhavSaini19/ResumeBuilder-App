package com.example.resumebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

public class SelectProfileActivity extends AppCompatActivity {

    private String name, templateImgPath, templateFilePath;
    private ProfileRVAdapter profileRVAdapter;
    private RecyclerView rv_profile_list;
    private ArrayList<Profile> profileList;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);


        Button btn_add_profile = findViewById(R.id.btn_add_profile);
        btn_add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditDetailsActivity.class);
                intent.putExtra("ProfileId", UUID.randomUUID().toString());
                intent.putExtra("CategoryName", name);
                intent.putExtra("TemplateImgPath", templateImgPath);
                intent.putExtra("TemplateFilePath", templateFilePath);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        name = intent.getStringExtra("CategoryName");
        templateImgPath = intent.getStringExtra("TemplateImgPath");
        templateFilePath = intent.getStringExtra("TemplateFilePath");

        profileList = new ArrayList<>();

//        Toast.makeText(this,  name + ' ' + templateImgPath + ' ' + templateFilePath, Toast.LENGTH_SHORT).show();

        databaseReference.child("users").child(user.getUid()).child("profiles")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            profileList.add(snapshot.getValue(Profile.class));
                        }
                        rv_profile_list = findViewById(R.id.recycler_view_profile_list);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_profile_list.setLayoutManager(layoutManager);

                        profileRVAdapter = new ProfileRVAdapter(getApplicationContext(), profileList, name, templateImgPath, templateFilePath);
                        rv_profile_list.setAdapter(profileRVAdapter);

                        profileRVAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
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
