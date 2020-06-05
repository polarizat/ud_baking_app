package com.example.bakingapp.utils;

import android.content.Context;

import com.example.bakingapp.data.BakingAppRepository;
import com.example.bakingapp.data.database.BakingAppDatabase;
import com.example.bakingapp.ui.main.MainViewModelFactory;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailSharedViewModelFactory;
import com.example.bakingapp.widget.config.WidgetConfigViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Popular Movies App
 */
public class InjectorUtils {

    public static BakingAppRepository provideRepository(Context context) {
        BakingAppDatabase database = BakingAppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return BakingAppRepository.getInstance(database.recipesDao(), executors);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context) {
        BakingAppRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static RecipeDetailSharedViewModelFactory provideRecipeDetailSharedViewModelFactory(Context context) {
        BakingAppRepository repository = provideRepository(context.getApplicationContext());
        return new RecipeDetailSharedViewModelFactory(repository);
    }

    public static WidgetConfigViewModelFactory provideWidgetConfigViewModelFactory(Context context) {
        BakingAppRepository repository = provideRepository(context.getApplicationContext());
        return new WidgetConfigViewModelFactory(repository);
    }
}