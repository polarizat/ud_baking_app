package com.example.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.bakingapp.data.models.Recipe;

public class RecipeContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.bakingapp.provider";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "recipes" directory
    public static final String PATH_RECIPES = Recipe.TABLE_NAME;

    public static final long INVALID_RECIPE_ID = -1;

    public static final class RecipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = Recipe.TABLE_NAME;
        public static final String COLUMN_ID = Recipe.RECIPE_ID;
        public static final String COLUMN_NAME = Recipe.RECIPE_NAME;
        public static final String COLUMN_INGREDIENTS = Recipe.RECIPE_INGREDIENTS;
        public static final String COLUMN_STEPS = Recipe.RECIPE_STEPS;
        public static final String COLUMN_SERVINGS = Recipe.RECIPE_SERVINGS;
        public static final String COLUMN_IMAGE = Recipe.RECIPE_IMAGE;
    }














}
