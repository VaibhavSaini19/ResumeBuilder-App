package com.example.resumebuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class TemplateRVAdapter extends RecyclerView.Adapter<TemplateRVAdapter.TemplateHolder> {

    // List to store all the contact details
    private String name;
    private ArrayList<Category.Template> templates;
    private Context mContext;
    private StorageReference storageReference;

    // Constructor for the Class
    public TemplateRVAdapter(Context context, String name, ArrayList<Category.Template> templates) {
        this.mContext = context;
        this.name = name;
        this.templates = templates;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public TemplateHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.rv_template_list, parent, false);
        return new TemplateHolder(view);
    }

    @Override
    public int getItemCount() {
        return templates == null? 0: templates.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull TemplateHolder holder, final int position) {
        final Category.Template template = templates.get(position);

        // Set the data to the views here
        holder.setTemplateUri(template);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class TemplateHolder extends RecyclerView.ViewHolder {

        private ImageView templateImg;

        public TemplateHolder(View itemView) {
            super(itemView);
            templateImg = itemView.findViewById(R.id.template_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Clicked here", Toast.LENGTH_SHORT).show();
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), SelectProfileActivity.class);
                    intent.putExtra("CategoryName", name);
                    intent.putExtra("TemplateImgPath", templates.get(pos).getImgPath());
                    intent.putExtra("TemplateFilePath", templates.get(pos).getFilePath());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setTemplateUri(Category.Template template) {
            storageReference = FirebaseStorage.getInstance().getReference(template.getImgPath());
            Glide.with(mContext)
                    .load(storageReference)
                    .into(templateImg);
//            final long ONE_MEGABYTE = 1024 * 1024;
//            storageReference.getBytes(ONE_MEGABYTE)
//                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
//                            templateImg.setMinimumHeight(dm.heightPixels);
//                            templateImg.setMinimumWidth(dm.widthPixels);
//                            templateImg.setImageBitmap(bm);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    exception.printStackTrace();
//                }
//            });
        }
    }
}