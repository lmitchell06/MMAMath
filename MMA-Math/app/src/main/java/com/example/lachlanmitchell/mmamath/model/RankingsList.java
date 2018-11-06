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

import com.example.lachlanmitchell.mmamath.model.UfcRank;
import com.example.lachlanmitchell.mmamath.model.UfcRanking;
import com.example.lachlanmitchell.mmamath.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotates the division names for use with Gson
 */

public class RankingsList {

    @SerializedName("FLYWEIGHT")
    public List<UfcRank> mUfcFlyweights;
    @SerializedName("BANTAMWEIGHT")
    public List<UfcRank> mUfcBantamweights;
    @SerializedName("FEATHERWEIGHT")
    public List<UfcRank> mUfcFeatherweights;
    @SerializedName("LIGHTWEIGHT")
    public List<UfcRank> mUfcLightweights;
    @SerializedName("WELTERWEIGHT")
    public List<UfcRank> mUfcWelterweights;
    @SerializedName("MIDDLEWEIGHT")
    public List<UfcRank> mUfcMiddleweights;
    @SerializedName("LIGHTHEAVYWEIGHT")
    public List<UfcRank> mUfCLightheavyweights;
    @SerializedName("HEAVYWEIGHT")
    public List<UfcRank> mUfcHeavyweights;

    /**
     * Returns the list of fighters in that division based off retrofit callback
     * @param division
     * @return
     */
    public List<UfcRank> getRanks(String division) {
        List<UfcRank> emptyList = null;
        switch (division) {
            case Constants.FLY_DIVISION:
                return mUfcFlyweights;
            case Constants.BAN_DIVISION:
                return mUfcBantamweights;
            case Constants.FTW_DIVISION:
                return mUfcFeatherweights;
            case Constants.LW_DIVISION:
                return mUfcLightweights;
            case Constants.WW_DIVISION:
                return mUfcWelterweights;
            case Constants.MW_DIVISION:
                return mUfcMiddleweights;
            case Constants.LH_DIVISION:
                return mUfCLightheavyweights;
            case Constants.HW_DIVISION:
                return mUfcHeavyweights;
        }
        return emptyList;
    }
}
