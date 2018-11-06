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

import com.example.lachlanmitchell.mmamath.view.fragment.CardContainerFragment;
import com.example.lachlanmitchell.mmamath.model.Fighter;

import timber.log.Timber;

/**
 * Implements logic behind generic fighter statistics
 */

public class FighterPresenter {

    private String mFighterName;
    private Fighter mFighter;
    private CardContainerFragment mCardContainerFragment;

    /**
     * Constructs the presenter and retrieves database response
     * @param fighter
     * @param fighterName
     */
    public FighterPresenter(Fighter fighter, String fighterName) {

        Timber.d("OPENED_FIGHTER_PRESENTER");

        mFighter = fighter;
        mFighterName = fighterName;
        mFighter.retrieveFirebase(mFighterName);
        mFighter.setOnFighterPresenterListener(new onFighterPresenterListener() {
            @Override
            public void onFinish() {
                Timber.d("DATA_READY");
                mCardContainerFragment.updateUI(mFighter.mMap);
            }
        });

    }

    /**
     * Calls the on finish listener once stats are received
     */
    public interface onFighterPresenterListener {
        void onFinish();
    }

    /**
     * Binds presenter to activity
     * @param fighterActivity
     */
    public void bind(CardContainerFragment fighterActivity) {
        this.mCardContainerFragment = fighterActivity;
    }

    /**
     * Unbinds presenter from activity
     */

    public void unbind() {
        mCardContainerFragment = null;
    }

}
