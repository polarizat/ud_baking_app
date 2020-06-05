package com.example.bakingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.databinding.ActivityMainBinding;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailActivity;
import com.example.bakingapp.utils.InjectorUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener{
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private RecipesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupViewModel();
        setupRecyclerView();
    }

    private void setupViewModel() {
        MainViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        viewModel.getRecipesLiveData().observe(this, recipes -> {
            if (recipes.size() == 0) {
                binding.mainProgressBar.setVisibility(View.VISIBLE);
            } else {
                binding.mainProgressBar.setVisibility(View.GONE);
            }
            mAdapter.setNewList(recipes);
        });
    }

    private void setupRecyclerView(){
        GridLayoutManager layoutManager;
        layoutManager = new GridLayoutManager(this, numberOfColumns());
        binding.recipesRv.setLayoutManager(layoutManager);
        binding.recipesRv.setHasFixedSize(true);

        mAdapter = new RecipesAdapter(new ArrayList<Recipe>(), this);
        binding.recipesRv.setAdapter(mAdapter);
    }

    private void launchRecipeDetailActivity(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(Recipe.KEY_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        launchRecipeDetailActivity(recipe);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider;
        if(getResources().getBoolean(R.bool.isTablet)) widthDivider = 800;
        else widthDivider = 1200;

        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 1) return 1; //to keep the grid aspect
        return nColumns;
    }
}
