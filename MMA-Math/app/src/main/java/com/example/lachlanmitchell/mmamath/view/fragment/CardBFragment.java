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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.model.FighterDetails;
import com.example.lachlanmitchell.mmamath.presenter.FighterDetailsPresenter;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Displays the fighter's more detailed stats on the front of the card
 */

public class CardBFragment extends Fragment {

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
    @BindView(R.id.fighter_activity_flip_btn) Button mFlipButton;
    @BindView(R.id.fighter_detail_activity_legend_btn) Button mLegendBtn;

    private Map<String, String> mFighterData = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_card_b, container, false);

        ButterKnife.bind(this, v);

        mFlipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CardContainerFragment)getActivity()).flipCard();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFighterData = ((CardContainerFragment)getActivity()).getGlobalData();

        if(mFighterData != null){
            executeUIChange();

        }

    }

    /**
     * Takes in a fighter map from the model and updates the activity UI
     * @param fighterMap
     */

    public void updateUI(Map<String, String> fighterMap) {

        Log.d("TAG", "CALLED_UPDATE_A1");

        //this is called by presenter
        mFighterData = fighterMap;

    }

    /**
     * Executes UI changes once data arrives
     */

    public void executeUIChange(){

        Glide.with(getActivity()).load(mFighterData.get("Image URL")).into(mFighterImageView);
        mSigStrikesLandedTv.setText(mFighterData.get("SLpM"));
        mStrkAccTv.setText(mFighterData.get("StrAcc").substring(2) + getString(R.string.percent));
        mStrkAttTv.setText(mFighterData.get("SApM"));
        mStrkDefTv.setText(mFighterData.get("StrDef").substring(2) + getString(R.string.percent));
        mTdAvgTv.setText(mFighterData.get("TD Avg"));// + getString(R.string.percent));
        mTdAccTv.setText(mFighterData.get("TD Acc").substring(2) + getString(R.string.percent));
        mTdDefTv.setText(mFighterData.get("TD Def").substring(2)  + getString(R.string.percent));
        mSubAvgTv.setText(mFighterData.get("SubAvg"));


    }

    /**
     * Displays the legend fragment
     */

    @OnClick(R.id.fighter_detail_activity_legend_btn)
    public void viewLegend() {
        ((CardContainerFragment)getActivity()).showLegend();
    }

}
