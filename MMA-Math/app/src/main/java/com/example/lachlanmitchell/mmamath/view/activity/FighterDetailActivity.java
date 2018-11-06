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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lachlanmitchell.mmamath.BuildConfig;
import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.interfaces.FighterDetailsView;
import com.example.lachlanmitchell.mmamath.model.FighterDetails;
import com.example.lachlanmitchell.mmamath.presenter.FighterDetailsPresenter;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


/**
 * Displays the detailed stats of each fighter including striking and grappling
 */

public class FighterDetailActivity extends AppCompatActivity implements FighterDetailsView{

    // Variables to implement model and presenter in the view
    private FighterDetails mFighterDetails;
    private FighterDetailsPresenter mFighterDetailsPresenter;
    private String mFighterName;
    private String mDivisionName;
    // Text views for the statistics
    @BindView(R.id.image_view_fighter_detail) ImageView mFighterImageView;
    @BindView(R.id.text_view_fighter_detail_slpm) TextView mSigStrikesLandedTv;
    @BindView(R.id.text_view_fighter_detail_strk_acc) TextView mStrkAccTv;
    @BindView(R.id.text_view_fighter_detail_sapm) TextView mStrkAttTv;
    @BindView(R.id.text_view_fighter_detail_strk_def) TextView mStrkDefTv;
    @BindView(R.id.text_view_fighter_detail_td_avg) TextView mTdAvgTv;
    @BindView(R.id.text_view_fighter_detail_td_acc) TextView mTdAccTv;
    @BindView(R.id.text_view_fighter_detail_td_def) TextView mTdDefTv;
    @BindView(R.id.text_view_fighter_detail_sub_avg) TextView mSubAvgTv;
    @BindView(R.id.fighter_activity_flip_btn) Button mReturnBtn;
    @BindView(R.id.fighter_detail_activity_legend_btn) Button mLegendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighter_detail);
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        Intent intent = getIntent();
        mFighterName = intent.getStringExtra(Constants.FIGHTER_NAME);
        mDivisionName = intent.getStringExtra(Constants.DIVISION_NAME);
        Timber.d("Fighter name is ", mFighterName);

        getSupportActionBar().setTitle(mDivisionName + " " + mFighterName.toUpperCase());


        mFighterDetails = new FighterDetails();
        mFighterDetailsPresenter = new FighterDetailsPresenter(mFighterDetails, mFighterName);
        mFighterDetailsPresenter.bind(this);
        // bind all UI elements
        ButterKnife.bind(this);

    }

    /**
     * Updates the UI with the detailed statistics
     * @param fighterMap
     */

    public void updateUI(Map<String, String> fighterMap) {
        //mFighterNameTv.setText(fighterMap.get("Name"));
        Glide.with(getApplicationContext()).load(fighterMap.get("Image URL")).into(mFighterImageView);
        mSigStrikesLandedTv.setText(fighterMap.get("SLpM"));
        mStrkAccTv.setText(fighterMap.get("StrAcc").substring(2) + getString(R.string.percent));
        mStrkAttTv.setText(fighterMap.get("SApM"));
        mStrkDefTv.setText(fighterMap.get("StrDef").substring(2) + getString(R.string.percent));
        mTdAvgTv.setText(fighterMap.get("TD Avg"));// + getString(R.string.percent));
        mTdAccTv.setText(fighterMap.get("TD Acc").substring(2) + getString(R.string.percent));
        mTdDefTv.setText(fighterMap.get("TD Def").substring(2)  + getString(R.string.percent));
        mSubAvgTv.setText(fighterMap.get("SubAvg")); //.substring(2) + getString(R.string.percent));
        Timber.d("Print stats" + mSigStrikesLandedTv.toString());
    }

    /**
     * Handles legend button click
     */

    @OnClick(R.id.fighter_detail_activity_legend_btn)
    public void viewLegend() {
        Intent statisticsLegendIntent = new Intent(FighterDetailActivity.this, LegendActivity.class);
        FighterDetailActivity.this.startActivity(statisticsLegendIntent);
        Timber.d("LEGEND_CALLED");
    }
}
