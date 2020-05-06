package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
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

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceCategory;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_grid);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CATEGORY");
        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("category/"+categoryName);

        category_name_gv = findViewById(R.id.category_name_gv);
        category_name_gv.setText(categoryName);
        category_name_gv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back_black_24dp, 0, 0, 0);
        category_name_gv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
                finish();
            }
        });

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
//                                Toast.makeText(TemplateGridActivity.this, "You clicked " + i, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), SelectProfileActivity.class);
                                intent.putExtra("CategoryName", categoryName);
                                intent.putExtra("TemplateImgPath", category.getTemplates().get(i).getImgPath());
                                intent.putExtra("TemplateFilePath", category.getTemplates().get(i).getImgPath());
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
