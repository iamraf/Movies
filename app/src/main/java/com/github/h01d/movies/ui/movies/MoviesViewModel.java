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

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.github.h01d.movies.data.model.Movie;

public class MoviesViewModel extends ViewModel
{
    private LiveData<MoviesDataSource> liveDataSource;

    private LiveData<PagedList<Movie>> moviesPagedList;

    private LiveData<Boolean> initialLoading;
    private LiveData<String> errorMessage;

    public MoviesViewModel(String path)
    {
        MoviesDataSourceFactory moviesDataSourceFactory = new MoviesDataSourceFactory(path);

        liveDataSource = moviesDataSourceFactory.getMoviesDataSource();

        initialLoading = Transformations.switchMap(liveDataSource, MoviesDataSource::isInitialLoading);
        errorMessage = Transformations.switchMap(liveDataSource, MoviesDataSource::getErrorMessage);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        moviesPagedList = new LivePagedListBuilder<>(moviesDataSourceFactory, config).build();
    }

    public void refresh()
    {
        liveDataSource.getValue().invalidate();
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList()
    {
        return moviesPagedList;
    }

    public LiveData<Boolean> getInitialLoading()
    {
        return initialLoading;
    }

    public LiveData<String> getErrorMessage()
    {
        return errorMessage;
    }
}
