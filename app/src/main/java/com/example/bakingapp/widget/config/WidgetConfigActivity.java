package com.example.bakingapp.widget.config;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.utils.InjectorUtils;
import com.example.bakingapp.widget.IngredientsWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WidgetConfigActivity extends AppCompatActivity
        implements RecipeWidgetConfigAdapter.ListItemClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private WidgetConfigViewModel viewModel;
    private RecyclerView mRecipesRv;
    private RecipeWidgetConfigAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_widget_config);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecipesRv = findViewById(R.id.widget_config_recipes_rv);

        setupViewModel();
        setupRecyclerView();
    }

    private void setupViewModel() {
        WidgetConfigViewModelFactory factory =
                InjectorUtils.provideWidgetConfigViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(WidgetConfigViewModel.class);

        viewModel.getRecipesLiveData().observe(this, recipes -> {
            if (recipes.size() == 0) {
                showErrorMessage();
            }
            mAdapter.setNewList(recipes);
        });
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        mRecipesRv.setLayoutManager(layoutManager);
        mRecipesRv.setHasFixedSize(true);

        mAdapter = new RecipeWidgetConfigAdapter(new ArrayList<Recipe>(), this);
        mRecipesRv.setAdapter(mAdapter);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        final Context context = WidgetConfigActivity.this;

        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        IngredientsWidgetProvider.updateCurrentIngredientWidget(
                context,
                appWidgetManager,
                recipe.getId(),
                recipe.getName(),
                new Gson().toJson(recipe.getIngredients()),
                mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(mAppWidgetId), recipe.getId());
        editor.apply();

        finish();
    }

    public void showErrorMessage(){
        Toast.makeText(WidgetConfigActivity.this,
                R.string.error_message_config_widget, Toast.LENGTH_LONG).show();
    }
}