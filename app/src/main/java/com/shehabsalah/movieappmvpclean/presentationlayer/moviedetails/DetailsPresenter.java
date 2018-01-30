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
package com.shehabsalah.movieappmvpclean.presentationlayer.moviedetails;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.shehabsalah.movieappmvpclean.domainlayer.AddToFavoriteUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.MoviesUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.RemoveFromFavoriteUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.ReviewsUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.TrailersUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.UseCaseCallback;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;
import com.shehabsalah.movieappmvpclean.models.response.ReviewsResponse;
import com.shehabsalah.movieappmvpclean.models.response.TrailersResponse;
import com.shehabsalah.movieappmvpclean.util.Constants;

import java.util.ArrayList;

/**
 * Created by ShehabSalah on 1/12/18.
 * Listens to user actions from the UI ({@link DetailsFragment}), retrieves the data and updates the
 * UI as required.
 */

public class DetailsPresenter implements DetailsContract.presenter, UseCaseCallback, UseCaseCallback.FavoriteCallBack {

    private DetailsContract.view view;
    private TrailersUseCase trailersUseCase;
    private ReviewsUseCase reviewsUseCase;
    private AddToFavoriteUseCase addToFavoriteUseCase;
    private RemoveFromFavoriteUseCase removeFromFavoriteUseCase;
    private Activity activity;

    public DetailsPresenter(DetailsContract.view view, Activity activity, TrailersUseCase trailersUseCase,
                            ReviewsUseCase reviewsUseCase, AddToFavoriteUseCase addToFavoriteUseCase,
                            RemoveFromFavoriteUseCase removeFromFavoriteUseCase) {
        this.view                       = view;
        this.trailersUseCase            = trailersUseCase;
        this.reviewsUseCase             = reviewsUseCase;
        this.addToFavoriteUseCase       = addToFavoriteUseCase;
        this.removeFromFavoriteUseCase  = removeFromFavoriteUseCase;
        this.activity                   = activity;
    }

    @Override
    public void loadMovieInformation(int movieId) {
        trailersUseCase.setUseCaseCallback(this);
        reviewsUseCase.setUseCaseCallback(this);
        trailersUseCase.loadTrailers(movieId, false);
        reviewsUseCase.loadReviews(movieId, false);
    }

    @Override
    public void onFavoriteClick(Movie movie) {
        if (movie.getFavorite() == Constants.FAVORITE_ACTIVE)
            removeFromFavoriteUseCase.removeMovieToFavorites(this, movie);
        else
            addToFavoriteUseCase.addMovieToFavorites(this, movie);
    }

    @Override
    public void onTrailerClicked(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            activity.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            activity.startActivity(webIntent);
        }
    }

    @Override
    public void noInternetConnection() {
        view.hideTrailers();
        view.hideReviews();
    }
    @Override
    public void onFavoriteResponse(Movie movie) {
        view.favoriteResponse(movie);
    }

    @Override
    public void onSuccess(Object response) {
        if (response instanceof ReviewsResponse) {
            ReviewsResponse reviewsResponse = (ReviewsResponse) response;
            ArrayList<MovieReviews> movieReviews = reviewsResponse.getResults();
            if (movieReviews.size() > 0) {
                view.showReviews(movieReviews);
            } else {
                view.hideReviews();
            }
        } else if (response instanceof TrailersResponse) {
            TrailersResponse trailersResponse = (TrailersResponse) response;
            ArrayList<MovieTrailers> movieTrailers = trailersResponse.getResults();
            if (movieTrailers.size() > 0) {
                view.showTrailers(movieTrailers);
            } else {
                view.hideTrailers();
            }
        }else{
            dataNotAvailable();
        }
    }

    @Override
    public void dataNotAvailable() {
        view.hideTrailers();
        view.hideReviews();
    }

    @Override
    public void onError(String error) {
       dataNotAvailable();
    }

    @Override
    public void onDestroy() {
        this.trailersUseCase            = null;
        this.reviewsUseCase             = null;
        this.addToFavoriteUseCase       = null;
        this.removeFromFavoriteUseCase  = null;
    }
}
