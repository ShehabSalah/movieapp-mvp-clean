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
package com.shehabsalah.movieappmvpclean.datalayer;

import android.support.annotation.NonNull;
import com.shehabsalah.movieappmvpclean.datalayer.source.remote.listeners.NetworkListener;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;
import com.shehabsalah.movieappmvpclean.presentationlayer.movieslist.MoviesSortType;
import java.util.ArrayList;

/**
 * Created by shehabsalah on 1/25/18.
 */

public interface MoviesDataSource{

    interface LoadMoviesCallback extends NetworkListener{
        void onMoviesLoaded(ArrayList<Movie> movies);
        void onMoviesNotAvailable();
    }

    interface LoadTrailersCallback extends NetworkListener{
        void onTrailersLoaded(ArrayList<MovieTrailers> trailers);
        void onTrailersNotAvailable();
    }

    interface LoadReviewsCallback extends NetworkListener{
        void onReviewsLoaded(ArrayList<MovieReviews> reviews);
        void onReviewsNotAvailable();
    }


    void refreshData();
    void getMovies(@NonNull LoadMoviesCallback callback, MoviesSortType filter);
    void getTrailers(@NonNull LoadTrailersCallback callback, int movieId);
    void getReviews(@NonNull LoadReviewsCallback callback, int movieId);

    void saveTopRatedMovies(ArrayList<Movie> movies);
    void saveMostPopularMovies(ArrayList<Movie> movies);
    void saveMovieTrailers(ArrayList<MovieTrailers> movieTrailers, int movieId);
    void saveMovieReviews(ArrayList<MovieReviews> movieReviews, int movieId);

    void updateMovie(Movie movie);

    void deleteAllMovies();
    void deleteMovieTrailers(int movieId);
    void deleteMovieReviews(int movieId);



}
