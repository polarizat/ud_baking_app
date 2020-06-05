package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsWidgetRemoteViewsFactory(getApplicationContext(), intent);
    }
}

class IngredientsWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<Ingredient> mIngredients;

    public IngredientsWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        String ingredientsJson = intent.getStringExtra(Ingredient.KEY_EXTRA_INGREDIENTS);
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        mIngredients = new Gson().fromJson(ingredientsJson, type);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mIngredients.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_ingredient);

        views.setTextViewText(R.id.ingredient_name_tv, ingredient.getName());
        views.setTextViewText(R.id.ingredient_quantity_tv, ingredient.getQuantity());
        views.setTextViewText(R.id.ingredient_measure_tv, ingredient.getMeasure());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the ListView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
