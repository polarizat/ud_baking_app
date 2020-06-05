package com.example.bakingapp.data.database;

import androidx.room.TypeConverter;

import com.example.bakingapp.data.models.Ingredient;
import com.example.bakingapp.data.models.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromIngredientsList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.toJson(ingredients, type);
    }

    @TypeConverter
    public String fromStepsList(List<Step> steps) {
        if (steps == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        return gson.toJson(steps, type);
    }

    @TypeConverter
    public List<Ingredient> toIngredientsList(String ingredientsJsonString) {
        if (ingredientsJsonString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(ingredientsJsonString, type);
    }

    @TypeConverter
    public List<Step> toStepsList(String stepsJsonString) {
        if (stepsJsonString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        return gson.fromJson(stepsJsonString, type);
    }
}