package com.example.resumebuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button btn_logout;
    private TextView tv_user;

    private CategoryRVAdapter categoryRVAdapter;
    private ArrayList<Category> categoryList;
    private RecyclerView rv_cateory_list;

    private DatabaseReference databaseReferenceCategory;

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
        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference();

        tv_user = findViewById(R.id.tv_user);
        tv_user.setText("Welcome "+user.getEmail());

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        rv_cateory_list = findViewById(R.id.recycler_view_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_cateory_list.setLayoutManager(layoutManager);

//        TODO: Fetch data for 'categoryList' from DB
        databaseReferenceCategory.child("category")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot categoryListDB) {
                        for(int i=0; i < categoryListDB.getChildrenCount(); i++){
                            final ArrayList<Uri> uris = new ArrayList<Uri>();
                            databaseReferenceCategory.child(categoryListDB.child(String.valueOf(i)).getValue(String.class))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot uriListDB) {
                                            for (int j=0; j < uriListDB.getChildrenCount(); j++){
                                                uris.add(uriListDB.child(String.valueOf(i)).getValue(String.class));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            throw databaseError.toException();
                                        }
                                    });
                        }
                        categoryRVAdapter = new CategoryRVAdapter(getApplicationContext(), categoryList);
                        rv_cateory_list.setAdapter(categoryRVAdapter);

                        categoryRVAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
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
