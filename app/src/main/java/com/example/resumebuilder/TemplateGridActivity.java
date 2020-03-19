package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class TemplateGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_grid);

//        TODO: Fetch data for 'category' and fill imagesUri with Templates Uri
        ArrayList<Uri> imagesUri = new ArrayList<>();

        GridView templateGV = findViewById(R.id.template_gv);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, imagesUri);

        templateGV.setAdapter(gridViewAdapter);

        templateGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TemplateGridActivity.this, "You clicked " + i, Toast.LENGTH_SHORT).show();
//                TODO: Start EditDetailsActivity
            }
        });

    }
}
