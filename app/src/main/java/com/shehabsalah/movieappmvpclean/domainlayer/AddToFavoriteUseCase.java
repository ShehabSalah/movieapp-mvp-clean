package com.shehabsalah.movieappmvpclean.domainlayer;

import com.shehabsalah.movieappmvpclean.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.util.Constants;

/**
 * Created by shehabsalah on 1/30/18.
 * Add movie to favorites use case.
 */

public class AddToFavoriteUseCase {

    private MoviesRepository moviesRepository;

    public AddToFavoriteUseCase(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public void addMovieToFavorites(UseCaseCallback.FavoriteCallBack callBack, Movie movie) {
        movie.setFavorite(Constants.FAVORITE_ACTIVE);
        moviesRepository.updateMovie(movie);
        callBack.onFavoriteResponse(movie);
    }
}
