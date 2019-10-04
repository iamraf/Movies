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

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.h01d.movies.R;
import com.github.h01d.movies.data.model.Movie;
import com.github.h01d.movies.databinding.FragmentMoviesBinding;

public class MoviesFragment extends Fragment implements MoviesAdapter.MovieAdapterListener
{
    private FragmentMoviesBinding mDataBinding;
    private MoviesViewModel mViewModel;

    private MoviesAdapter mAdapter;

    public MoviesFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        mAdapter = new MoviesAdapter(this);

        mDataBinding.fMoviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.fMoviesRecycler.setHasFixedSize(true);
        mDataBinding.fMoviesRecycler.setAdapter(mAdapter);

        mDataBinding.fMoviesSwipe.setOnRefreshListener(() -> mViewModel.refresh());

        return mDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new MoviesViewModelFactory(MoviesFragmentArgs.fromBundle(getArguments()).getPath())).get(MoviesViewModel.class);

        mViewModel.getMoviesPagedList().observe(getViewLifecycleOwner(), movies ->
        {
            mAdapter.submitList(movies);
            mDataBinding.fMoviesRecycler.setVisibility(View.VISIBLE);
        });

        mViewModel.getInitialLoading().observe(getViewLifecycleOwner(), aBoolean ->
        {
            if(aBoolean)
            {
                if(mAdapter.getItemCount() == 0)
                {
                    mDataBinding.fMoviesRecycler.setVisibility(View.GONE);
                    mDataBinding.fMoviesError.setVisibility(View.GONE);

                    mDataBinding.fMoviesProgress.setVisibility(View.VISIBLE);

                    mDataBinding.fMoviesSwipe.setRefreshing(false);
                }
                else
                {
                    mDataBinding.fMoviesError.setVisibility(View.GONE);
                }
            }
            else
            {
                mDataBinding.fMoviesProgress.setVisibility(View.GONE);

                mDataBinding.fMoviesSwipe.setRefreshing(false);
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), s ->
        {
            mDataBinding.fMoviesError.setText(s);
            mDataBinding.fMoviesError.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onClicked(Movie movie)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        bundle.putString("title", movie.getTitle());

        Navigation.findNavController(mDataBinding.getRoot()).navigate(R.id.detailsFragment, bundle);
    }
}
