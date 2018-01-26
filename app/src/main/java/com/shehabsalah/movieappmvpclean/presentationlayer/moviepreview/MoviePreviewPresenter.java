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
package com.shehabsalah.movieappmvpclean.presentationlayer.moviepreview;

import com.shehabsalah.movieappmvpclean.domainlayer.MoviesUseCase;
import com.shehabsalah.movieappmvpclean.domainlayer.UseCaseCallback;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.datalayer.source.local.MovieAppDatabase;
import com.shehabsalah.movieappmvpclean.util.ApplicationClass;
import com.shehabsalah.movieappmvpclean.util.Constants;

/**
 * Created by ShehabSalah on 1/10/18.
 * Listens to user actions from the UI ({@link MoviePreviewActivity}), retrieves the data and updates the
 * UI as required.
 */

public class MoviePreviewPresenter implements MoviePreviewContract.presenter, UseCaseCallback.FavoriteCallBack {

    private MoviePreviewContract.view view;
    private MoviesUseCase moviesUseCase;

    MoviePreviewPresenter(MoviePreviewContract.view view) {
        this.view = view;
        moviesUseCase = new MoviesUseCase();
    }

    @Override
    public void onFavoritePressed(Movie movie) {
        if (movie.getFavorite() == Constants.FAVORITE_ACTIVE)
            moviesUseCase.removeMovieToFavorites(this, movie);
        else
           moviesUseCase.addMovieToFavorites(this, movie);
    }

    @Override
    public void onFavoriteResponse(Movie movie) {
        view.onFavoriteResponse(movie);
    }
}
