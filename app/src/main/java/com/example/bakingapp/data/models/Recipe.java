package com.example.bakingapp.data.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe {

    public static final String TABLE_NAME = "recipes";
    public static final String KEY_RECIPE_ID = "recipe_id";

    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_STEPS = "steps";
    public static final String RECIPE_SERVINGS = "servings";
    public static final String RECIPE_IMAGE = "image";


    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public Recipe(@NotNull int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public Recipe(){
        this.id = 0;
        this.name = "default";
        this.ingredients = null;
        this.steps = null;
        this.servings = 0;
        this.image = null;
    }

    @Ignore
    public Recipe(int id, String name, List<Ingredient> ingredients){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = null;
        this.servings = 0;
        this.image = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}
