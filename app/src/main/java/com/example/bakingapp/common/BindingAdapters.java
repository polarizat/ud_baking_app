package com.example.bakingapp.common;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.bakingapp.R;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl){
        if (imageUrl.equals("")) {
            Glide.with(imageView.getContext())
                    .load(R.drawable.default_recipe_img)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.color.colorPrimaryLight)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }
}