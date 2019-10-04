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

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.github.h01d.movies.data.Repository;
import com.github.h01d.movies.data.model.ErrorResponse;
import com.github.h01d.movies.data.model.Movie;
import com.github.h01d.movies.data.model.MovieResponse;
import com.github.h01d.movies.util.Constants;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Long, Movie>
{
    private Repository repository;

    private MutableLiveData<Boolean> initialLoading;
    private MutableLiveData<String> errorMessage;

    private String path;

    public MoviesDataSource(String path)
    {
        repository = new Repository();

        initialLoading = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();

        this.path = path;
    }

    public MutableLiveData<Boolean> isInitialLoading()
    {
        return initialLoading;
    }

    public MutableLiveData<String> getErrorMessage()
    {
        return errorMessage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback)
    {
        initialLoading.postValue(true);

        try
        {
            Response<MovieResponse> movieResponse = repository.getMovies(path, Constants.API_KEY, 1).execute();

            if(movieResponse.isSuccessful() && movieResponse.body() != null)
            {
                callback.onResult(movieResponse.body().getResults(), null, 2L);
            }
            else
            {
                Converter<ResponseBody, ErrorResponse> converter = repository.getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);

                ErrorResponse error = converter.convert(movieResponse.errorBody());

                if(error != null)
                {
                    errorMessage.postValue(error.getMessage());
                }
                else
                {
                    errorMessage.postValue("Unknown error occurred.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

            if(e instanceof UnknownHostException)
            {
                errorMessage.postValue("No internet connection.");
            }
            else if(e instanceof SocketTimeoutException)
            {
                errorMessage.postValue("Connection timeout.");
            }
            else
            {
                errorMessage.postValue("Unknown error occurred.");
            }
        }

        initialLoading.postValue(false);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback)
    {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback)
    {
        try
        {
            Response<MovieResponse> movieResponse = repository.getMovies(path, Constants.API_KEY, params.key).execute();

            if(movieResponse.isSuccessful() && movieResponse.body() != null)
            {
                callback.onResult(movieResponse.body().getResults(), params.key + 1);
            }
            else
            {
                Converter<ResponseBody, ErrorResponse> converter = repository.getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);

                ErrorResponse error = converter.convert(movieResponse.errorBody());

                if(error != null)
                {
                    errorMessage.postValue(error.getMessage());
                }
                else
                {
                    errorMessage.postValue("Unknown error occurred.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

            if(e instanceof UnknownHostException)
            {
                errorMessage.postValue("No internet connection.");
            }
            else if(e instanceof SocketTimeoutException)
            {
                errorMessage.postValue("Connection timeout.");
            }
            else
            {
                errorMessage.postValue("Unknown error occurred.");
            }
        }
    }
}
