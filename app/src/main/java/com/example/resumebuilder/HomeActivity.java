package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button btn_logout;
    private TextView tv_user;

    private CategoryRVAdapter categoryRVAdapter;
    private ArrayList<Category> categoryList;
    private RecyclerView rv_cateory_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        tv_user = findViewById(R.id.tv_user);
        tv_user.setText("Welcome "+user.getEmail());

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        rv_cateory_list = findViewById(R.id.recycler_view_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_cateory_list.setLayoutManager(layoutManager);

//        TODO: Fetch data for 'categoryList' from DB
        categoryRVAdapter = new CategoryRVAdapter(this, categoryList);
        rv_cateory_list.setAdapter(categoryRVAdapter);

        categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
