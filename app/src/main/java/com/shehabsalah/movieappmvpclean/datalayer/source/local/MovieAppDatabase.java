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

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shehabsalah.movieappmvpclean.models.Movie;
import com.shehabsalah.movieappmvpclean.models.MovieReviews;
import com.shehabsalah.movieappmvpclean.models.MovieTrailers;

/**
 * Created by ShehabSalah on 1/9/18.
 */
@Database(entities = {Movie.class, MovieReviews.class, MovieTrailers.class}, version = 1)
public abstract class MovieAppDatabase extends RoomDatabase {

    private static MovieAppDatabase INSTANCE;

    public abstract MovieDAO movieDAO();

    private static final Object sLock = new Object();

    public static MovieAppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieAppDatabase.class, "MoviesAppMvpClean.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }


}
