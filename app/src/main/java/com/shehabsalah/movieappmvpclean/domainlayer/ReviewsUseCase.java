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
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.response.GeneralResponse;
import com.shehabsalah.movieappmvpclean.models.response.ReviewsResponse;
import java.util.ArrayList;

/**
 * Created by shehabsalah on 1/26/18.
 * Reviews use case
 */

public class ReviewsUseCase {
    private MoviesRepository moviesRepository;
    private UseCaseCallback useCaseCallback;

    public ReviewsUseCase(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public void loadReviews(final int movieId, boolean mForceUpdate){
        if (mForceUpdate)
            moviesRepository.refreshData();

        moviesRepository.getReviews(new MoviesDataSource.LoadReviewsCallback() {
            @Override
            public void onReviewsLoaded(ArrayList<MovieReviews> reviews) {
                ReviewsResponse reviewsResponse = new ReviewsResponse(null, null);
                reviewsResponse.setResults(reviews);
                useCaseCallback.onSuccess(reviewsResponse);
            }

            @Override
            public void onReviewsNotAvailable() {
                useCaseCallback.dataNotAvailable();
            }

            @Override
            public void noInternetConnection() {
                useCaseCallback.noInternetConnection();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                moviesRepository.deleteMovieReviews(movieId);
                moviesRepository.saveMovieReviews(((ReviewsResponse) response).getResults(), movieId);
                useCaseCallback.onSuccess(response);
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                useCaseCallback.onSuccess(response.getMessage());
            }
        }, movieId);
    }

    /**
     * This method used to set the reviews use case call back.
     *
     * @param useCaseCallback interface used to notify back with result.
     * */
    public void setUseCaseCallback(UseCaseCallback useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }
}
