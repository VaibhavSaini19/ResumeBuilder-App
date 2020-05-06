package com.example.resumebuilder;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btn_logout;
    private TextView tv_user;

    private CategoryRVAdapter categoryRVAdapter;
    private ArrayList<Category> categoryList;
    private RecyclerView rv_cateory_list;

    private ProgressBar progressBar;
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

//        tv_user = findViewById(R.id.tv_user);
//        tv_user.setText("Welcome " + user.getEmail());

        categoryList = new ArrayList<>();
        rv_cateory_list = findViewById(R.id.recycler_view_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_cateory_list.setLayoutManager(layoutManager);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        databaseReferenceCategory.child("category")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i=0, count = (int) dataSnapshot.getChildrenCount();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                            Log.d("TAG", snapshot.getValue().toString());
                            categoryList.add(snapshot.getValue(Category.class));
                            progressBar.setProgress((100/count)*(++i));
                        }
                        progressBar.setVisibility(View.GONE);
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
