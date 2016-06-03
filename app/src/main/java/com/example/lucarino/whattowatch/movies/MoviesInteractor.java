package com.example.lucarino.whattowatch.movies;

import android.util.Log;

import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.service.TheMovieDBService;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * This is used to handle the model interactions.
 * This should be used for {@link MoviesPresenter} as the main access point for API calls, device storage, etc.
 */
public class MoviesInteractor implements MoviesContract.Interactor {

    //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&page=2&api_key=b5c017a3b95c06a7e161e9b9ccd68aeb
    private final String BASE_API_URL = "http://api.themoviedb.org";
    private final String API_KEY_PARAMETER = "api_key";
    private final String API_KEY_VALUE = "b5c017a3b95c06a7e161e9b9ccd68aeb";

    @Override
    public void fetchMoviePage(final retrofitMoviesLoadPageCallback listener, int page) {
        final String SORT_PARAMETER = "sort_by";
        final String PAGE_PARAMETER = "page";
        final String SORT_VALUE = "popularity.desc";

        // this can be resolved used dependency injection??
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // build query parameters in the URL request
        Map<String, String> parameters = new HashMap<>();
        parameters.put(API_KEY_PARAMETER, API_KEY_VALUE);
        parameters.put(SORT_PARAMETER, SORT_VALUE);
        parameters.put(PAGE_PARAMETER, String.valueOf(page));

        // retrofit generates de implementation of the service interface
        TheMovieDBService moviesService = retrofit.create(TheMovieDBService.class);

        Call<Movies> moviesCall = moviesService.getMovies(parameters);
        moviesCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(retrofit.Response<Movies> response, Retrofit retrofit) {

                if (response != null && response.body() != null && response.body().getResults() != null) {


                    listener.onSuccess(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(getClass().getSimpleName(), t.getMessage());
                listener.onFailure();
            }
        });
    }



}
