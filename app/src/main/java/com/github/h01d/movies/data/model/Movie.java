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

package com.github.h01d.movies.data.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.h01d.movies.R;
import com.github.h01d.movies.util.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable
{
    @SerializedName("poster_path")
    @Nullable
    private String posterPath;
    private boolean adult;
    @NonNull
    private String overview;
    @SerializedName("release_date")
    @NonNull
    private String releaseDate;
    @SerializedName("genre_ids")
    @NonNull
    private List<Integer> genreIds;
    private int id;
    @SerializedName("original_title")
    @NonNull
    private String originalTitle;
    @SerializedName("original_language")
    @NonNull
    private String originalLanguage;
    @NonNull
    private String title;
    @SerializedName("backdrop_path")
    @Nullable
    private String backdropPath;
    private float popularity;
    @SerializedName("vote_count")
    private int voteCount;
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;

    public Movie(@Nullable String posterPath, boolean adult, @NonNull String overview, @NonNull String releaseDate, @NonNull List<Integer> genreIds, int id, @NonNull String originalTitle, @NonNull String originalLanguage, @NonNull String title, @Nullable String backdropPath, float popularity, int voteCount, boolean video, float voteAverage)
    {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    @Nullable
    public String getPosterPath()
    {
        return posterPath;
    }

    public boolean isAdult()
    {
        return adult;
    }

    @NonNull
    public String getOverview()
    {
        return overview;
    }

    @NonNull
    public String getReleaseDate()
    {
        return releaseDate;
    }

    @NonNull
    public List<Integer> getGenreIds()
    {
        return genreIds;
    }

    public int getId()
    {
        return id;
    }

    @NonNull
    public String getOriginalTitle()
    {
        return originalTitle;
    }

    @NonNull
    public String getOriginalLanguage()
    {
        return originalLanguage;
    }

    @NonNull
    public String getTitle()
    {
        return title;
    }

    @Nullable
    public String getBackdropPath()
    {
        return backdropPath;
    }

    public float getPopularity()
    {
        return popularity;
    }

    public int getVoteCount()
    {
        return voteCount;
    }

    public boolean isVideo()
    {
        return video;
    }

    public float getVoteAverage()
    {
        return voteAverage;
    }

    @BindingAdapter("poster")
    public static void loadPoster(ImageView view, String path)
    {
        Glide.with(view.getContext())
                .load(Constants.IMAGE_URL_POSTER + path)
                .apply(new RequestOptions().placeholder(R.drawable.ic_refresh_black_24dp).error(R.drawable.ic_warning_black_24dp))
                .into(view);
    }

    @BindingAdapter("backdrop")
    public static void loadBackdrop(ImageView view, String path)
    {
        Glide.with(view.getContext())
                .load(Constants.IMAGE_URL_BACKDROP + path)
                .apply(new RequestOptions().placeholder(R.drawable.ic_refresh_black_24dp).error(R.drawable.ic_warning_black_24dp).centerCrop())
                .into(view);
    }
}
