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
package com.shehabsalah.movieappmvpclean.datalayer.source.local;

import android.support.annotation.NonNull;

import com.shehabsalah.movieappmvpclean.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpclean.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;
import com.shehabsalah.movieappmvpclean.presentationlayer.movieslist.MoviesSortType;
import com.shehabsalah.movieappmvpclean.util.ApplicationClass;
import com.shehabsalah.movieappmvpclean.util.Constants;

import java.util.ArrayList;

/**
 * Created by shehabsalah on 1/25/18.
 * Concrete implementation of a data source as a db.
 */

public class MoviesLocalDataSource implements MoviesDataSource {
    private static volatile MoviesLocalDataSource INSTANCE;
    private MovieAppDatabase mMovieAppDatabase;

    // Prevent direct instantiation.
    private MoviesLocalDataSource() {
        mMovieAppDatabase = MovieAppDatabase.getInstance(ApplicationClass.getAppContext());
    }

    public static MoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (MoviesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Load movies list that has type most popular.
     * <p>
     * Note: {@link LoadMoviesCallback#onMoviesNotAvailable()} is fired if the {@link Movie}s isn't
     * found or there is no {@link Movie}s in the DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     */
    private void loadMostPopular(@NonNull LoadMoviesCallback callback) {
        ArrayList<Movie> movies = new ArrayList<>(mMovieAppDatabase.movieDAO().selectPopularMovies());
        if (movies.size() > 0)
            callback.onMoviesLoaded(movies);
        else
            callback.onMoviesNotAvailable();
    }

    /**
     * Load movies list that has type top rated.
     * <p>
     * Note: {@link LoadMoviesCallback#onMoviesNotAvailable()} is fired if the {@link Movie}s isn't
     * found or there is no {@link Movie}s in the DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     */
    private void loadTopRated(@NonNull LoadMoviesCallback callback) {
        ArrayList<Movie> movies = new ArrayList<>(mMovieAppDatabase.movieDAO().selectTopRatedMovies());
        if (movies.size() > 0)
            callback.onMoviesLoaded(movies);
        else
            callback.onMoviesNotAvailable();
    }

    /**
     * Load movies list that has type favorite.
     * <p>
     * Note: {@link LoadMoviesCallback#onMoviesNotAvailable()} is fired if the {@link Movie}s isn't
     * found or there is no {@link Movie}s in the DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     */
    private void loadFavorites(@NonNull LoadMoviesCallback callback) {
        ArrayList<Movie> movies = new ArrayList<>(mMovieAppDatabase.movieDAO().selectFavorites());
        if (movies.size() > 0)
            callback.onMoviesLoaded(movies);
        else
            callback.onMoviesNotAvailable();
    }


    /**
     * Load movies from DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     * @param filter   movie type to load from DB.
     */
    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback, MoviesSortType filter) {
        switch (filter) {
            case FAVORITES:
                loadFavorites(callback);
                break;
            case TOP_RATED:
                loadTopRated(callback);
                break;
            case MOST_POPULAR:
                loadMostPopular(callback);
                break;
            default:
                callback.onMoviesNotAvailable();
        }
    }

    /**
     * Load the movie trailers from DB.
     * <p>
     * Note: {@link LoadTrailersCallback#onTrailersNotAvailable()} is fired if the {@link MovieTrailers}
     * isn't found or there is no {@link MovieTrailers} in the DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     * @param movieId  to load it's trailers.
     */
    @Override
    public void getTrailers(@NonNull LoadTrailersCallback callback, int movieId) {
        ArrayList<MovieTrailers> movieTrailers =
                new ArrayList<>(mMovieAppDatabase.movieDAO().selectTrailers(movieId));

        if (movieTrailers.size() > 0)
            callback.onTrailersLoaded(movieTrailers);
        else
            callback.onTrailersNotAvailable();
    }

    /**
     * Load the movie reviews from DB.
     * <p>
     * Note: {@link LoadReviewsCallback#onReviewsNotAvailable()} is fired if the {@link MovieReviews}
     * isn't found or there is no {@link MovieReviews} in the DB.
     *
     * @param callback to notify back the {@link MoviesRepository} with the query result.
     * @param movieId  to load it's reviews.
     */
    @Override
    public void getReviews(@NonNull LoadReviewsCallback callback, int movieId) {
        ArrayList<MovieReviews> movieReviews =
                new ArrayList<>(mMovieAppDatabase.movieDAO().selectReviews(movieId));

        if (movieReviews.size() > 0)
            callback.onReviewsLoaded(movieReviews);
        else
            callback.onReviewsNotAvailable();
    }

    @Override
    public void refreshData() {
        // Not required because the {@link MoviesRepository} handles the logic of refreshing the
        // movies from all the available data sources.
    }

    /**
     * Save list of movies in the DB as top rated movies.
     *
     * @param movies movies list to save in the DB as top rated movies.
     */
    @Override
    public void saveTopRatedMovies(ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            movie.setType(Constants.PAGE_TOP_RATED);
            mMovieAppDatabase.movieDAO().insertMovie(movie);
        }

    }

    /**
     * Save list of movies in the DB as most popular movies.
     *
     * @param movies list to save in the DB as most popular movies.
     */
    @Override
    public void saveMostPopularMovies(ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            movie.setType(Constants.PAGE_POPULAR);
            mMovieAppDatabase.movieDAO().insertMovie(movie);
        }
    }

    /**
     * Save list of movie trailers in the DB.
     *
     * @param movieTrailers list to save in the DB.
     * @param movieId       movie id that those trailers is belong to.
     */
    @Override
    public void saveMovieTrailers(ArrayList<MovieTrailers> movieTrailers, int movieId) {
        for (MovieTrailers mTrailer : movieTrailers) {
            mTrailer.setMovieId(movieId);
            mMovieAppDatabase.movieDAO().insertMovieTrailer(mTrailer);
        }
    }

    /**
     * Save list of movie reviews in the DB.
     *
     * @param movieReviews list to save in the DB.
     * @param movieId      movie id that those reviews is belong to.
     */
    @Override
    public void saveMovieReviews(ArrayList<MovieReviews> movieReviews, int movieId) {
        for (MovieReviews mReviews : movieReviews) {
            mReviews.setMovieId(movieId);
            mMovieAppDatabase.movieDAO().insertMovieReview(mReviews);
        }
    }

    /**
     * Update the movie information.
     *
     * @param movie to update.
     */
    @Override
    public void updateMovie(Movie movie) {
        mMovieAppDatabase.movieDAO().updateMovie(movie);
    }

    /**
     * Delete all movies from the DB.
     */
    @Override
    public void deleteAllMovies() {
        mMovieAppDatabase.movieDAO().deleteAll();
    }

    /**
     * Delete all movie trailers from DB.
     *
     * @param movieId to delete it's trailers.
     */
    @Override
    public void deleteMovieTrailers(int movieId) {
        mMovieAppDatabase.movieDAO().deleteAllTrailers(movieId);
    }

    /**
     * Delete all movie reviews from DB.
     *
     * @param movieId to delete it's reviews.
     */
    @Override
    public void deleteMovieReviews(int movieId) {
        mMovieAppDatabase.movieDAO().deleteAllReviews(movieId);
    }
}
