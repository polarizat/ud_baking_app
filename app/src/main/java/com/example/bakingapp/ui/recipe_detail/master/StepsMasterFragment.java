package com.example.bakingapp.ui.recipe_detail.master;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.data.models.Step;
import com.example.bakingapp.databinding.StepsListFragmentBinding;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailSharedViewModel;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailSharedViewModelFactory;
import com.example.bakingapp.ui.recipe_detail.detail.StepDetailFragment;
import com.example.bakingapp.utils.InjectorUtils;

import java.util.ArrayList;
import java.util.Objects;

public class StepsMasterFragment extends Fragment implements StepsAdapter.ListItemClickListener {
    public static final String TAG_FRAGMENT_MASTER = "master";

    private RecipeDetailSharedViewModel viewModel;
    private StepsListFragmentBinding binding;

    private View previousView = null;

    public static StepsMasterFragment newInstance() {
        return new StepsMasterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StepsListFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSharedViewModel();

    }

    private void setupSharedViewModel() {
        RecipeDetailSharedViewModelFactory factory =
                InjectorUtils.provideRecipeDetailSharedViewModelFactory(getActivity().getApplicationContext());
        viewModel = new ViewModelProvider(getActivity(), factory).get(RecipeDetailSharedViewModel.class);
        viewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), this::setupStepsListRecyclerView);
    }

    private void setupStepsListRecyclerView(Recipe recipe){
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this.getActivity());
        binding.stepsRv.setHasFixedSize(false);
        binding.stepsRv.setLayoutManager(layoutManager);

        StepsAdapter mAdapter;
        if (recipe != null){
            mAdapter = new StepsAdapter(recipe.getSteps(), this);
        } else {
            mAdapter = new StepsAdapter(new ArrayList<>(), this);

        }
        binding.stepsRv.setAdapter(mAdapter);

        //Perform click on the First Item in the RecyclerView to show Ingredients.
        //A small delay is added to be sure that RecyclerView is drawn when click si performed.
        if (viewModel.isTwoPane()){
            binding.stepsRv.postDelayed(() -> {
                 View itemView = Objects.requireNonNull(binding.stepsRv
                         .findViewHolderForLayoutPosition(viewModel.getStepIndex())).itemView;
                 itemView.setSoundEffectsEnabled(false);
                 itemView.performClick();
            }, 50);
        }
    }

    @Override
    public void onStepClick(Step step, int stepIndex, View view) {
        viewModel.setCurrentStep(step, stepIndex);

        view.setSelected(true);
        if (previousView != null){
            previousView.setSelected(false);
        }
        previousView = view;
        previousView.setSelected(true);

        if (!viewModel.isTwoPane()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_steps_list,
                            StepDetailFragment.newInstance(),
                            StepDetailFragment.TAG_FRAGMENT_DETAIL)
                    .addToBackStack(null)
                    .commit();
        }

    }

}
