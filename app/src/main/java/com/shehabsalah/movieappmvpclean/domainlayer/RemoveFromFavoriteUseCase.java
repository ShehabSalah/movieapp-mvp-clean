package com.shehabsalah.movieappmvpclean.domainlayer;

import com.shehabsalah.movieappmvpclean.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.util.Constants;

/**
 * Created by shehabsalah on 1/30/18.
 * Remove movie from favorites use case.
 */

public class RemoveFromFavoriteUseCase {
    private MoviesRepository moviesRepository;

    public RemoveFromFavoriteUseCase(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public void removeMovieToFavorites(UseCaseCallback.FavoriteCallBack callBack, Movie movie) {
        movie.setFavorite(Constants.FAVORITE_NOT_ACTIVE);
        moviesRepository.updateMovie(movie);
        callBack.onFavoriteResponse(movie);
    }
}
