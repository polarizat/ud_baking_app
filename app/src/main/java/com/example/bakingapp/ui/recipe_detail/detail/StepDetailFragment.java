package com.example.bakingapp.ui.recipe_detail.detail;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.data.models.Step;
import com.example.bakingapp.databinding.StepDetailFragmentBinding;
import com.example.bakingapp.ui.main.MainActivity;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailSharedViewModel;
import com.example.bakingapp.ui.recipe_detail.RecipeDetailSharedViewModelFactory;
import com.example.bakingapp.utils.InjectorUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

public class StepDetailFragment extends Fragment {
    public static final String TAG_FRAGMENT_DETAIL = "detail";

    private RecipeDetailSharedViewModel viewModel;
    private StepDetailFragmentBinding binding;
    private SimpleExoPlayer mExoPlayer;

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StepDetailFragmentBinding.inflate(getLayoutInflater());
        setupSharedViewModel();

        if (isOnePaneVideoMode()) hideSystemUI();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!viewModel.isTwoPane()){
            Objects.requireNonNull(binding.bottomNavLayout.ingredientsBtn).setOnClickListener(v -> {
                releasePlayer(false);
                viewModel.moveToIngredients();
            });

            Objects.requireNonNull(binding.bottomNavLayout.previousBtn).setOnClickListener(v -> {
                releasePlayer(false);
                viewModel.moveToPreviousStep();
            });

            Objects.requireNonNull(binding.bottomNavLayout.nextBtn).setOnClickListener(v -> {
                releasePlayer(false);
                viewModel.moveToNextStep();
            });

            Objects.requireNonNull(binding.bottomNavLayout.finishBtn).setOnClickListener(v -> {
                releasePlayer(false);
                launchMainActivity();
                Toast.makeText(getContext(), getString(R.string.well_done), Toast.LENGTH_LONG).show();
            });
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void setupSharedViewModel() {
        RecipeDetailSharedViewModelFactory factory =
                InjectorUtils.provideRecipeDetailSharedViewModelFactory(getActivity().getApplicationContext());
        viewModel = new ViewModelProvider(getActivity(), factory).get(RecipeDetailSharedViewModel.class);

        viewModel.getCurrentStepLiveData().observe(getViewLifecycleOwner(), step -> {
            releasePlayer(false);

            if (isOnePaneVideoMode()) hideSystemUI();

            binding.setViewmodel(viewModel);
            binding.stepDetailContainerCl.setVisibility(View.VISIBLE);

            if (step.getShortDescription().equals(Step.INGREDIENTS_KEY)) {
                setIngredientsList();
            } else {
                initializePlayer();
            }
        });

    }

    private void setIngredientsList() {
        IngredientAdapter adapter = new IngredientAdapter(viewModel.getIngredients());
        binding.ingredientsLayout.ingredientsLv.setAdapter(adapter);
    }

    /*** Initialize ExoPlayer.*/
    private void initializePlayer() {
        mExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
        binding.playerView.setPlayer(mExoPlayer);

        MediaSource mediaSource = buildMediaSource();
        mExoPlayer.setPlayWhenReady(viewModel.isPlayWhenReady());
        mExoPlayer.seekTo(viewModel.getCurrentWindow(), viewModel.getPlaybackPosition());
        mExoPlayer.prepare(mediaSource, false, false);
    }
    /*** Release ExoPlayer.*/
    private void releasePlayer(boolean wantToSaveState) {
        if (mExoPlayer != null) {
            if (wantToSaveState){
                viewModel.savePlayerState(mExoPlayer.getPlayWhenReady(),
                        mExoPlayer.getCurrentWindowIndex(),
                        mExoPlayer.getContentPosition());
            } else {
                viewModel.resetPlayerState();
            }
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private MediaSource buildMediaSource() {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "Baking-app");
        // This is the MediaSource representing the media to be played.
        ProgressiveMediaSource.Factory mediaSourceFactory =
                new ProgressiveMediaSource.Factory(dataSourceFactory);
        return mediaSourceFactory.createMediaSource(Uri.parse(viewModel.getCurrentStepVideoUrl()));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            //initializePlayer();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUI();
        if (Util.SDK_INT < 24) {
            initializePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) releasePlayer(true);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) releasePlayer(true);
    }

    private boolean isOnePaneVideoMode(){
        return (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && !viewModel.isTwoPane() && !viewModel.getCurrentStepVideoUrl().isEmpty());
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUI() {
        binding.playerView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void showSystemUI() {
        binding.playerView.setSystemUiVisibility(
                                     View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
