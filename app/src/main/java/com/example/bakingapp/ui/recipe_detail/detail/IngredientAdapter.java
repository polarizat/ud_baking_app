package com.example.bakingapp.ui.recipe_detail.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bakingapp.data.models.Ingredient;
import com.example.bakingapp.databinding.ListItemIngredientBinding;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {

    private List<Ingredient> mIngredients;
    private LayoutInflater mLayoutInflater;

    IngredientAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        View mView = view;
        ListItemIngredientBinding binding;

        if (mView == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            binding = ListItemIngredientBinding.inflate(
                    mLayoutInflater, parent, false);

            mView = binding.getRoot();
            mView.setTag(binding);
        }
        else {
            binding = (ListItemIngredientBinding) mView.getTag();
        }

        binding.setIngredient(mIngredients.get(position));

        return mView;
    }


    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public Object getItem(int i) {
        return mIngredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}


