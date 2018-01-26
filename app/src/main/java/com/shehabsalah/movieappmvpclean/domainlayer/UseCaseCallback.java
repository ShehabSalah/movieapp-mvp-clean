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

import com.shehabsalah.movieappmvpclean.models.Movie;

/**
 * Created by shehabsalah on 1/26/18.
 * Use cases are the entry points to the domain layer.
 */

public interface UseCaseCallback {
    interface FavoriteCallBack{
        void onFavoriteResponse(Movie movie);
    }
    void onSuccess(Object response);
    void noInternetConnection();
    void dataNotAvailable();
    void onError(String error);
}
