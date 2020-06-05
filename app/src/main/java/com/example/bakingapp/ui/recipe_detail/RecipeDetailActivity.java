package com.example.bakingapp.ui.recipe_detail;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.ui.recipe_detail.detail.StepDetailFragment;
import com.example.bakingapp.ui.recipe_detail.master.StepsMasterFragment;
import com.example.bakingapp.utils.InjectorUtils;

public class RecipeDetailActivity extends AppCompatActivity {

    RecipeDetailSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);
        ActionBar actionBar = getSupportActionBar();

        if(getIntent().getExtras() != null && savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(Recipe.KEY_RECIPE_ID, 0);
            setupViewModel(recipeId);

            // Sets the title of ActionBar as the name of Recipe
            if (actionBar != null) {
                actionBar.setElevation(0f);
                viewModel.getRecipeLiveData().observe(this, recipe -> {
                    if (recipe != null)
                        actionBar.setTitle(recipe.getName());
                });
            }

            if(viewModel.isTwoPane()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_steps_list, StepsMasterFragment.newInstance())
                        .replace(R.id.container_step_detail, StepDetailFragment.newInstance())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_steps_list,
                                StepsMasterFragment.newInstance(),
                                StepsMasterFragment.TAG_FRAGMENT_MASTER)
                        .commit();
            }
        }
    }

    private void setupViewModel(int recipeId) {
        RecipeDetailSharedViewModelFactory factory =
                InjectorUtils.provideRecipeDetailSharedViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(RecipeDetailSharedViewModel.class);
        viewModel.setTwoPane(getResources().getBoolean(R.bool.isTablet));
        viewModel.setRecipe(recipeId);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(StepsMasterFragment.TAG_FRAGMENT_MASTER);
        if (fragment instanceof StepsMasterFragment || viewModel.isTwoPane()){
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack(StepsMasterFragment.TAG_FRAGMENT_MASTER, 0);
        }
    }
}
