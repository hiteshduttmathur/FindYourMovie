package com.test.myapp.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.test.myapp.model.MovieData;
import com.test.myapp.networking.MoviesRepository;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<MovieData> mutableLiveData;
    private MoviesRepository moviesRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        moviesRepository = MoviesRepository.getInstance();
        mutableLiveData = moviesRepository.getMovies();

    }

    public LiveData<MovieData> getMoviesRepository() {
        return mutableLiveData;
    }

}