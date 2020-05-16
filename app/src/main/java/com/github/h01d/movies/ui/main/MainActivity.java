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

package com.github.h01d.movies.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.h01d.movies.R;
import com.github.h01d.movies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mDataBinding.aMainToolbar.setTitle("Now Playing Movies");
        mDataBinding.aMainToolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(mDataBinding.aMainToolbar);

        NavController navController = Navigation.findNavController(this, R.id.a_main_host);
        navController.addOnDestinationChangedListener((controller, destination, arguments) ->
        {
            switch(destination.getId())
            {
                case R.id.nowPlayingMovies:
                case R.id.popularMovies:
                case R.id.topMovies:
                    mDataBinding.aMainBottom.setVisibility(View.VISIBLE);
                    break;
                case R.id.detailsFragment:
                    mDataBinding.aMainBottom.setVisibility(View.GONE);
                    break;
            }
        });

        NavigationUI.setupWithNavController(mDataBinding.aMainToolbar, navController);
        NavigationUI.setupWithNavController(mDataBinding.aMainToolbar, navController, new AppBarConfiguration.Builder(R.id.nowPlayingMovies, R.id.popularMovies, R.id.topMovies).build());
        NavigationUI.setupWithNavController(mDataBinding.aMainBottom, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.m_main_about)
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Movies")
                    .setMessage("Icons made by Freepik from www.flaticon.com\n\nPowered by TMDb.")
                    .setNegativeButton("Close", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
