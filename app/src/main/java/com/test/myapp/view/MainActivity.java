package com.test.myapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.test.myapp.R;
import com.test.myapp.adapters.MoviesAdapter;
import com.test.myapp.model.Data;
import com.test.myapp.utils.AppUtils;
import com.test.myapp.utils.GridSpacingItemDecoration;
import com.test.myapp.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Data> dataArrayList = new ArrayList<>();
    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.init();
        moviesViewModel.getMoviesRepository().observe(this, moviesResponse -> {
            List<Data> newsArticles = moviesResponse.getData();
            dataArrayList.addAll(newsArticles);
            moviesAdapter.notifyDataSetChanged();
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(MainActivity.this, dataArrayList);
            rvMovies.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            rvMovies.setLayoutManager(mLayoutManager);
            rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dpToPx(10, MainActivity.this), true));
            rvMovies.setAdapter(moviesAdapter);
            rvMovies.setItemAnimator(new DefaultItemAnimator());
            rvMovies.setNestedScrollingEnabled(true);
        } else {
            moviesAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
     *  This function used when user put something in search box.
     * */
    private void search(final SearchView searchView) {

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                int size = dataArrayList.size();
                if (size == 0) {
                    return false;
                }
                s = s.trim().toLowerCase();
                ArrayList<Data> data = new ArrayList<>();

                for (int index = 0; index < size; index++) {
                    Data data1 = dataArrayList.get(index);
                    if (data1.getTitle().toLowerCase().contains(s) || data1.getGenre().toLowerCase().contains(s)) {
                        data.add(data1);
                    }
                }

                moviesAdapter.updateData(data);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }


}