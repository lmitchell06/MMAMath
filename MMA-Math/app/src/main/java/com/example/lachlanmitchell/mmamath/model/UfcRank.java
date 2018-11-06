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

import android.util.Log;

import com.example.lachlanmitchell.mmamath.presenter.FighterPresenter;
import com.example.lachlanmitchell.mmamath.presenter.RankingsPresenter;
import com.example.lachlanmitchell.mmamath.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Model class that takes the fighter's name and url to complete the rankings list
 */

public class UfcRank {

    public ArrayList<String> mUfcRanklist = new ArrayList<>();
    private RankingsPresenter.onRankingPresenterListener mOnRankingPresenterListener;

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;

    public UfcRank() { }

    /**
     * Calls the on event listener once a response is received
     */
    public interface OnRankingEventListener {
        void onEvent();
    }

    /**
     * Retrieves list of rankings
     * @param division
     */

    public void retrieveRanking(String division){

        final UfcRanking ufcRanking = new UfcRanking(division);

        ufcRanking.setOnRankingEventListener(new OnRankingEventListener() {
            @Override
            public void onEvent() {
                mUfcRanklist = ufcRanking.mRankingsList;
                mOnRankingPresenterListener.onFinish();

            }
        });
    }

    /**
     * Sets ranking presenter listener to interact with activity
     * @param onRankingEventListener
     */

    public void setOnRankingPresenterListener(RankingsPresenter.onRankingPresenterListener onRankingEventListener){
        this.mOnRankingPresenterListener = onRankingEventListener;
    }

}



