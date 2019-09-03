package com.test.myapp.networking;

import com.test.myapp.model.MovieData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesApi {
    @GET("api/movies")
    Call<MovieData> getMoviesList();
}