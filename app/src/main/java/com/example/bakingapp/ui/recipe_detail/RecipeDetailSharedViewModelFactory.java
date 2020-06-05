package com.example.bakingapp.ui.recipe_detail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.data.BakingAppRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link BakingAppRepository}
 */

public class RecipeDetailSharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BakingAppRepository mRepo;

    public RecipeDetailSharedViewModelFactory(BakingAppRepository repo) {
        this.mRepo = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecipeDetailSharedViewModel(mRepo);
    }
}