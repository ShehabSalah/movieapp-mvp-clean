/*
 * Copyright (C) 2018 Shehab Salah Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shehabsalah.movieappmvpclean.presentationlayer.movieslist;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.shehabsalah.movieappmvpclean.domainlayer.MoviesUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.UseCaseCallback;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.response.MoviesResponse;
import com.shehabsalah.movieappmvpclean.presentationlayer.moviedetails.DetailsActivity;
import com.shehabsalah.movieappmvpclean.presentationlayer.moviepreview.MoviePreviewActivity;
import com.shehabsalah.movieappmvpclean.util.Constants;
import java.util.ArrayList;

import static android.support.v4.app.ActivityOptionsCompat.*;

/**
 * Created by ShehabSalah on 1/8/18.
 * Listens to user actions from the UI ({@link MoviesListFragment}), retrieves the data and updates the
 * UI as required.
 */

public class MoviesPresenter implements MoviesContract.Presenter, UseCaseCallback {
    private MoviesContract.View views;
    private MoviesSortType moviesSortType;
    private MoviesUseCase moviesUseCase;
    private boolean setAdapter = true;
    private boolean forceUpdate;
    private Activity activity;

    MoviesPresenter(MoviesContract.View views) {
        this.views = views;
        moviesUseCase = new MoviesUseCase(this);
    }

    @Override
    public void goToDetailsActivity(Movie movie, View imageView, View textView) {
        if (movie != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair<View, String> imagePair = Pair.create(imageView, Constants.KEY_CONNECTION_IMAGE);
                ActivityOptionsCompat options = makeSceneTransitionAnimation(activity,
                         imagePair);
                activity.startActivity(DetailsActivity.getDetailsIntent(activity, movie), options.toBundle());
            } else {
                activity.startActivity(DetailsActivity.getDetailsIntent(activity, movie));
            }

        }
    }

    @Override
    public void loadMovies() {
        moviesUseCase.loadMovies(moviesSortType, forceUpdate, true);
    }

    @Override
    public void setAdvancedInit(MoviesSortType moviesType, boolean setAdapter, boolean forceUpdate) {
        this.moviesSortType = moviesType;
        this.setAdapter = setAdapter;
        this.forceUpdate = forceUpdate;
    }

    @Override
    public void setBasicInit(boolean setAdapter, boolean forceUpdate) {
        this.setAdapter = setAdapter;
        this.forceUpdate = forceUpdate;
    }

    @Override
    public MoviesSortType getMoviesType() {
        return moviesSortType;
    }

    @Override
    public void openMoviePreview(Movie movie, View imageView, View textView, View cardView) {
        if (movie != null) {
            views.makeBackgroundBlur();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair<View, String> titlePair = Pair.create(textView, Constants.KEY_CONNECTION_TITLE);
                Pair<View, String> imagePair = Pair.create(imageView, Constants.KEY_CONNECTION_IMAGE);
                Pair<View, String> containerPair = Pair.create(cardView, Constants.KEY_CONNECTION_CONTAINER);
                ActivityOptionsCompat options = makeSceneTransitionAnimation(activity,
                                titlePair, imagePair, containerPair);
                activity.startActivity(MoviePreviewActivity.getDetailsIntent(activity, movie), options.toBundle());
            } else {
                activity.startActivity(MoviePreviewActivity.getDetailsIntent(activity, movie));
            }
        }
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(Object response) {
        if (response instanceof MoviesResponse) {
            MoviesResponse moviesResponse = (MoviesResponse) response;
            ArrayList<Movie> movies = moviesResponse.getResults();
            if (movies.size() > 0) {
                views.showMovies(movies, setAdapter);
            } else {
                views.showNoMovies();
            }
        } else {
            onError("Error while loading data!");
        }
    }

    @Override
    public void dataNotAvailable() {
        switch (moviesSortType) {
            case MOST_POPULAR:
            case TOP_RATED:
                views.showNoMovies();
                break;
            case FAVORITES:
                views.showNoFavorites();
                break;
        }
    }

    @Override
    public void noInternetConnection() {
        views.showNoInternetConnection();
        dataNotAvailable();
    }

    @Override
    public void onError(String error) {
        views.showServerError(error);
    }
}