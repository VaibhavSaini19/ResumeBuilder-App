package com.example.resumebuilder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Category> categoryList;
    private Context mContext;
    private RecyclerView rv_template_list;
    private TextView categoryTitle;

    // Constructor for the Class
    public CategoryRVAdapter(Context context, ArrayList<Category> categoryList) {
        this.mContext = context;
        this.categoryList = categoryList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_category_list, parent, false);
        categoryTitle = view.findViewById(R.id.category_name);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoryList == null? 0: categoryList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final Category category = categoryList.get(position);

        // Set the data to the views here
        holder.setCategoryName(category.getName());
        holder.setCategoryTemplates(category.getTemplates(), category.getName(), this.mContext);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TemplateRVAdapter templateRVAdapter;
        private TextView cName;

        public ContactHolder(View itemView) {
            super(itemView);

            cName = itemView.findViewById(R.id.category_name);
            rv_template_list = itemView.findViewById(R.id.recycler_view_template_list);

            cName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_forward_black_24dp, 0);
            cName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TemplateGridActivity.class);
                    intent.putExtra("CATEGORY", cName.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }


        public void setCategoryName(String name) {
            cName.setText(name);
        }

        public void setCategoryTemplates(ArrayList<Category.Template> templates, String name, Context context) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_template_list.setLayoutManager(layoutManager);
            templateRVAdapter = new TemplateRVAdapter(context, name, templates);
            rv_template_list.setAdapter(templateRVAdapter);

            templateRVAdapter.notifyDataSetChanged();
        }
    }
}