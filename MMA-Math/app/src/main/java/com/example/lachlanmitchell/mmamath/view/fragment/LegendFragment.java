/*
 * Copyright 2017 The Android Open Source Project
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

package com.example.lachlanmitchell.mmamath.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lachlanmitchell.mmamath.BuildConfig;
import com.example.lachlanmitchell.mmamath.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Displays the legend explaining the statistics and how they're gathered
 */

public class LegendFragment extends Fragment {

    @BindView(R.id.legend_return_fab)FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());


        View v = inflater.inflate(R.layout.fragment_legend, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    /**
     * Takes the user back to the card they were viewing
     */

    @OnClick(R.id.legend_return_fab)
    public void backToFighter() {
        Timber.d("CALLED_BACK_TO_FIGHTER");

        ((CardContainerFragment)getActivity()).reshowCards();
    }
}
