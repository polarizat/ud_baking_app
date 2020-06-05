package com.example.bakingapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.data.database.RecipesDao;
import com.example.bakingapp.data.models.Recipe;
import com.example.bakingapp.data.network.GetDataService;
import com.example.bakingapp.data.network.RetrofitClientInstance;
import com.example.bakingapp.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BakingAppRepository {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static BakingAppRepository sInstance;
    private final RecipesDao mRecipesDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private LiveData <List<Recipe>> recipesLiveData;
    private MutableLiveData <Recipe> mRecipeLiveData;

    public synchronized static BakingAppRepository getInstance(RecipesDao recipesDao, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BakingAppRepository(recipesDao, executors);
            }
        }
        return sInstance;
    }

    public BakingAppRepository(RecipesDao recipesDao, AppExecutors executors){
        mRecipesDao = recipesDao;
        mExecutors = executors;
        recipesLiveData = mRecipesDao.getRecipesLiveData();
        mRecipeLiveData = new MutableLiveData<>();
        fetchRecipes();
    }

    /*** Gets the recipes*/
    public void fetchRecipes() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                insertRecipesIntoDatabase(response.body());
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e("onFailure: on Failure%s", t.getLocalizedMessage());
            }
        }) ;
    }

    public LiveData<Recipe> getRecipeLiveData(int id) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRecipeLiveData.postValue(mRecipesDao.getRecipe(id));
        });
        return mRecipeLiveData;
    }

    private void insertRecipesIntoDatabase(List<Recipe> recipes){
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRecipesDao.insertRecipes(recipes);
        });
    }

    public LiveData<List<Recipe>> getRecipesLiveData() {
        return recipesLiveData;
    }
}
