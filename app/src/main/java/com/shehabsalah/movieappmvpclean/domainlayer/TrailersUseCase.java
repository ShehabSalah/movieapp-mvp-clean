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
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;
import com.shehabsalah.movieappmvpclean.models.response.GeneralResponse;
import com.shehabsalah.movieappmvpclean.models.response.TrailersResponse;
import java.util.ArrayList;

/**
 * Created by shehabsalah on 1/26/18.
 *  Trailers use case.
 */

public class TrailersUseCase {
    private MoviesRepository moviesRepository;
    private UseCaseCallback useCaseCallback;

    public TrailersUseCase(UseCaseCallback useCaseCallback) {
        moviesRepository = MoviesRepository.getInstance();
        this.useCaseCallback = useCaseCallback;
    }

    public void loadTrailers(final int movieId, boolean mForceUpdate){
        if (mForceUpdate)
            moviesRepository.refreshData();

        moviesRepository.getTrailers(new MoviesDataSource.LoadTrailersCallback() {
            @Override
            public void onTrailersLoaded(ArrayList<MovieTrailers> trailers) {
                TrailersResponse trailersResponse = new TrailersResponse(null, null);
                trailersResponse.setResults(trailers);
                useCaseCallback.onSuccess(trailersResponse);
            }

            @Override
            public void onTrailersNotAvailable() {
                useCaseCallback.dataNotAvailable();
            }

            @Override
            public void noInternetConnection() {
                useCaseCallback.noInternetConnection();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                moviesRepository.deleteMovieTrailers(movieId);
                moviesRepository.saveMovieTrailers(((TrailersResponse) response).getResults(), movieId);
                useCaseCallback.onSuccess(response);
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                useCaseCallback.onError(response.getMessage());
            }
        }, movieId);
    }
}
