package com.example.bakingapp.ui.recipe_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.data.BakingAppRepository;
import com.example.bakingapp.data.models.Ingredient;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.data.models.Step;

import java.util.List;

public class RecipeDetailSharedViewModel extends ViewModel {

    private final BakingAppRepository mRepository;

    private static LiveData<Recipe> mRecipeLiveData;
    private MutableLiveData<Step> mCurrentStepLiveData;
    private int mCurrentStepIndex;

    private boolean mTwoPane = false;

    private boolean mPlayWhenReady = true;
    private int mCurrentWindow = 0;
    private long mPlaybackPosition = 0;

    public RecipeDetailSharedViewModel(BakingAppRepository repo){
        mRepository = repo;
        mRecipeLiveData = new MutableLiveData<>();
        mCurrentStepLiveData = new MutableLiveData<>();
        mCurrentStepIndex = 0;
    }

    public void setRecipe(int recipeId) {
        if (mRecipeLiveData.getValue() == null){
            mRecipeLiveData = mRepository.getRecipeLiveData(recipeId);
        }
    }

    public LiveData<Recipe> getRecipeLiveData() {
        return mRecipeLiveData;
    }


    public void setCurrentStep(Step step, int stepIndex) {
        mCurrentStepLiveData.setValue(step);
        mCurrentStepIndex = stepIndex;
    }

    public MutableLiveData<Step> getCurrentStepLiveData() {
        return mCurrentStepLiveData;
    }


    public Step getCurrentStep(){
        return mCurrentStepLiveData.getValue();
    }
    public Integer getStepIndex() {
        return mCurrentStepIndex;
    }


    public void moveToNextStep() {
        if (mCurrentStepIndex < getNbOfSteps()){
            int newStepNumber = mCurrentStepIndex + 1;
            setCurrentStep(getStep(newStepNumber), newStepNumber);
        }
    }
    public void moveToPreviousStep() {
        if (mCurrentStepIndex > 1){
            int newStepIndex = mCurrentStepIndex - 1;
            setCurrentStep(getStep(newStepIndex), newStepIndex);
        }
    }

    public void moveToIngredients() {
        setCurrentStep(new Step(true), 0);
    }

    public int getNbOfSteps(){
        return mRecipeLiveData.getValue().getSteps().size();
    }

    private Step getStep(int stepNumber){
        int stepIndex = stepNumber - 1;
        return mRecipeLiveData.getValue().getSteps().get(stepIndex);
    }

    public String getCurrentStepVideoUrl() {
        //Check if url exist and if not, return ThumbnailUrl
        String url = mCurrentStepLiveData.getValue().getVideoURL();
        if (url.isEmpty()){
            url = mCurrentStepLiveData.getValue().getThumbnailURL();
        }
        return url;
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }
    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public void savePlayerState(Boolean playWhenReady, int currentWindow, long playbackPosition ){
        mPlayWhenReady = playWhenReady;
        mCurrentWindow = currentWindow;
        mPlaybackPosition = playbackPosition;
    }
    public void resetPlayerState(){
        mPlayWhenReady = false;
        mCurrentWindow = 0;
        mPlaybackPosition = 0;
    }
    public boolean isPlayWhenReady() {
        return mPlayWhenReady;
    }
    public int getCurrentWindow() {
        return mCurrentWindow;
    }
    public long getPlaybackPosition() {
        return mPlaybackPosition;
    }

    public List<Ingredient> getIngredients() {
        return mRecipeLiveData.getValue().getIngredients();
    }

}
