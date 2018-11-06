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

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lachlanmitchell.mmamath.BuildConfig;
import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.interfaces.FighterView;
import com.example.lachlanmitchell.mmamath.model.Fighter;
import com.example.lachlanmitchell.mmamath.presenter.FighterPresenter;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Displays the relevant stats to a unique fighter
 */

public class FighterActivity extends AppCompatActivity implements FighterView{

    private Fighter mFighter;
    private FighterPresenter mFighterPresenter;
    private String mFighterName;
    private String mDivisionName;
    private String mOrgName;

    @BindView(R.id.image_view_fighter) ImageView mFighterImage;
    @BindView(R.id.text_view_fighter_activity_name) TextView mFighterNameTv;
    @BindView(R.id.text_view_fighter_activity_record) TextView mFighterRecordTv;
    @BindView(R.id.text_view_fighter_activity_height) TextView mFighterHeightTv;
    @BindView(R.id.text_view_fighter_activity_weight) TextView mFighterWeightTv;
    @BindView(R.id.text_view_fighter_activity_reach) TextView mFighterReachTv;
    @BindView(R.id.text_view_fighter_activity_stance) TextView mFighterStanceTv;
    @BindView(R.id.fighter_activity_flip_btn) Button mFlipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card_a);

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());


        Intent intent = getIntent();
        mFighterName = intent.getStringExtra(Constants.FIGHTER_NAME);
        mDivisionName = intent.getStringExtra(Constants.DIVISION_NAME);
        mOrgName = intent.getStringExtra(Constants.ORG_NAME);


        Timber.d("Fighter name is ", mFighterName);

        getSupportActionBar().setTitle(mDivisionName + " " + mFighterName.toUpperCase());

        mFighter = new Fighter();
        mFighterPresenter = new FighterPresenter(mFighter, mFighterName);
        //mFighterPresenter.bind(this);

        // bind all UI elements
        ButterKnife.bind(this);

    }

    /**
     * Handles flip button animation
     */

    @OnClick(R.id.fighter_activity_flip_btn)
    public void goToFighterDetail() {
        Intent fighterDetailIntent = new Intent(FighterActivity.this, FighterDetailActivity.class);
        fighterDetailIntent.putExtra(Constants.FIGHTER_NAME, mFighterName);
        fighterDetailIntent.putExtra(Constants.DIVISION_NAME, mDivisionName);

        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.from_middle);
        animator.setTarget(this);
        animator.setDuration(3000);
        animator.start();
        //overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        FighterActivity.this.startActivity(fighterDetailIntent);
    }

    /**
     * Updates the UI with fighter's general statistics
     * @param fighterMap
     */

    @Override
    public void updateUI(Map<String, String> fighterMap) {
        mFighterNameTv.setText(fighterMap.get("Nickname"));
        mFighterRecordTv.setText(fighterMap.get("Record"));
        mFighterHeightTv.setText(fighterMap.get("Height"));
        mFighterWeightTv.setText(fighterMap.get("Weight"));
        mFighterReachTv.setText(fighterMap.get("Reach") + getString(R.string.inches));
        mFighterStanceTv.setText(fighterMap.get("Stance"));
        Glide.with(getApplicationContext()).load(fighterMap.get("Image URL")).into(mFighterImage);
    }

}
