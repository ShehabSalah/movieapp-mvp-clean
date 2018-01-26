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

import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;

import java.util.ArrayList;

/**
 * Created by ShehabSalah on 1/12/18.
 * This specifies the contract between the view and the presenter.
 */

public interface DetailsContract {
    interface view{
        void showTrailers(ArrayList<MovieTrailers> movieTrailers);
        void hideTrailers();
        void showReviews(ArrayList<MovieReviews> reviews);
        void hideReviews();
        void favoriteResponse(Movie movie);
    }

    interface presenter{
        void loadMovieInformation(int movieId);
        void onTrailerClicked(String key);
        void onFavoriteClick(Movie movie);
    }
}
