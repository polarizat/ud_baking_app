package com.example.bakingapp.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.data.BakingAppRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link BakingAppRepository}
 */

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BakingAppRepository mRepo;

    public MainViewModelFactory(BakingAppRepository repo) {
        this.mRepo = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainViewModel(mRepo);
    }
}