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

package com.example.lachlanmitchell.mmamath.model;

import com.example.lachlanmitchell.mmamath.database.FirebaseResult;
import com.example.lachlanmitchell.mmamath.interfaces.FighterContract;
import com.example.lachlanmitchell.mmamath.interfaces.FirebaseQuery;
import com.example.lachlanmitchell.mmamath.presenter.FighterDetailsPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Model class with the fighters more detailed statistics
 */

public class FighterDetails implements FighterContract, FirebaseQuery{

    // Fighter statistics for side B of card

    public Map<String, String> mFighterDetailsMap = new HashMap<String, String>();
    private FighterDetailsPresenter.onFighterDetailsPresenterListener mOnFighterDetailsPresenterListener;

    /**
     * No arguments constructor
     */
    public FighterDetails() { }

    /**
     * Calls the on event listener once a query response is received
     */
    public interface OnFighterDetailsEventListener {
        void onEvent();
    }

    /**
     * Retrieves results from firebase depending on fighter name passed in
     * @param fighterName
     */
    public void retrieveFirebase(String fighterName) {
        // Call DB
        final FirebaseResult firebaseResult = new FirebaseResult(fighterName);

        firebaseResult.setOnFighterPresenterListener(new Fighter.OnFighterEventListener() {
            @Override
            public void onEvent() {
                mFighterDetailsMap = firebaseResult.mFighterDataMap;
                mOnFighterDetailsPresenterListener.onFinish();
            }
        });
    }

    /**
     * Sets fighter presenter listener to interact with activity
     * @param onFighterPresenterListener
     */
    public void setOnFighterDetailsPresenterListener(FighterDetailsPresenter.onFighterDetailsPresenterListener onFighterPresenterListener){
        this.mOnFighterDetailsPresenterListener = onFighterPresenterListener;
    }

}
