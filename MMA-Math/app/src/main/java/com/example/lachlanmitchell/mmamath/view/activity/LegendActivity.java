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

package com.example.lachlanmitchell.mmamath.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lachlanmitchell.mmamath.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Displays the legend explaining the statstics and how they're gathered
 */

public class LegendActivity extends AppCompatActivity {

    @BindView(R.id.text_view_fighter_detail_slpm) TextView slpmTv;
    @BindView(R.id.text_view_fighter_detail_strk_acc) TextView strkAccTv;
    @BindView(R.id.text_view_fighter_detail_sapm) TextView sapmTv;
    @BindView(R.id.text_view_fighter_detail_strk_def) TextView strkDefTv;
    @BindView(R.id.text_view_fighter_detail_td_avg) TextView tdAvgTv;
    @BindView(R.id.text_view_fighter_detail_td_acc) TextView tdAccTv;
    @BindView(R.id.text_view_fighter_detail_td_def) TextView tdDefTv;
    @BindView(R.id.text_view_fighter_detail_sub_avg) TextView subAvgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_legend);

        Intent intent = getIntent();

        ButterKnife.bind(this);
    }

}
