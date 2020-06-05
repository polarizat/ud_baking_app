package com.example.bakingapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.databinding.ListItemRecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes;
    final private ListItemClickListener mOnClickLister;


    public RecipesAdapter(ArrayList<Recipe> recipes, ListItemClickListener listener){
        mRecipes = recipes;
        mOnClickLister = listener;
    }

    public interface ListItemClickListener{
        void onRecipeClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemRecipeBinding itemRecipeBinding = ListItemRecipeBinding.inflate(inflater, viewGroup, false);

        return new RecipeViewHolder(itemRecipeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
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
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemRecipeBinding binding;

        RecipeViewHolder(ListItemRecipeBinding binding){
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
