package com.example.bakingapp.widget.config;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.databinding.ListItemWidgetConfigRecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetConfigAdapter extends RecyclerView.Adapter<RecipeWidgetConfigAdapter.ConfigViewHolder> {

    private List<Recipe> mRecipes;
    final private ListItemClickListener mOnClickLister;

    public RecipeWidgetConfigAdapter(ArrayList<Recipe> recipes, ListItemClickListener listener){
        mRecipes = recipes;
        mOnClickLister = listener;
    }

    public interface ListItemClickListener{
        void onRecipeClick(Recipe recipe);
    }

    @NonNull
    @Override
    public ConfigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemWidgetConfigRecipeBinding itemRecipeBinding =
                ListItemWidgetConfigRecipeBinding.inflate(inflater, parent, false);

        return new ConfigViewHolder(itemRecipeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setNewList(List<Recipe> list){
        if (list != null){
            mRecipes.clear();
            mRecipes.addAll(list);
            notifyDataSetChanged();
        }
    }

    /** VIEW HOLDER*/
    class ConfigViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemWidgetConfigRecipeBinding binding;

        ConfigViewHolder(ListItemWidgetConfigRecipeBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        void bind (Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipes.get(getAdapterPosition());
            mOnClickLister.onRecipeClick(recipe);
        }
    }

}
