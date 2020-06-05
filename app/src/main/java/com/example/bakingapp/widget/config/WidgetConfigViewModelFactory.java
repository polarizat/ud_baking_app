package com.example.bakingapp.widget.config;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.data.BakingAppRepository;

public class WidgetConfigViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BakingAppRepository mRepo;

    public WidgetConfigViewModelFactory(BakingAppRepository repo) {
        this.mRepo = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WidgetConfigViewModel(mRepo);
    }

}
