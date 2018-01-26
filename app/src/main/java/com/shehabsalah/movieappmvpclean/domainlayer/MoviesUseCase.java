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
package com.shehabsalah.movieappmvpclean.domainlayer;

import com.shehabsalah.movieappmvpclean.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpclean.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.response.GeneralResponse;
import com.shehabsalah.movieappmvpclean.models.response.MoviesResponse;
import com.shehabsalah.movieappmvpclean.presentationlayer.movieslist.MoviesSortType;
import com.shehabsalah.movieappmvpclean.util.Constants;

import java.util.ArrayList;

/**
 * Created by shehabsalah on 1/25/18.
 * Movies use case.
 */

public class MoviesUseCase {

    private MoviesRepository moviesRepository;
    private UseCaseCallback useCaseCallback;

    public MoviesUseCase(UseCaseCallback useCaseCallback) {
        moviesRepository = MoviesRepository.getInstance();
        this.useCaseCallback = useCaseCallback;
    }

    public MoviesUseCase() {
        moviesRepository = MoviesRepository.getInstance();
    }

    public void loadMovies(final MoviesSortType filter, final boolean mForceUpdate, final boolean presenterCall) {
        if (mForceUpdate && filter != MoviesSortType.FAVORITES)
            moviesRepository.refreshData();

        moviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
            @Override
            public void noInternetConnection() {
                useCaseCallback.noInternetConnection();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                if (filter == MoviesSortType.MOST_POPULAR) {
                    moviesRepository.deleteAllMovies();
                    moviesRepository.saveMostPopularMovies(((MoviesResponse) response).getResults());
                    loadMovies(filter, false, false);
                    loadMovies(MoviesSortType.TOP_RATED, true, false);
                } else if (filter == MoviesSortType.TOP_RATED) {
                    moviesRepository.saveTopRatedMovies(((MoviesResponse) response).getResults());
                    if (presenterCall)
                        loadMovies(filter, false, false);
                }
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                useCaseCallback.onError(response.getMessage());
            }

            @Override
            public void onMoviesLoaded(ArrayList<Movie> movies) {
                MoviesResponse moviesResponse = new MoviesResponse(null, null);
                moviesResponse.setResults(movies);
                useCaseCallback.onSuccess(moviesResponse);
            }

            @Override
            public void onMoviesNotAvailable() {
                useCaseCallback.dataNotAvailable();
            }
        }, filter);
    }

    public void addMovieToFavorites(UseCaseCallback.FavoriteCallBack callBack, Movie movie) {
        movie.setFavorite(Constants.FAVORITE_ACTIVE);
        moviesRepository.updateMovie(movie);
        callBack.onFavoriteResponse(movie);
    }

    public void removeMovieToFavorites(UseCaseCallback.FavoriteCallBack callBack, Movie movie) {
        movie.setFavorite(Constants.FAVORITE_NOT_ACTIVE);
        moviesRepository.updateMovie(movie);
        callBack.onFavoriteResponse(movie);
    }
}
