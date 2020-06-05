package com.example.bakingapp.ui.recipe_detail.master;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.models.Step;
import com.example.bakingapp.databinding.ListItemStepBinding;


import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> mSteps;
    final private StepsAdapter.ListItemClickListener mOnClickLister;

    public StepsAdapter(List<Step> steps, StepsAdapter.ListItemClickListener listener){
        List<Step> list = new ArrayList<>();
        list.add(new Step(true));
        list.addAll(1, steps);
        mSteps = list;
        mOnClickLister = listener;
    }

    public interface ListItemClickListener{
        void onStepClick(Step step, int stepIndex, View view);
    }


    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemStepBinding itemStepBinding = ListItemStepBinding.inflate(inflater, viewGroup, false);

        return new StepsViewHolder(itemStepBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setNewList(List<Step> list){
        if (list != null){
            mSteps.clear();
            mSteps.addAll(list);
            notifyDataSetChanged();
        }
    }




    /** VIEW HOLDER*/
    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemStepBinding binding;

        StepsViewHolder(ListItemStepBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            binding.stepLl.setOnClickListener(this);
        }

        void bind (Step step) {
            binding.setStep(step);
            if (step.getDescription().equals(Step.INGREDIENTS_KEY))
                binding.stepShortDescriptionTv.setAllCaps(true);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Step step = mSteps.get(getAdapterPosition());
            mOnClickLister.onStepClick(step, getLayoutPosition(), v);
        }
    }


}