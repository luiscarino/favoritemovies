package com.example.lucarino.whattowatch.endpoint;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

import com.example.lucarino.whattowatch.data.Movies;

/**
 * Defines an interface to the service API that used by Retrofit.
 */
public interface TheMovieDBService {

    //3/discover/movie?api_key=b5c017a3b95c06a7e161e9b9ccd68aeb&sort_by=popularity.desc
    @GET("/3/discover/movie")
    Call<Movies> getMovies(@QueryMap Map<String, String> options);

}
