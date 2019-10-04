/*
 * Copyright (C) 2019 Raf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.h01d.movies.ui.movies;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.github.h01d.movies.data.model.Movie;

public class MoviesDataSourceFactory extends DataSource.Factory<Long, Movie>
{
    private MutableLiveData<MoviesDataSource> moviesDataSource = new MutableLiveData<>();

    private String path;

    public MoviesDataSourceFactory(String path)
    {
        this.path = path;
    }

    @Override
    public DataSource<Long, Movie> create()
    {
        MoviesDataSource dataSource = new MoviesDataSource(path);

        moviesDataSource.postValue(dataSource);

        return dataSource;
    }

    public MutableLiveData<MoviesDataSource> getMoviesDataSource()
    {
        return moviesDataSource;
    }
}
