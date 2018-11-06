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

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lachlanmitchell.mmamath.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Displays the fighter's generic stats on the front of the card
 */

public class CardAFragment extends Fragment {
    // UI fields
    @BindView(R.id.image_view_fighter) ImageView mFighterImage;
    @BindView(R.id.text_view_fighter_activity_name) TextView mFighterNameTv;
    @BindView(R.id.text_view_fighter_activity_record) TextView mFighterRecordTv;
    @BindView(R.id.text_view_fighter_activity_height) TextView mFighterHeightTv;
    @BindView(R.id.text_view_fighter_activity_weight) TextView mFighterWeightTv;
    @BindView(R.id.text_view_fighter_activity_reach) TextView mFighterReachTv;
    @BindView(R.id.text_view_fighter_activity_stance) TextView mFighterStanceTv;
    @BindView(R.id.fighter_activity_flip_btn) Button mFlipButton;
    @BindView(R.id.ranking_return_fab) FloatingActionButton mReturnToRankingsFab;
    private View mCardAView;

    public Map<String, String> mFighterData = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mCardAView =  inflater.inflate(R.layout.fragment_card_a, container, false);

        ButterKnife.bind(this, mCardAView);

        //flipButton functionality
        mFlipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CardContainerFragment)getActivity()).flipCard();
            }
        });

        mCardAView.setVisibility(View.GONE);

        return mCardAView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //data = ((CardContainerFragment)getActivity()).getGlobalData();

        if(mFighterData != null){
            executeUIChange();
        }

    }

    /**
     * Takes in a fighter map from the model and updates the activity UI
     *
     * @param fighterMap
     */

    public void updateUI(Map<String, String> fighterMap) {
        mFighterData = fighterMap;
        executeUIChange();

    }

    /**
     * Executes UI changes once data arrives
     */

    public void executeUIChange(){
        mFighterNameTv.setText(mFighterData.get("Name"));
        mFighterRecordTv.setText(mFighterData.get("Record"));
        mFighterHeightTv.setText(mFighterData.get("Height"));
        mFighterWeightTv.setText(mFighterData.get("Weight") + " " + getString(R.string.pounds));
        mFighterReachTv.setText(mFighterData.get("Reach") + getString(R.string.inches));
        mFighterStanceTv.setText(mFighterData.get("Stance"));
        Glide.with(getActivity()).load(mFighterData.get("Image URL")).into(mFighterImage);

        mCardAView.setVisibility(View.VISIBLE);

    }

    /**
     * Returns user back to previous rankings activity
     */

    @OnClick(R.id.ranking_return_fab)
    public void backToRankings() {
        Timber.d("CALLED_BACK_TO_RANKINGS");
        ((CardContainerFragment)getActivity()).reshowRankings();
    }

}
