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

package com.github.h01d.movies.data;

import com.github.h01d.movies.data.model.MovieResponse;
import com.github.h01d.movies.data.remote.ApiClient;
import com.github.h01d.movies.data.remote.ApiService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Repository
{
    private ApiService apiService;
    private Retrofit retrofit;

    public Repository()
    {
        apiService = ApiClient.getClient();
        retrofit = ApiClient.getRetrofit();
    }

    public Call<MovieResponse> getMovies(String path, String apiKey, long page)
    {
        return apiService.getMovies(path, apiKey, page);
    }

    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}
