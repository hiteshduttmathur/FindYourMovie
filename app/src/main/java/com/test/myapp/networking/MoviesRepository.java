package com.test.myapp.networking;

import android.arch.lifecycle.MutableLiveData;

import com.test.myapp.model.MovieData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository moviesRepository;

    public static MoviesRepository getInstance() {
        if (moviesRepository == null) {
            moviesRepository = new MoviesRepository();
        }
        return moviesRepository;
    }

    private MoviesApi moviesApi;

    public MoviesRepository() {
        moviesApi = RetrofitService.createService(MoviesApi.class);
    }

    public MutableLiveData<MovieData> getMovies() {
        final MutableLiveData<MovieData> movieData = new MutableLiveData<>();
        moviesApi.getMoviesList().enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }
}