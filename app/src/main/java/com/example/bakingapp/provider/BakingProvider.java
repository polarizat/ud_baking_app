package com.example.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bakingapp.data.database.BakingAppDatabase;
import com.example.bakingapp.data.database.RecipesDao;
import com.example.bakingapp.data.models.Recipe;

public class BakingProvider extends ContentProvider {

    /** The match code for some items in the Recipe table. */
    static final int CODE_RECIPES_DIR = 100;
    /** The match code for an item in the Recipe table. */
    private static final int CODE_RECIPE_ITEM = 101;


    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(RecipeContract.AUTHORITY, Recipe.TABLE_NAME, CODE_RECIPES_DIR);
        MATCHER.addURI(RecipeContract.AUTHORITY, Recipe.TABLE_NAME + "/#", CODE_RECIPE_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final Context context = getContext();
        if (context == null) {
            return null;
        }

        RecipesDao recipesDao = BakingAppDatabase.getInstance(context).recipesDao();
        final int code = MATCHER.match(uri);
        final Cursor cursor;

        switch (code) {
            case CODE_RECIPES_DIR:
                cursor = recipesDao.getAllRecipesCursor();
                break;
            case CODE_RECIPE_ITEM:
                cursor = recipesDao.getRecipeCursor(Integer.parseInt(uri.getQuery()));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_ITEM:
                return "vnd.android.cursor.item/" + RecipeContract.AUTHORITY + "." + Recipe.TABLE_NAME;
            case CODE_RECIPES_DIR:
                return "vnd.android.cursor.dir/" + RecipeContract.AUTHORITY + "." + Recipe.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
