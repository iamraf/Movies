/*
 * Copyright (C) 2019-2020 Raf.
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

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.github.h01d.movies.data.model.Movie;
import com.github.h01d.movies.databinding.MovieItemBinding;

public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>
{
    private MovieAdapterListener listener;

    public MoviesAdapter(MovieAdapterListener listener)
    {
        super(COMPARATOR);

        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MovieItemBinding binding = MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        holder.bind(getItem(position));
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
    {
        private final MovieItemBinding binding;

        MovieViewHolder(@NonNull MovieItemBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(Movie item)
        {
            binding.getRoot().setOnClickListener(v -> listener.onClicked(binding.getMovie()));
            binding.setMovie(item);
            binding.executePendingBindings();
        }
    }

    interface MovieAdapterListener
    {
        void onClicked(Movie movie);
    }

    private static final DiffUtil.ItemCallback<Movie> COMPARATOR = new DiffUtil.ItemCallback<Movie>()
    {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem)
        {
            return oldItem == newItem;
        }
    };
}
