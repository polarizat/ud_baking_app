package com.example.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Ingredient;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int recipeId, String recipeName, String ingredientsJson,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.widget_recipe_name_tv, recipeName);

        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent intentIngredientsList = new Intent(context, ListWidgetService.class);
        intentIngredientsList.putExtra(Ingredient.KEY_EXTRA_INGREDIENTS, ingredientsJson);
        views.setRemoteAdapter(R.id.widget_ingredients_lv, intentIngredientsList);

        // Set the RecipeDetailActivity intent to launch when clicked
        Intent detailsActivityIntent = new Intent(context, RecipeDetailActivity.class);
        detailsActivityIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0,
                detailsActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_recipe_name_tv, appPendingIntent);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredients_lv);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Updates all widget instances given the widget Ids and display information
     */
    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                int id, String name, String ingredientsJson, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id, name, ingredientsJson, appWidgetId);
        }
    }

    public static void updateCurrentIngredientWidget(Context context, AppWidgetManager appWidgetManager,
                                                     int id, String name, String ingredientsJson,
                                                     int appWidgetId) {
        updateAppWidget(context, appWidgetManager, id, name, ingredientsJson, appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientsService.startActionUpdateIngredientsWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

