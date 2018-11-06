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

package com.example.lachlanmitchell.mmamath.presenter;


import android.widget.ProgressBar;

import com.example.lachlanmitchell.mmamath.view.activity.RankingsActivity;
import com.example.lachlanmitchell.mmamath.model.UfcRank;
import com.example.lachlanmitchell.mmamath.utils.Constants;

/**
 * Interacts with the rankings activity
 */

public class RankingsPresenter {
    private String mDivision;
    private UfcRank mUfcRank;
    private RankingsActivity mRankingActivity;
    private ProgressBar mProgressBar;

    /**
     * Constructs the presenter and retrieves rankings
     * @param ufcRank
     * @param division
     */

    public RankingsPresenter(UfcRank ufcRank, String division) {
        mUfcRank = ufcRank;
        mDivision = division;
        //mProgressBar = progressBar;
        mUfcRank.retrieveRanking(division);
        mUfcRank.setOnRankingPresenterListener(new onRankingPresenterListener() {
            @Override
            public void onFinish() {
                mRankingActivity.updateUI(mUfcRank.mUfcRanklist);
            }
        });
    }

    /**
     * Calls the on finish listener once a response is received
     */

    public interface onRankingPresenterListener {
        void onFinish();
    }

    /*public void setProgressBarVisiblity(int visibility) {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(visibility);
    }*/

    /**
     * Converts weight division name to its numeric weight limit
     * @param division
     * @return
     */

    public String convertToPounds(String division) {
        String divisionPoundLimit = "";
        switch (division) {
            case Constants.FLY_DIVISION:
                divisionPoundLimit = Constants.FLY_POUNDS;
                break;
            case Constants.BAN_DIVISION:
                divisionPoundLimit = Constants.BAN_POUNDS;
                break;
            case Constants.FTW_DIVISION:
                divisionPoundLimit = Constants.FTW_POUNDS;
                break;
            case Constants.LW_DIVISION:
                divisionPoundLimit = Constants.LW_POUNDS;
                break;
            case Constants.WW_DIVISION:
                divisionPoundLimit = Constants.WW_POUNDS;
                break;
            case Constants.MW_DIVISION:
                divisionPoundLimit = Constants.MW_POUNDS;
                break;
            case Constants.LHW_DIVISION:
                divisionPoundLimit = Constants.LHW_POUNDS;
                break;
            case Constants.HW_DIVISION:
                divisionPoundLimit = Constants.HW_POUNDS;
                break;
        }
        return divisionPoundLimit;
    }

    /**
     * Binds presenter to activity
     * @param view
     */

    public void bind(RankingsActivity view) {
        this.mRankingActivity = view;
    }

    /**
     * Unbinds presenter from activity
     */
    public void unbind() {
        mRankingActivity = null;
    }
}
