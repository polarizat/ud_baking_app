package com.example.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.bakingapp.R;

import java.util.Map;

import static com.example.bakingapp.provider.RecipeContract.BASE_CONTENT_URI;
import static com.example.bakingapp.provider.RecipeContract.PATH_RECIPES;
import static com.example.bakingapp.provider.RecipeContract.RecipeEntry.COLUMN_ID;
import static com.example.bakingapp.provider.RecipeContract.RecipeEntry.COLUMN_INGREDIENTS;
import static com.example.bakingapp.provider.RecipeContract.RecipeEntry.COLUMN_NAME;

public class IngredientsService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS_WIDGET =
            "com.example.bakingapp.action.update_ingredients_widget";

    public static final String EXTRA_RECIPE_ID = "com.example.bakingapp.extra.RECIPE_ID";
    private static final String EXTRA_WIDGET_ID = "com.example.bakingapp.extra.WIDGET_ID";

    public IngredientsService() {
        super("IngredientsService");
    }

    /**
     * Starts this service to perform UpdateIngredientsWidget action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateIngredientsWidgets(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.Preference_file_key), Context.MODE_PRIVATE);
        Map<String,?> keys = sharedPref.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Intent intent = new Intent(context, IngredientsService.class);
            intent.setAction(ACTION_UPDATE_INGREDIENTS_WIDGET);
            intent.putExtra(EXTRA_RECIPE_ID, (Integer) entry.getValue());
            intent.putExtra(EXTRA_WIDGET_ID, (Integer.valueOf(entry.getKey())));
            context.startService(intent);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            int widgetId = intent.getIntExtra(EXTRA_WIDGET_ID, 0);
            int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 1);
            switch (action) {
                case ACTION_UPDATE_INGREDIENTS_WIDGET:
                    handleActionUpdateIngredientsWidgets(widgetId, recipeId);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handle action UpdateIngredientsWidgets in the provided background thread
     */
    private void handleActionUpdateIngredientsWidgets(int widgetId, int recipeId) {
        Uri SINGLE_RECIPE_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_RECIPES)
                        .appendQueryParameter(COLUMN_ID, String.valueOf(recipeId))
                        .build();

        Cursor cursor = getContentResolver().query(
                SINGLE_RECIPE_URI, null, null, null, null);

        String name = "";
        String ingredientsJson = "";

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            ingredientsJson = cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS));

            cursor.close();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        //Trigger data update to handle the ListView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredient_ll);
        //Now update all widgets
        IngredientsWidgetProvider.updateCurrentIngredientWidget(
                this,
                appWidgetManager,
                recipeId,
                name,
                ingredientsJson,
                widgetId);
    }

}
