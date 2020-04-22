package com.example.resumebuilder;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

@GlideModule
public class myGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull android.content.Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(StorageReference.class, InputStream.class, new FirebaseImageLoader.Factory());
    }

    private class Context {
    }
}
