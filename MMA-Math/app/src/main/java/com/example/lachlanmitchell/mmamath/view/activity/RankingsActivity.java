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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lachlanmitchell.mmamath.BuildConfig;
import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.view.fragment.CardContainerFragment;
import com.example.lachlanmitchell.mmamath.interfaces.RankingsView;
import com.example.lachlanmitchell.mmamath.model.UfcRank;
import com.example.lachlanmitchell.mmamath.presenter.RankingsPresenter;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Displays the top ten ranked fighters of the organization
 */


public class RankingsActivity extends AppCompatActivity implements RankingsView {

    private TextView[] mRankingsTv = new TextView[Constants.FIGHTER_RANKINGS];
    @BindView(R.id.progress_bar_rankings) ProgressBar mRankingsPb;
    @BindView(R.id.home_return_fab) FloatingActionButton mHomeReturnFab;
    private String mDivisionVal;
    private String mOrgVal;
    private View mView;
    private UfcRank mUfcRank;
    private RankingsPresenter mRankingsPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Timber.d("ON_CREATE");

        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_rankings);
        mView = (View) findViewById(R.id.rankings_scroll_view);


        Intent intent = getIntent();
        mOrgVal = intent.getStringExtra(Constants.ORGANIZATION);
        mDivisionVal = intent.getStringExtra(Constants.DIVISION);


        mUfcRank = new UfcRank();
        mRankingsPresenter = new RankingsPresenter(mUfcRank, switchDivision(mDivisionVal));
        mRankingsPresenter.bind(this);


        getSupportActionBar().setTitle(mDivisionVal + " " + mRankingsPresenter.convertToPounds(mDivisionVal) + " " + getString(R.string.division_title));

        // bind all UI elements
        ButterKnife.bind(this);
        mView.setVisibility(View.INVISIBLE);


        // text view array to hold current rankings
        mRankingsTv[0] = (TextView) findViewById(R.id.text_view_champ);
        mRankingsTv[1] = (TextView) findViewById(R.id.text_view_first);
        mRankingsTv[2] = (TextView) findViewById(R.id.text_view_second);
        mRankingsTv[3] = (TextView) findViewById(R.id.text_view_third);
        mRankingsTv[4] = (TextView) findViewById(R.id.text_view_fourth);
        mRankingsTv[5] = (TextView) findViewById(R.id.text_view_fifth);
        mRankingsTv[6] = (TextView) findViewById(R.id.text_view_sixth);
        mRankingsTv[7] = (TextView) findViewById(R.id.text_view_seventh);
        mRankingsTv[8] = (TextView) findViewById(R.id.text_view_eighth);
        mRankingsTv[9] = (TextView) findViewById(R.id.text_view_ninth);
        mRankingsTv[10] = (TextView) findViewById(R.id.text_view_tenth);



        //mWeightClassTv.setText(mDivisionVal + " " + mRankingsPresenter.convertToPounds(mDivisionVal));

        // sets listeners for each text view when clicked to take to fighter profile
        for (int i = 0; i < Constants.FIGHTER_RANKINGS; i++) {
            mRankingsTv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFighterCard((TextView) view);
                }
            });
        }

    }

    /**
     * Switches division to display relevant rankings
     * @param divisionVal
     * @return
     */

    public String switchDivision(String divisionVal) {
        mDivisionVal = divisionVal;
        String selectionData = "";
        switch (mDivisionVal) {
            case Constants.FLY_DIVISION:
                selectionData = Constants.FLY_DIVISION;
                break;
            case Constants.BAN_DIVISION:
                selectionData = Constants.BAN_DIVISION;
                break;
            case Constants.FTW_DIVISION:
                selectionData = Constants.FTW_DIVISION;
                break;
            case Constants.LW_DIVISION:
                selectionData = Constants.LW_DIVISION;
                break;
            case Constants.WW_DIVISION:
                selectionData = Constants.WW_DIVISION;
                break;
            case Constants.MW_DIVISION:
                selectionData = Constants.MW_DIVISION;
                break;
            case Constants.LHW_DIVISION:
                selectionData = Constants.LH_DIVISION;
                break;
            case Constants.HW_DIVISION:
                selectionData = Constants.HW_DIVISION;
                break;

        }
        return selectionData;
    }


    /**
     * Takes in a fighter name and opens their respective card
     *
     * @param fighterClicked
     */
    public void openFighterCard(TextView fighterClicked) {
        mRankingsPb.setVisibility(View.VISIBLE);

        Intent fighterIntent = new Intent(RankingsActivity.this, CardContainerFragment.class);
        String fighterName = fighterClicked.getText().toString();
        fighterIntent.putExtra(Constants.FIGHTER_NAME, fighterName);
        fighterIntent.putExtra(Constants.DIVISION, mDivisionVal);
        fighterIntent.putExtra(Constants.ORGANIZATION, mOrgVal);
        RankingsActivity.this.startActivity(fighterIntent);
    }

    /**
     * Takes in a rankings list from the model and updates the activity UI
     *
     * @param rankingsList
     */
    @Override
    public void updateUI(ArrayList<String> rankingsList) {
        mRankingsPb.setVisibility(View.VISIBLE);
        Timber.d("CALLED_UPDATE_UI - SIZE: " + rankingsList.size());
            for (int i = 0; i < 11; i++) {
                if (i == 0) {
                    mRankingsTv[i].setText(getString(R.string.champ) + " " + rankingsList.get(i));
                } else {
                    mRankingsTv[i].setText(getString(R.string.fighter_rank) + i  + " " + rankingsList.get(i));
                }
            }
            mView.setVisibility(View.VISIBLE);
            mRankingsPb.setVisibility(View.INVISIBLE);

    }

    @OnClick(R.id.home_return_fab)
    public void returnHome() {
        Intent returnHome = new Intent(this, MainActivity.class);
        returnHome.putExtra(Constants.DIVISION, mDivisionVal);
        startActivity(returnHome);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRankingsPb.getVisibility() == View.VISIBLE){
            mRankingsPb.setVisibility(View.GONE);
        }
    }

    /**
     *  Handles navigating with the back button
     */

    public void onBackPressed() {
        Timber.d("Back pressed");
        Intent backToMainAct = new Intent(this, MainActivity.class);
        backToMainAct.putExtra(Constants.DIVISION, mDivisionVal);
        startActivity(backToMainAct);
        finish();
    }
}
