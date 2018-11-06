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

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.view.activity.RankingsActivity;
import com.example.lachlanmitchell.mmamath.model.Fighter;
import com.example.lachlanmitchell.mmamath.presenter.FighterPresenter;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Handles the animations as the container for side A to side B
 */

public class CardContainerFragment extends FragmentActivity {

    private Fighter mFighter;
    private FighterPresenter mFighterPresenter;
    private CardAFragment mCardAFragment;
    private CardBFragment mCardBFragment;
    private LegendFragment mLegendFragment;
    private Map<String, String> globalData;
    private String mDivisionName;
    private String mOrgName;


    //card flip status
    private Boolean mFlipped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card_container);

        Intent intent = getIntent();
        String fighterName = intent.getStringExtra(Constants.FIGHTER_NAME);
        mDivisionName = intent.getStringExtra(Constants.DIVISION);
        mOrgName = intent.getStringExtra(Constants.ORGANIZATION);

        ButterKnife.bind(this);

        mFighter = new Fighter();
        mFighterPresenter = new FighterPresenter(mFighter, fighterName);
        mFighterPresenter.bind(this);



        //programmatically add fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mCardAFragment = new CardAFragment();
        mCardBFragment = new CardBFragment();
        mLegendFragment = new LegendFragment();
        fragmentTransaction.add(R.id.fragment_container, mCardAFragment).commit();

    }

    /**
     * Updates the UI with fighter's statistics
     * @param fighterMap
     */

    public void updateUI(Map<String, String> fighterMap) {
        globalData = fighterMap;
        mCardAFragment.mFighterData = globalData;
        mCardAFragment.updateUI(globalData);

    }

    /**
     * Displays the legend fragment which articulates statistics and how they're captured
     */

    public void showLegend(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mLegendFragment).commit();
    }

    /**
     * Displays the card again after returning from the legend fragment
     */

    public void reshowCards() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mCardBFragment).commit();

    }

    /**
     *  Returns user back to the rankings page of their selected division
     */

    public void reshowRankings() {
        Timber.d("Back pressed");
        Intent backToRankAct = new Intent(this, RankingsActivity.class);
        backToRankAct.putExtra(Constants.DIVISION, mDivisionName);
        backToRankAct.putExtra(Constants.ORGANIZATION, mOrgName);
        startActivity(backToRankAct);
    }

    /**
     * Flips the card between side A and side B
     */

    public void flipCard() {
        if (mFlipped) {
            getFragmentManager().popBackStack();
            mFlipped = false;
            return;
        }

        // Flip to the back.

        mFlipped = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources representing
                // rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when
                // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                // Replace any fragments currently in the container view with a fragment
                // representing the next page (indicated by the just-incremented currentPage
                // variable).
                .replace(R.id.fragment_container, mCardBFragment)

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

                //mCardBFragment.updateUI(globalData);

    }

    /**
     *  Returns global data shared between cards
     */

    public Map<String, String> getGlobalData(){
        return globalData;
    }

}
