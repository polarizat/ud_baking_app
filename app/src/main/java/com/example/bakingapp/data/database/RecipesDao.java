package com.example.bakingapp.data.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bakingapp.data.models.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    @Query("SELECT * FROM  recipes ORDER BY id ASC")
    LiveData<List<Recipe>> getRecipesLiveData();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipe(int id);

    @Query("SELECT * FROM recipes WHERE id = :id")
    Cursor getRecipeCursor(int id);

    @Query("SELECT * FROM " + Recipe.TABLE_NAME)
    Cursor getAllRecipesCursor();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

}