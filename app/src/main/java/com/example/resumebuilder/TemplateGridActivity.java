package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TemplateGridActivity extends AppCompatActivity {

    private String categoryName;
    private Category category;
    private TextView category_name_gv;

    private DatabaseReference databaseReferenceCategory;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_grid);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CATEGORY");
        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("category/"+categoryName);

        category_name_gv = findViewById(R.id.category_name_gv);
        category_name_gv.setText(categoryName);

//        TODO: Fetch data for 'category' and fill imagesUri with Templates Uri
        databaseReferenceCategory
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        category = dataSnapshot.getValue(Category.class);

                        GridView templateGV = findViewById(R.id.template_gv);
                        GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(), category.getTemplates());
                        templateGV.setAdapter(gridViewAdapter);
                        templateGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(TemplateGridActivity.this, "You clicked " + i, Toast.LENGTH_SHORT).show();
    //                          TODO: Start SelectProfileActivity instead of EditDetailsActivity
                                Intent intent = new Intent(getApplicationContext(), EditDetailsActivity.class);
                                intent.putExtra("ProfileId", "RandomProfileId");
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
    }
}
