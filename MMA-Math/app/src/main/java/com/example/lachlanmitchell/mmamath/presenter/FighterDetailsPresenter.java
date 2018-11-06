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

import com.example.lachlanmitchell.mmamath.view.activity.FighterDetailActivity;
import com.example.lachlanmitchell.mmamath.model.FighterDetails;

/**
 * Implements logic behind detailed fighter statistics
 */

public class FighterDetailsPresenter {

    private String mFighterName;
    private FighterDetails mFighterDetails;
    private FighterDetailActivity mFighterDetailsActivity;

    /**
     * Constructs the presenter and retrieves database response
     * @param fighterDetails
     * @param fighterName
     */
    public FighterDetailsPresenter(FighterDetails fighterDetails, String fighterName) {
        mFighterDetails = fighterDetails;
        mFighterName = fighterName;
        mFighterDetails.retrieveFirebase(mFighterName);
        mFighterDetails.setOnFighterDetailsPresenterListener(new onFighterDetailsPresenterListener() {
            @Override
            public void onFinish() {
                mFighterDetailsActivity.updateUI(mFighterDetails.mFighterDetailsMap);
            }
        });

    }

    /**
     * Calls the on finish listener once stats are received
     */

    public interface onFighterDetailsPresenterListener {
        void onFinish();
    }

    /**
     * Binds presenter to activity
     * @param fighterDetailActivity
     */

    public void bind(FighterDetailActivity fighterDetailActivity) {
        this.mFighterDetailsActivity = fighterDetailActivity;
    }

    /**
     * Unbinds presenter from activity
     */

    public void unbind() {
        mFighterDetailsActivity = null;
    }
}
