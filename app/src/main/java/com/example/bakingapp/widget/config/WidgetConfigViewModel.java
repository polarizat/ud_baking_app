package com.example.bakingapp.widget.config;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.data.BakingAppRepository;
import com.example.bakingapp.data.models.Recipe;

import java.util.List;

public class WidgetConfigViewModel extends ViewModel {

    private final BakingAppRepository mRepository;

    public WidgetConfigViewModel(BakingAppRepository repo){
        mRepository = repo;
    }

    public LiveData<List<Recipe>> getRecipesLiveData(){
        return mRepository.getRecipesLiveData();
    }

}
