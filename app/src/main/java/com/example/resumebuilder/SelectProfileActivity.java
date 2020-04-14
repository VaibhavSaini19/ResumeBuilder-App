package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class SelectProfileActivity extends AppCompatActivity {

    private ProfileRVAdapter profileRVAdapter;
    private RecyclerView rv_profile_list;
    private ArrayList<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

//        Intent intent = getIntent();

        Button btn_add_profile = findViewById(R.id.btn_add_profile);
//        btn_add_profile.setOnClickListener();     Start edit details activity

        rv_profile_list = findViewById(R.id.recycler_view_profile_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_profile_list.setLayoutManager(layoutManager);

        profileRVAdapter = new ProfileRVAdapter(this, profileList);
        rv_profile_list.setAdapter(profileRVAdapter);

        profileRVAdapter.notifyDataSetChanged();
    }
}
