package com.example.resumebuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private CategoryRVAdapter categoryRVAdapter;
    private ArrayList<Category> categoryList;
    private RecyclerView rv_cateory_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv_cateory_list = findViewById(R.id.recycler_view_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_cateory_list.setLayoutManager(layoutManager);

//        TODO: Fetch data for 'categoryList' from DB
        categoryRVAdapter = new CategoryRVAdapter(this, categoryList);
        rv_cateory_list.setAdapter(categoryRVAdapter);

        categoryRVAdapter.notifyDataSetChanged();
    }
}
